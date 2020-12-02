package com.example.marchenandroid.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marchenandroid.MainActivity
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private val VIDEO_SAMPLE = "presentations"//"tapok"
    private val PLAYBACK_TIME = "play_time"
    private var mCurrentPosition = 0
    private var mVideoView: VideoView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false
        )
        binding.accountViewModel = viewModel

        binding.getIdBtn.setOnClickListener {
            val id = viewModel.getTeacherId()
            Toast.makeText(context, "Your Id: $id", Toast.LENGTH_LONG).show()
        }

        mVideoView = binding.videoView

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        val controller = MediaController(activity)
        controller.setMediaPlayer(mVideoView)
        mVideoView!!.setMediaController(controller);

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView!!.pause()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYBACK_TIME, mVideoView!!.currentPosition)
    }

    private fun quit() {
        viewModel.quit()
        requireActivity().finish()
        startActivity(Intent(context, MainActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)

        if (viewModel == null) {
            menu.findItem(R.id.logout)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> quit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMedia(mediaName: String): Uri? {
        return if (URLUtil.isValidUrl(mediaName)) {
            // media name is an external URL
            Uri.parse(mediaName)
        } else { // media name is a raw resource embedded in the app
            Uri.parse(
                    "android.resource://" + context?.getPackageName() +
                            "/raw/" + mediaName
            )
        }
    }

    private fun initializePlayer() {
        val videoUri = getMedia(VIDEO_SAMPLE)
        mVideoView!!.setVideoURI(videoUri)

//        if (mCurrentPosition > 0) {
//            mVideoView!!.seekTo(mCurrentPosition);
//        } else {
//            // Skipping to 1 shows the first frame of the video.
//            mVideoView!!.seekTo(1);
//        }

        mVideoView!!.setOnPreparedListener {
            if (mCurrentPosition > 0) {
                mVideoView!!.seekTo(mCurrentPosition);
            } else {
                mVideoView!!.seekTo(1);
            }

            mVideoView!!.start();
        }
    }

    private fun releasePlayer() {
        mVideoView!!.stopPlayback()
    }
}