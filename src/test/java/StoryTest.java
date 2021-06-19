import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoryTest extends BaseTest {
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldGetAStory() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAStoryWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAStoryWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAStoryWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldGetAllStoriesOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllStoriesOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotGetAllStoriesOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","setUp","deleteCreatedProject"})
    public void itShouldDeleteAStory() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAStoryWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAStoryWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAStoryWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAStoryWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAStoryWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotDeleteAStoryWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedStory","deleteCreatedProject"})
    public void itShouldCreateAStory() throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("Story created");
        apiRequest.setEndpoint("projects/{projectId}/stories");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        story = apiResponse.getBody(Story.class);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotCreateAStoryWithEmptyName() throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("");
        apiRequest.setEndpoint("projects/{projectId}/stories");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusBadRequest);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotCreateAStoryWithoutProjectId() throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("Story created");
        apiRequest.setEndpoint("projects/{projectId}/stories");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","setUp","deleteCreatedProject"})
    public void itShouldNotCreateAStoryWithInvalidProjectId() throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("Story created");
        apiRequest.setEndpoint("projects/{projectId}/stories");
        apiRequest.setMethod(ApiMethod.POST);
        apiRequest.addPathParam("projectId", "250450561615");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }
}
