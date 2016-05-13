package com.gin.library.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;

/**
 * Created by gin on 16/4/14.
 * 方便使用bitmap的工具类，提供了bitmap转换，缩略等实用的方法.</br>
 *
 * @see <a>http://www.cnblogs.com/tianzhijiexian/p/4263897.html</a>
 * <p/>
 * http://www.open-open.com/lib/view/open1329994992015.html
 */
public class BitmapUtils {

    private static int mDesiredWidth;
    private static int mDesiredHeight;

    /**
     * byte[]转Bitmap
     *
     * @param data
     *         byte数组
     *
     * @return Bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] data) {
        Bitmap bitmap;
        if (data.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * Bitmap转byte[]
     *
     * @param bitmap
     *
     * @return byte[]
     */
    public static byte[] bitmap2Bytes(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap转Drawable
     *
     * @param bitmap
     *
     * @return Drawable
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Drawable转Bitmap
     * 使用Drawable的子类(BitmapDrawable)方法直接得到bitmap
     *
     * @param drawable
     *
     * @return Bitmap
     */
    public static Bitmap drawable2Bitmap(@NonNull Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 根据Drawable的属性重新得到一个Bimap
     *
     * @param drawable
     *
     * @return Bitmap
     */
    public static Bitmap drawable2Bitmap2(@NonNull Drawable drawable) {
        Bitmap bitmap;
        //获取Drawable宽高
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        //取drawable颜色格式
        Bitmap.Config config = ((drawable.getOpacity() != PixelFormat.OPAQUE) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        //创建对应bitmap
        bitmap = Bitmap.createBitmap(width, height, config);
        //简历对应画布
        Canvas canvas = new Canvas(bitmap);
        //绘制到画布上
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 旋转图像
     *
     * @param bitmap
     *         原始图片
     * @param degrees
     *         旋转角度
     *
     * @return Bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * 获得带倒影的Bitmap
     *
     * @param bitmap
     *
     * @return
     */
    public static Bitmap getShadowWithOrigin(Bitmap bitmap) {
        final int shadowGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);//y轴翻转

        Bitmap shadow = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint tmpPaint = new Paint();
        canvas.drawRect(0, h, w, h + shadowGap, tmpPaint);
        canvas.drawBitmap(shadow, 0, h + shadowGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + shadowGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in  
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient  
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + shadowGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 得到bitmap的大小
     * <p>1.API19以上提供bitmap.getAllocationByteCount()</p>
     * <p>2.API12以上提供bitmap.getByteCount()</p>
     * <p>3.低版本直接使用bitmap的宽*高</p>
     * <p/>
     * 底层实现基本相同
     */
    public static int getBitmapSize(@NonNull Bitmap bitmap) {
        int size;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  // >=19
            size = bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// 19>API>=12
            size = bitmap.getByteCount();
        } else {
            // others
            size = bitmap.getRowBytes() * bitmap.getHeight();
        }
        return size;
    }

    /**
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     *
     * @return Bitmap
     *
     * @description 从Resources中加载图片
     */
    public static Bitmap resouse2Bitmap(Resources res, int resId, int reqWidth, int reqHeight) {
        //        Bitmap bitmap = BitmapFactory.decodeResource(res, resId);

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置成了true,不占用内存，只获取bitmap宽高
        options.inJustDecodeBounds = true;
        // 初始化options对象
        BitmapFactory.decodeResource(res, resId, options);
        // 得到计算好的options，目标宽、目标高
        options = getBestOptions(options, reqWidth, reqHeight);
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, mDesiredWidth, mDesiredHeight); // 进一步得到目标大小的缩略图
    }

    /**
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     *
     * @return Bitmap
     *
     * @description 从SD卡上加载图片
     */
    public static Bitmap file2Bitmap(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options = getBestOptions(options, reqWidth, reqHeight);
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, mDesiredWidth, mDesiredHeight);
    }

    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     *
     * @return BitmapFactory.Options对象
     *
     * @description 计算目标宽度，目标高度，inSampleSize
     */
    private static BitmapFactory.Options getBestOptions(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 读取图片长宽
        int actualWidth = options.outWidth;
        int actualHeight = options.outHeight;
        // Then compute the dimensions we would ideally like to decode to.
        mDesiredWidth = getResizedDimension(reqWidth, reqHeight, actualWidth, actualHeight);
        mDesiredHeight = getResizedDimension(reqHeight, reqWidth, actualHeight, actualWidth);
        // 根据现在得到计算inSampleSize
        options.inSampleSize = calculateBestInSampleSize(actualWidth, actualHeight, mDesiredWidth, mDesiredHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * Scales one side of a rectangle to fit aspect ratio. 最终得到重新测量的尺寸
     *
     * @param maxPrimary
     *         Maximum size of the primary dimension (i.e. width for max
     *         width), or zero to maintain aspect ratio with secondary
     *         dimension
     * @param maxSecondary
     *         Maximum size of the secondary dimension, or zero to maintain
     *         aspect ratio with primary dimension
     * @param actualPrimary
     *         Actual size of the primary dimension
     * @param actualSecondary
     *         Actual size of the secondary dimension
     */
    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * Returns the largest power-of-two divisor for use in downscaling a bitmap
     * that will not result in the scaling past the desired dimensions.
     *
     * @param actualWidth
     *         Actual width of the bitmap
     * @param actualHeight
     *         Actual height of the bitmap
     * @param desiredWidth
     *         Desired width of the bitmap
     * @param desiredHeight
     *         Desired height of the bitmap
     */
    // Visible for testing.
    private static int calculateBestInSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float inSampleSize = 1.0f;
        while ((inSampleSize * 2) <= ratio) {
            inSampleSize *= 2;
        }

        return (int) inSampleSize;
    }

    /**
     * @param tempBitmap
     * @param desiredWidth
     * @param desiredHeight
     *
     * @return Bitmap
     *
     * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
     */
    private static Bitmap createScaleBitmap(Bitmap tempBitmap, int desiredWidth, int desiredHeight) {
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
            // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
            Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
            tempBitmap.recycle(); // 释放Bitmap的native像素数组
            return bitmap;
        } else {
            return tempBitmap; // 如果没有缩放，那么不回收
        }
    }


}
