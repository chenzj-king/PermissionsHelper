package com.dreamliner.easypermissions.sample.ui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dreamliner.easypermissions.AfterPermissionGranted;
import com.dreamliner.easypermissions.EasyPermissions;
import com.dreamliner.easypermissions.EasyPermissions.PermissionCallbacks;
import com.dreamliner.easypermissions.sample.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenzj
 * @Title: BaseActivity
 * @Description: 类的描述 -
 * @date 2016/7/17 17:14
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseActivity extends AppCompatActivity implements PermissionCallbacks {

    protected abstract int getLayoutId();

    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    protected void onDestroy() {
        EasyPermissions.hidePermissionsDialog();
        super.onDestroy();
    }

    public final static int RC_ALL_PERM = 0x100;
    private boolean isNeverAskAgain = false;

    //运行时权限管理
    protected void doPermissinosSuc() {

    }

    protected String[] getPermissions() {
        return new String[]{};
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @AfterPermissionGranted(RC_ALL_PERM)
    protected void checkPermission() {

        String[] perms = getPermissions();

        if (EasyPermissions.hasPermissions(this, perms)) {
            doPermissinosSuc();
        } else {
            String[] denieds = EasyPermissions.getDeniedPermissions(this, perms);
            if (!isNeverAskAgain) {
                EasyPermissions.requestPermissions(this, getString(R.string.rationale_all), RC_ALL_PERM, denieds);
            } else {

                ArrayList<String> deniedList = new ArrayList<>();
                Collections.addAll(deniedList, denieds);
                isNeverAskAgain = EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, getString(R.string.rationale_ask_again),
                        R.string.setting, android.R.string.cancel, deniedList);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == getPermissions().length) {
            doPermissinosSuc();
        } else {
            Log.e("BaseAct", "onPermissionsGranted: 有部分权限没有允许");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        isNeverAskAgain = EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, getString(R.string.rationale_ask_again),
                R.string.setting, android.R.string.cancel, perms);
    }
}
