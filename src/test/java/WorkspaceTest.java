import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Workspace;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WorkspaceTest {
    ApiRequest apiRequest = new ApiRequest();
    Workspace workspace = new Workspace();
    ConfigFile configFile = new ConfigFile();
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @BeforeMethod(onlyForGroups = "workspace")
    public void setGeneralConfig() {
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
    }

    @Test(groups = "workspace")
    public void itShouldGetAllWorkspaces() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/my/workspaces");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = "workspace")
    public void itShouldGetAWorkspace() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", "875999");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = "workspace")
    public void itShouldNotGetAWorkspaceWithInvalidId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", "87599945655");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = "workspace")
    public void itShouldCreateAWorkspace() throws JsonProcessingException {
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("Workspace test");
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaceTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = "workspace")
    public void itShouldNotCreateAWorkspaceWithEmptyName() throws JsonProcessingException {
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("");
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaceTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = "workspace")
    public void itShouldDeleteWorkspace() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", "876001");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = "workspace")
    public void itShouldNotDeleteAWorkspaceWithInvalidId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", "250508554112");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = "workspace")
    public void itShouldNotDeleteAWorkspaceWithoutId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
