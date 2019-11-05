package pt.webdetails.cpf.packager.dependencies;

import junit.framework.TestCase;
import pt.webdetails.cpf.context.api.IUrlProvider;
import pt.webdetails.cpf.packager.origin.PathOrigin;
import pt.webdetails.cpf.repository.api.IContentAccessFactory;
import pt.webdetails.cpf.repository.api.IRWAccess;
import pt.webdetails.cpf.repository.api.IReadAccess;
import pt.webdetails.cpf.repository.api.IUserContentAccess;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileDependencyTest extends TestCase {

    public void testGetFileInputStreamNullContentFactory() throws IOException {
        FileDependencyForTest fdft = new FileDependencyForTest( "1.0", null, "/home/admin/t.js", true );
        assertNull( fdft.getFileInputStream() );
    }


    public void testGetFileInputStream() throws IOException {
        PathOrigin pathOrigin = mock (PathOrigin.class);
        IReadAccess readAccess = mock (IReadAccess.class);
        InputStream is = mock (InputStream.class);
        when( pathOrigin.getReader(anyObject()) ).thenReturn(readAccess);

        when (readAccess.getFileInputStream("/home/admin/t.js")).thenReturn(is);
        FileDependencyForTest fdft = new FileDependencyForTest( "1.0", pathOrigin, "/home/admin/t.js", false );
        assertEquals("Resource is correctly read from repository", is, fdft.getFileInputStream());

        fdft = new FileDependencyForTest( "1.0", pathOrigin, "/home/admins/t.js", false );
        assertNull("Different path fails to load resource", fdft.getFileInputStream());

    }
}

 class FileDependencyForTest extends FileDependency {

    private boolean returnNullContentFactory;

    public FileDependencyForTest(String version, PathOrigin origin, String path, boolean returnNullContentFactory ) {
        super(version, origin, path, new IUrlProvider() {
            @Override
            public String getPluginBaseUrl(String pluginId) {
                return null;
            }

            @Override
            public String getPluginBaseUrl() {
                return null;
            }

            @Override
            public String getPluginStaticBaseUrl(String pluginId) {
                return null;
            }

            @Override
            public String getPluginStaticBaseUrl() {
                return null;
            }

            @Override
            public String getRepositoryUrl(String fullPath) {
                return null;
            }

            @Override
            public String getWebappContextPath() {
                return null;
            }

            @Override
            public String getWebappContextRoot() {
                return null;
            }

            @Override
            public String getResourcesBasePath() {
                return null;
            }
        });
        this.returnNullContentFactory= returnNullContentFactory;

    }

    @Override
    protected IContentAccessFactory getContentFactory() {
        if (returnNullContentFactory) {
            return null;
        } else
            return new IContentAccessFactory() {
                @Override
                public IUserContentAccess getUserContentAccess(String basePath) {
                    return null;
                }

                @Override
                public IReadAccess getPluginRepositoryReader(String basePath) {
                    return null;
                }

                @Override
                public IRWAccess getPluginRepositoryWriter(String basePath) {
                    return null;
                }

                @Override
                public IReadAccess getPluginSystemReader(String basePath) {
                    return null;
                }

                @Override
                public IRWAccess getPluginSystemWriter(String basePath) {
                    return null;
                }

                @Override
                public IReadAccess getOtherPluginSystemReader(String pluginId, String basePath) {
                    return null;
                }

                @Override
                public IRWAccess getOtherPluginSystemWriter(String pluginId, String basePath) {
                    return null;
                }
            };
    }
}

