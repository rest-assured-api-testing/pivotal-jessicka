import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EpicTest {
    ApiRequest apiRequest = new ApiRequest();
    Epic epic = new Epic();
    Project project = new Project();
    ConfigFile configFile = new ConfigFile();
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @BeforeMethod(onlyForGroups = "createdProject")
    public void createProjectToTestEpic() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to test in epic");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @BeforeMethod(onlyForGroups = "epic")
    public void setGeneralConfig() throws JsonProcessingException {
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
    }

    @BeforeMethod(onlyForGroups = "createdEpic")
    public void setCreatedEpicConfig() throws JsonProcessingException {
        Epic epicTemp = new Epic();
        epicTemp.setName("Epic to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects/{projectId}/epics");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        epic = ApiManager.executeWithBody(apiRequest).getBody(Epic.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedEpic")
    public void deleteCreatedEpicConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @AfterMethod(onlyForGroups = "deleteProjectOfEpic")
    public void deleteCreatedProjectToTestEpic() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @Test(groups = {"createdProject","createdEpic","epic","deleteCreatedEpic","deleteProjectOfEpic"})
    public void itShouldGetAnEpic() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotGetAnEpicWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("epicId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotGetAnEpicWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotGetAnEpicWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("epicId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldGetAllEpicsOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotGetAllEpicsOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotGetAllEpicsOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdEpic","epic","deleteProjectOfEpic"})
    public void itShouldDeleteAnEpic() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotDeleteAnEpicWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("epicId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotDeleteAnEpicWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotDeleteAnEpicWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotDeleteAnEpicWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotDeleteAnEpicWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotDeleteAnEpicWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteCreatedEpic","deleteProjectOfEpic"})
    public void itShouldCreateAnEpic() throws JsonProcessingException {
        Epic epicTemp = new Epic();
        epicTemp.setName("Epic created");
        apiRequest.setEndpoint("projects/{projectId}/epics");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        epic = apiResponse.getBody(Epic.class);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotCreateAnEpicWithEmptyName() throws JsonProcessingException {
        Epic epicTemp = new Epic();
        epicTemp.setName("");
        apiRequest.setEndpoint("projects/{projectId}/epics");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotCreateAnEpicWithoutProjectId() throws JsonProcessingException {
        Epic epicTemp = new Epic();
        epicTemp.setName("Epic created");
        apiRequest.setEndpoint("projects/{projectId}/epics");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","epic","deleteProjectOfEpic"})
    public void itShouldNotCreateAnEpicWithInvalidProjectId() throws JsonProcessingException {
        Epic epicTemp = new Epic();
        epicTemp.setName("Epic created");
        apiRequest.setEndpoint("projects/{projectId}/epics");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "250450561615");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
