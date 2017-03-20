package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jd.jarvisdemonim.IMyAidlInterface;
import com.jd.jarvisdemonim.MyIpcBean;
import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.entity.TestBeanPracelable;
import com.jd.jarvisdemonim.service.AIDLService;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/8 0008
 * Name:
 * OverView: 测试进程间的通信,app间的通信,aidl,隐式启动;
 * Usage: aidl远程调用;
 */

public class NormalTestAidlActivity extends DBaseActivity {
    @Bind(R.id.txt)
    TextView txt;
    @Bind(R.id.edttext)
    EditText editText;
    @Bind(R.id.btn)
    TextView btnLaunch;
    @Bind(R.id.btn_secord)
    Button btnSecord;
    @Bind(R.id.btn_add)
    Button btnAddObj;
    @Bind(R.id.btn_display)
    Button btnDisplay;
    @Bind(R.id.txt_show)
    TextView tvShow;
    private String launchPackageName = "com.jrd.baseproject";
    private String uniqueAction = "com.jdtest.action";
    //实现Parcelable的实体类;
    private TestBeanPracelable testParcelable;
    IMyAidlInterface mBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i("AIDLService","onServiceConnected in jarvis");
            if (service != null) {
                mBinder = IMyAidlInterface.Stub.asInterface(service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
            LogUtils.i("AIDLService","onServiceDisconnected in jarvis");
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_aidl_ipc;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {
        editText.setText(launchPackageName);
        testParcelable = new TestBeanPracelable("测试名称", 12, "测试性别", new TestBeanPracelable.InnerObject("内部名称"));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent install = isInstall(editText.getText().toString());
                    if (install == null) {
                        ToastUtils.showToast("没有安装此app");
                        return;
                    }
//                    //(方法一:)隐式启动相同action的activity;
//                    Intent intent = new Intent();
//                    intent.setAction(uniqueAction);
//                    startActivity(intent);

                    //(方法二:)设置包名和类名寻找app;
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName(editText.getText().toString(), editText.getText().toString() + ".MainActivity");
                    intent.setComponent(cn);
                    intent.putExtra("name", "我是jarvisdemonimApp,请问你是?");
                    startActivity(intent);
                    //(方法二简写:)设置包名启动app
//                    doStartApplicationWithPackageName(editText.getText().toString());

//                    方法三:通过查找包名,有启动;
//                    与方法1,2不同的是,该启动方式在按HOME键后,在打开app时,不会显示第二个app界面;
//                    startActivity(install);

                } catch (Exception e) {
                    if (e instanceof ActivityNotFoundException) {
                        ToastUtils.showToast("ActivityNotFoundException");
                    }
                } finally {
                    txt.setText(testParcelable.toString());
                }
            }
        });
        btnSecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("com.jdtest.secordactivity.action");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                } catch (Exception e) {
                    if (e instanceof ActivityNotFoundException) {
                        ToastUtils.showToast("ActivityNotFoundException");
                    }
                }
            }
        });

        //aidl的使用
        doServiceAidl();
        //设置添加对象方法;
        btnAddObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinder != null) {
                    MyIpcBean bean = new MyIpcBean("jarvisdong", 22, true, "汉");
                    try {
                        mBinder.addBean(bean);
                    } catch (RemoteException e) {
                        ToastUtils.showToast("RemoteException in add");
                        e.printStackTrace();
                    } finally {
                        btnDisplay.performClick();
                    }
                }else {
                    ToastUtils.showToast("mbinder is null or classcastexception");
                }
            }
        });
        //设置显示所有对象方法;
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinder != null ) {
                    try {
                        List<MyIpcBean> bean = mBinder.getBean();
                        StringBuilder sb = new StringBuilder();
                        for(int i=0;i<bean.size();i++){
                            sb.append(bean.get(i).toString()+"\n");
                        }
                        tvShow.setText(bean.size()+"/\n"+sb);
                    } catch (RemoteException e) {
                        ToastUtils.showToast("RemoteException in display");
                        e.printStackTrace();
                    }
                }else {
                    ToastUtils.showToast("mbinder is null or classcastexception");
                }
            }
        });
    }

    private void doServiceAidl() {
        //绑定本 context;
        Intent service = new Intent(mContext, AIDLService.class);
        bindService(service, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    //通过包名判断是否安装
    public Intent isInstall(String pkgname) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(pkgname);
        return intent;
    }

    //根据包名启动app;
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, PackageManager.MATCH_DEFAULT_ONLY);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            startActivity(intent);
        }
    }
}
