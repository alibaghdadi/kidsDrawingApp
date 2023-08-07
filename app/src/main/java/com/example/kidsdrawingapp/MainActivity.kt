package com.example.kidsdrawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.kidsdrawingapp.databinding.ActivityMainBinding
import com.example.kidsdrawingapp.databinding.DialogueBrushSizeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var brushSizeDialogBinding: DialogueBrushSizeBinding

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