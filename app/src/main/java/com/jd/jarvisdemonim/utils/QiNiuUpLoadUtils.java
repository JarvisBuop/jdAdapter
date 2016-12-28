package com.jd.jarvisdemonim.utils;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 *  七牛上传图片工具类;
 *  普通上传 UpLoadImgPath
 *  带进度条上传 UpLoadImgPathWithProgress
 *  取消上传 cancelUpLoad
 *  恢复上传功能 renewUpLoad 取消后使用
 */
public class QiNiuUpLoadUtils {
    private static final UploadManager uploadManager = new UploadManager();
    //七牛空间外链
    public static String externalLinks = "http://o71tin5mf.bkt.clouddn.com/";
    public static final String ACCESS_KEY = "ufx-leqZOVwZDzJ9ygeQY47eDyYjdvO6hGMInROt";
    public static final String SECRET_KEY = "tZBJtZ_H2L_Rh-aoPraafAiHWwcBo4kSMnxxysD-";
    public static final String TAG = "QiniuUtils";

    //空间名称
    public static String bucketName = "";

    private static String imgPath = "";//上传的图片网址;
    private static QiNiuImgPathListener mListener;
    private static boolean isCancelled;

    /**
     * 传出需要的图片网址;
     */
    public interface QiNiuImgPathListener{
        void getImgPath(String imgPath);
        void getImgProgress(double progress);
    }

    /**
     * 简单上传
     * 使用图片本地地址和七牛token获取七牛图片下载地址;
     */
    public static void UpLoadImgPath(final String path, final String qiniutoken, final QiNiuImgPathListener listener) {
        mListener = listener;
        uploadManager.put(path, suiJiName(), qiniutoken, new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                listener.getImgPath(QiNiuUpLoadUtils.externalLinks + key);
            }
        },new UploadOptions(null, null, false, null,
                new UpCancellationSignal(){
                    public boolean isCancelled(){
                        return isCancelled;
                    }
                }));
    }

    /**
     * 简单上传
     * 使用图片本地地址和七牛token获取七牛图片下载地址;
     */
    public static void UpLoadImgPathWithProgress(final String path, final String qiniutoken, final QiNiuImgPathListener listener) {
        mListener = listener;
        uploadManager.put(path, suiJiName(), qiniutoken, new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                listener.getImgPath(QiNiuUpLoadUtils.externalLinks + key);
            }
        },  new UploadOptions(null, null, false,
                new UpProgressHandler(){
                    public void progress(String key, double percent){
                        listener.getImgProgress(percent);
                    }
                },
                new UpCancellationSignal(){
                    public boolean isCancelled(){
                      return isCancelled;
                    }
        }));
    }

    // 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
    private static void cancelUpLoad() {
        isCancelled = true;
    }
    private static void renewUpLoad() {
        isCancelled = false;
    }


    /**
     * 七牛上传文件随机数生成文件名
     */
    private static String suiJiName() {
        //随机数
        StringBuffer sb = new StringBuffer();
        Long tempLong = System.currentTimeMillis();
        Random ran = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(ran.nextInt(100));
        }
        //上传的文件名
        sb.append(tempLong);
        String keyname = "android_" + sb.toString() + ".jpg";
        return keyname;
    }



    /**
     * 获取上传文件的token
     * 根据官方建议 TOKEN从服务器获取，本地不再生成
     * @param usefulTime token有效时间（单位：秒）
     * @param bucket     上传的空间
     */
    private static String getUploadToken(long usefulTime, String bucket) {
        //构造上传策略
        JSONObject json = new JSONObject();
        long deadline = System.currentTimeMillis() / 1000 + usefulTime;
        String uploadToken = null;
        try {
            // 有效时间为一个小时
            json.put("deadline", deadline);
            json.put("scope", bucket);
            String encodedPutPolicy = UrlSafeBase64.encodeToString(json.toString().getBytes());
            byte[] sign = hMacSHA1Encrypt(encodedPutPolicy, SECRET_KEY);
            String encodedSign = UrlSafeBase64.encodeToString(sign);
            uploadToken = ACCESS_KEY + ':' + encodedSign + ':' + encodedPutPolicy;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadToken;
    }

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    private static byte[] hMacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }


}
