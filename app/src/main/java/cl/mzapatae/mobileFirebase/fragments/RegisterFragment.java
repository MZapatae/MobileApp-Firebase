package cl.mzapatae.mobileFirebase.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContext;
    private String mFirstName;
    private String mLastName;
    private String mUserName;

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

    @OnClick(R.id.button_register)
    public void onClick() {
        registerUser(mEditName.getText().toString().trim(),
                mEditLastname.getText().toString().trim(),
                mEditUsername.getText().toString().trim(),
                mEditEmail.getText().toString().trim(),
                mEditPassword.getText().toString().trim());
    }

    private void registerUser(final String firstName, final String lastName, final String userName, final String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "registerUser:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        Log.w(TAG, "registerUser Success!");
                        mFirstName = firstName;
                        mLastName = lastName;
                        mUserName = userName;

                    } else {
                        Log.w(TAG, "registerUser Fail!: ", task.getException());
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            mEditPassword.setError(getString(R.string.error_weak_password));
                            mEditPassword.requestFocus();
                            Toast.makeText(mContext, R.string.error_weak_password, Toast.LENGTH_SHORT).show();

                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            mEditEmail.setError(getString(R.string.error_invalid_password));
                            mEditEmail.requestFocus();
                            Toast.makeText(mContext, R.string.error_invalid_password, Toast.LENGTH_SHORT).show();

                        } catch (FirebaseAuthUserCollisionException e) {
                            mEditEmail.setError(getString(R.string.error_user_exists));
                            mEditEmail.requestFocus();
                            Toast.makeText(mContext, R.string.error_user_exists, Toast.LENGTH_SHORT).show();

                        } catch (FirebaseAuthInvalidUserException e) {
                            mEditEmail.setError(getString(R.string.error_user_not_exists));
                            mEditEmail.requestFocus();
                            Toast.makeText(mContext, R.string.error_user_not_exists, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } catch (IllegalArgumentException e) { //final String firstName, final String lastName, final String userName, final String email, String password
            if (firstName == null || firstName.equals("")) {
                mEditName.setError(getString(R.string.error_blank_first_name));
                Toast.makeText(mContext, R.string.error_blank_first_name, Toast.LENGTH_SHORT).show();
            }
            if (lastName == null || lastName.equals("")) {
                mEditLastname.setError(getString(R.string.error_blank_last_name));
                Toast.makeText(mContext, R.string.error_blank_last_name, Toast.LENGTH_SHORT).show();
            }
            if (userName == null || userName.equals("")) {
                mEditUsername.setError(getString(R.string.error_blank_user_name));
                Toast.makeText(mContext, R.string.error_blank_user_name, Toast.LENGTH_SHORT).show();
            }
            if (email == null || email.equals("")) {
                mEditEmail.setError(getString(R.string.error_blank_email));
                Toast.makeText(mContext, R.string.error_blank_email, Toast.LENGTH_SHORT).show();
            }
            if (password == null || password.equals("")) {
                mEditPassword.setError(getString(R.string.error_blank_password));
                Toast.makeText(mContext, R.string.error_blank_email, Toast.LENGTH_SHORT).show();
            }
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
                                if (user.getPhotoUrl() != null) userAvatarUrl = user.getPhotoUrl().toString();

                                writeUserToDatabase(userUID, userEmail, mFirstName, mLastName, mUserName, userAvatarUrl);
                                LocalStorage.loginUser(userUID, userEmail, mFirstName, mLastName, mUserName, userAvatarUrl, userProviderID, userToken);

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