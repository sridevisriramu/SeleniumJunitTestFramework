package test.common.utils;

public enum Apps {

	MAIN_PAGE("Google"),PRODUCTIVITY("facebook"),SITE_NOT_SECURE("This site isn’t secure"),CERTIFICATE_ERROR("Certificate Error: Navigation Blocked"),PAGE_NOT_EXIST("The page you were looking for doesn't exist (404)");

	private final String name;

	private Apps(String eName) {
		name = eName;
	}

	@Override
	public String toString() {
		return this.name;
	}

}


