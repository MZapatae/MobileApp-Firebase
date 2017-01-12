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
    public void formValidator_ValidPassword() {
        String password = "1234";
        boolean valid = FormValidator.isValidPassword(password);
        System.out.print("Password is valid: " + password + " , " + valid);
        Assert.assertEquals(true, valid);
    }

    @Test
    public void formValidator_InvalidPassword() {
        String password = "a";
        boolean valid = FormValidator.isValidPassword(password);
        System.out.print("Password is valid: " + password + " , " + valid);
        Assert.assertEquals(true, valid);
    }

    @Test
    public void formValidator_ValidUsername() {

        String[] validNames = new String[]{
                "mkyong34", "mkyong_2002", "mkyong-2002", "mk3-4_yong"
        };

        for(String temp : validNames){
            boolean valid = FormValidator.isValidUsername(temp);
            System.out.println("Username is valid : " + temp + " , " + valid);
            Assert.assertEquals(true, valid);
        }

    }

    @Test
    public void formValidator_InvalidUserName() {
        String[] invalidNames = new String[]{
                "mk", "mk@yong", "mkyong123456789_-", "  as"
        };

        for(String temp : invalidNames){
            boolean valid = FormValidator.isValidUsername(temp);
            System.out.println("username is valid : " + temp + " , " + valid);
            Assert.assertEquals(false, valid);
        }
    }
}