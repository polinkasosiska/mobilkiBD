package com.sysoliatina.roomwordssample.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sysoliatina.roomwordssample.R
import com.sysoliatina.roomwordssample.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewWordBinding
    private var image: String? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        image = uri.toString()
        Glide.with(this)
            .load(uri)
            .transform(RoundedCorners(10))
            .into(binding.imageView)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.editWord.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = binding.editWord.text.toString()
                replyIntent.putExtra(ARG_WORD, word)
                if (image != null) {
                    replyIntent.putExtra(ARG_IMAGE, image)
                }
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        binding.imageView.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    companion object {
        const val ARG_WORD = "word"
        const val ARG_IMAGE = "image"
    }
}