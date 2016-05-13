package com.gin.library.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by gin on 5/13/16.
 * 对文件的一些操作
 */
public class FileUtils {

    /**
     * 通过路径删除文件
     *
     * @param path
     */
    public static void delFile(String path) {
        delFile(new File(path));
    }

    /**
     * 通过File删除文件
     *
     * @param file
     */
    public static void delFile(@NonNull File file) {
        file.delete();
    }

    /**
     * 复制文本到剪贴板
     *
     * @param ctx
     *         Context
     * @param text
     *         String
     */
    public static void copyToClipboard(Context ctx, String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager cbm = (ClipboardManager) ctx.getSystemService(Activity.CLIPBOARD_SERVICE);
            cbm.setPrimaryClip(ClipData.newPlainText(ctx.getPackageName(), text));
        } else {
            android.text.ClipboardManager cbm = (android.text.ClipboardManager) ctx.getSystemService(Activity.CLIPBOARD_SERVICE);
            cbm.setText(text);
        }
    }

    /**
     * 通过Uri得到图片的绝对路径
     *
     * @param context
     * @param uri
     *
     * @return the file path or null
     */

    public static String uri2FilePath(Context context, final Uri uri) {
        String path = null;
        if (null == uri) {
            path = null;
        } else {
            String scheme = uri.getScheme();
            if (scheme == null || ContentResolver.SCHEME_FILE.equals(scheme)) {
                path = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            path = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
        }
        return path;
    }

    /**
     * 通过图片的绝对路径得到Uri
     *
     * @param path
     *
     * @return the file path or null
     */
    private Uri filePath2Uri(Activity activity, String path) {
        Uri mUri = Uri.parse("content://media/external/images/media");
        Uri mImageUri = null;
        Cursor cursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            if (path.equals(data)) {
                int ringtoneID = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);
                break;
            }
            cursor.moveToNext();
        }
        return mImageUri;
    }
}
