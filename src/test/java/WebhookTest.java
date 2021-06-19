import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Webhook;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebhookTest extends BaseTest {
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @Test(groups = {"createdProject","createdWebhook","setUp","deleteCreatedWebhook","deleteCreatedProject"})
    public void itShouldGetAWebhook() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAWebhookWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("webhookId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAWebhookWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAWebhookWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("webhookId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldGetAllWebhooksOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/webhooks");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllWebhooksOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/webhooks");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllWebhooksOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/webhooks");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdWebhook","setUp","deleteCreatedProject"})
    public void itShouldDeleteAWebhook() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAWebhookWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("webhookId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAWebhookWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAWebhookWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAWebhookWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAWebhookWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAWebhookWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/webhooks/{webhookId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("webhookId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedWebhook","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
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
