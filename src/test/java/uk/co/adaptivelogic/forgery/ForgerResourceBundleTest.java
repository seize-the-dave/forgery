package uk.co.adaptivelogic.forgery;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ForgerResourceBundleTest {
	private ForgerDataSource classUnderTest;

	@Before
	public void setUp() {
		classUnderTest = new ForgerResourceBundle();
	}
	
	@Test
	public void shouldGetValuesEnglish() {
		// Given
		Locale locale = Locale.ENGLISH;
		
		// When
		Set<String> values = classUnderTest.getValues(FirstNameStringForger.class, locale);

		// Then
		assertThat(values.size(), is(5));
		assertTrue(values.contains("Dave"));
	}
	
	@Test
	public void shouldFindResourceBundleEnglish() {
		// Given
		Locale locale = Locale.ENGLISH;
		
		// When
		Set<String> values = classUnderTest.getValues(FirstNameStringForger.class, locale);

		// Then
		assertThat(values.size(), is(5));
		assertTrue(values.contains("Dave"));
	}
	
	@Test
	public void shouldFindResourceBundleFrench() {
		// Given
		Locale locale = Locale.FRENCH;
		
		// When
		Set<String> values = classUnderTest.getValues(FirstNameStringForger.class, locale);

		// Then
		assertThat(values.size(), is(2));
		assertTrue(values.contains("Claude"));
	}
	
	@Test
	public void shouldFindResourceBundleDefault() {
		// Given
		Locale locale = Locale.GERMAN;
		
		// When
		Set<String> values = classUnderTest.getValues(FirstNameStringForger.class, locale);

		// Then
		assertThat(values.size(), is(5));
		assertTrue(values.contains("Dave"));
	}
	
	@Test (expected = MissingResourceException.class)
	public void shouldCopeFindResourceBundleDefault() {
		// Given
		Locale locale = Locale.ENGLISH;
		
		// When
		Set<String> values = classUnderTest.getValues(String.class, locale);
	}
}
