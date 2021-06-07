package team.getherfolg.capstone

import android.content.ContentResolver
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import team.getherfolg.capstone.ui.preference.Constant
import java.io.*

object FilePathFormUri {
    fun get(contentUri: Uri, contentResolver: ContentResolver): String? {
        val fileName: String = getFile(contentUri)
        val wallpaperDirectory =
            File(Environment.getExternalStorageDirectory().toString() + Constant.IMAGE)

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(wallpaperDirectory.toString() + File.separator + fileName)

            // Create folder if not exist
            copy(contentUri, copyFile, contentResolver)

            return copyFile.absolutePath
        }

        return null
    }

    private fun getFile(contentUri: Uri): String {
        var fileName: String? = null
        val path: String = contentUri.path.toString()
        val cut = path.lastIndexOf('/')
        if (cut != 1) {
            fileName = path.substring(cut + 1)
        }
        return fileName!!
    }

    private fun copy(contentUri: Uri, copyFile: File, contentResolver: ContentResolver) {
        val inputStream: InputStream = contentResolver.openInputStream(contentUri) ?: return
        val outputStream: OutputStream = FileOutputStream(copyFile)

        copyStream(inputStream, outputStream)
        inputStream.close()
        outputStream.close()
    }

    private fun copyStream(inputStream: InputStream, outputStream: OutputStream): Int {
        val buffer = ByteArray(Constant.BUFFER_SIZE)
        val inp = BufferedInputStream(inputStream, Constant.BUFFER_SIZE)
        val out = BufferedOutputStream(outputStream, Constant.BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (inp.read(buffer, 0, Constant.BUFFER_SIZE).also { n = it } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Log.e(e.message, e.toString())
            }

            try {
                inp.close()
            } catch (e: IOException) {
                Log.e(e.message, e.toString())
            }
        }

        return count
    }
}