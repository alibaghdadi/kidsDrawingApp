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