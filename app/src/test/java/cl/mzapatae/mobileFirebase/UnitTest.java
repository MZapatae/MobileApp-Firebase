package cl.mzapatae.mobileFirebase;

import org.junit.Assert;
import org.junit.Test;

import cl.mzapatae.mobileFirebase.utils.Constants;
import cl.mzapatae.mobileFirebase.utils.FormValidator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 *          Created on: 10-01-17
 *          E-mail: miguel.zapatae@gmail.com
 */

public class UnitTest {
    @Test
    public void PackageConstants() throws Exception {
        assertThat(Constants.PACKAGE_APP, is("cl.mzapatae.mobileFirebase"));
    }

    @Test
    public void formValidator_Valid_Email() {
        String[] validEmails = new String[]{
                "mzapatae@gmail.com",
                "testmail@yahoo.com",
                "validmail@aol.cl"
        };

        for(String value : validEmails) {
            boolean result = FormValidator.isValidEmail(value);
            System.out.println("Check Email: " + value + " , " + result);
            Assert.assertEquals(true, result);
        }
    }

    @Test
    public void formValidator_Invalid_Email() {
        String[] invalidEmails = new String[]{
                "mzapataegmail.com",
                "@yahoo.com",
                "validmail@aol"
        };

        for(String value : invalidEmails) {
            boolean result = FormValidator.isValidEmail(value);
            System.out.println("Check Email: " + value + " , " + result);
            Assert.assertEquals(false, result);
        }
    }

    @Test
    public void formValidator_Valid_Password() {
        String[] validPass = new String[]{
                "asdf15",
                "mypassword123",
                "11mypass11"
        };

        for(String value : validPass) {
            boolean result = FormValidator.isValidPassword(value);
            System.out.println("Check Password: " + value + " , " + result);
            Assert.assertEquals(true, result);
        }
    }

    @Test
    public void formValidator_Invalid_Password() {
        String[] invalidPass = new String[]{
                "111111111",
                "pass1",
                "passwo",
                "password"
        };

        for(String value : invalidPass) {
            boolean result = FormValidator.isValidPassword(value);
            System.out.println("Check Password: " + value + " , " + result);
            Assert.assertEquals(false, result);
        }
    }

    @Test
    public void formValidator_Valid_Name() {
        String[] validName = new String[]{
                "Camilo",
                "Miguel Angel",
                "Peter Müller",
                "François Hollande",
                "Patrick O'Brian",
                "Silvana Koch-Mehrin"
        };

        for(String value : validName) {
            boolean result = FormValidator.isValidName(value);
            System.out.println("Check Name: " + value + " , " + result);
            Assert.assertEquals(true, result);
        }
    }

    @Test
    public void formValidator_Invalid_Name() {
        String[] invalidName = new String[]{
                "",
                "    ",
                "Peter324",
                "François #¢¬",
                "|#∞÷¬∞¢"
        };

        for(String value : invalidName) {
            boolean result = FormValidator.isValidName(value);
            System.out.println("Check Name: " + value + " , " + result);
            Assert.assertEquals(false, result);
        }
    }

    @Test
    public void formValidator_Valid_LastName() {
        String[] validName = new String[]{
                "Zapata",
                "Müller",
                "Hollande",
                "O'Brian",
                "Koch-Mehrin"
        };

        for(String value : validName) {
            boolean result = FormValidator.isValidLastName(value);
            System.out.println("Check Lastname: " + value + " --> " + result);
            Assert.assertEquals(true, result);
        }
    }

    @Test
    public void formValidator_Invalid_LastName() {
        String[] invalidName = new String[]{
                "",
                "    ",
                "Peter324",
                "François #¢¬",
                "|#∞÷¬∞¢"
        };

        for(String value : invalidName) {
            boolean result = FormValidator.isValidLastName(value);
            System.out.println("Check Lastname: " + value + " , " + result);
            Assert.assertEquals(false, result);
        }
    }

    @Test
    public void formValidator_Valid_Username() {
        String[] validName = new String[]{
                "MZapatae",
                "Leslat",
                "Rootman",
                "Oman0",
                "Koch_Mehrin"
        };

        for(String value : validName) {
            boolean result = FormValidator.isValidUsername(value);
            System.out.println("Check Username: " + value + " , " + result);
            Assert.assertEquals(true, result);
        }
    }

    @Test
    public void formValidator_Invalid_Username() {
        String[] invalidName = new String[]{
                "",
                "   ace",
                "Peter32465Hertym",
                "François #¢¬",
                "_|#∞÷¬∞¢"
        };

        for(String value : invalidName) {
            boolean result = FormValidator.isValidUsername(value);
            System.out.println("Check Username: " + value + " , " + result);
            Assert.assertEquals(false, result);
        }
    }

    @Test
    public void formValidator_Valid_MobilePhone() {
        String[] validPhone = new String[]{
                "54013454",
                "79495823",
                "98345819"
        };

        for(String value : validPhone) {
            boolean result = FormValidator.isValidMobilePhone(value);
            System.out.println("Check Phone: " + value + " , " + result);
            Assert.assertEquals(true, result);
        }
    }

    @Test
    public void formValidator_Invalid_MobilePhone() {
        String[] invalidPhone = new String[]{
                "",
                "   432",
                "+5695403",
                "+56923456789",
                "956928345"
        };

        for(String value : invalidPhone) {
            boolean result = FormValidator.isValidUsername(value);
            System.out.println("Check Phone: " + value + " , " + result);
            Assert.assertEquals(false, result);
        }
    }
}