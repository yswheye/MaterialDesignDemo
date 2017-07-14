package com.support.android.designlibdemo.utils;

/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*a
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public abstract class PermissionUtil {

    private static final String[] PERMISSIONS_LOCATION = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private static final String[] PERMISSIONS_CALENDAR = {
            android.Manifest.permission.READ_CALENDAR
    };

    private static final String[] PERMISSIONS_PHONE = {
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
            android.Manifest.permission.CALL_PHONE
    };

    private static final String[] PERMISSIONS_CAMERA = {
            android.Manifest.permission.CAMERA
    };

    private static final String[] PERMISSIONS_SMS = {
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_SMS
    };

    private static final String[] PERMISSIONS_CONTACTS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.GET_ACCOUNTS
    };

    private static final String[] PERMISSIONS_ALL = {
//            android.Manifest.permission.ACCESS_COARSE_LOCATION,
//            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
//            android.Manifest.permission.READ_CALENDAR,
//            android.Manifest.permission.READ_PHONE_STATE,
//            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
//            android.Manifest.permission.CALL_PHONE,
//            android.Manifest.permission.CAMERA,
//            android.Manifest.permission.SEND_SMS,
//            android.Manifest.permission.READ_SMS,
//            android.Manifest.permission.RECEIVE_SMS,
//            android.Manifest.permission.READ_CONTACTS,
//            android.Manifest.permission.GET_ACCOUNTS
    };


    public static void verifyAllPermissions(Activity activity) {
        boolean needPermissions = false;
        for (int i = 0; i < PERMISSIONS_ALL.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                needPermissions = true;
                break;
            }
        }

        if (needPermissions) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_ALL, 0);
        }

    }

    public static void handleVerifyPermissions(Activity activity, int requestCode, String[] permissions,
                                               int[] grantResults) {
        boolean granted = true;
        if (requestCode == 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
        }
        if (!granted) {
            Toast.makeText(activity, "未获取所有权限，请在设置中设置", Toast.LENGTH_SHORT).show();
            //startManagePermissionsActivity(activity);
            activity.finish();
        }
    }

    private static void startManagePermissionsActivity(Activity activity) {
        // start new activity to manage app permissions
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", activity.getApplicationInfo().packageName);
        intent.putExtra("hideInfoButton", true);
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("PermissionUtil", "No app can handle android.intent.action.MANAGE_APP_PERMISSIONS");
        }
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}

