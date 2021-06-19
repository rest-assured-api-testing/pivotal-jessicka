import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected ApiRequest apiRequest = new ApiRequest();
    protected ApiResponse apiResponse = new ApiResponse();
    protected ConfigFile configFile = new ConfigFile();
    protected Project project = new Project();
    protected Workspace workspace = new Workspace();
    protected Story story = new Story();
    protected Epic epic = new Epic();
    protected Webhook webhook = new Webhook();
    protected Label label = new Label();
    protected Task task = new Task();

    @BeforeMethod(onlyForGroups = "setUp")
    public void setGeneralConfig() {
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
    }

    @BeforeMethod(onlyForGroups = "createdProject")
    public void setCreatedProjectConfig() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedProject")
    public void deleteCreatedProjectConfig() {
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.clear();
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "createdWorkspace")
    public void setCreatedWorkspaceConfig() throws JsonProcessingException {
        Workspace workspaceTemp = new Workspace();
        workspaceTemp.setName("Workspace to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("/my/workspaces");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(workspaceTemp));
        workspace = ApiManager.executeWithBody(apiRequest).getBody(Workspace.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedWorkspace")
    public void deleteCreatedWorkspaceConfig() {
        apiRequest.setEndpoint("/my/workspaces/{workspaceId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.clear();
        apiRequest.addPathParam("workspaceId", String.valueOf(workspace.getId()));
        ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "createdStory")
    public void setCreatedStoryConfig() throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("Story to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects/{projectId}/stories");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        story = ApiManager.executeWithBody(apiRequest).getBody(Story.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedStory")
    public void deleteCreatedStoryConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.clear();
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "createdEpic")
    public void setTheCreatedEpicConfig() throws JsonProcessingException {
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
        apiRequest.clear();
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
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
        apiRequest.clear();
        apiRequest.addPathParam("webhookId", String.valueOf(webhook.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "createdLabel")
    public void setTheCreatedLabelConfig() throws JsonProcessingException {
        Label labelTemp = new Label();
        labelTemp.setName("Label to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects/{projectId}/labels");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(labelTemp));
        label = ApiManager.executeWithBody(apiRequest).getBody(Label.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedLabel")
    public void deleteCreatedLabelConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/labels/{labelId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.clear();
        apiRequest.addPathParam("labelId", String.valueOf(label.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @BeforeMethod(onlyForGroups = "createdTask")
    public void setCreatedTaskConfig() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        task = ApiManager.executeWithBody(apiRequest).getBody(Task.class);
    }

    @AfterMethod(onlyForGroups = "deleteCreatedTask")
    public void deleteCreatedTaskConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.clear();
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiManager.execute(apiRequest);
    }
}
