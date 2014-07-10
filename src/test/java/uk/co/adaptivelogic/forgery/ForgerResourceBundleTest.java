package uk.co.adaptivelogic.forgery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ForgerResourceBundleTest {
	private ForgerDataSource<String> classUnderTest;

	@Before
	public void setUp() {
		classUnderTest = new ForgerResourceBundle.Builder().name("FirstNameStringForger").build();
	}

	@Test
	public void shouldGetValuesEnglish() {
		// Given
		Locale locale = Locale.ENGLISH;

		// When
		Set<String> values = classUnderTest.getValues(locale);

		// Then
		assertThat(values.size(), equalTo(5));
		assertTrue(values.contains("Dave"));
	}

	@Test
	public void shouldGetValuesFrench() {
		// Given
		Locale locale = Locale.FRENCH;

		// When
		Set<String> values = classUnderTest.getValues(locale);

		// Then
		assertThat(values.size(), equalTo(2));
		assertTrue(values.contains("Claude"));
	}

	@Test
	public void shouldFindDefaultResourceBundle() {
		// Given
		Locale locale = Locale.GERMAN;

		// When
		Set<String> values = classUnderTest.getValues(locale);

		// Then
		assertThat(values.size(), equalTo(5));
		assertTrue(values.contains("Dave"));
	}

	@Test (expected = MissingResourceException.class)
	public void shouldThrowExceptionIfNoBundleFound() {
		// Given
		classUnderTest = new ForgerResourceBundle.Builder().name("String").build();
		Locale locale = Locale.ENGLISH;

		// When
		classUnderTest.getValues(locale);
	}
}
