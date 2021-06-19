import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Webhook;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WebhookTest {
    ApiRequest apiRequest = new ApiRequest();
    Webhook webhook = new Webhook();
    Project project = new Project();
    ConfigFile configFile = new ConfigFile();
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @BeforeMethod(onlyForGroups = "createdProject")
    public void createProjectToTestWebhook() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to test in webhook");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @BeforeMethod(onlyForGroups = "webhook")
    public void setGeneralConfig() throws JsonProcessingException {
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
    }

    @BeforeMethod(onlyForGroups = "createdWebhook")
    public void setCreatedWebhookConfig() throws JsonProcessingException {
        Webhook webhookTemp = new Webhook();
        webhookTemp.setWebhook_url("https://www.webhooktotest.com/");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects/{projectId}/webhooks");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(webhookTemp));
        webhook = ApiManager.executeWithBody(apiRequest).getBody(Webhook.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedWebhook")
    public void deleteCreatedWebhookConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @AfterMethod(onlyForGroups = "deleteProjectOfWebhook")
    public void deleteCreatedProjectToTestWebhook() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @Test(groups = {"createdProject","createdWebhook","webhook","deleteCreatedWebhook","deleteProjectOfWebhook"})
    public void itShouldGetAWebhook() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotGetAWebhookWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("webhookId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotGetAWebhookWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotGetAWebhookWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("webhookId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldGetAllWebhooksOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/webhooks");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotGetAllWebhooksOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/webhooks");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotGetAllWebhooksOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/webhooks");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdWebhook","webhook","deleteProjectOfWebhook"})
    public void itShouldDeleteAWebhook() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotDeleteAWebhookWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("webhookId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotDeleteAWebhookWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotDeleteAWebhookWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotDeleteAWebhookWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotDeleteAWebhookWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotDeleteAWebhookWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteCreatedWebhook","deleteProjectOfWebhook"})
    public void itShouldCreateAWebhook() throws JsonProcessingException {
        Webhook webhookTemp = new Webhook();
        webhookTemp.setWebhook_url("https://www.webhooktotest.com/");
        apiRequest.setEndpoint("projects/{projectId}/webhooks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(webhookTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        webhook = apiResponse.getBody(Webhook.class);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotCreateAWebhookWithEmptyName() throws JsonProcessingException {
        Webhook webhookTemp = new Webhook();
        webhookTemp.setWebhook_url("");
        apiRequest.setEndpoint("projects/{projectId}/webhooks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(webhookTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotCreateAWebhookWithoutProjectId() throws JsonProcessingException {
        Webhook webhookTemp = new Webhook();
        webhookTemp.setWebhook_url("https://www.webhooktotest.com/");
        apiRequest.setEndpoint("projects/{projectId}/webhooks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(webhookTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","webhook","deleteProjectOfWebhook"})
    public void itShouldNotCreateAWebhookWithInvalidProjectId() throws JsonProcessingException {
        Webhook webhookTemp = new Webhook();
        webhookTemp.setWebhook_url("https://www.webhooktotest.com/");
        apiRequest.setEndpoint("projects/{projectId}/webhooks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "250450561615");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(webhookTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
