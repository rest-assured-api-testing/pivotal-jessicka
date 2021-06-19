import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Story;
import entities.Task;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TaskTest {
    ApiRequest apiRequest = new ApiRequest();
    Task task = new Task();
    Project project = new Project();
    Story story = new Story();
    ConfigFile configFile = new ConfigFile();
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @BeforeMethod(onlyForGroups = "createdProjectAndStory")
    public void createProjectAndStoryToTestTask() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to test in task");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);

        Story storyTemp = new Story();
        storyTemp.setName("Story to test in task");
        ApiRequest apiRequestStory = new ApiRequest();
        apiRequestStory.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequestStory.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequestStory.setEndpoint("projects/{projectId}/stories");
        apiRequestStory.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequestStory.setMethod(ApiMethod.valueOf("POST"));
        apiRequestStory.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        story = ApiManager.executeWithBody(apiRequestStory).getBody(Story.class);
    }

    @BeforeMethod(onlyForGroups = "task")
    public void setGeneralConfig() throws JsonProcessingException {
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
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
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiManager.execute(apiRequest);
    }

    @AfterMethod(onlyForGroups = "deleteProjectAndStoryOfTask")
    public void deleteCreatedProjectAndStoryToTestTask() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);

        ApiRequest apiRequestStory = new ApiRequest();
        apiRequestStory.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequestStory.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequestStory.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequestStory.setMethod(ApiMethod.DELETE);
        apiRequestStory.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequestStory.addPathParam("storyId", String.valueOf(story.getId()));
        ApiManager.execute(apiRequestStory);
    }

    @Test(groups = {"createdProjectAndStory","createdTask","task","deleteCreatedTask","deleteProjectAndStoryOfTask"})
    public void itShouldGetATask() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "8465156165156");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "8484848484888");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidIdAndStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "4651651656546");
        apiRequest.addPathParam("taskId", "565165165165516");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidStoryIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "498416515615616");
        apiRequest.addPathParam("storyId", "546565465465456");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithInvalidIdAndStoryIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "464685464646846");
        apiRequest.addPathParam("storyId", "6546441648469465");
        apiRequest.addPathParam("taskId", "56465464846894984");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithoutStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetATaskWithoutStoryIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldGetAllTasksOfAStory() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetAllTasksOfAStoryWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "984849489489498");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetAllTasksOfAStoryWithInvalidProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "8484894894894984");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetAllTasksOfAStoryWithInvalidIdAndProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "49848948998948");
        apiRequest.addPathParam("storyId", "984849489489498");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetAllTasksOfAStoryWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetAllTasksOfAStoryWithoutProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotGetAllTasksOfAStoryWithoutIdNorProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","createdTask","task","deleteProjectAndStoryOfTask"})
    public void itShouldDeleteATask() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "89489489498489449499848489");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "894894984984894894984");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "84184984894894894984");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "8484894984984894894");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "4894894984894894984984");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidIdAndInvalidStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "984894894894984948948");
        apiRequest.addPathParam("taskId", "48948948948498498498");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidStoryIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "8484894984984894894");
        apiRequest.addPathParam("storyId", "9584894894894894984984");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithInvalidIdAndInvalidStoryIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "8484894984984894894");
        apiRequest.addPathParam("storyId", "654449848948489498489");
        apiRequest.addPathParam("taskId", "4894894984894894984984");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutIdNorStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutStoryIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotDeleteATaskWithoutIdNorStoryIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteCreatedTask","deleteProjectAndStoryOfTask"})
    public void itShouldCreateATask() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        task = apiResponse.getBody(Task.class);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskWithEmptyName() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskWithoutProjectId() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskWithoutStoryId() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskWithoutProjectIdNorStoryId() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskInvalidProjectId() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "250450561615");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskInvalidStoryId() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "894894894984984988949");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProjectAndStory","task","deleteProjectAndStoryOfTask"})
    public void itShouldNotCreateATaskInvalidProjectIdAndStoryId() throws JsonProcessingException {
        Task taskTemp = new Task();
        taskTemp.setDescription("Task created");
        apiRequest.setEndpoint("projects/{projectId}/stories/{storyId}/tasks");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "250450561615");
        apiRequest.addPathParam("storyId", "98498494894894849");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(taskTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
