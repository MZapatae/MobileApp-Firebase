package cl.mzapatae.mobileFirebase;

import org.junit.Assert;
import org.junit.Test;
import java.util.regex.Pattern;

import cl.mzapatae.mobileFirebase.utils.FormValidator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 *          Created on: 10-01-17
 *          E-mail: miguel.zapatae@gmail.com
 */

public class LoginFormValidatorTest {
    @Test
    public void formValidator_CorrectEmail_ReturnsTrue() {
        Assert.assertThat(FormValidator.isValidEmail("mzapatae@icloud.com"), is(true));
        //assertEquals(true, FormValidator.isValidEmail("mzapatae@icloud.com"));
        //assertEquals(false, FormValidator.isValidEmail("    mzapatae@icloud.com"));
    }
}