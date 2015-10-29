package uk.co.adaptivelogic.forgery.forger;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Test for {@link RandomCreditCardForger} and the rules around forging valid credit card numbers
 */
public class RandomCreditCardForgerTest {

    @Test
    public void shouldProvideARandomValidCreditCardNumberWithTheCorrectIIN() {
        // When
        String creditCardNumber = new RandomCreditCardForger().get();

        // Then
        assertThat(creditCardNumber,
                anyOf(startsWith("4"), startsWith("51"), startsWith("52")));
    }

    @Test
    public void shouldProvideARandomValidCreditCardNumberWithTheCorrectLength() {
        // When
        String creditCardNumber = new RandomCreditCardForger().get();

        // Then
        assertThat(creditCardNumber.length(), is(16));
    }

    @Test
    public void shouldProvideARandomValidCreditCardNumberWithCorrectLuhnCheckDigit() {
        /// When
        String creditCardNumber = new RandomCreditCardForger().get();

        // Then
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardNumber),
                is(true));
    }

}