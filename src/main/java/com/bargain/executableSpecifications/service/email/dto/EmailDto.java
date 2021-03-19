package com.bargain.executableSpecifications.service.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDto {

    private List<Object> attachments;
    private List<HeaderLineDto> headerLines;
    private String text;
    private String textAsHtml;
    private String subject;
    private String date;
    private EmailPersonDto to;
    private EmailPersonDto from;
    private String messageId;
    private Boolean html;
}
