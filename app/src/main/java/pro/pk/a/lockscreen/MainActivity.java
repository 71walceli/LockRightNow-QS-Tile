package pro.pk.a.lockscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    static final int RESULT_ENABLE = 1;
    public static final String KEY_ENAMBE_ADMIN = "ENABLE_ADMIN";

    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceManger = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, AdminReceiver.class);


        enableAdmin();
        finish();

        setContentView(R.layout.activity_main);
    }

    public void enableAdmin() {
        Intent intent = new Intent(DevicePolicyManager
                .ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Additional text explaining why this needs to be added.");
        startActivityForResult(intent, RESULT_ENABLE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_ENABLE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("DeviceAdminSample", "Admin enabled!");
                finish();
            } else {
                Log.i("DeviceAdminSample", "Admin enable FAILED!");
            }
        }
    }
}

