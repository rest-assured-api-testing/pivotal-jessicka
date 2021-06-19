import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoryTest {
    ApiRequest apiRequest = new ApiRequest();
    Story story = new Story();
    Project project = new Project();
    ConfigFile configFile = new ConfigFile();
    int statusNotFound = 404;
    int statusNoContent = 204;
    int statusOk = 200;
    int statusBadRequest = 400;

    @BeforeMethod(onlyForGroups = "createdProject")
    public void createProjectToTestStory() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project to test in story");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @BeforeMethod(onlyForGroups = "story")
    public void setGeneralConfig() throws JsonProcessingException {
        apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
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
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @AfterMethod(onlyForGroups = "deleteProjectOfStory")
    public void deleteCreatedProjectToTestStory() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", configFile.getConfig().getProperty("PIVOTAL_TOKEN"));
        apiRequest.setBaseUri(configFile.getConfig().getProperty("PIVOTAL_BASE_URI"));
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

    @Test(groups = {"createdProject","createdStory","story","deleteCreatedStory","deleteProjectOfStory"})
    public void itShouldGetAStory() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotGetAStoryWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotGetAStoryWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotGetAStoryWithInvalidIdAndProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.addPathParam("projectId", "65465465465");
        apiRequest.addPathParam("storyId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldGetAllStoriesOfAProject() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusOk);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotGetAllStoriesOfAProjectWithInvalidId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", "651231654165165");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotGetAllStoriesOfAProjectWithoutId() {
        apiRequest.setMethod(ApiMethod.GET);
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","createdStory","story","deleteProjectOfStory"})
    public void itShouldDeleteAStory() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNoContent);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotDeleteAStoryWithInvalidId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", "25046515616505");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotDeleteAStoryWithoutId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotDeleteAStoryWithoutProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotDeleteAStoryWithoutIdNorProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", "");
        apiRequest.addPathParam("projectId", "");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotDeleteAStoryWithInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", "2504505561561");
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
    public void itShouldNotDeleteAStoryWithInvalidIdAndInvalidProjectId() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", "6565164465");
        apiRequest.addPathParam("projectId", "25045855605");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), statusNotFound);
    }

    @Test(groups = {"createdProject","story","deleteCreatedStory","deleteProjectOfStory"})
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

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
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

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
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

    @Test(groups = {"createdProject","story","deleteProjectOfStory"})
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
