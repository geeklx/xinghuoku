package com.zxing.google.zxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zzz on 2020/10/12
 * Email:zgqmax@foxmail.com
 */

public class QRCodeParseUtils {
    public static final Map<DecodeHintType, Object> HINTS = new EnumMap<>(DecodeHintType.class);

    static {
        List<BarcodeFormat> allFormats = new ArrayList<>();
        allFormats.add(BarcodeFormat.AZTEC);
        allFormats.add(BarcodeFormat.CODABAR);
        allFormats.add(BarcodeFormat.CODE_39);
        allFormats.add(BarcodeFormat.CODE_93);
        allFormats.add(BarcodeFormat.CODE_128);
        allFormats.add(BarcodeFormat.DATA_MATRIX);
        allFormats.add(BarcodeFormat.EAN_8);
        allFormats.add(BarcodeFormat.EAN_13);
        allFormats.add(BarcodeFormat.ITF);
        allFormats.add(BarcodeFormat.MAXICODE);
        allFormats.add(BarcodeFormat.PDF_417);
        allFormats.add(BarcodeFormat.QR_CODE);
        allFormats.add(BarcodeFormat.RSS_14);
        allFormats.add(BarcodeFormat.RSS_EXPANDED);
        allFormats.add(BarcodeFormat.UPC_A);
        allFormats.add(BarcodeFormat.UPC_E);
        allFormats.add(BarcodeFormat.UPC_EAN_EXTENSION);
        HINTS.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, allFormats);
        HINTS.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }

    /**
     * 同步解析本地图片二维码。该方法是耗时操作，请在子线程中调用。
     *
     * @param picturePath 要解析的二维码图片本地路径
     * @return 返回二维码图片里的内容 或 null
     */
    public static String syncDecodeQRCode(String picturePath) {
        return syncDecodeQRCode(getDecodeAbleBitmap(picturePath));
    }

    /**
     * 同步解析bitmap二维码。该方法是耗时操作，请在子线程中调用。
     *
     * @param bitmap 要解析的二维码图片
     * @return 返回二维码图片里的内容 或 null
     */
    public static String syncDecodeQRCode(Bitmap bitmap) {
//        Result result = null;
//        RGBLuminanceSource source = null;
//        try {
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            int[] pixels = new int[width * height];
//            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
//            source = new RGBLuminanceSource(width, height, pixels);
//            result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINTS);
//            return result.getText();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (source != null) {
//                try {
//                    result = new MultiFormatReader().decode(new BinaryBitmap(new GlobalHistogramBinarizer(source)), HINTS);
//                    return result.getText();
//                } catch (Throwable e2) {
//                    e2.printStackTrace();
//                }
//            }
//            return null;
//        }
        String qrCode = "";
        try {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
//            int bitmapWidth = 256;
//            int bitmapHeight = 256;
            // 1.将bitmap的RGB数据转化成YUV420sp数据
            byte[] bmpYUVBytes = getBitmapYUVBytes(bitmap);
            // 2.塞给zxing进行decode
            qrCode = decodeYUVByZxing(bmpYUVBytes, bitmapWidth, bitmapHeight);
            // 3.识别不成功，使用zbar进行decode
            if (qrCode.isEmpty()) {
                qrCode = decodeYUVByZbar(bmpYUVBytes, bitmapWidth, bitmapHeight);
            }
            bitmap.recycle();
            bitmap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap。为了避免图片太大，这里对图片进行了压缩。感谢 https://github.com/devilsen 提的 PR
     *
     * @param picturePath 本地图片文件路径
     * @return
     */
    private static Bitmap getDecodeAbleBitmap(String picturePath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, options);
            int sampleSize = options.outHeight / 400;
            if (sampleSize <= 0) {
                sampleSize = 1;
            }
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(picturePath, options);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到bitmap的YUV数据
     *
     * @param sourceBmp 原始bitmap
     * @return yuv数据
     */
    public static byte[] getBitmapYUVBytes(Bitmap sourceBmp) {
        if (null != sourceBmp) {
            int inputWidth = sourceBmp.getWidth();
            int inputHeight = sourceBmp.getHeight();
            int[] argb = new int[inputWidth * inputHeight];
            sourceBmp.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
            byte[] yuv = new byte[inputWidth
                    * inputHeight
                    + ((inputWidth % 2 == 0 ? inputWidth : (inputWidth + 1)) * (inputHeight % 2 == 0 ? inputHeight
                    : (inputHeight + 1))) / 2];
            encodeYUV420SP(yuv, argb, inputWidth, inputHeight);
            sourceBmp.recycle();
            return yuv;
        }
        return null;
    }

    /**
     * 将bitmap里得到的argb数据转成yuv420sp格式
     * 这个yuv420sp数据就可以直接传给MediaCodec, 通过AvcEncoder间接进行编码
     *
     * @param yuv420sp 用来存放yuv429sp数据
     * @param argb     传入argb数据
     * @param width    bmpWidth
     * @param height   bmpHeight
     */
    private static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        // 帧图片的像素大小
        final int frameSize = width * height;
        // Y的index从0开始
        int yIndex = 0;
        // UV的index从frameSize开始
        int uvIndex = frameSize;
        // YUV数据, ARGB数据
        int Y, U, V, a, R, G, B;
        ;
        int argbIndex = 0;
        // ---循环所有像素点，RGB转YUV---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // a is not used obviously
                a = (argb[argbIndex] & 0xff000000) >> 24;
                R = (argb[argbIndex] & 0xff0000) >> 16;
                G = (argb[argbIndex] & 0xff00) >> 8;
                B = (argb[argbIndex] & 0xff);
                argbIndex++;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                Y = Math.max(0, Math.min(Y, 255));
                U = Math.max(0, Math.min(U, 255));
                V = Math.max(0, Math.min(V, 255));

                // NV21 has a plane of Y and interleaved planes of VU each
                // sampled by a factor of 2
                // meaning for every 4 Y pixels there are 1 V and 1 U. Note the
                // sampling is every other
                // pixel AND every other scanline.
                // ---Y---
                yuv420sp[yIndex++] = (byte) Y;
                // ---UV---
                if ((j % 2 == 0) && (i % 2 == 0)) {
                    yuv420sp[uvIndex++] = (byte) V;
                    yuv420sp[uvIndex++] = (byte) U;
                }
            }
        }
    }

    private static String decodeYUVByZxing(byte[] bmpYUVBytes, int bmpWidth, int bmpHeight) {
        String zxingResult = "";
        // Both dimensions must be greater than 0
        if (null != bmpYUVBytes && bmpWidth > 0 && bmpHeight > 0) {
            try {
                PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(bmpYUVBytes, bmpWidth,
                        bmpHeight, 0, 0, bmpWidth, bmpHeight, true);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new QRCodeReader();
                Result result = reader.decode(binaryBitmap);
                if (null != result) {
                    zxingResult = result.getText();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return zxingResult;
    }

    private static String decodeYUVByZbar(byte[] bmpYUVBytes, int bmpWidth, int bmpHeight) {
        String zbarResult = "";
//        // Both dimensions must be greater than 0
        if (null != bmpYUVBytes && bmpWidth > 0 && bmpHeight > 0) {
//            ZBarDecoder decoder = new ZBarDecoder();
//            zbarResult = decoder.decodeRaw(bmpYUVBytes, bmpWidth, bmpHeight);
        }
//        Log.e("HtscCodeScanningUtil", "decode by zbar, result = " + zbarResult);
        return zbarResult;
    }
}
