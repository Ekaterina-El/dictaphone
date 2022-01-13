package el.ka.dictophone.objects

import android.media.MediaRecorder

class DictaphoneRecorder {
    var recording = false
    var recorder: MediaRecorder? = null

    fun startRecord(filePath: String) {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(filePath)
            setAudioEncodingBitRate(16 * 44100)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD)
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