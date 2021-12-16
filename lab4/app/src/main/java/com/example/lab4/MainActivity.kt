package com.example.lab4

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView
import android.widget.Toast
import android.widget.ArrayAdapter

enum class MediaType {
    AUDIO,
    VIDEO,
    NOT_SELECTED
}

class MainActivity : Activity() {
    private lateinit var player: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var spinner: Spinner
    private lateinit var onlineResourceInput: EditText
    private val audioFiles = arrayOf("Audio1", "Audio2")
    private val videoFiles = arrayOf("Video1", "Video2")
    private var mediaType = MediaType.NOT_SELECTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        player = findViewById(R.id.videoPlayer)
        spinner = findViewById(R.id.file_chooser)
        onlineResourceInput = findViewById(R.id.media_uri)
        mediaController = MediaController(this)
        player.setMediaController(mediaController)
        mediaController.setMediaPlayer(player)

        findViewById<Button>(R.id.play_online_button).setOnClickListener {
            val source = onlineResourceInput.text.toString()

            if (mediaType == MediaType.NOT_SELECTED) {
                showToast("Please select media type!")
                return@setOnClickListener
            }

            if (source.isEmpty()) {
                showToast("Please enter resource URI!")
                return@setOnClickListener
            }

            if (mediaType == MediaType.VIDEO) {
                playOnlineVideo(source)
            } else {
                playOnlineAudio(source)
            }
        }

        player.setOnErrorListener { _, _, _ ->
            showToast("MediaPlayer Error")
            true
        }

        val typeSelector: RadioGroup = findViewById(R.id.media_type_selector)
        typeSelector.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            mediaType = when (checkedId) {
                R.id.audio_option -> MediaType.AUDIO
                R.id.video_option -> MediaType.VIDEO
                else -> MediaType.VIDEO
            }

            val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                if (mediaType == MediaType.VIDEO) videoFiles else audioFiles
            )

            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerArrayAdapter
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                if (parent != null) {
                    val selectedFile = parent.getItemAtPosition(selectedItemPosition).toString()

                    showToast("Playing $selectedFile!")

                    if (mediaType == MediaType.VIDEO) {
                        playLocalVideo(selectedFile)
                    } else {
                        playLocalAudio(selectedFile)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun playLocalAudio(fileName: String) {
        player.stopPlayback()
        player.alpha = 0F

        val id = when (fileName) {
            "Audio1" -> R.raw.audio1
            "Audio2" -> R.raw.audio2
            else -> R.raw.audio1
        }
        val uri = Uri.parse("android.resource://" + packageName + "/" + id)

        player.setVideoURI(uri)
        player.start()

        mediaController.show(0)
    }

    private fun playLocalVideo(fileName: String) {
        player.stopPlayback()
        player.alpha = 1F

        val id = when (fileName) {
            "Video1" -> R.raw.video1
            "Video2" -> R.raw.video2
            else -> R.raw.video1
        }
        val uri = Uri.parse("android.resource://" + packageName + "/" + id)

        player.setVideoURI(uri)
        player.start()
    }

    private fun playOnlineAudio(source: String) {
        player.stopPlayback()
        player.alpha = 0F

        val uri = Uri.parse(source)

        player.setVideoURI(uri)
        player.start()

        mediaController.show(0)
    }

    private fun playOnlineVideo(source: String) {
        player.stopPlayback()
        player.alpha = 1F

        val uri = Uri.parse(source)

        player.setVideoURI(uri)
        player.start()
    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}

// https://static.videezy.com/system/resources/previews/000/056/873/original/Comp-3-zoom.mp4
// https://samplelib.com/lib/preview/mp3/sample-12s.mp3
