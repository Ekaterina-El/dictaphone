package el.ka.dictophone

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import el.ka.dictophone.objects.DictaphonePlayer
import el.ka.dictophone.objects.DictaphoneRecorder
import el.ka.dictophone.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var btnToggleRecord: Button
    private lateinit var btnTogglePlay: Button

    private lateinit var dictaphonePlayer: DictaphonePlayer
    private lateinit var dictaphoneRecorder: DictaphoneRecorder

    private lateinit var fileName: String


    private var permissionToRecordAudion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAllPermissions()
        fileName = "${externalCacheDir!!.absolutePath}/audio.3gp"

        dictaphoneRecorder = DictaphoneRecorder()
        dictaphonePlayer = DictaphonePlayer()

        btnToggleRecord = findViewById(R.id.btnStart)
        btnToggleRecord.setOnClickListener {
            stopPlaying()
            if (!dictaphoneRecorder.recording) startRecord() else stopRecord()
        }

        btnTogglePlay = findViewById(R.id.btnTogglePlay)
        btnTogglePlay.setOnClickListener {
            if (dictaphonePlayer.playing) stopPlaying() else startPlaying()
        }
    }

    private fun startRecord() {
        if (!dictaphoneRecorder.recording) {
            btnToggleRecord.text = "Запись..."
            dictaphoneRecorder.startRecord(fileName)
        }
    }

    private fun stopRecord() {
        if (dictaphoneRecorder.recording) {
            btnToggleRecord.text = "Начать"
            dictaphoneRecorder.stopRecord()
        }
    }

    private fun startPlaying() {
        dictaphonePlayer.startPlaying(fileName)
        btnTogglePlay.text = "Остановить прослушивание"
    }


    private fun stopPlaying() {
        dictaphonePlayer.stopPlaying()
        btnTogglePlay.text = "Прослушать"
    }

    private fun requestAllPermissions() {
        requestPermissions(
            Constants.permissions,
            Constants.RECORD_AUDIO_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.RECORD_AUDIO_PERMISSION) {
            permissionToRecordAudion = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (!permissionToRecordAudion) finish()
        }
    }

}