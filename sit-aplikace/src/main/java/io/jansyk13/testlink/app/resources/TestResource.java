package io.jansyk13.testlink.app.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;

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
    public TestProject getProject (@RequestParam String projectId) {
        return testLinkAPI.getTestProjectByName(projectId);
    }


}