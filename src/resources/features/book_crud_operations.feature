Feature: Book CRUD operations

  Scenario: Create a new book with valid data
    Given I have a book JSON with name "Naz Name", author "Author Name", published year "2023", and summary "Some summary"
    When I create a new book with the provided details
    Then The book should be created successfully

  Scenario: Retrieve a book's details
    Given I have a valid auth token
    When I retrieve the details of the created book
    Then I should get the book details

  Scenario: Update an existing book
    Given I have a valid auth token
    When I update the book with new title "The Great Gatsby Updated", author "F. Scott Fitzgerald", and isbn "1234567891"
    Then The book should be updated successfully

  Scenario: Delete a book
    Given I have a valid auth token
    When I delete the created book
    Then The book should be deleted successfully
