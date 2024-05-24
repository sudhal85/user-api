package com.herokuapp.testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/createUser1.feature",
		glue = {"com.herokuapp.stepDefinition"},
		monochrome = true,
		
		plugin = {"pretty", "json:target/jsonReports/cucumber-report.json"}
//       		       "pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
//		           "pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		)
public class TestRunner {

}
