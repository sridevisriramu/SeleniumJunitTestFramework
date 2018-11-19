package com.test.setup;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		format   = {"pretty","html:target/cucumber", "json:target/cucumber.json","rerun:target/rerun_featureName.txt"},
		features = {"src/test/resources"},								
		strict = true,
	    monochrome = true,
	    dryRun=true,
        glue = {"com.test.stepdefs","test.common.hooks"}
    	//,tags = {"@Browser"}
        
		)
public class AppRunnerSanityNonParallelTest {

}
