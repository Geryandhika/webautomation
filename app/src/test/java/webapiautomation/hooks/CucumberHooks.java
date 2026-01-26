package webapiautomation.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class CucumberHooks {

    @After
    public void afterScenario(Scenario scenario) {
        // Clean Up Cache/Log
        System.out.println("Finished scenario: " + scenario.getName());
    }
}
