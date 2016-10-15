package party.sicef.borderless.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * Created by Andro on 11/14/2015.
 */
public class CameraHelper {

    public static Bitmap rotateImage(Bitmap bitmap, ExifInterface exif) throws OutOfMemoryError {
        System.gc();
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        Matrix m = new Matrix();
        switch (orientation) {
            case 1:
                return bitmap;
            case 3:
                m.postRotate(180);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            case 6:
                m.postRotate(90);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            case 8:
                m.postRotate(270);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            default:
                return bitmap;
        }
    }

}
