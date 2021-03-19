package com.bargain.executableSpecifications.steps;

import com.bargain.executableSpecifications.service.SharedStorage;
import com.bargain.users.client.AuthClient;
import com.bargain.users.client.SubscriptionClient;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.UserDto;
import com.bargain.users.client.dto.request.RegisterRequestDto;
import com.bargain.users.client.dto.response.RegisterResponseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CompositeSteps {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private SharedStorage sharedStorage;

    @Autowired
    private SubscriptionClient subscriptionClient;

    @Given("^(.*) is an already registered customer with email address (.*) and password (.*)$")
    public void is_already_registered_customer_with_email_address_and_password(String name, String email, String password) {
        UserDto user = UserDto.builder().email(email).password(password).build();
        String userReference = registerUser(user);
        user.setRef(userReference);
        sharedStorage.set(String.format("%s_%s", "user", name), user);
    }

    @Then("^(.*) should have an active (.*) subscription$")
    public void should_have_package_activated(String name, String subscriptionName) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        SubscriptionStatus subscriptionStatus = fetchUserSubscription(user);
        assertThat(subscriptionStatus).isEqualTo(SubscriptionStatus.of(subscriptionName));
    }

    private String registerUser(UserDto userDto) {
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        RegisterResponseDto registerResponse = authClient.register(registerRequestDto);
        return registerResponse.getRef();
    }

    private SubscriptionStatus fetchUserSubscription(UserDto userDto) {
        return subscriptionClient.getSubscription(userDto.getRef());
    }
}
