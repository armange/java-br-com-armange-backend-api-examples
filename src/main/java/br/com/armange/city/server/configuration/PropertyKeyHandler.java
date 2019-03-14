package br.com.armange.city.server.configuration;

public class PropertyKeyHandler {
	
	public static final String PROPERTY_SEPARATOR = ".";

	private PropertyKeyHandler() {}
	
	public static String build(final String key) {
		return String.join(PROPERTY_SEPARATOR, ServerProperties.PREFIX, key);
	}
}