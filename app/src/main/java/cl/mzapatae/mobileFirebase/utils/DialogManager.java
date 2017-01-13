package cl.mzapatae.mobileFirebase.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import cl.mzapatae.mobileFirebase.R;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 *          Created on: 10-01-17
 *          E-mail: miguel.zapatae@gmail.com
 */

public class DialogManager {
    public static void showAlertSimple(Context context, int message_resource) {
        new MaterialDialog.Builder(context)
                .title(R.string.alert_title_error)
                .content(message_resource)
                .positiveText(R.string.alert_button_accept)
                .show();
    }

    public static void showAlertSimple(Context context, String message) {
        new MaterialDialog.Builder(context)
                .title(R.string.alert_title_error)
                .content(message)
                .positiveText(R.string.alert_button_accept)
                .show();
    }
}
