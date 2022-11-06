package com.androidlabs.finalproject

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import com.androidlabs.finalproject.databinding.ActivityRequestsBinding
import com.androidlabs.finalproject.databinding.ActivityResourcesBinding
import kotlinx.android.synthetic.main.activity_resources.*

class Resources : DrawerBaseActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer1: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var runnable1: Runnable
    private var handler: Handler = Handler()
    private var handler1: Handler = Handler()
    private var pause: Boolean = false
    private var pause1: Boolean = false

    var activityResourcesBinding: ActivityResourcesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResourcesBinding = ActivityResourcesBinding.inflate(layoutInflater)
        allocateActivityTitle("Resources")
        setContentView(activityResourcesBinding!!.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.obongi)
        mediaPlayer1 = MediaPlayer.create(this, R.raw.motema)

        // Start the media player
        playBtn.setOnClickListener {
            try {
                if (mediaPlayer1.isPlaying || pause1.equals(true)) {
                    pause1 = false
                    seek_bar1.setProgress(0)
                    mediaPlayer1.stop()
                    mediaPlayer1.reset()
                    mediaPlayer1.release()
                    handler1.removeCallbacks(runnable1)
                    playBtn1.isEnabled = true
                    pauseBtn1.isEnabled = false
                    stopBtn1.isEnabled = false
                    tv_pass1.text = ""
                    tv_due1.text = ""
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace();
            }

            if (pause) {
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                Toast.makeText(this, "Playing media", Toast.LENGTH_SHORT).show()
            } else {
                mediaPlayer = MediaPlayer.create(this, R.raw.obongi)
                mediaPlayer.start()
                Toast.makeText(this, "Playing media", Toast.LENGTH_SHORT).show()
            }

            initializeSeekBar()
            playBtn.isEnabled = false
            pauseBtn.isEnabled = true
            stopBtn.isEnabled = true

            mediaPlayer.setOnCompletionListener {
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                Toast.makeText(this, "End", Toast.LENGTH_SHORT).show()
            }
        }

        playBtn1.setOnClickListener {
            try {
                if (mediaPlayer.isPlaying || pause.equals(true)) {
                    pause = false
                    seek_bar.setProgress(0)
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.release()
                    handler.removeCallbacks(runnable)
                    playBtn.isEnabled = true
                    pauseBtn.isEnabled = false
                    stopBtn.isEnabled = false
                    tv_pass.text = ""
                    tv_due.text = ""
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace();
            }

            if (pause1) {
                mediaPlayer1.seekTo(mediaPlayer1.currentPosition)
                mediaPlayer1.start()
                pause1 = false
                Toast.makeText(this, "Playing media", Toast.LENGTH_SHORT).show()
            } else {
                mediaPlayer1 = MediaPlayer.create(this, R.raw.motema)
                mediaPlayer1.start()
                Toast.makeText(this, "Playing media", Toast.LENGTH_SHORT).show()
            }

            initializeSeekBar1()
            playBtn1.isEnabled = false
            pauseBtn1.isEnabled = true
            stopBtn1.isEnabled = true

            mediaPlayer1.setOnCompletionListener {
                playBtn1.isEnabled = true
                pauseBtn1.isEnabled = false
                stopBtn1.isEnabled = false
                Toast.makeText(this, "End", Toast.LENGTH_SHORT).show()
            }
        }

        // Pause the media player
        pauseBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                pause = true
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = true
                Toast.makeText(this, "Media paused", Toast.LENGTH_SHORT).show()
            }
        }

        // Pause the media player 1
        pauseBtn1.setOnClickListener {
            if (mediaPlayer1.isPlaying) {
                mediaPlayer1.pause()
                pause1 = true
                playBtn1.isEnabled = true
                pauseBtn1.isEnabled = false
                stopBtn1.isEnabled = true
                Toast.makeText(this, "Media paused", Toast.LENGTH_SHORT).show()
            }
        }

        // Stop the media player
        stopBtn.setOnClickListener {
            if (mediaPlayer.isPlaying || pause.equals(true)) {
                pause = false
                seek_bar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                tv_pass.text = ""
                tv_due.text = ""
                Toast.makeText(this, "Media stopped", Toast.LENGTH_SHORT).show()
            }
        }

        // Stop the media player 1
        stopBtn1.setOnClickListener {
            if (mediaPlayer1.isPlaying || pause1.equals(true)) {
                pause1 = false
                seek_bar1.setProgress(0)
                mediaPlayer1.stop()
                mediaPlayer1.reset()
                mediaPlayer1.release()
                handler1.removeCallbacks(runnable1)

                playBtn1.isEnabled = true
                pauseBtn1.isEnabled = false
                stopBtn1.isEnabled = false
                tv_pass1.text = ""
                tv_due1.text = ""
                Toast.makeText(this, "Media stopped", Toast.LENGTH_SHORT).show()
            }
        }

        // Seek bar change listener
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        // Seek bar change listener 1
        seek_bar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer1.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds

            tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            tv_due.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar1() {
        seek_bar1.max = mediaPlayer1.seconds

        runnable1 = Runnable {
            seek_bar1.progress = mediaPlayer1.currentSeconds

            tv_pass1.text = "${mediaPlayer1.currentSeconds} sec"
            val diff1 = mediaPlayer1.seconds - mediaPlayer1.currentSeconds
            tv_due1.text = "$diff1 sec"

            handler1.postDelayed(runnable1, 1000)
        }
        handler1.postDelayed(runnable1, 1000)
    }

    // Creating an extension property to get the media player time duration in seconds
    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }

    // Creating an extension property to get media player current position in seconds
    val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }
}
