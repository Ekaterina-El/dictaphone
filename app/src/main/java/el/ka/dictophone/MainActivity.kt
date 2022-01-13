package el.ka.dictophone

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var recorder: MediaRecorder? = null
    private lateinit var btnToggleRecord: Button
    private lateinit var btnTogglePlay: Button
    private var isRecord = false
    private var isPlaying = false

    private lateinit var fileName: String

    companion object {
        const val RECORD_AUDIO_PERMISSION = 1
    }

    private var permissionToRecordAudion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAllPermissions()
        fileName = "${externalCacheDir!!.absolutePath}/audio.3gp"

        btnToggleRecord = findViewById(R.id.btnStart)
        btnToggleRecord.setOnClickListener {
            stopPlaying()
            if (!isRecord) startRecord() else stopRecord()
        }

        btnTogglePlay = findViewById(R.id.btnTogglePlay)
        btnTogglePlay.setOnClickListener {
            if (!isRecord) {
                if (isPlaying) stopPlaying() else startPlaying()
            }
        }
    }

    private fun startRecord() {
        btnToggleRecord.text = "Запись..."
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncodingBitRate(16 * 44100)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD)
            prepare()
            start()
        }
        isRecord = true
    }

    private fun stopRecord() {
        btnToggleRecord.text = "Начать"
        recorder?.apply {
            stop()
            release()
        }
        isRecord = false
        recorder = null
    }

    private fun startPlaying() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(fileName)
            prepare()
            start()
            setOnCompletionListener {
                stopPlaying()
            }
        }
        isPlaying = true
        btnTogglePlay.text = "Остановить прослушивание"
    }


    private fun stopPlaying() {
        mediaPlayer?.release()
        btnTogglePlay.text = "Прослушать"
        isPlaying = false
        mediaPlayer = null
    }

    private fun requestAllPermissions() {
        val permissions = arrayOf(
            android.Manifest.permission.RECORD_AUDIO
        )

        requestPermissions(
            permissions,
            RECORD_AUDIO_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RECORD_AUDIO_PERMISSION) {
            permissionToRecordAudion = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (!permissionToRecordAudion) finish()
        }
    }

}