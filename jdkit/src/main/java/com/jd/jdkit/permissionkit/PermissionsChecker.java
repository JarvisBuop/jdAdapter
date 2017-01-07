package com.jd.jdkit.permissionkit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限的工具类
 * ......................我佛慈悲......................
 * 　　　　　　　　　　　　　　　_oo0oo_
 * 　　　　　　　　　　　　　　o8888888o
 * 　　　　　　　　　　　　　　88" . "88
 * 　　　　　　　　　　　　　　(| -_- |)
 * 　　　　　　　　　　　　　　0\  =  /0
 * 　　　　　　　　　　　　　___/`---'\___
 * 　　　　　　　　　　　.' \\|     |// '.
 * 　　　　　　　　　　　/ \\|||  :  |||// \
 * 　　　　　　　　　　/ _||||| -卍-|||||- \
 * 　　　　　　　　　　|   | \\\  -  /// |   |
 * 　　　　　　　　　　| \_|  ''\---/''  |_/ |
 * 　　　　　　　　　　\  .-\__  '-'  ___/-. /
 * 　　　　　　　　___'. .'  /--.--\  `. .'___
 * 　　　　　　."" '<  `.___\_<|>_/___.' >' "".
 * 　　　　　　| | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * 　　　　　　\  \ `_.   \_ __\ /__ _/   .-` /  /
 * 　　　=====`-.____`.___ \_____/___.-`___.-'=====
 * 　　　　　　　　　　　　　　　`=---='
 * ..................佛祖开光 ,永无BUG...................
 */
public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
