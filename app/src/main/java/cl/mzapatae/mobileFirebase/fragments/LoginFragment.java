package cl.mzapatae.mobileFirebase.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.mzapatae.mobileFirebase.BuildConfig;
import cl.mzapatae.mobileFirebase.R;
import cl.mzapatae.mobileFirebase.base.FragmentBase;
import cl.mzapatae.mobileFirebase.utils.LocalStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends FragmentBase {
    private static final String TAG = "Login Fragment";

    @BindView(R.id.edit_email) EditText mEditEmail;
    @BindView(R.id.edit_password) EditText mEditPassword;
    @BindView(R.id.button_login) Button mButtonLogin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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
                signInUser(mEditEmail.getText().toString().trim(), mEditPassword.getText().toString().trim());
                break;
        }
    }

    /*
    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }*/

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail", task.getException());
                    Toast.makeText(mContext, "Authentication failed: SignUserTask Exception.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                                if (user.getPhotoUrl() != null) mUserPhotoUrl = user.getPhotoUrl().toString();

                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, "1:" + mUserUID);
                                    Log.d(TAG, "2:" + mUserEmail);
                                    Log.d(TAG, "3:" + mUserProviderID);
                                    Log.d(TAG, "4:" + mUserPhotoUrl);
                                    Log.d(TAG, "5:" + mUserToken);
                                }
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