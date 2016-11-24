package com.eunut.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaly on 16/5/11.
 */
public class PermissionUtil {

    private static PermissionUtil instance;
    private Activity activity;

    private PermissionUtil() {instance = this;}

    public static PermissionUtil with(Activity activity) {
        if (instance == null) new PermissionUtil();
        instance.activity = activity;
        return instance;
    }

    private String getAppName() {
        PackageManager pm = activity.getPackageManager();
        ApplicationInfo info = null;
        try {
            info = pm.getApplicationInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.loadLabel(pm).toString();
    }

    private void showInstalledAppDetails() {
        String packageName = activity.getPackageName();
        final int apiLevel = Build.VERSION.SDK_INT;
        Intent intent = new Intent();
        if (apiLevel >= 9) {
            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
        } else {
            final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(appPkgName, packageName);
        }
        // Start Activity
        activity.startActivity(intent);
    }

    public boolean check(final String... permissions) {
        if (permissions == null || permissions.length == 0) return true;
        boolean result = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, p)) {
                    View view = activity.findViewById(android.R.id.content);
                    String text = "请为" + getAppName() + "开启权限";
                    Snackbar.make(view, text, Snackbar.LENGTH_LONG).setAction("授权", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPermissions(permissions);
                        }
                    }).show();
                    return false;
                }
                result = false;
            }
        }
        if (!result) {
            requestPermissions(permissions);
        }
        return result;
    }

    private void requestPermissions(String... permissions) {
        if (permissions == null || permissions.length == 0) return;
        List<String> needs = new ArrayList<>();
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                needs.add(p);
            }
        }
        if (needs.size() > 0) {
            ActivityCompat.requestPermissions(activity, needs.toArray(new String[]{}), 0);
        }
    }
}
