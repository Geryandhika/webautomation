package webapiautomation;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "webapiautomation",
        plugin = {"pretty"},
        tags = "not @ignore"
)
public class RunCucumberTest {
}