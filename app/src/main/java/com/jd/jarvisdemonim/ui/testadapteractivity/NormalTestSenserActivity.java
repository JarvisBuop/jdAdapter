package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/31 0031
 * Name:
 * OverView: 测试传感器
 * Usage: 方向传感器是基于软件的,并且数据是通过加速度传感器和磁场传感器共同获得的;
 * 传感器坐标系统（Sensor Coordinate System）:
 */

public class NormalTestSenserActivity extends DBaseActivity implements SensorEventListener {
    @Bind(R.id.txt_ori)
    TextView txtOri;
    @Bind(R.id.txt_accu)
    TextView txtAccu;
    @Bind(R.id.txt_chagn)
    TextView txtCh;
    @Bind(R.id.txt_total)
    TextView txtTotal;
    SensorManager sensorManager;
    Sensor mAccelate;//加速度传感器
    Sensor mChSensor;//磁场传感器;
    Sensor mOrientSensor;//磁场传感器;

    //    private float[] gravity = new float[3];
    private float[] accelerate = new float[3];
    private float[] cichang = new float[3];

    @Override
    public int getContentViewId() {
        return R.layout.activity_senser;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            txtTotal.append(sensor.getName() + "\n");
        }

        mAccelate = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mChSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        mOrientSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerate, cichang);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);

        Log.i(TAG, values[0] + "");
        if (values[0] >= -5 && values[0] < 5) {
            txtOri.setText("正北");
        } else if (values[0] >= 5 && values[0] < 85) {
            // Log.i(TAG, "东北");
            txtOri.setText("东北");
        } else if (values[0] >= 85 && values[0] <= 95) {
            // Log.i(TAG, "正东");
            txtOri.setText("正东");
        } else if (values[0] >= 95 && values[0] < 175) {
            // Log.i(TAG, "东南");
            txtOri.setText("东南");
        } else if ((values[0] >= 175 && values[0] <= 180)
                || (values[0]) >= -180 && values[0] < -175) {
            // Log.i(TAG, "正南");
            txtOri.setText("正南");
        } else if (values[0] >= -175 && values[0] < -95) {
            // Log.i(TAG, "西南");
            txtOri.setText("西南");
        } else if (values[0] >= -95 && values[0] < -85) {
            // Log.i(TAG, "正西");
            txtOri.setText("正西");
        } else if (values[0] >= -85 && values[0] < -5) {
            // Log.i(TAG, "西北");
            txtOri.setText("西北");
        }
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelate, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, mChSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                final float alpha = (float) 0.8;
                accelerate[0] = event.values[0];
                accelerate[1] = event.values[1];
                accelerate[2] = event.values[2];
                LogUtils.e("\n加速度传感器: x:" + accelerate[0] + "\ny:" + accelerate[1] + "\nz:" + accelerate[2]);
                txtAccu.setText("加速度传感器: x:" + accelerate[0] + "\ny:" + accelerate[1] + "\nz:" + accelerate[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                cichang[0] = event.values[0];
                cichang[1] = event.values[1];
                cichang[2] = event.values[2];
                LogUtils.e("\n磁场传感器: x:" + cichang[0] + "\ny:" + cichang[1] + "\nz:" + cichang[2]);
                txtCh.setText("磁场传感器: x:" + cichang[0] + "\ny:" + cichang[1] + "\nz:" + cichang[2]);
                break;
        }
        calculateOrientation();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
