Feature: Request for epics endpoint

  @CreateProjectEpicAndDeleteProjectEpic
  Scenario: Get an epic of a project
    Given I build "GET" request to get a epic
    When I execute "project/{projectId}/epics/{epicId}" request to get a epic
    Then The response status code of the epic should be "OK"

  @CreateProjectEpicAndDeleteProjectEpic
  Scenario: Get all epics of a project
    Given I build "GET" request to get all epics
    When I execute "project/{projectId}/epics" request to get all epics
    Then The response status code of the epics obtained should be "OK"

  @CreateProjectEpicAndDeleteProjectEpic
  Scenario: Update an epic
    Given I build "PUT" request to update an epic
    When I execute "project/{projectId}/epics/{epicId}" request to update a epic
    Then The response status code of the epic updated obtained should be "OK"