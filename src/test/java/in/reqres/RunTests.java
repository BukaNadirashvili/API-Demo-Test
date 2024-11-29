package in.reqres;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "in.reqres",
        plugin = {"pretty", "html:test-output/HtmlReports"}
)

public class RunTests extends AbstractTestNGCucumberTests {
}