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
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.semantics.Stub;
import com.xebialabs.restito.server.StubServer;

import net.javacrumbs.restfire.RequestBuilder;
import net.javacrumbs.restfire.RequestFactory;
import net.javacrumbs.restfire.RequestProcessor;
import net.javacrumbs.restfire.httpcomponents.HttpComponentsRequestFactory;

public abstract class SitAplikaceApplicationComponentTests {

    @LocalServerPort
    private int port;

    private static StubServer testLinkMocker;

    static {
        testLinkMocker = new StubServer(8091);
        testLinkMocker.start();
        testLinkMocker.addStub(new Stub(
                Condition.composite(withHeader("content-type", "text/xml"),
                        xmlEquals(getResourceAsString("xml/checkDevKey.xml")),
                        post("/test")),
                Action.composite(ok(),
                        resourceContent("xml/checkDevKeyResponse.xml"))
        ));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                testLinkMocker.stop();
            }
        });
    }

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    protected RequestFactory fire() {
        return new HttpComponentsRequestFactory(httpClient, new RequestProcessor() {
            @Override
            public void processRequest(RequestBuilder requestBuilder) {
                requestBuilder.withPort(port);
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        testLinkMocker.clear();
    }

    protected static StubHttp whenTestLink() {
        return whenHttp(testLinkMocker);
    }

    protected static Condition xmlEquals(String xmlString) {
        return Condition.custom(input -> {
            Diff myDiff = DiffBuilder.compare(Input.fromString(input.getPostBody()))
                    .withTest(Input.fromString(xmlString))
                    .build();

            return !myDiff.hasDifferences();
        });
    }

    public static String getResourceAsString(final String fullPathOnClassPath) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + fullPathOnClassPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
