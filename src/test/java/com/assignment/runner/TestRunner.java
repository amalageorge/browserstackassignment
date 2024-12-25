package com.assignment.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.assignment.steps",
        plugin = {"pretty", "html:target/cucumber-reports"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    public static ThreadLocal<String> useBrowserStack = new ThreadLocal<>();
    public static ThreadLocal<String> browser = new ThreadLocal<>();
    public static ThreadLocal<String> browserVersion = new ThreadLocal<>();
    public static ThreadLocal<String> os = new ThreadLocal<>();
    public static ThreadLocal<String> osVersion = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    @Parameters({"useBrowserStack", "browser", "browserVersion", "os", "osVersion"})
    public void setupTestNGParameters(String useBS, String br, String brVersion, String operatingSystem, String osVer) {
        useBrowserStack.set(useBS);
        browser.set(br);
        browserVersion.set(brVersion);
        os.set(operatingSystem);
        osVersion.set(osVer);
    }
}
