import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Label;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LabelTest extends BaseTest {
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @Test(groups = {"createdProject","createdLabel","setUp","deleteCreatedLabel","deleteCreatedProject"})
    public void itShouldGetALabel() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetALabelWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("labelId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetALabelWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetALabelWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("labelId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldGetAllLabelsOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllLabelsOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllLabelsOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/labels");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdLabel","setUp","deleteCreatedProject"})
    public void itShouldDeleteALabel() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteALabelWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("labelId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteALabelWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("labelId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteALabelWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteALabelWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("labelId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteALabelWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteALabelWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("labelId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedLabel","deleteCreatedProject"})
    public void itShouldCreateALabel() throws JsonProcessingException {
        Label labelTemp = new Label();
        labelTemp.setName("Label created");
        apiRequest.setEndpoint("projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(labelTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        label = apiResponse.getBody(Label.class);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotCreateALabelWithEmptyName() throws JsonProcessingException {
        Label labelTemp = new Label();
        labelTemp.setName("");
        apiRequest.setEndpoint("projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(labelTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotCreateALabelWithoutProjectId() throws JsonProcessingException {
        Label labelTemp = new Label();
        labelTemp.setName("Label created");
        apiRequest.setEndpoint("projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(labelTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotCreateALabelWithInvalidProjectId() throws JsonProcessingException {
        Label labelTemp = new Label();
        labelTemp.setName("Label created");
        apiRequest.setEndpoint("projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "250450561615");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(labelTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
