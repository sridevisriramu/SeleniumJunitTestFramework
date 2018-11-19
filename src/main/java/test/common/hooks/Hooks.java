package test.common.hooks;


import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.common.base.Joiner;
import com.test.po.GoogleHome;

import test.common.config.ApplicationProperties;
import test.common.core.DriverBase;
import test.common.core.PageObjectManager;
import test.common.utils.UITestUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {
	
	private GoogleHome googleHome;
	
	
	
//	private AppToolbar appToolbar;
//	private AdminSiteConfig adminSiteConfig;
//
//	public Hooks(PageObjectManager pageObjectManager) {
//		
//		googleHome = pageObjectManager.getGoogleHomePage();
//		
//	}
//		sweepToolbar = pageObjectManager.getSweepToolbarPage();
//		adminSiteConfig = pageObjectManager.getAdminSiteConfigPage();
//
//	}
//
//	@After("@51461_TabsActivation")
//	public void tearDownTabsActivation() throws Exception {
//		sweepToolbar.navigateToAdminTab();
//		adminSiteConfig.clickOnSubMenOfMenuTab("Site Configuration");
//		adminSiteConfig.activateTab("Uncompleted", true);
//		adminSiteConfig.activateTab("Unsigned", true);
//		adminSiteConfig.activateTab("Undictated", true);
//		log.info("Reset all tabs to active");
//	}
	
	
    @Before("@Browser")
    public void setup(Scenario scenario) throws Exception {
        MDC.put("scenarioId", "scenarioId:" + UUID.randomUUID().toString());
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator jc = new JoranConfigurator();
		jc.setContext(context);
		context.reset(); 
		context.putProperty("scenario-name", MDC.get("scenarioId"));
		jc.doConfigure("./src/test/resources/logback-test.xml");
        ApplicationProperties.setProperties();
        DriverBase.instantiateDriverObject();
        String sessionId = ((RemoteWebDriver) DriverBase.getDriver()).getSessionId().toString();
        log.info("Starting Scenario: \"" + scenario.getName() + "\" with Session ID: " + sessionId + "\" with " + MDC.get("scenarioId"));
        DriverBase.getDriver().manage().deleteAllCookies();
        DriverBase.getDriver().manage().window().maximize();
    }

    @After("@Browser")
    public void tearDown(Scenario scenario) throws Exception {
            try {
            	if (scenario.isFailed()) {
            	byte[] screenshot = ((TakesScreenshot) DriverBase.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
                UITestUtils.takeSnapShot(scenario.getName());
            	}
            	
            	log.info(String.format("Ending Scenario: \"%s\"", scenario.getName()) + " result: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
            	
                String file_name = MDC.get("scenarioId") + ".log";
				String file_Path = Joiner.on(File.separator).join("logs", file_name);
				File file = new File("logs/" + file_name);
				String log = FileUtils.readFileToString(file, "UTF-8");
				scenario.write(log);
            } catch (WebDriverException wde) {
            	log.error(wde.getMessage());
            } catch (ClassCastException cce) {
            	log.error(cce.getMessage());
            }
        log.info(String.format("Ending Scenario: \"%s\"", scenario.getName()) + " result: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
        DriverBase.closeDriverObjects();
    }
    
    @Before("@Platform")
    public void setupPlatform(Scenario scenario) throws Exception {
        MDC.put("scenarioId", "scenarioId:" + UUID.randomUUID().toString());
        ApplicationProperties.setProperties();
       
    }

    @After("@Platform")
    public void tearDownPlatform(Scenario scenario) throws Exception {
        log.info(String.format("Ending Scenario: \"%s\"", scenario.getName()) + " result: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
    }
}
