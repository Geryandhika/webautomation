@web
Feature: Shopping on Demoblaze

  Scenario: Home page is accessible
    Given user is on Demoblaze home page
    Then home page title should be "STORE"

  Scenario: Add one product to cart
    Given user is on Demoblaze home page
    When user opens first product detail
    And user adds the product to cart
    And user navigates to cart page
    Then cart should contain at least one product

  Scenario: Cart should be empty when no product added
    Given user is on Demoblaze home page
    When user navigates directly to cart page
    Then cart should be empty

  Scenario: E2E purchase product
    Given user is on Demoblaze home page
    When user opens first product detail
    And user adds the product to cart
    And user navigates to cart page
    And user places an order with valid data
    Then order success popup should be displayed
