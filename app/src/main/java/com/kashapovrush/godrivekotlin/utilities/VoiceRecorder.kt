package com.kashapovrush.godrivekotlin.utilities

import android.media.MediaRecorder
import android.util.Log
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.mainActivity
import java.io.File

class VoiceRecorder {

    private val mediaRecorder = MediaRecorder()
    private lateinit var file: File
    private lateinit var mMessageKey: String

    fun startRecorder(messageKey: String) {
        try {
            mMessageKey = messageKey
            createFileForRecorder()
            prepareMediaRecorder()
            mediaRecorder.start()

        } catch (e: Exception) {

        }
    }

    private fun prepareMediaRecorder() {

        mediaRecorder.apply {
            reset()
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(file.absoluteFile)
            prepare()
        }
    }

    private fun createFileForRecorder() {
        file = File(mainActivity.filesDir, mMessageKey)
        file.createNewFile()
    }

    fun stopRecorder(onSuccess: (file: File, messageKey: String) -> Unit) {
        try {
            mediaRecorder.stop()
            onSuccess(file, mMessageKey)
        } catch (e: Exception) {
            file.delete()
        }

    }

    fun releaseRecorder() {
        try {
            mediaRecorder.release()
        } catch (e: Exception) {

        }
    }
}


