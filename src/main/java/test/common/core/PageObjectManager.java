package test.common.core;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.po.GoogleHome;

public class PageObjectManager {

	protected WebDriver driver;
	private WebDriverWait wait;

	private GoogleHome googleHome;

	public PageObjectManager() throws Exception {
		this.driver = DriverBase.getDriver();
	}

	 public final Map<String, Object> context = new HashMap<String, Object>();
	
	 public <Value> PageObjectManager setValue(final String field, final Value value) {
	 context.put(field, value);
	
	 return this;
	 }
	
	 @SuppressWarnings("unchecked")
	 public <Value> Value getValue(final String field) {
	 return (Value) context.get(field);
	 }

	public GoogleHome getGoogleHomePage() {
		// TODO Auto-generated method stub
		return (googleHome == null) ? googleHome = new GoogleHome(driver) : googleHome;
	}

	
}