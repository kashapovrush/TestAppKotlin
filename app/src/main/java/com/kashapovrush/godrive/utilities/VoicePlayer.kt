package com.kashapovrush.godrive.utilities

import android.media.MediaPlayer
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kashapovrush.godrive.utilities.Constants.Companion.mainActivity
import java.io.File

class VoicePlayer {

    private lateinit var file: File
    private lateinit var mediaPlayer: MediaPlayer
    private val storage: StorageReference = FirebaseStorage.getInstance().reference

    fun play(messageKey: String, fileUrl: String, function: () -> Unit) {
        file = File(mainActivity.filesDir, messageKey)
        if (file.exists() && file.length() > 0 && file.isFile) {
            try {
                mediaPlayer.setDataSource(file.absolutePath)
                mediaPlayer.prepare()
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    stop {
                        function()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            file.createNewFile()
            val path = storage.storage.getReferenceFromUrl(fileUrl)
            path.getFile(file)
                .addOnSuccessListener {
                    try {
                        mediaPlayer.setDataSource(file.absolutePath)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                        mediaPlayer.setOnCompletionListener {
                            stop {
                                function()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .addOnFailureListener {

                }
        }
    }

    fun stop(function: () -> Unit) {
        try {
            mediaPlayer.stop()
            mediaPlayer.reset()
            function()
        } catch (e: Exception) {
            e.printStackTrace()
            function()
        }
    }

    fun release() {
        mediaPlayer.release()
    }

    fun init() {
        mediaPlayer = MediaPlayer()
    }

}