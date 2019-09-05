package pro.pk.a.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class AdminReceiver extends DeviceAdminReceiver {
    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, context.getResources().getString(R.string.app_name)
                + context.getString(R.string.toast_dev_admin_on));
    }

    @Override
    public CharSequence onDisableRequested(@NotNull Context context, Intent intent) {
        return String.format(context.getString(R.string.androidDialog_disableDevAdmin),
                context.getResources().getString(R.string.app_name));
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, context.getString(R.string.toast_devAdminDisabled));
    }
}