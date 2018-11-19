package test.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;

import lombok.extern.slf4j.Slf4j;

import static test.common.config.ApplicationProperties.ApplicationProperty.ENV;

@Slf4j
public class ApplicationProperties {

	@SuppressWarnings("serial")
	private static HashMap<String, Properties> DEFAULT_VALUES = new HashMap<String, Properties>() {

		{
			put("default", new Properties() {
				{
					// timeout and wait time properties
					setProperty(ApplicationProperty.WAIT_SHORT_SECONDS.name, "3");
					setProperty(ApplicationProperty.WAIT_NORMAL_SECONDS.name, "5");
					setProperty(ApplicationProperty.WAIT_LONG_SECONDS.name, "20");
					setProperty(ApplicationProperty.WAIT_TOO_LONG_SECONDS.name, "60");

					setProperty(ApplicationProperty.BROWSER.name, "chrome");

					setProperty(ApplicationProperty.PROXY_ENABLED.name, "false");
					setProperty(ApplicationProperty.PROXY_HOST.name, "proxy.com");
					setProperty(ApplicationProperty.PROXY_PORT.name, "8080");

					// application URL's
					setProperty(ApplicationProperty.APP_URL.name, "https://google.com/");

					// Selenium grid settings
					setProperty(ApplicationProperty.REMOTE_DRIVER.name, "false");
					setProperty(ApplicationProperty.SELENIUM_GRID_URL.name, "http://localhost:4444/wd/hub");

					setProperty(ApplicationProperty.DESIRED_BROWSER_VERSION.name, "");
					setProperty(ApplicationProperty.DESIRED_PLATFORM.name, "");
					setProperty(ApplicationProperty.SELENIUM_GRID_RETRY_COUNT.name, "3");

					// this should be changed for local execution
					setProperty(ApplicationProperty.CHROME_BINARY_PATH.name, "./driver_exe/chromedriver.exe");
					setProperty(ApplicationProperty.IE_BINARY_PATH.name, "./driver_exe/IEDriverServer.exe");
					setProperty(ApplicationProperty.FIREFOX_BINARY_PATH.name, "./driver_exe/geckodriver.exe");
					setProperty(ApplicationProperty.CHROME_DRIVER_PATH.name,
							"src/test/resources/drivers/chromedriver-v2.37.0-win32/chromedriver.exe");
					setProperty(ApplicationProperty.GECKO_DRIVER_PATH.name,
							"src/test/resources/drivers/geckodriver-v0.20.0-win64/geckodriver.exe");
					setProperty(ApplicationProperty.USE_BIRNARIES.name, "true");

					setProperty("admin.username", "useradmin");
					setProperty("admin.password", "testpassword");

				}
			});

		}
	};

	public static void setProperties() throws Exception {

		String currentEnv = System.getProperties().getProperty(ENV.name,
				System.getenv(ENV.name.toUpperCase().replace('.', '_')));
		if (currentEnv.equalsIgnoreCase("system")) {

			Properties systemProp = new Properties();
			String file_Path = Joiner.on(File.separator).join("src", "test", "resources", "system_env.properties");
			try {
				InputStream inputStream = new FileInputStream(file_Path);
				systemProp.load(inputStream);
				DEFAULT_VALUES.put("system", systemProp);
			} catch (IOException e) {
				 log.error("ERROR reading the properties file: " + file_Path);
				throw new RuntimeException("ERROR reading the properties file: " + file_Path + e);
			}

		}
	}

	private static String getString(String propertyName) {
		String currentEnv = System.getProperties().getProperty(ENV.name,
				System.getenv(ENV.name.toUpperCase().replace('.', '_')));

		if (System.getProperties().containsKey(propertyName)) {
			return System.getProperties().getProperty(propertyName);
		}
		if (currentEnv != null) {
			if (DEFAULT_VALUES.get(currentEnv).containsKey(propertyName)) {
				if (StringUtils.isBlank(DEFAULT_VALUES.get(currentEnv).getProperty(propertyName))) {
					if (DEFAULT_VALUES.get("default").containsKey(propertyName)) {
						return DEFAULT_VALUES.get("default").getProperty(propertyName);
					}
				} else {
					return DEFAULT_VALUES.get(currentEnv).getProperty(propertyName);
				}
			}
		}
		if (DEFAULT_VALUES.get("default").containsKey(propertyName)) {
			return DEFAULT_VALUES.get("default").getProperty(propertyName);
		}

		 log.warn("Unknown application property: " + propertyName);
		throw new RuntimeException("Unknown application property: " + propertyName);
	}

	public static String getUsername(String userRole) {
		return getString(userRole + ".username");
	}

	public static String getPassword(String userRole) {
		return getString(userRole + ".password");
	}

	public static String getString(ApplicationProperty property) {
		return getString(property.name);
	}

	public static Integer getInteger(ApplicationProperty property) {
		return Integer.valueOf(getString(property));
	}

	public static boolean getBoolean(ApplicationProperty property) {
		return Boolean.valueOf(getString(property));
	}

	public enum ApplicationProperty {

		ENV("env"), APP_URL("appUrl"), OPENAM_URL("openUrl"), BROWSER("browser"), PROXY_ENABLED(
				"proxyEnabled"), PROXY_HOST("proxyHost"), PROXY_PORT("proxyPort"), WAIT_SHORT_SECONDS(
						"waitShortSeconds"), WAIT_NORMAL_SECONDS("waitNormalSeconds"), WAIT_LONG_SECONDS(
								"waitLongSeconds"), WAIT_TOO_LONG_SECONDS("waitTooLongSeconds"), REMOTE_DRIVER(
										"remoteDriver"), SELENIUM_GRID_RETRY_COUNT(
												"seleniumGridRetries"), SELENIUM_GRID_URL(
														"seleniumGridUrl"), DESIRED_BROWSER_VERSION(
																"desiredBrowserVersion"), DESIRED_PLATFORM(
																		"desiredPlatform"), CHROME_BINARY_PATH(
																				"chromeBinaryPath"), FIREFOX_BINARY_PATH(
																						"firefoxBinaryPath"), IE_BINARY_PATH(
																								"ieBinaryPath"), CHROME_DRIVER_PATH(
																										"chromeDriverPath"), GECKO_DRIVER_PATH(
																												"geckoDriverPath"), USE_BIRNARIES(
																														"useLocalBirnaries"), SSH_USER(
																																"ssh_user"), SSH_PASSWORD(
																																		"ssh_password"), SSH_HOST(
																																				"ssh_host"), SSH_PORT(
																																						"ssh_port"), SSH_SCENARIO_PATH(
																																								"ssh_scenario_path"), SSH_FOLDER_NAME(
																																										"ssh_folder_name"), SSH_DIRECTORY_PATH(
																																												"ssh_directory_path"), EXCELHEADING(
																																														"ExcelHeading"), ACTIVE(
																																																"active"), FTE(
																																																		"FTE"), CLINICAL_ROLE_1(
																																																				"clinical_role_1"), CLINCICAL_ROLE_2(
																																																						"clinical_role_2"), SPECIALITY(
																																																								"speciality");

		private String name;

		ApplicationProperty(String name) {
			this.name = name;
		}
	}
}
