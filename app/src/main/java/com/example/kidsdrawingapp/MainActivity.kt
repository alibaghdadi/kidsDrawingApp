package com.example.kidsdrawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.lifecycleScope
import com.example.kidsdrawingapp.databinding.ActivityMainBinding
import com.example.kidsdrawingapp.databinding.DialogueBrushSizeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var brushSizeDialogBinding: DialogueBrushSizeBinding

    private val openGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                binding.imageView.setImageURI(result.data?.data)
            }
        }

    private val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value

                if (isGranted) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted, now you can read the storage files.",
                        Toast.LENGTH_LONG
                    ).show()

                    val pickIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    openGalleryLauncher.launch(pickIntent)

                } else {
                    if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        Toast.makeText(
                            this@MainActivity,
                            "Oops, you just denied the permission.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.drawingView.setSizeForBrush(5F)

        binding.ibUndo.setOnClickListener {
            binding.drawingView.onClickUndo()
        }

        binding.ibSave.setOnClickListener {
            if (isReadStorageAllowed()) {
                lifecycleScope.launch {
                    saveBitmapFile(getBitmapFromView(binding.frameLayout))
                }
            }
        }

        binding.imBrushSize.setOnClickListener {
            showBrushSizeChooserDialog()
        }

        binding.ibClear.setOnClickListener {
            binding.drawingView.setClearView()
        }

        binding.ibGallery.setOnClickListener {
            requestStoragePermission()
        }

        binding.ibRedColor.setOnClickListener {
            val color = binding.ibRedColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibBlackColor.setOnClickListener {
            val color = binding.ibBlackColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibWhiteColor.setOnClickListener {
            val color = binding.ibWhiteColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibYellowColor.setOnClickListener {
            val color = binding.ibYellowColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibGreenColor.setOnClickListener {
            val color = binding.ibGreenColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibBlueColor.setOnClickListener {
            val color = binding.ibBlueColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibPurpleColor.setOnClickListener {
            val color = binding.ibPurpleColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

        binding.ibPinkColor.setOnClickListener {
            val color = binding.ibPinkColor.tag.toString()
            binding.drawingView.setColorForBrush(color)
        }

    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background

        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)

        return returnedBitmap
    }

    private suspend fun saveBitmapFile(mBitmap: Bitmap?): String {
        var result = ""
        withContext(Dispatchers.IO) {
            if (mBitmap != null) {
                try {
                    val bytes = ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)

                    val file = File(
                        externalCacheDir?.absoluteFile.toString()
                                + File.separator + "kidDrawingApp_" + System.currentTimeMillis() / 1000
                                + ".png"
                    )

                    val fo = FileOutputStream(file)
                    fo.write(bytes.toByteArray())
                    fo.close()

                    result = file.absolutePath

                    runOnUiThread {
                        if (result.isNotEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                "File saved successfully: $result",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Something went wrong while saving the file.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                } catch (e: Exception) {
                    result = ""
                    e.printStackTrace()
                }
            }
        }

        return result
    }

    private fun isReadStorageAllowed(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            showRationalDialog(
                "Kids Drawing App",
                "Kids Drawing App needs to Access Your External Storage."
            )
        } else {
            requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            requestPermission.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    private fun showBrushSizeChooserDialog() {
        val brushDialog = Dialog(this)

        brushSizeDialogBinding = DialogueBrushSizeBinding.inflate(layoutInflater)
        val viewDialog = brushSizeDialogBinding.root
        brushDialog.setContentView(viewDialog)

        brushDialog.setTitle("Brush Size: ")

        brushSizeDialogBinding.ibSmallBrush.setOnClickListener {
            binding.drawingView.setSizeForBrush(5F)
            brushDialog.dismiss()
        }

        brushSizeDialogBinding.ibMediumBrush.setOnClickListener {
            binding.drawingView.setSizeForBrush(10F)
            brushDialog.dismiss()
        }

        brushSizeDialogBinding.ibLargeBrush.setOnClickListener {
            binding.drawingView.setSizeForBrush(15F)
            brushDialog.dismiss()
        }

        brushDialog.show()

    }

    private fun showRationalDialog(title: String, message: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()

    }
}