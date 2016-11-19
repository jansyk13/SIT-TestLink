package io.jansyk13.testlink.app;

import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Condition.post;
import static com.xebialabs.restito.semantics.Condition.withHeader;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SitAplikaceApplicationTests extends SitAplikaceApplicationComponentTests {

    @Test
    public void testKey() {
        whenTestLink()
                .match(
                        withHeader("content-type", "text/html"),
                        xmlEquals(getResourceAsString("xml/checkDevKey.xml")),
                        post("/")
                )
                .then(
                        ok(),
                        resourceContent("xml/checkDevKeyResponse.xml")
                );

        fire()
                .get()
                .to("/sit/key")
                .expectResponse()
                .havingStatusEqualTo(200);
    }

    @Test
    public void testCreateProject() {
        fire()
                .post()
                .to("/sit/projects")
                .expectResponse()
                .havingStatusEqualTo(200);
    }

    @Test
    public void testGetProject() throws Exception {
        fire()
                .post()
                .to("/sit/projects/test")
                .expectResponse()
                .havingStatusEqualTo(200);
    }

}
