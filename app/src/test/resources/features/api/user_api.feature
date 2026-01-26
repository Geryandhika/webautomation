@api
Feature: User CRUD via DummyAPI

Scenario: Get list of users
  When client sends GET user list
  Then response status code should be 200
  And response body should contain field "data"

Scenario: Create new user
  When client creates a new user with random email
  Then response status code should be 200
  And response body should contain field "id"

Scenario: Update existing user lastname
  Given an existing user created via API
  When client updates that user lastname to "UpdatedLastName"
  Then response status code should be 200

Scenario: Delete existing user
  Given an existing user created via API
  When client deletes that user
  Then response status code should be 200

Scenario: Get user by invalid id should return 404
  When client sends GET user by id "invalid-id-123"
  Then response status code should be 400
