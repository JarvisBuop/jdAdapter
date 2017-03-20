package com.jd.jarvisdemonim.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/14 0014
 * Name:
 * OverView: 建立TCP server 服务;
 * Usage:注意:必须设置自动刷新,服务端才可返回数据;
 */

public class SocketTestService extends Service {
    private String TAG = "SocketTestService2";

    private boolean isServiceConnection = true;//判断服务端是否存货;
    //模拟服务端的对客户端的回复;
    private String[] responseArray = new String[]{
            "你好!", "你是?", "你谁!", "我是人工智能", "小M", "今天天气正不错", "还有什么?"
    };

    /**
     * 建立服务时开启socket;异步开启,可能耗时;
     */
    private class SocketRunnable implements Runnable {

        @Override
        public void run() {
            /**
             * 建立socketservice,监听有没有socket传送过来;
             */
            ServerSocket sSocket = null;
            int port = 8080;
            try {
                sSocket = new ServerSocket(port);
                LogUtils.i(TAG, "建立端口号为" + port + "的tcp 服务成功");
            } catch (IOException e) {
                LogUtils.i(TAG, "建立端口号为" + port + "的tcp 服务失败");
                e.printStackTrace();
                return;//没建立,则返回;
            }
            //服务存活得时候,不断接受socket,显示客户端的信息;
            while (isServiceConnection) {
                try {
                    /**
                     取出连接的那个socket;
                     */
                    final Socket accept = sSocket.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                /**
                                 * 接受客户端信息和给客户端发送信息;
                                 * 开启线程是由于读出socket数据需要耗时;
                                 */
                                doHandlerSocket(accept);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //获取socket中的信息;
    private void doHandlerSocket(Socket mSocket) throws IOException {
        //使用包装流: 接受客户端socket的信息
        BufferedReader inBr = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        //用于向客户端的socket写出信息;需要自动刷新;
        PrintWriter OutPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);

        OutPw.println("创建聊天室");
        while (isServiceConnection) {
            String s = inBr.readLine();
            if (s == null) {
                return;
            }
            String randomString = responseArray[new Random().nextInt(responseArray.length)];
            OutPw.println(randomString);
            LogUtils.i(TAG, "该次信息是:" + s + "/" + randomString);
        }
        inBr.close();
        OutPw.close();
        mSocket.close();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new SocketRunnable()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceConnection = false;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
