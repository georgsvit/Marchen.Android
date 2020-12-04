package com.example.marchenandroid.ui.play

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentPlayBinding
import java.util.*

class PlayFragment  : Fragment() {
    private lateinit var viewModel: PlayViewModel
    lateinit var mTTS: TextToSpeech

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mTTS = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale.UK
            }
        })

        viewModel = PlayViewModel(requireActivity().application)
        val binding: FragmentPlayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play, container, false)
        val text = binding.slideText
        val img = binding.img

        viewModel.slide.observe(viewLifecycleOwner, Observer {
            text.text = it.Content
            if (it.ImagePath != null) {
                getImage(it.ImagePath, img)
            }
        })

        binding.speakerBtn.setOnClickListener {
            if (mTTS.isSpeaking) {
                mTTS.stop()
            } else {
                if (binding.slideText.text != "" && binding.slideText.visibility == View.VISIBLE) {
                    mTTS.speak(binding.slideText.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
                } else if (binding.question.text != "" && binding.question.visibility == View.VISIBLE) {
                    mTTS.speak(binding.question.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
                }
            }
        }

        viewModel.hasNextSlide.observe(viewLifecycleOwner, Observer { newState ->
            binding.next.isEnabled = newState
        })

        viewModel.hasPrevSlide.observe(viewLifecycleOwner, Observer { newState ->
            binding.prev.isEnabled = newState
        })

        viewModel.unit.observe(viewLifecycleOwner, Observer {
            binding.question.text = viewModel.unit.value!!.QuestionText
            val options = viewModel.unit.value!!.Options

            if (binding.btnLayout.childCount > 0)  binding.btnLayout.removeAllViews()

            if (options.isNotEmpty()) {
                for (op in options) {
                    val btn = Button(context)
                    btn.text = op.AnswerText
                    btn.isEnabled = true
                    btn.setOnClickListener {
                        viewModel.optionOnClick(op.ToUnitId)
                    }
                    btn.visibility = View.VISIBLE
                    binding.btnLayout.addView(btn)
                }
            } else {
                getImage(viewModel.award.value!!.AwardURL, img)
                val btn = Button(context)
                btn.text = "Go to main menu"
                btn.isEnabled = true
                btn.setOnClickListener {
                    requireActivity().finish()
                }
                btn.visibility = View.VISIBLE
                binding.btnLayout.addView(btn)
            }
        })

        viewModel.question.observe(viewLifecycleOwner, Observer { newState ->
            if (newState) {
                getImage(viewModel.award.value!!.AwardURL, img)
                text.visibility = View.GONE

                if (viewModel.unit.value!!.Options.isNotEmpty()) {
                    img.visibility = View.GONE
                }

                binding.question.visibility = View.VISIBLE
                binding.btnLayout.visibility = View.VISIBLE
            } else {
                text.visibility = View.VISIBLE
                img.visibility = View.VISIBLE
                binding.question.visibility = View.GONE
                binding.btnLayout.visibility = View.GONE
            }
        })


        binding.prev.setOnClickListener {
            if (mTTS.isSpeaking) mTTS.stop()
            viewModel.prevSlide()
        }

        binding.next.setOnClickListener {
            if (mTTS.isSpeaking) mTTS.stop()
            viewModel.nextSlide()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialogBuilder = AlertDialog.Builder(requireActivity())

                dialogBuilder.setMessage("Do you want to exit?\nCurrent game state will be saved").setTitle("Exit?")

                dialogBuilder.setPositiveButton("Exit") { _, _ ->
                    requireActivity().finish()
                }

                dialogBuilder.setNegativeButton("Cancel") { _, _ -> }

                dialogBuilder.show()
            }
        })

        binding.viewModel = viewModel
        return binding.root
    }


    private fun getImage(path: String, img: ImageView) {
        val imgUri = path.toUri().buildUpon().scheme("https").build()
        Glide.with(img.context)
                .load(imgUri)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image))
                .dontTransform()
                .into(img)
    }
}