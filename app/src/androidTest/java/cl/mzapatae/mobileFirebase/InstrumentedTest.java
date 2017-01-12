package cl.mzapatae.mobileFirebase;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cl.mzapatae.mobileFirebase.utils.FormValidator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 * Created on: 10-01-17
 * E-mail: miguel.zapatae@gmail.com
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("cl.mzapatae.mobileapp", appContext.getPackageName());
    }

    @Test
    public void formValidator_CorrectEmail_ReturnsTrue() {
        Assert.assertThat(FormValidator.isValidEmail("mzapatae@icloud.com"), is(true));
    }

    @Test
    public void formValidator_IncorrectEmail_ReturnsFalse() {
        Assert.assertThat(FormValidator.isValidEmail("mzapataeicloud.com"), is(false));
    }
}
