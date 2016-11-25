package cl.mzapatae.mobileapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cl.mzapatae.mobileapp.R;
import cl.mzapatae.mobileapp.utils.DeviceInfo;
import cl.mzapatae.mobileapp.utils.LocalStorage;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        runSplash();
    }

    private void runSplash() {
        ConfigureApp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LocalStorage.isUserLogged()) {
                    //initMainScreen();
                }
                else {
                    //initLoginScreen();
                }
            }
        }, 1500); //Stay splash for 1.5 seg
    }

    /*
    private void initLoginScreen() {
        Intent intent = new Intent().setClass(SplashActivity.this, LandingActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finishAffinity();
    }*/

    /*
    private void initMainScreen() {
        Intent intent = new Intent().setClass(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finishAffinity();
    }*/

    private void ConfigureApp() {
        //Put Fabric config here!
        DeviceInfo deviceInfo = new DeviceInfo(this);
        saveDeviceInfoData(deviceInfo);
        deviceInfo.generateKeyhashSHA();
    }

    private void saveDeviceInfoData(DeviceInfo deviceInfo){
        LocalStorage.setDeviceId(deviceInfo.getAndroidId());
        LocalStorage.setVersionApp(deviceInfo.getVersionApp());
        LocalStorage.setModelDevice(deviceInfo.getModelDevice());
        LocalStorage.setManufacturerDevice(deviceInfo.getManufacturerDevice());
        LocalStorage.setAndroidVersion(deviceInfo.getAndroidVersion());
        LocalStorage.setAppName(deviceInfo.getAppName());
    }
}
