import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProjectTest {

    ApiRequest apiRequest = new ApiRequest();

    @BeforeTest
    public void setGeneralConfig() {
        apiRequest.addHeader("X-TrackerToken", "9f14b3ef7ece5f8404f8e1082f1a404b");
        apiRequest.setBaseUri("https://www.pivotaltracker.com/services/v5");
    }

    @Test
    public void itShouldGetAllProjects() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects");
        ApiResponse apiResponse = new ApiResponse(ApiManager.execute(apiRequest));
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void itShouldGetAProject() {
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "2504505");
        ApiResponse apiResponse = new ApiResponse(ApiManager.execute(apiRequest));
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }
}
