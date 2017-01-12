package cl.mzapatae.mobileFirebase.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String regx = "^[a-z0-9_-]{3,15}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(password);
        return (matcher.matches() && isEmptyText(password));
    }

    public static boolean isValidName(String name) {

        String regx = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";  //Minimum 6 characters at least 1 Alphabet and 1 Number
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return (matcher.find() && isEmptyText(name));
    }

    public static boolean isValidLastName(String lastName) {
        String regx = "^[\\\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(lastName);
        return (matcher.find() && isEmptyText(lastName));
    }

    public static boolean isValidUsername(String username) {
        String regx = "^[a-z0-9_-]{3,15}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(username);
        return (matcher.matches());
    }

    public static boolean isValidMobile(String phone) {
        String regex = "[0-9]+";
        return phone.length() == 10 && phone.matches(regex);
    }

    private static boolean isEmptyText(String text) {
        return text.trim().isEmpty();
    }

    private static boolean isValidPatternEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

