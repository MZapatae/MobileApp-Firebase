package cl.mzapatae.mobileFirebase.utils;

import android.text.TextUtils;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 * Created on: 10-01-17
 * E-mail: miguel.zapatae@gmail.com
 */

public class FormValidator {

    public static boolean isValidEmail(String email) {
        return !(email.isEmpty() || !isValidPatternEmail(email));
    }

    public static boolean isValidPassword(String password) {
        return !password.trim().isEmpty();
    }

    private static boolean isValidPatternEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
