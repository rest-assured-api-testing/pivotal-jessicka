import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EpicTest extends BaseTest {
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @Test(groups = {"createdProject","createdEpic","setUp","deleteCreatedEpic","deleteCreatedProject"})
    public void itShouldGetAnEpic() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAnEpicWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("epicId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAnEpicWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAnEpicWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("epicId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldGetAllEpicsOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllEpicsOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllEpicsOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdEpic","setUp","deleteCreatedProject"})
    public void itShouldDeleteAnEpic() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAnEpicWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("epicId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAnEpicWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAnEpicWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAnEpicWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAnEpicWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAnEpicWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedEpic","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
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
