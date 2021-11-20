Feature: Sign up user 
  As a user, I want to sign up on the web platform 

  Scenario: A registered person tries to activate their user
    Given An user with name "customer1" is registered on the system
    When The user "customer1" introduces their email "customer1@uca.es" and the verification code "key" 
    Then The user gets a message with the text "User is activated"
    
  Scenario: A non-registered person tries to activate their user
    When The user "customer999" introduces their email "customer999@uca.es" and the verification code "key999" 
    Then The user gets a message with the text "User is not activated"
    
   
