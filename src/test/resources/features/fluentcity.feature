@fluentcity
Feature: Purchasing product
  Background: Navigation to the site
    Given I navigate to page "fluentcity"

  @fluentcity1
  Scenario: Adding prepaid package
    When I click "ADULTS"
    Then I see "STARTER" package on the page
    When I select "Spanish" language on "STARTER" package
    And I click button on "STARTER" card
    Then I am directed to "Complete Your Purchase"
    And cart contains "STARTER" pack with "560.00" price