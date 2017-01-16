package cl.mzapatae.mobileFirebase.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.mzapatae.mobileFirebase.R;
import cl.mzapatae.mobileFirebase.activities.MainActivity;
import cl.mzapatae.mobileFirebase.objets.User;
import cl.mzapatae.mobileFirebase.utils.DialogManager;
import cl.mzapatae.mobileFirebase.utils.FormValidator;
import cl.mzapatae.mobileFirebase.utils.LocalStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = "MobileApp - Register";

    @BindView(R.id.text_title) TextView mTextTitle;
    @BindView(R.id.edit_name) TextInputEditText mEditName;
    @BindView(R.id.edit_lastname) TextInputEditText mEditLastname;
    @BindView(R.id.edit_email) TextInputEditText mEditEmail;
    @BindView(R.id.edit_username) TextInputEditText mEditUsername;
    @BindView(R.id.edit_password) TextInputEditText mEditPassword;
    @BindView(R.id.button_register) Button mButtonRegister;
    @BindView(R.id.edit_layout_name) TextInputLayout mEditLayoutName;
    @BindView(R.id.edit_layout_lastname) TextInputLayout mEditLayoutLastname;
    @BindView(R.id.edit_layout_email) TextInputLayout mEditLayoutEmail;
    @BindView(R.id.edit_layout_username) TextInputLayout mEditLayoutUsername;
    @BindView(R.id.edit_layout_password) TextInputLayout mEditLayoutPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContext;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FireBaseAuthListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @OnClick({R.id.button_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                if (isRegisterValid()) {
                    registerUser(mEditEmail.getText().toString(), mEditPassword.getText().toString());
                }
                break;
        }
    }

    private boolean isRegisterValid() {
        String nameValue = mEditName.getText().toString().trim();
        String lastnameValue = mEditLastname.getText().toString().trim();
        String usernameValue = mEditUsername.getText().toString().trim();
        String emailValue = mEditEmail.getText().toString().trim();
        String passwordValue = mEditPassword.getText().toString().trim();

        mEditName.setText(nameValue);
        mEditLastname.setText(lastnameValue);
        mEditUsername.setText(usernameValue);
        mEditEmail.setText(emailValue);
        mEditPassword.setText(passwordValue);

        Log.d(TAG, "Validate Name: " + nameValue);
        if (!FormValidator.isValidName(nameValue)) {
            mEditLayoutName.setError(getString(R.string.error_invalid_first_name));
            mEditLayoutName.requestFocus();
            mEditName.setSelection(nameValue.length());
            Log.d(TAG, "Validation: Fail!");
            Log.i(TAG, "Login Form is Invalid!");
            return false;
        } else {
            Log.d(TAG, "Validation: Success!");
            mEditLayoutName.setErrorEnabled(false);
        }

        Log.d(TAG, "Validate LastName: " + lastnameValue);
        if (!FormValidator.isValidLastName(lastnameValue)) {
            mEditLayoutLastname.setError(getString(R.string.error_invalid_last_name));
            mEditLayoutLastname.requestFocus();
            mEditLastname.setSelection(lastnameValue.length());
            Log.d(TAG, "Validation: Fail!");
            Log.i(TAG, "Login Form is Invalid!");
            return false;
        } else {
            Log.d(TAG, "Validation: Success!");
            mEditLayoutLastname.setErrorEnabled(false);
        }

        Log.d(TAG, "Validate Username: " + usernameValue);
        if (!FormValidator.isValidUsername(usernameValue)) {
            mEditLayoutUsername.setError(getString(R.string.error_invalid_user_name));
            mEditLayoutUsername.requestFocus();
            mEditUsername.setSelection(usernameValue.length());
            Log.d(TAG, "Validation: Fail!");
            Log.i(TAG, "Login Form is Invalid!");
            return false;
        } else {
            Log.d(TAG, "Validation: Success!");
            mEditLayoutUsername.setErrorEnabled(false);
        }

        Log.d(TAG, "Validate Email: " + emailValue);
        if (!FormValidator.isValidEmail(emailValue)) {
            mEditLayoutEmail.setError(getString(R.string.error_invalid_email));
            mEditLayoutEmail.requestFocus();
            mEditEmail.setSelection(emailValue.length());
            Log.d(TAG, "Validation: Fail!");
            Log.i(TAG, "Login Form is Invalid!");
            return false;
        } else {
            Log.d(TAG, "Validation: Success!");
            mEditLayoutEmail.setErrorEnabled(false);
        }

        Log.d(TAG, "Validate Password: ********");
        if (!FormValidator.isValidPassword(passwordValue)) {
            mEditLayoutPassword.setError(getString(R.string.error_invalid_password));
            mEditLayoutPassword.requestFocus();
            mEditPassword.setSelection(passwordValue.length());
            Log.d(TAG, "Validation: Fail!");
            Log.i(TAG, "Login Form is Invalid!");
            return false;
        } else {
            Log.d(TAG, "Validation: Success!");
            mEditLayoutPassword.setErrorEnabled(false);
        }
        Log.i(TAG, "Register Form is Valid!");
        return true;
    }


    private void registerUser(String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "registerUser:onComplete:" + task.isSuccessful());
                    if (task.isSuccessful()) {
                        Log.w(TAG, "registerUser Success!");

                    } else {
                        Log.w(TAG, "registerUser Fail!: ", task.getException());
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            DialogManager.showAlertSimple(mContext, R.string.error_weak_password);
                            mEditPassword.setSelection(mEditPassword.getText().length());
                            mEditPassword.requestFocus();

                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            DialogManager.showAlertSimple(mContext, R.string.error_invalid_password);
                            mEditPassword.setSelection(mEditPassword.getText().length());
                            mEditPassword.requestFocus();

                        } catch (FirebaseAuthUserCollisionException e) {
                            DialogManager.showAlertSimple(mContext, R.string.error_user_exists);
                            mEditEmail.setSelection(mEditEmail.getText().length());
                            mEditEmail.requestFocus();

                        } catch (FirebaseAuthInvalidUserException e) {
                            DialogManager.showAlertSimple(mContext, R.string.error_user_not_exists);
                            mEditEmail.setSelection(mEditEmail.getText().length());
                            mEditEmail.requestFocus();

                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            DialogManager.showAlertSimple(mContext, e.getMessage());
                        }

                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void FireBaseAuthListener() {   //TODO: Fix multiple calls
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    user.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            Log.d(TAG, "onAuthStateChanged:onComplete: Task " + task.isSuccessful());
                            if (task.isSuccessful()) {

                                // Get User Basic Info
                                String userAvatarUrl = "";
                                String userToken = task.getResult().getToken();
                                String userUID = user.getUid();
                                String userEmail = user.getEmail();
                                String userProviderID = user.getProviderId();
                                if (user.getPhotoUrl() != null)
                                    userAvatarUrl = user.getPhotoUrl().toString();

                                String firstName = mEditName.getText().toString();
                                String lastName = mEditLastname.getText().toString();
                                String userName = mEditUsername.getText().toString();

                                writeUserToDatabase(userUID, userEmail, firstName, lastName, userName, userAvatarUrl);
                                LocalStorage.loginUser(userUID, userEmail, firstName, lastName, userName, userAvatarUrl, userProviderID, userToken);

                                Intent intent = new Intent().setClass(mContext, MainActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                getActivity().finishAffinity();

                            } else {
                                Log.d(TAG, "onAuthStateChanged:onComplete: Task " + task.isSuccessful());
                            }
                        }
                    });
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void writeUserToDatabase(String userId, String email, String name, String lastname, String username, String avatarUrl) {
        User user = new User(userId, email, name, lastname, username, avatarUrl);
        mDatabase.child("users").child(userId).setValue(user);
    }
}