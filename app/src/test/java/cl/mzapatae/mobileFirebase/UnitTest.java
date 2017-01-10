package cl.mzapatae.mobileFirebase;

import org.junit.Test;

import cl.mzapatae.mobileFirebase.utils.Constants;

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
}