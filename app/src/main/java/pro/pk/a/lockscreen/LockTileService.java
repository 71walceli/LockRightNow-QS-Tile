package pro.pk.a.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
        super.onStartListening();

        deviceManger = (DevicePolicyManager) getSystemService(Context
                .DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);
        isAdminActive = deviceManger.isAdminActive(compName);

        Log.i(getClass().getSimpleName(),getClass().getSimpleName() + " started listening");
        update();
    }

    @Override
    public void onClick() {
        super.onClick();

        if (isAdminActive) deviceManger.lockNow();
        else {
            Intent intent = new Intent(this, MainActivity.class)
                    .putExtra(MainActivity.KEY_ENAMBE_ADMIN, MainActivity.KEY_ENAMBE_ADMIN)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        Log.i(getClass().getSimpleName(),"Tile clicked");
        update();
    }

    private void update() {
        Tile tile = getQsTile();

        isAdminActive = deviceManger.isAdminActive(compName);
        if (isAdminActive) {
            tile.setState(Tile.STATE_ACTIVE);
            tile.setLabel(getResources().getString(R.string.app_name));
        } else {
            tile.setState(Tile.STATE_INACTIVE);
            tile.setLabel(String.format(getResources().getString(R.string.tile_inactive),
                    getResources().getString(R.string.app_name)));
        }
        tile.updateTile();

        Log.i(getClass().getSimpleName(),"isAdminActive = " + isAdminActive);
    }
}
