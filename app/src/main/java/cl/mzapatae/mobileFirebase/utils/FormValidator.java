package cl.mzapatae.mobileFirebase.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import cl.mzapatae.mobileFirebase.R;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 * Created on: 10-01-17
 * E-mail: miguel.zapatae@gmail.com
 */

public class FormValidator {

    public static boolean validateEmail(Context context, String email, TextInputLayout inputLayout) {
        if (email.trim().isEmpty() || !isValidEmail(email.trim())) {
            inputLayout.setError(context.getString(R.string.error_invalid_email));
            inputLayout.requestFocus();
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public static boolean validatePassword(Context context, String password, TextInputLayout inputLayout) {
        if (password.trim().isEmpty()) {
            inputLayout.setError(context.getString(R.string.error_blank_password));
            inputLayout.requestFocus();
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean validateFirstName(Context context, String name, TextInputLayout inputLayout) {
        if (name.trim().isEmpty()) {
            inputLayout.setError(context.getString(R.string.error_blank_first_name));
            inputLayout.requestFocus();
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
