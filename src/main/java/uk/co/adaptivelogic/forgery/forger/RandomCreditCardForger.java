package uk.co.adaptivelogic.forgery.forger;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import uk.co.adaptivelogic.forgery.Property;

import javax.inject.Provider;

import static java.lang.String.format;
import static org.apache.commons.lang.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@Property({"creditCardNumber", "pan", "bankCardNumber"})
public class RandomCreditCardForger implements Provider<String> {

    public String get() {
        CreditCard[] creditCards = CreditCard.values();
        return creditCards[nextInt(creditCards.length)].generateRandomCreditCardNumber();
    }

    private enum CreditCard {

        VISA(16, "4"),
        MASTERCARD(16, "51", "52");

        private final Integer length;
        private final String[] issuerIdentificationRange;

        CreditCard(Integer length,
                   String... issuerIdentificationRange) {

            this.length = length;
            this.issuerIdentificationRange = issuerIdentificationRange;
        }

        public String generateRandomCreditCardNumber() {

            String issuerIdentificationNumber = randomIssuerIdentificationNumber();

            String cardNumberWithoutCheckDigit =
                    format("%s%s",
                            issuerIdentificationNumber,
                            randomNumeric(length - issuerIdentificationNumber.length() - 1));
            try {

                String checkDigit = LuhnCheckDigit.LUHN_CHECK_DIGIT.calculate(cardNumberWithoutCheckDigit);

                return format("%s%s", cardNumberWithoutCheckDigit, checkDigit);

            } catch (CheckDigitException e) {
                throw new RuntimeException(e);
            }
        }

        private String randomIssuerIdentificationNumber() {
            return issuerIdentificationRange[nextInt(issuerIdentificationRange.length)];
        }
    }
}
