package rest.pivotaltracker.org.steps;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import entities.Story;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class APIStorySteps {
    ApiRequest apiRequest = new ApiRequest();
    Project project = new Project();
    Story story = new Story();
    ApiResponse apiResponse;

    private String token = "9f14b3ef7ece5f8404f8e1082f1a404b";
    private String baseUri ="https://www.pivotaltracker.com/services/v5";

    @Before("@CreateProjectStoryAndDeleteProjectStory")
    public void createStoryConfig() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Cucumber project to test story");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);

        Story storyTemp = new Story();
        storyTemp.setName("Create story to test");
        ApiRequest apiRequestStory = new ApiRequest();
        apiRequestStory.addHeader("X-TrackerToken", token);
        apiRequestStory.setBaseUri(baseUri);
        apiRequestStory.setEndpoint("projects/{projectId}/stories");
        apiRequestStory.setMethod(ApiMethod.valueOf("POST"));
        apiRequestStory.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequestStory.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        story = ApiManager.executeWithBody(apiRequestStory).getBody(Story.class);

    }

    @Given("I build {string} request to get a story")
    public void iBuildRequestToGetAStory(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to get a story")
    public void iExecuteRequestToGetAStory(String method) {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("The response status code of the story should be {string}")
    public void theResponseStatusCodeOfTheStoryShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @Given("I build {string} request to get all stories")
    public void iBuildRequestToGetAllStories(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to get all stories")
    public void iExecuteRequestToGetAllStories(String method) {
        apiRequest.setEndpoint("/projects/{projectId}/stories");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("The response status code of the stories obtained should be {string}")
    public void theResponseStatusCodeOfTheStoriesObtainedShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @Given("I build {string} request to update a story")
    public void iBuildRequestToUpdateAStory(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to update a story")
    public void iExecuteRequestToUpdateAStory(String method) throws JsonProcessingException {
        Story storyTemp = new Story();
        storyTemp.setName("Story edited");
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(storyTemp));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiResponse = ApiManager.executeWithBody(apiRequest);
    }

    @Then("The response status code of the story updated obtained should be {string}")
    public void theResponseStatusCodeOfTheStoryUpdatedObtainedShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After("@CreateProjectStoryAndDeleteProjectStory")
    public void deleteCreatedStoryConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/stories/{storyId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("storyId", String.valueOf(story.getId()));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);

        ApiRequest apiRequestStory = new ApiRequest();
        apiRequestStory.addHeader("X-TrackerToken", token);
        apiRequestStory.setBaseUri(baseUri);
        apiRequestStory.setEndpoint("/projects/{projectId}");
        apiRequestStory.setMethod(ApiMethod.DELETE);
        apiRequestStory.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequestStory);
    }
}
