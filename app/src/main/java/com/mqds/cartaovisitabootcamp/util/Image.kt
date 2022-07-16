package com.mqds.cartaovisitabootcamp.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mqds.cartaovisitabootcamp.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Image {
    companion object {

        fun share(context: Context, card: View) {
            val bitmap = getScreenShotFromView(card)

            bitmap?.let {
                saveMediaToStorage(context, bitmap)
            }
        }

        private fun getScreenShotFromView(card: View): Bitmap? {
            var screenshot: Bitmap? = null
            try {
                screenshot = Bitmap.createBitmap(
                    card.measuredWidth,//measuredWidth
                    card.measuredHeight,//measuredHeight
                    Bitmap.Config.ARGB_8888
                )
                val outraVar = screenshot

                val canvas = Canvas(screenshot)
                //card.layout(card.left,card.top,card.right,card.bottom)
                card.draw(canvas)

            } catch (e: Exception) {
                Log.e("ERROR", "Falha ao capturar imagem " + e)
            }
            return screenshot
        }

        private fun saveMediaToStorage(context: Context, bitmap: Bitmap) {
            val filename = "${System.currentTimeMillis()}.jpeg"

            var fos: OutputStream? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    var imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    fos = imageUri?.let {
                        shareIntent(context, imageUri)
                        resolver.openOutputStream(it)
                    }
                }
            } else {
                //Devices less than Q
                val image = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
                //val image = File.createTempFile("${System.currentTimeMillis()}",".jpg",imagesDir)
                shareIntent(context, Uri.fromFile(image))
                fos = FileOutputStream(image)
            }

            fos.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                Toast.makeText(context, "Image salva com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

        private fun shareIntent(context: Context, imageUri: Uri) {
            var shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, imageUri)
                type = "image/*"
            }
            context.startActivity(
                Intent.createChooser(shareIntent, context.resources.getText(R.string.txtLbShae))
            )
        }
    }
}