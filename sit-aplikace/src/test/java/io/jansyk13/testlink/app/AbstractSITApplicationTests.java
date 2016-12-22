package io.jansyk13.testlink.app;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Condition.post;
import static com.xebialabs.restito.semantics.Condition.withHeader;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.util.ResourceUtils;

import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.semantics.Call;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;

import net.javacrumbs.restfire.RequestBuilder;
import net.javacrumbs.restfire.RequestFactory;
import net.javacrumbs.restfire.RequestProcessor;
import net.javacrumbs.restfire.httpcomponents.HttpComponentsRequestFactory;

public abstract class AbstractSITApplicationTests {

    @LocalServerPort
    private int port;

    private static StubServer testLinkMocker;

    /**
     * Static block for starting mocker, adding first stub(due to application checking key on startup) and
     * shutdown hook
     */
    static {
        testLinkMocker = new StubServer(8091);
        testLinkMocker.start();
        whenTestLink()
                .match(
                        withHeader("content-type", "text/xml"),
                        post("/test"),
                        bodyEquals("xml/checkDevKey.xml")
                )
                .then(
                        ok(),
                        resourceContent("xml/checkDevKeyResponse.xml")
                );

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                testLinkMocker.stop();
            }
        });
    }

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * Static method for creating {@link RequestFactory}, useful for fluent API. Sets port and
     * content-type.
     */
    protected RequestFactory fire() {
        return new HttpComponentsRequestFactory(httpClient, new RequestProcessor() {
            @Override
            public void processRequest(RequestBuilder requestBuilder) {
                requestBuilder.withPort(port);
                requestBuilder.withHeader("content-type", "application/json");
            }
        });
    }

    /**
     * Clear stubs before each tests
     */
    @Before
    public void setUp() throws Exception {
        testLinkMocker.clear();
    }

    /**
     * Delegation to {@link StubHttp#whenHttp(StubServer)}
     */
    protected static StubHttp whenTestLink() {
        return whenHttp(testLinkMocker);
    }

    /**
     * Whitespace insensitive HTTP request body equals (could be trouble some parts of XML are whitespace sensitive)
     * @param path resource path
     */
    protected static Condition bodyEquals(String path) {
        String resourceAsString = getResourceAsString(path);
        return Condition.custom((Call input) -> input.getPostBody().replaceAll("\\s+", "").equalsIgnoreCase(resourceAsString.replaceAll("\\s+", "")));
    }

    /**
     * Locates file on classpath and reads it to String
     * @param fullPathOnClassPath resource path
     * @return String content of file
     */
    public static String getResourceAsString(final String fullPathOnClassPath) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + fullPathOnClassPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
