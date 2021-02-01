package com.bargain.executableSpecifications.config;

import com.bargain.executableSpecifications.ExecutableSpecifications;
import io.cucumber.core.backend.ObjectFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;
import java.util.HashSet;

public class CucumberSpringObjectFactory implements ObjectFactory {

    private final Collection<Class<?>> stepClasses = new HashSet<>();
    private ConfigurableApplicationContext applicationContext;

    @Override
    public boolean addClass(final Class<?> stepClass) {
        if (!stepClasses.contains(stepClass)) {
            stepClasses.add(stepClass);
        }
        return true;
    }

    @Override
    public void start() {
        if (applicationContext != null) {
            return;
        }
        applicationContext = SpringApplication.run(ExecutableSpecifications.class);

        for (Class<?> glueClass : stepClasses) {
            String beanName = glueClass.getName();
            if (applicationContext.containsBeanDefinition(beanName)) {
                return;
            }
            ((BeanDefinitionRegistry) applicationContext).registerBeanDefinition(beanName, BeanDefinitionBuilder
                    .genericBeanDefinition(glueClass)
                    .getBeanDefinition()
            );
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public <T> T getInstance(final Class<T> type) {
        return applicationContext.getBean(type);
    }
}
