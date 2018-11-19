package com.test.po;

import static test.common.config.ApplicationProperties.ApplicationProperty.BROWSER;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import test.common.config.ApplicationProperties;

public class GoogleHome {
	
	public WebDriver driver = null;
	private final String browser = ApplicationProperties.getString(BROWSER).toUpperCase();

	public GoogleHome(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
	