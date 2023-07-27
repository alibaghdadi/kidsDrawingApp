package com.example.kidsdrawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kidsdrawingapp.databinding.ActivityMainBinding
import com.example.kidsdrawingapp.databinding.DialogueBrushSizeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var brushSizeDialogBinding: DialogueBrushSizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.drawingView.setSizeForBrush(5F)
        binding.imBrushSize.setOnClickListener {
            showBrushSizeChooserDialog()
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
}