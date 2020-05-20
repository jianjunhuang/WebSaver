package com.jianjun.base.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.ByteArrayOutputStream
import kotlin.math.min
import kotlin.math.roundToInt


object BitmapDecoder {


    /**
     *
     * @param reqWidth 压缩后宽度
     * @param reqHeight 压缩后高度
     */

    fun decodeBitmapFromResource(
        res: Resources?, resId: Int,
        reqWidth: Int, reqHeight: Int
    ): Bitmap? {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    /**
     * @param path 图片路径
     * @param reqWidth 压缩后宽度
     * @param reqHeight 压缩后高度
     */
    fun decodeBitmapFromPath(path: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inPreferredConfig = Bitmap.Config.RGB_565
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int, reqHeight: Int
    ): Int {
        // 源图片的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            val heightRatio =
                (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio =
                (width.toFloat() / reqWidth.toFloat()).roundToInt()
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    fun decodeBitmapFromBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap? {
        val options = BitmapFactory.Options()
        val outPutStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outPutStream)
        val array = outPutStream.toByteArray()
        options.inJustDecodeBounds = true
        options.inPreferredConfig = Bitmap.Config.RGB_565
        BitmapFactory.decodeByteArray(array, 0, array.size, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(array, 0, array.size, options)
    }

    fun scaleBitmap(bitmap: Bitmap, dstWidth: Int, dstHeight: Int): Bitmap {
        val matrix = Matrix()
        val width: Int = bitmap.width
        val height: Int = bitmap.height
        if (width != dstWidth || height != dstHeight) {
            val ratio: Float = min(dstHeight / height.toFloat(), dstWidth / width.toFloat())
            matrix.setScale(ratio, ratio)
        } else {
            return bitmap
        }
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            false
        )
    }
}