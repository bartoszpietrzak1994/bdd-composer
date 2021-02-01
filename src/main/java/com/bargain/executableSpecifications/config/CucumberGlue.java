package com.bargain.executableSpecifications.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CucumberGlue {
    private List<String> arguments;

    public static CucumberGlue of(String... args) {
        return new CucumberGlue(List.of(args));
    }
}
