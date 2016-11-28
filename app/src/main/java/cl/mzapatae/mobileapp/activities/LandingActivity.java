package cl.mzapatae.mobileapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cl.mzapatae.mobileapp.R;
import cl.mzapatae.mobileapp.fragments.WelcomeFragment;

public class LandingActivity extends AppCompatActivity {
    private static final String TAG = "Landing Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = WelcomeFragment.newInstance();
        if (fragment != null) fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}

