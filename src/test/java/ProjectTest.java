import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.*;

public class ProjectTest {

    ApiRequest apiRequest = new ApiRequest();

    @BeforeMethod
    public void setGeneralConfig() {
        ConfigFile configFile = new ConfigFile();
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
    }

    @Test
    public void itShouldGetAProject() {
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "2504505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void itShouldGetAllProjects() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

}
