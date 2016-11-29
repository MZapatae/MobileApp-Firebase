package cl.mzapatae.mobileFirebase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.mzapatae.mobileFirebase.R;
import cl.mzapatae.mobileFirebase.base.FragmentBase;
import cl.mzapatae.mobileFirebase.enums.Animation;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends FragmentBase {
    private static final String TAG = "Welcome FRG";

    @BindView(R.id.button_login) Button mButtonLogin;
    @BindView(R.id.button_signup) Button mButtonSignup;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.button_login, R.id.button_signup})
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.button_login:
                fragment = LoginFragment.newInstance();
                replaceFragment(fragment, "loginFragment", Animation.FADE);
                break;
            case R.id.button_signup:
                //fragment = RegisterFragment.newInstance(0, "", "", "", "", "");
                //replaceFragment(fragment, "registerFragmewnt", Animation.SLIDE_TO_RIGHT);
                //break;
        }
    }

}
