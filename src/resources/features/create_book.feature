Feature: Book creation functionality

  Scenario: Create a new book with valid data
    Given I have a book JSON with name "Naz Name", author "Author Name", published year "2023", and summary "Some summary"
    When I create a new book with the provided details
    Then The book should be created successfully

  Scenario: Create a new book with invalid data
    Given I have an invalid book JSON
    Then I should receive an error for invalid data
