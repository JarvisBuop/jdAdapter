package com.jd.jdkit.elementkit.utils.common;

import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 图片选择器快捷使用工具
 */
public class ImagePickUtils {

    private static int requestCode = 0x33;

    public static void openImagePick(int num, ImagePickListener pickListener) {
        GalleryFinal.openGalleryMuti(requestCode, num, pickListener);
    }

    public static void openImagePick(int requestCode, int num, ImagePickListener pickListener) {
        GalleryFinal.openGalleryMuti(requestCode, num, pickListener);
    }

    public static void openImagePick(int requestCode, FunctionConfig config , ImagePickListener pickListener) {
        GalleryFinal.openGalleryMuti(requestCode, config , pickListener);
    }

    public interface ImagePickListener extends GalleryFinal.OnHanlderResultCallback {
        @Override
        void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList);

        @Override
        void onHanlderFailure(int requestCode, String errorMsg);
    }
}
