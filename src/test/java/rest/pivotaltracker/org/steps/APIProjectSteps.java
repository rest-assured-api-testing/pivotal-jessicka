package rest.pivotaltracker.org.steps;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import org.testng.Assert;

public class APIProjectSteps {

    ApiRequest apiRequest = new ApiRequest();
    Project project = new Project();
//    ConfigFile configFile = new ConfigFile();
    ApiResponse apiResponse;

    private String token = "9f14b3ef7ece5f8404f8e1082f1a404b";
    private String baseUri ="https://www.pivotaltracker.com/services/v5";

    @Before("@CreateProjectAndDeleteProject")
    public void createProjectConfig() throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Cucumber project to test");
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setEndpoint("projects");
        apiRequest.setMethod(ApiMethod.valueOf("POST"));
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        project = ApiManager.executeWithBody(apiRequest).getBody(Project.class);
    }

    @Given("I build {string} request")
    public void iBuildRequest(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request")
    public void iExecuteRequest(String method) {
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("The response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @Given("I build {string} request to get all projects")
    public void iBuildRequestToGetAllProjects(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to get all projects")
    public void iExecuteRequestToGetAllProjects(String method) {
        apiRequest.setEndpoint("/projects");
        apiResponse = ApiManager.execute(apiRequest);
    }

    @Then("The response status code of the projects obtained should be {string}")
    public void theResponseStatusCodeOfTheProjectsObtainedShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @Given("I build {string} request to update a project")
    public void iBuildRequestToUpdateAProject(String method) {
        apiRequest.addHeader("X-TrackerToken", token);
        apiRequest.setBaseUri(baseUri);
        apiRequest.setMethod(ApiMethod.valueOf(method));
    }

    @When("I execute {string} request to update a projects")
    public void iExecuteRequestToUpdateAProjects(String method) throws JsonProcessingException {
        Project projectTemp = new Project();
        projectTemp.setName("Project edited");
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setBody(new ObjectMapper().writeValueAsString(projectTemp));
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        apiResponse = ApiManager.executeWithBody(apiRequest);
    }

    @Then("The response status code of the project updated obtained should be {string}")
    public void theResponseStatusCodeOfTheProjectUpdatedObtainedShouldBe(String statusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), HttpStatus.SC_OK);
        apiResponse.getResponse().then().log().body();
    }

    @After("@CreateProjectAndDeleteProject")
    public void deleteCreatedProjectConfig() {
        apiRequest.setEndpoint("/projects/{projectId}");
        apiRequest.setMethod(ApiMethod.DELETE);
        apiRequest.addPathParam("projectId", String.valueOf(project.getId()));
        ApiManager.execute(apiRequest);
    }

}
