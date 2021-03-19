package com.bargain.executableSpecifications.steps;

import com.bargain.executableSpecifications.service.SharedStorage;
import com.bargain.executableSpecifications.service.email.client.FakeSmtpServerClient;
import com.bargain.executableSpecifications.service.email.dto.EmailDto;
import com.bargain.notification.client.ChannelClient;
import com.bargain.notification.client.dto.NotificationChannel;
import com.bargain.notification.client.dto.request.CreateChannelRequest;
import com.bargain.users.client.SubscriptionClient;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.UserDto;
import com.bargain.users.client.dto.request.SubscribeRequestDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificationSteps {

    @Autowired
    private ChannelClient channelClient;

    @Autowired
    private FakeSmtpServerClient fakeSmtpServerClient;

    @Autowired
    private SharedStorage sharedStorage;

    @Autowired
    private SubscriptionClient subscriptionClient;

    @Given("^notification channel (.*) is active$")
    public void notification_channel_is_active(String notificationChannel) {
        createNotificationChannel(NotificationChannel.valueOf(notificationChannel));
    }

    @When("^(.*) subscribes for the (.*) subscription$")
    public void user_subscribes_for_package(String name, String subscription) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        SubscriptionStatus subscriptionStatus = SubscriptionStatus.of(subscription);
        subscribe(subscriptionStatus, user.getRef());
    }

    @Then("^notification (.*) should be sent to (.*)")
    public void notification_should_be_sent(String message, String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        List<EmailDto> emailsForReceiver = fakeSmtpServerClient.getEmailsForReceiver(user.getEmail());
        assertThat(emailsForReceiver).hasSize(1);
        EmailDto email = emailsForReceiver.get(0);
        assertThat(email.getText()).contains(message);
    }

    private void createNotificationChannel(NotificationChannel notificationChannel) {
        CreateChannelRequest createChannelRequest = CreateChannelRequest
                .builder()
                .notificationChannel(notificationChannel)
                .enabled(true)
                .build();

        channelClient.create(createChannelRequest);
    }

    private void subscribe(SubscriptionStatus subscriptionStatus, String userRef) {
        SubscribeRequestDto subscribeRequest = SubscribeRequestDto
                .builder()
                .subscriptionStatus(subscriptionStatus)
                .userRef(userRef)
                .build();

        subscriptionClient.subscribe(subscribeRequest);
    }
}
