package io.jansyk13.testlink.app.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;

@RestController
@RequestMapping("/sit")
public class TestResource {

    @Value("${testlink.devkey}")
    private String devKey;

    @Autowired
    private TestLinkAPI testLinkAPI;

    @RequestMapping(method = GET, value = "/key")
    public ResponseEntity<Void> get() {
        Boolean checkDevKey = testLinkAPI.checkDevKey(devKey);
        if (checkDevKey) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(method = POST, value = "/projects")
    public TestProject createProject(@RequestBody TestProject testProject) {
        return testLinkAPI.createTestProject(
                testProject.getName(),
                testProject.getPrefix(),
                testProject.getNotes(),
                testProject.isEnableRequirements(),
                testProject.isEnableTestPriority(),
                testProject.isEnableAutomation(),
                testProject.isEnableInventory(),
                testProject.isActive(),
                testProject.isPublic()
        );
    }

    @RequestMapping(method = GET, value = "/projects/{projectId}")
    public TestProject getProject(@PathVariable String projectId) {
        return testLinkAPI.getTestProjectByName(projectId);
    }


    @RequestMapping(method = GET, value = "/projects/{projectId}/testplan/{testplanId}")
    public TestPlan getTestPlan(@PathVariable String projectId, @PathVariable String testplanId) {
        return testLinkAPI.getTestPlanByName(projectId, testplanId);
    }

    @RequestMapping(method = POST, value = "/projects/{projectId}/testplan")
    public TestPlan createTestPlan(@PathVariable String projectId, @RequestBody TestPlan testPlan) {
        return testLinkAPI.createTestPlan(
                testPlan.getName(),
                projectId,
                testPlan.getNotes(),
                testPlan.isActive(),
                testPlan.isPublic()
        );
    }

    @RequestMapping(method = GET, value = "/testsuites/{testsuiteId}")
    public TestSuite[] getTestSuite(@PathVariable int testsuiteId) {
        return testLinkAPI.getTestSuitesForTestSuite(testsuiteId);
    }

    @RequestMapping(method = POST, value = "/testsuites")
    public TestSuite createTestSuite(@RequestBody TestSuite testSuite) {
        return testLinkAPI.createTestSuite(
                testSuite.getTestProjectId(),
                testSuite.getName(),
                testSuite.getDetails(),
                testSuite.getParentId(),
                testSuite.getOrder(),
                testSuite.getCheckDuplicatedName(),
                testSuite.getActionOnDuplicatedName()
        );
    }

    @RequestMapping(method = GET, value = "/testplans/{testplanId}/builds")
    public Build[] getBuilds(@PathVariable int testplanId) {
        return testLinkAPI.getBuildsForTestPlan(testplanId);
    }

    @RequestMapping(method = POST, value = "/testplans/{testplanId}/builds")
    public Build createBuild(@PathVariable int testplanId, @RequestBody Build build) {
        return testLinkAPI.createBuild(
                testplanId,
                build.getName(),
                build.getNotes()
        );
    }
}