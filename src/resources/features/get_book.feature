Feature: Book retrieval functionality

  Scenario: Retrieve a book's details
    Given I have a valid auth token
    When I retrieve the details of the created book
    Then I should get the book details
