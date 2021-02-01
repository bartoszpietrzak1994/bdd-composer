package com.bargain.executableSpecifications;

import com.bargain.executableSpecifications.config.CucumberGlue;
import com.bargain.executableSpecifications.config.CucumberSpringObjectFactory;
import io.cucumber.core.cli.Main;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@PropertySource("classpath:/config/application.yml")
public class ExecutableSpecifications {

    public static final List<CucumberGlue> GLUE = List.of(
            CucumberGlue.of("com/bargain/executableSpecifications/steps", "classpath:/features")
    );

    public static void main(String[] args) {
        List<String> inputArguments = new ArrayList<>();

        for (CucumberGlue glue : GLUE) {
            inputArguments.add("-g");
            inputArguments.addAll(glue.getArguments());
        }

        inputArguments.add("--strict");
        inputArguments.add("-p");
        inputArguments.add("pretty");

        inputArguments.add("--tags");
        inputArguments.add("not @ignore");

        inputArguments.add("--object-factory");
        inputArguments.add(CucumberSpringObjectFactory.class.getName());

        Collections.addAll(inputArguments, args);

        Main.main(inputArguments.toArray(String[]::new));
    }
}
