import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Workspace;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WorkspaceTest extends BaseTest {
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @Test(groups = "setUp")
    public void itShouldGetAllWorkspaces() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/my/workspaces");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdWorkspace","setUp","deleteCreatedWorkspace"})
    public void itShouldGetAWorkspace() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", String.valueOf(workspace.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = "setUp")
    public void itShouldNotGetAWorkspaceWithInvalidId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("workspaceId", "87599945655");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"setUp","deleteCreatedWorkspace"})
    public void itShouldCreateAWorkspace() throws JsonProcessingException {
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("Workspace created");
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaceTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        workspace = apiResponse.getBody(Workspace.class);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = "setUp")
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

    @Test(groups = {"createdWorkspace","setUp"})
    public void itShouldDeleteWorkspace() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", String.valueOf(workspace.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = "setUp")
    public void itShouldNotDeleteAWorkspaceWithInvalidId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", "250508554112");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = "setUp")
    public void itShouldNotDeleteAWorkspaceWithoutId() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("workspaceId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
