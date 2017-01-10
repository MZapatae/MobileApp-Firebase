package cl.mzapatae.mobileFirebase.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import cl.mzapatae.mobileFirebase.R;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 * Created on: 10-01-17
 * E-mail: miguel.zapatae@gmail.com
 */

public class CustomTextWatcher implements TextWatcher {
    private static final String TAG = "TextWatcher";

    private Context mContext;
    private TextInputLayout mInputLayout;
    private View mView;

    public CustomTextWatcher(Context context, View view, TextInputLayout inputLayout) {
        this.mContext = context;
        this.mView = view;
        this.mInputLayout = inputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {/*
        boolean statusValidation;
        switch (mView.getId()) {
            case R.id.edit_email:
                Log.d(TAG, "Validate Email: \"" + editable.toString() + "\"");
                statusValidation = FormValidator.validateEmail(mContext, editable.toString(), mInputLayout);
                Log.d(TAG, "Validation Pass: " + statusValidation);
                break;
            case R.id.edit_password:
                Log.d(TAG, "Validate Password: \"" + editable.toString() + "\"");
                statusValidation = FormValidator.validatePassword(mContext, editable.toString(), mInputLayout);
                Log.d(TAG, "Validation Pass: " + statusValidation);
                break;
        }*/
    }
}
