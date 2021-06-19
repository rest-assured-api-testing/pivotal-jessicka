Feature: Request for projects endpoint

  @CreateProjectAndDeleteProject
  Scenario: Get a project
    Given I build "GET" request
    When I execute "project/{projectId}" request
    Then The response status code should be "OK"

  @CreateProjectAndDeleteProject
  Scenario: Get all projects
    Given I build "GET" request to get all projects
    When I execute "projects" request to get all projects
    Then The response status code of the projects obtained should be "OK"

  @CreateProjectAndDeleteProject
  Scenario: Update a projects
    Given I build "PUT" request to update a project
    When I execute "projects/{projectId}" request to update a projects
    Then The response status code of the project updated obtained should be "OK"