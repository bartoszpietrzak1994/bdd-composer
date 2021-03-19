Feature: Creating account in Bargain Service
  In order to be aware of the best bargains
  As a Customer
  I want to be able sign up to Bargain Service

Scenario: Signing up to Bargain Service
  Given there is a customer John with email address bartosz.pietrzak@gmail.com and password well-protected-password
  When John signs up
  Then John's account should be created
  Then John should have an active BRONZE subscription