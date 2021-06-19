import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Task;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TaskTest extends BaseTest{
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @Test(groups = {"createdProject","createdStory","createdTask","setUp","deleteCreatedTask","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldGetATask() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "8465156165156");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "8484848484888");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidIdAndStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "4651651656546");
        apiRequest.addPathParam("taskId", "565165165165516");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidStoryIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "498416515615616");
        apiRequest.addPathParam("storyId", "546565465465456");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithInvalidIdAndStoryIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "464685464646846");
        apiRequest.addPathParam("storyId", "6546441648469465");
        apiRequest.addPathParam("taskId", "56465464846894984");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithoutStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetATaskWithoutStoryIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldGetAllTasksOfAStory() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetAllTasksOfAStoryWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "984849489489498");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetAllTasksOfAStoryWithInvalidProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "8484894894894984");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetAllTasksOfAStoryWithInvalidIdAndProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "49848948998948");
        apiRequest.addPathParam("storyId", "984849489489498");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetAllTasksOfAStoryWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetAllTasksOfAStoryWithoutProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotGetAllTasksOfAStoryWithoutIdNorProjectId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/");
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","createdTask","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldDeleteATask() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "89489489498489449499848489");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "894894984984894894984");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "84184984894894894984");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "8484894984984894894");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "4894894984894894984984");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidIdAndInvalidStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "984894894894984948948");
        apiRequest.addPathParam("taskId", "48948948948498498498");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidStoryIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "8484894984984894894");
        apiRequest.addPathParam("storyId", "9584894894894894984984");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithInvalidIdAndInvalidStoryIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "8484894984984894894");
        apiRequest.addPathParam("storyId", "654449848948489498489");
        apiRequest.addPathParam("taskId", "4894894984894894984984");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutIdNorStoryId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutStoryIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", String.valueOf(task.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldNotDeleteATaskWithoutIdNorStoryIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "");
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("taskId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedTask","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
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
