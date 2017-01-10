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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.mzapatae.mobileFirebase.R;
import cl.mzapatae.mobileFirebase.activities.MainActivity;
import cl.mzapatae.mobileFirebase.base.FragmentBase;
import cl.mzapatae.mobileFirebase.objets.User;
import cl.mzapatae.mobileFirebase.utils.FormValidator;
import cl.mzapatae.mobileFirebase.utils.LocalStorage;
import cl.mzapatae.mobileFirebase.utils.DialogManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends FragmentBase {
    private static final String TAG = "Login Fragment";

    @BindView(R.id.edit_email) TextInputEditText mEditEmail;
    @BindView(R.id.edit_password) TextInputEditText mEditPassword;
    @BindView(R.id.button_login) Button mButtonLogin;
    @BindView(R.id.edit_layout_email) TextInputLayout mEditLayoutEmail;
    @BindView(R.id.edit_layout_password) TextInputLayout mEditLayoutPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private Context mContext;
    private String mUserUID = null;
    private String mUserEmail = null;
    private String mUserProviderID = null;
    private String mUserPhotoUrl = null;
    private String mUserToken = null;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
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

    @OnClick({R.id.button_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (isLoginValid()) {
                    signInUser(mEditEmail.getText().toString(), mEditPassword.getText().toString());
                }
                break;
        }
    }

    private boolean isLoginValid() {
        String emailValue = mEditEmail.getText().toString().trim();
        String passwordValue = mEditPassword.getText().toString().trim();

        mEditEmail.setText(emailValue);
        mEditPassword.setText(passwordValue);

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

        Log.d(TAG, "Validate Password...");
        if (!FormValidator.isValidPassword(passwordValue)) {
            mEditLayoutPassword.setError(getString(R.string.error_invalid_password));
            mEditLayoutEmail.requestFocus();
            mEditPassword.setSelection(passwordValue.length());
            Log.d(TAG, "Validation: Fail!");
            Log.i(TAG, "Login Form is Invalid!");
            return false;
        } else {
            Log.d(TAG, "Validation: Success!");
            mEditLayoutPassword.setErrorEnabled(false);
        }
        Log.i(TAG, "Login Form is Valid!");
        return true;
    }

    private void signInUser(String email, String password) {
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInUser:onComplete:" + task.isSuccessful());
                    if (task.isSuccessful()) {
                        Log.w(TAG, "signInUser Success!");
                        Toast.makeText(mContext, "Login Exitoso!", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.w(TAG, "signInUser Fail!: ", task.getException());
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
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void FireBaseAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    user.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                mUserToken = task.getResult().getToken();
                                mUserUID = user.getUid();
                                mUserEmail = user.getEmail();
                                mUserProviderID = user.getProviderId();
                                if (user.getPhotoUrl() != null)
                                    mUserPhotoUrl = user.getPhotoUrl().toString();

                                mDatabase.child("users").child(mUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        LocalStorage.loginUser(
                                                mUserUID,
                                                mUserEmail,
                                                user.getFirstName(),
                                                user.getLastName(),
                                                user.getUserName(),
                                                user.getAvatar(),
                                                mUserProviderID,
                                                mUserToken
                                        );

                                        Intent intent = new Intent().setClass(mContext, MainActivity.class);
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        getActivity().finishAffinity();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(mContext, "Authentication failed: GetTokenTask Exception.", Toast.LENGTH_SHORT).show();
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
}