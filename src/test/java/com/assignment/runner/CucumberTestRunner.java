package com.assignment.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.assignment.steps",
        plugin = {"pretty", "html:target/cucumber-reports"},
        monochrome = true
)
public class CucumberTestRunner {
}