Feature: Book deletion functionality

  Scenario: Delete a book
    Given I have a valid auth token
    When I delete the created book
    Then The book should be deleted successfully
