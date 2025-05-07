Feature: Book update functionality

  Scenario: Update an existing book
    Given I have a valid auth token
    When I update the book with new title "The Great Gatsby Updated", author "F. Scott Fitzgerald", and isbn "1234567891"
    Then The book should be updated successfully
