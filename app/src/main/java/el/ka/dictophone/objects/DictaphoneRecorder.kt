package el.ka.dictophone.objects

import android.media.MediaRecorder

class DictaphoneRecorder {
    var recording = false
    var recorder: MediaRecorder? = null

    fun startRecord(filePath: String) {


        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
            setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    setAudioEncodingBitRate(3000000)
            setOutputFile(filePath)
            prepare()
            start()
        }
        recording = true
    }

    fun stopRecord() {
        recorder?.apply {
            stop()
            release()
        }
        recording = false
        recorder = null
    }
}