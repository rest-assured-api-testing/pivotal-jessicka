Feature: Request for stories endpoint

  @CreateProjectStoryAndDeleteProjectStory
  Scenario: Get a story of a project
    Given I build "GET" request to get a story
    When I execute "project/{projectId}/stories/{storyId}" request to get a story
    Then The response status code of the story should be "OK"

  @CreateProjectStoryAndDeleteProjectStory
  Scenario: Get all stories of a project
    Given I build "GET" request to get all stories
    When I execute "project/{projectId}/stories" request to get all stories
    Then The response status code of the stories obtained should be "OK"

  @CreateProjectStoryAndDeleteProjectStory
  Scenario: Update a story
    Given I build "PUT" request to update a story
    When I execute "project/{projectId}/stories/{storyId}" request to update a story
    Then The response status code of the story updated obtained should be "OK"