package io.jansyk13.testlink.app;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.jadler.JadlerMocker;
import net.jadler.stubbing.server.jdk.JdkStubHttpServer;
import net.jadler.stubbing.server.jetty.JettyStubHttpServer;
import net.javacrumbs.restfire.RequestBuilder;
import net.javacrumbs.restfire.RequestFactory;
import net.javacrumbs.restfire.RequestProcessor;
import net.javacrumbs.restfire.httpcomponents.HttpComponentsRequestFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SitAplikaceApplicationTests {

    private JadlerMocker testLinkMocker  = new JadlerMocker(new JdkStubHttpServer(8090));

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    private RequestFactory fire() {
        return new HttpComponentsRequestFactory(httpClient, new RequestProcessor() {
            @Override
            public void processRequest(RequestBuilder requestBuilder) {
                requestBuilder.withPort(8080);
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        testLinkMocker.start();
    }

    @After
    public void tearDown() throws Exception {
        testLinkMocker.close();
    }

    @Test
    public void call() {
        fire().get().to("/sit").expectResponse().havingStatusEqualTo(200);
    }

}
