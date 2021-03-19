Feature: Receiving notifications on subscribing to package
  In order to be in touch with Bargain Service
  As a Customer
  I want to receive notifications on any changes to my package

  Scenario: Subscribing to a new package in Bargain Service
    Given John is an already registered customer with email address john@john.com and password password
    And notification channel EMAIL is active
    When John subscribes for the PLATINUM subscription
    Then John should have an active PLATINUM subscription
    And notification Congrats! You've just picked up the PLATINUM subscription! should be sent to John
