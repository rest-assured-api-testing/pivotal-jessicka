package rest.pivotaltracker.org.steps;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Epic;
import entities.Project;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class APIEpicSteps {
    ApiRequest apiRequest = new ApiRequest();
    Project project = new Project();
    Epic epic = new Epic();
    ApiResponse apiResponse;

    private String token = "9f14b3ef7ece5f8404f8e1082f1a404b";
    private String baseUri ="https://www.pivotaltracker.com/services/v5";

    @Before("@CreateProjectEpicAndDeleteProjectEpic")
    public void createEpicConfig() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Cucumber project to test epic");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);

        Epic epicTemp = new Epic();
        epicTemp.setName("Create epic to test");
        ApiRequest apiRequestEpic = new ApiRequest();
        apiRequestEpic.addHeader("X-TrackerToken", token);
        apiRequestEpic.setBaseUri(baseUri);
        apiRequestEpic.setEndpoint("projects/{projectId}/epics");
        apiRequestEpic.setMethod(ApiMethod.valueOf("POST"));
        apiRequestEpic.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequestEpic.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        epic = ApiManager.executeWithBody(apiRequestEpic).getBody(Epic.class);

    }

//    @Before("@CreateProjectEpicAndDeleteProjectEpic")
//    public void createEpicConfig2() throws JsonProcessingException {
//        Epic epicTemp = new Epic();
//        epicTemp.setName("Create epic to test");
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.addHeader("X-TrackerToken", token);
//        apiRequest.setBaseUri(baseUri);
//        apiRequest.setEndpoint("projects/{projectId}/epics");
//        apiRequest.setMethod(ApiMethod.valueOf("POST"));
//        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
//        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
//        epic = ApiManager.executeWithBody(apiRequest).getBody(Epic.class);
//    }

    @Given("I build {string} request to get a epic")
    public void iBuildRequestToGetAEpic(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to get a epic")
    public void iExecuteRequestToGetAEpic(String method) {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("The response status code of the epic should be {string}")
    public void theResponseStatusCodeOfTheEpicShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @Given("I build {string} request to get all epics")
    public void iBuildRequestToGetAllEpics(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to get all epics")
    public void iExecuteRequestToGetAllEpics(String methods) {
        apiRequest.setEndpoint("/projects/{projectId}/epics");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("The response status code of the epics obtained should be {string}")
    public void theResponseStatusCodeOfTheEpicsObtainedShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @Given("I build {string} request to update an epic")
    public void iBuildRequestToUpdateAnEpic(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to update a epic")
    public void iExecuteRequestToUpdateAEpic(String method) throws JsonProcessingException {
        Epic epicTemp = new Epic();
        epicTemp.setName("Epic edited");
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(epicTemp));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
        apiResponse = ApiManager.executeWithBody(apiRequest);
    }

    @Then("The response status code of the epic updated obtained should be {string}")
    public void theResponseStatusCodeOfTheEpicUpdatedObtainedShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After("@CreateProjectEpicAndDeleteProjectEpic")
    public void deleteCreatedStoryConfig() {
        apiRequest.setEndpoint("/projects/{projectId}/epics/{epicId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("epicId", String.valueOf(epic.getId()));
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
