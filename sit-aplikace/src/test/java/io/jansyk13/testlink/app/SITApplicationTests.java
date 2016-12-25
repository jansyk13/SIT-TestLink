package io.jansyk13.testlink.app;

import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Condition.post;
import static com.xebialabs.restito.semantics.Condition.withHeader;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonStringEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SITApplicationTests extends AbstractSITApplicationTests {

    @Test
    public void testKey() {
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

        fire()
                .get()
                .to("/sit/key")
                .expectResponse()
                .havingStatusEqualTo(200);
    }

    @Test
    public void testCreateProject() {
        whenTestLink()
                .match(
                        withHeader("content-type", "text/xml"),
                        post("/test"),
                        bodyEquals("xml/createProject.xml")
                )
                .then(
                        ok(),
                        resourceContent("xml/createProjectResponse.xml")
                );

        fire()
                .post()
                .withBody(getResourceAsString("json/createProject.json"))
                .to("/sit/projects")
                .expectResponse()
                .havingBody(jsonStringEquals(getResourceAsString("json/createProjectResponse.json")))
                .havingStatusEqualTo(200);
    }

    @Test
    public void testGetProject() throws Exception {
        whenTestLink()
                .match(
                        withHeader("content-type", "text/xml"),
                        post("/test"),
                        bodyEquals("xml/getProject.xml")
                )
                .then(
                        ok(),
                        resourceContent("xml/getProjectResponse.xml")
                );

        fire()
                .get()
                .to("/sit/projects/test")
                .expectResponse()
                .havingBody(jsonStringEquals(getResourceAsString("json/getProjectResponse.json")))
                .havingStatusEqualTo(200);
    }

    @Test
    public void testCreateTestPlan() throws Exception {
        whenTestLink()
                .match(
                        withHeader("content-type", "text/xml"),
                        post("/test"),
                        bodyEquals("xml/createTestPlan.xml")
                )
                .then(
                        ok(),
                        resourceContent("xml/createTestPlanResponse.xml")
                );

        fire()
                .post()
                .withBody(getResourceAsString("json/createTestPlan.json"))
                .to("/sit/projects/test/testplans")
                .expectResponse()
                .havingBody(jsonStringEquals(getResourceAsString("json/createTestPlanResponse.json")))
                .havingStatusEqualTo(200);
    }

    @Test
    public void testGetTestPlan() throws Exception {
        whenTestLink()
                .match(
                        withHeader("content-type", "text/xml"),
                        post("/test"),
                        bodyEquals("xml/getTestPlan.xml")
                )
                .then(
                        ok(),
                        resourceContent("xml/getTestPlanResponse.xml")
                );

        fire()
                .get()
                .to("/sit/projects/test/testplans/test2")
                .expectResponse()
                .havingBody(jsonStringEquals(getResourceAsString("json/getTestPlanResponse.json")))
                .havingStatusEqualTo(200);
    }

    @Test
    public void testCreateTestSuite() throws Exception {
        fire()
                .post()
                .to("/sit/testsuits/test")
                .expectResponse()
                .havingStatusEqualTo(200);
        //TODO mock + check resource
        //TODO David
    }

    @Test
    public void testGetTestSuite() throws Exception {
        fire()
                .post()
                .to("/sit/testsuits/test")
                .expectResponse()
                .havingStatusEqualTo(200);
        //TODO mock + check resource
        //TODO David
    }

    @Test
    public void testCreateBuild() throws Exception {
        whenTestLink()
                .match(
                        withHeader("content-type", "text/xml"),
                        post("/test"),
                        bodyEquals("xml/createBuild.xml")
                )
                .then(
                        ok(),
                        resourceContent("xml/createBuildResponse.xml")
                );
        fire()
                .post()
                .withBody(getResourceAsString("json/createBuild.json"))
                .to("/sit/testplans/test/builds")
                .expectResponse()
                .havingStatusEqualTo(200);
    }

    @Test
    public void testGetBuilds() throws Exception {
        fire()
                .get()
                .to("/sit/testplans/test/builds")
                .expectResponse()
                .havingStatusEqualTo(200);
        //TODO mock + check resource
        //TODO David
    }
}
