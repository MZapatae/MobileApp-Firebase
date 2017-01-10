package cl.mzapatae.mobileFirebase.utils;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import cl.mzapatae.mobileFirebase.R;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 * Created on: 10-01-17
 * E-mail: miguel.zapatae@gmail.com
 */

public class FormValidator {
    private static final String TAG = "Form Validator";

    public static boolean validateEmail(Context context, String email, TextInputLayout inputLayout) {
        email = email.trim();
        Log.d(TAG, "Validate Email: " + email);

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayout.setError(context.getString(R.string.error_invalid_email));
            inputLayout.requestFocus();
            Log.d(TAG, "Validation: Fail!");
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        Log.d(TAG, "Validation: Success!");
        return true;
    }

    public static boolean validatePassword(Context context, String password, TextInputLayout inputLayout) {
        password = password.trim();
        Log.d(TAG, "Validate Password...");

        if (password.trim().isEmpty()) {
            inputLayout.setError(context.getString(R.string.error_blank_password));
            inputLayout.requestFocus();
            Log.d(TAG, "Validation: Fail!");
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }
        Log.d(TAG, "Validation: Success!");
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
