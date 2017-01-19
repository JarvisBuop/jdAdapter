package com.jd.jdkit.permissionkit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jd.jdkit.R;


/**
 * 此方案参考 支付宝某版本app方式，应用在进入首页就要求赋予全部危险权限组的权限，否则不予使用
 * example
 * 需要检查权限的页面，一般为MainActivity的onCreate方法中，加入下列代码
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
 * new CheckPermissionsForAPI23(this);
 * }
 * 然后在MainActivity中添加如下2个方法
 * private void checkPermissionsResult(int requestCode, int resultCode) {
 * //sdk23+用 如果用户拒绝赋予你所需要的权限 则直接退出app，if内方法可自行斟酌
 * if (requestCode == PermissionsActivity.REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
 * finish();
 * }
 * }
 *
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
 * checkPermissionsResult(requestCode, resultCode);
 * }
 * <p>
 * 注意manifest中需要注册PermissionsActivity;
 */
public class PermissionsActivity extends AppCompatActivity {

    /**
     * TODO 配置所需的全部权限
     * 9个危险权限组分类，如有申请，则保留，如无申请，则整行注释
     */
    static final String[] PERMISSIONS = new String[]{
//            Manifest.permission.READ_CALENDAR,//WRITE_CALENDAR
            Manifest.permission.CAMERA,
//            Manifest.permission.READ_CONTACTS,//WRITE_CONTACTS，GET_ACCOUNTS
//            Manifest.permission.ACCESS_FINE_LOCATION,//ACCESS_COARSE_LOCATION
//            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,//CALL_PHONE,READ_CALL_LOG,WRITE_CALL_LOG,ADD_VOICEMAIL,USE_SIP,PROCESS_OUTGOING_CALLS
//            Manifest.permission.BODY_SENSORS,
//            Manifest.permission.SEND_SMS,//RECEIVE_SMS,READ_SMS,RECEIVE_WAP_PUSH,RECEIVE_MMS
            Manifest.permission.READ_EXTERNAL_STORAGE//WRITE_EXTERNAL_STORAGE
    };

    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String EXTRA_PERMISSIONS =
            "permission.extra_permission"; // 权限参数
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    private PermissionsChecker mChecker; // 权限检测器
    private boolean isRequireCheck; // 是否需要系统权限检测

    public static final int REQUEST_CODE = 0; // 请求码

    // 启动当前权限页面的公开接口
    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        LinearLayout rl = new LinearLayout(this);
        setContentView(rl);
        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions); // 请求权限
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionsActivity.this);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}