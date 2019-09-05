package pro.pk.a.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LockTileService extends TileService {
    public LockTileService() {}

    private DevicePolicyManager deviceManger;
    private ComponentName compName;
    private boolean isAdminActive;

    @Override
    public void onStartListening() {
        deviceManger = (DevicePolicyManager) getSystemService(Context
                .DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, AdminReceiver.class);
        isAdminActive = deviceManger.isAdminActive(compName);

        Log.i(getClass().getSimpleName(),getClass().getSimpleName() + " started listening");
        update();
    }

    @Override
    public void onClick() {
        if (isAdminActive) deviceManger.lockNow();
        else {
            Intent intent = new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        Log.i(getClass().getSimpleName(),"Tile clicked");
        sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        update();
    }

    private void update() {
        Tile tile = getQsTile();

        isAdminActive = deviceManger.isAdminActive(compName);
        if (isLocked()) {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.setLabel(getResources().getString(R.string.deviceLocked));
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_lock_black_24dp));
        } else if (isAdminActive) {
            tile.setState(Tile.STATE_ACTIVE);
            tile.setLabel(getResources().getString(R.string.app_name));
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_lock_black_24dp));
        } else {
            tile.setState(Tile.STATE_INACTIVE);
            tile.setLabel(String.format(getResources().getString(R.string.tile_inactive),
                    getResources().getString(R.string.app_name)));
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_lock_open_black_24dp));
        }

        tile.updateTile();

        Log.i(getClass().getSimpleName(),"isAdminActive = " + isAdminActive + "; isisLocked() " +
                "= " + isLocked());
    }
}
