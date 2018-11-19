package com.test.stepdefs;

import com.test.po.GoogleHome;

import cucumber.api.java.en.Given;
import test.common.config.ApplicationProperties;
import test.common.config.ApplicationProperties.ApplicationProperty;
import test.common.core.PageObjectManager;
import test.common.utils.UITestUtils;

public class GoogleHomeSteps {

	@SuppressWarnings("unused")
	private GoogleHome googleHomePage;

	public GoogleHomeSteps() throws Exception {

		PageObjectManager pageObjectManager = new PageObjectManager();
		googleHomePage = pageObjectManager.getGoogleHomePage();

	}

	public GoogleHomeSteps(PageObjectManager pageObjectManager) {
		googleHomePage = pageObjectManager.getGoogleHomePage();
	}

	@Given("^user opens google page$")
	public void user_opens_google_page() throws Throwable {
		UITestUtils.launchApplication(ApplicationProperties.getString(ApplicationProperty.APP_URL));

	}

}
