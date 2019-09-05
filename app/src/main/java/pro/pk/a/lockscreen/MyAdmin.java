package pro.pk.a.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAdmin extends DeviceAdminReceiver {

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, context.getResources().getString(R.string.app_name)
                + " Device Admin: enabled");
    }

    @Override
    public CharSequence onDisableRequested(@org.jetbrains.annotations.NotNull Context context, Intent intent) {
        String appName = context.getResources().getString(R.string.app_name);
        return "Disabling " + appName + " will disable the tile. Are you sure you want to disable "
                + appName + "?";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Sample Device Admin: disabled");
    }
}