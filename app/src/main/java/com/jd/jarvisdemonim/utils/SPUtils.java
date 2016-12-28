package com.jd.jarvisdemonim.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * SharedPreferences工具类 save保存 load读取
 */
public class SPUtils {
    private static volatile SPUtils sp = null;
    //	private Context con;
    private SharedPreferences preferences;
    private Editor editor;
    private String strBase64;
    private byte[] base64Bytes;

    private SPUtils(Context context) {
//		con = context;
        preferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SPUtils getInstance() {
        if (sp == null) {
            synchronized (SPUtils.class) {
                if (sp == null) {
                    sp = new SPUtils(BaseUtils.application);
                }
            }
        }
        return sp;
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public void saveToken(String token) {
        saveString(token, "token");
    }

    public String loadToken() {
        return loadString("token").toString();
    }

    public void saveName(String user) {
        saveString(user, "name");
    }

    public String loadName() {
        return loadString("name").toString();
    }

    public void saveLoginname(String loginname) {
        saveString(loginname, "loginname");
    }

    public String loadLoginname() {
        return loadString("loginname").toString();
    }

    public void saveEmail(String email) {
        saveString(email, "email");
    }

    public String loadEmail() {
        return loadString("email").toString();
    }

    public void saveOrderHistory(String orderHistory) {
        saveString(orderHistory, "orderHistory");
    }

    public String loadOrderHistory() {
        return loadString("orderHistory").toString();
    }

    public void saveScore(String count) {
        saveString(count, "count");
    }
    public String loadScore() {
        return loadString("count").toString();
    }
    /**
     * 0通过 1审核中 2审核失败
     *
     * @param emailvalidate,realnamevalidate
     */
    public void saveEmailValidate(String emailvalidate) {
        saveString(emailvalidate, "emailvalidate");
    }

    public String loadEmailValidate() {
        return loadString("emailvalidate").toString();
    }

    public void saveRealnamevalidate(String realnamevalidate) {
        saveString(realnamevalidate, "realnamevalidate");
    }

    public String loadRealnamevalidate() {
        return loadString("realnamevalidate").toString();
    }

    public void saveUserID(String memberid) {
        saveString(memberid, "memberid");
    }

    public String loadUserID() {
        return loadString("memberid").toString();
    }

    private boolean saveString(String str, String name) {
        return editor.putString(name, str).commit();
    }

    private String loadString(String name) {
        return preferences.getString(name, "");
    }

//    public void saveUserVo(UserVo userVo) {
//        saveObj(userVo, "uservo");
//    }
//
//    public UserVo loadUserVo() {
//        if (loadObj("uservo") instanceof UserVo) {
//            return (UserVo) loadObj("uservo");
//        } else {
//            return null;
//        }
//    }

    /**
     * @param obj
     * @param name
     * @return
     * @Author guyj
     * @date 2015年11月10日 下午4:20:41
     */
    public boolean saveObj(Object obj, String name) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // 将Product对象转换成byte数组，并将其进行base64编码
        strBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        // 将编码后的字符串写到xml文件中
        editor.putString(name, strBase64);
        editor.commit();
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @param name
     * @return
     * @Author guyj
     * @date 2015年11月10日 下午4:45:00
     */
    public Object loadObj(String name) {
        Object obj = null;
        strBase64 = preferences.getString(name, "");
        if (TextUtils.isEmpty(strBase64)) {
            return "";
        }
        // 对Base64格式的字符串进行解码
        base64Bytes = Base64.decode(strBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            // 从ObjectInputStream中读取Product对象
            obj = ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * @param img
     * @param name
     * @return
     * @Author guyj
     * @date 2015年11月11日 下午3:17:33
     */
    public boolean saveImage(ImageView img, String name) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 将ImageView组件中的图像压缩成JPEG格式，并将压缩结果保存在ByteArrayOutputStream对象中
        ((BitmapDrawable) img.getDrawable()).getBitmap().compress(CompressFormat.PNG, 100, baos);
        strBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        // 保存由图像字节流转换成的Base64格式字符串
        editor.putString(name, strBase64);
        editor.commit();
        if (baos != null) {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param name
     * @return
     * @Author guyj
     * @date 2015年11月11日 下午3:34:06
     */
    public Bitmap loadImage(String name) {
        strBase64 = preferences.getString(name, "");
        base64Bytes = Base64.decode(strBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Bitmap bitmap = BitmapFactory.decodeStream(bais);
//		Drawable drawable = Drawable.createFromStream(bais, name);
//		Drawable drawable = new BitmapDrawable(bitmap);
        if (bais != null) {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
