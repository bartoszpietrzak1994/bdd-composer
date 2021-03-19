package com.bargain.executableSpecifications.service.email.client;

import com.bargain.executableSpecifications.service.email.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Component
public class FakeSmtpServerClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${email-server.get-emails-url}")
    private String getEmailsUrl;

    public List<EmailDto> getEmailsForReceiver(String emailAddress) {
        String url = UriComponentsBuilder.fromHttpUrl(getEmailsUrl)
                .queryParam("to", emailAddress)
                .build().toString();

        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, EmailDto[].class).getBody()));
    }
}
