package uk.co.adaptivelogic.forgery;

import java.util.Date;

/**
 * Class to encapsulate the default properties that Forgery supports
 */
public class DefaultPojo {
    private Date plainOldDate;
    private Double plainOldDouble;
    private Integer plainOldInteger;
    private Integer plainOldLong;

    private String creditCardNumber;
    private Date dateOfBirth;
    private String nationalInsurance;
    private String socialSecurityNumber;

    public Date getPlainOldDate() {
        return plainOldDate;
    }

    public void setPlainOldDate(Date plainOldDate) {
        this.plainOldDate = plainOldDate;
    }

    public Double getPlainOldDouble() {
        return plainOldDouble;
    }

    public void setPlainOldDouble(Double plainOldDouble) {
        this.plainOldDouble = plainOldDouble;
    }

    public Integer getPlainOldInteger() {
        return plainOldInteger;
    }

    public void setPlainOldInteger(Integer plainOldInteger) {
        this.plainOldInteger = plainOldInteger;
    }

    public Integer getPlainOldLong() {
        return plainOldLong;
    }

    public void setPlainOldLong(Integer plainOldLong) {
        this.plainOldLong = plainOldLong;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalInsurance() {
        return nationalInsurance;
    }

    public void setNationalInsurance(String nationalInsurance) {
        this.nationalInsurance = nationalInsurance;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
