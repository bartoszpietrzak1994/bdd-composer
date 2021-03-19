package com.bargain.executableSpecifications.service.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailPersonDto {

    private List<EmailPersonValueDto> value;
    private String html;
    private String text;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class EmailPersonValueDto {

        private String address;
        private String name;
    }
}
