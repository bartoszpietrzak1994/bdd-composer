package com.bargain.executableSpecifications.steps;

import com.bargain.executableSpecifications.service.SharedStorage;
import com.bargain.users.client.AuthClient;
import com.bargain.users.client.SubscriptionClient;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.UserDto;
import com.bargain.users.client.dto.request.LoginRequestDto;
import com.bargain.users.client.dto.request.RegisterRequestDto;
import com.bargain.users.client.dto.response.RegisterResponseDto;
import io.cucumber.core.backend.CucumberBackendException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {

    private static final String USER_STORAGE_KEY_FORMAT = "%s_%s";

    @Autowired
    private AuthClient authClient;

    @Autowired
    private SubscriptionClient subscriptionClient;

    @Autowired
    private SharedStorage sharedStorage;

    @Given("^there is a customer (.*) with email address (.*) and password (.*)$")
    public void there_is_a_customer_with_email_address(String name, String email, String password) {
        UserDto user = UserDto.builder().email(email).password(password).build();
        sharedStorage.set(String.format(USER_STORAGE_KEY_FORMAT, "user", name), user);
    }

    @When("^(.*) signs up$")
    public void customer_signs_up(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format(USER_STORAGE_KEY_FORMAT, "user", name));
        String userReference = registerUser(user);
        user.setRef(userReference);
        sharedStorage.set(String.format(USER_STORAGE_KEY_FORMAT, "user", name), user);
    }

    @Then("^(.*)'s account should be created$")
    public void account_should_be_created(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format(USER_STORAGE_KEY_FORMAT, "user", name));

        try {
            authenticateUser(user);
        } catch (Exception e) {
            throw new CucumberBackendException(
                    String.format("Authentication failed for user with ref %s", user.getRef()));
        }
    }

    private String registerUser(UserDto userDto) {
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        RegisterResponseDto registerResponse = authClient.register(registerRequestDto);
        return registerResponse.getRef();
    }

    private void authenticateUser(UserDto userDto) {
        LoginRequestDto loginRequest = LoginRequestDto.builder().email(userDto.getEmail()).password(userDto.getPassword())
                .build();
        authClient.login(loginRequest);
    }
}
