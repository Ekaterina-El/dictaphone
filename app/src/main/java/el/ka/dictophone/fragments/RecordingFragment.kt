package el.ka.dictophone.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import el.ka.dictophone.R
import el.ka.dictophone.models.Record
import el.ka.dictophone.objects.DictaphonePlayer
import el.ka.dictophone.objects.DictaphoneRecorder
import el.ka.dictophone.utils.MAIN_ACTIVITY
import el.ka.dictophone.utils.RECORDS_LIST_FRAGMENT
import el.ka.dictophone.utils.changeFragment
import el.ka.dictophone.utils.db
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class RecordingFragment : Fragment(R.layout.fragment_recording) {
    private lateinit var btnTogglePlay: Button
    private lateinit var btnToggleRecord: Button

    private lateinit var dictaphonePlayer: DictaphonePlayer
    private lateinit var dictaphoneRecorder: DictaphoneRecorder

    private lateinit var newRecordName: String
    private lateinit var newRecordPath: String
    private val cachePath = MAIN_ACTIVITY.externalCacheDir!!.absolutePath

    private fun getNewRecordName(): String {
        return "${Date().time}"
    }

    private fun getNewRecordPath(): String {
        return "$cachePath/${Date().time}.3gp"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initControlDictaphone()
        initButtons(view)
    }

    private fun initControlDictaphone() {
        dictaphoneRecorder = DictaphoneRecorder()
        dictaphonePlayer = DictaphonePlayer()
    }

    private fun initButtons(view: View) {
        btnToggleRecord = view.findViewById(R.id.btnStart)
        btnToggleRecord.setOnClickListener {
            stopPlaying()
            if (!dictaphoneRecorder.recording) startRecord() else stopRecord()
        }

        btnTogglePlay = view.findViewById(R.id.btnTogglePlay)
        btnTogglePlay.setOnClickListener {
            if (dictaphonePlayer.playing) stopPlaying() else startPlaying()
        }

        val btnGoToRecords = view.findViewById<Button>(R.id.btnGoToRecords)
        btnGoToRecords.setOnClickListener {
            changeFragment(RECORDS_LIST_FRAGMENT)
        }
    }

    private fun startRecord() {
        if (!dictaphoneRecorder.recording) {
            newRecordName = getNewRecordName()
            newRecordPath = getNewRecordPath()

            btnToggleRecord.text = "Запись..."
            dictaphoneRecorder.startRecord(newRecordPath)
        }
    }

    private fun stopRecord() {
        if (dictaphoneRecorder.recording) {
            dictaphoneRecorder.stopRecord()
            val duration = dictaphonePlayer.getDurationOfFile(newRecordPath)
            val record = Record(
                0,
                newRecordName,
                newRecordPath,
                Date().time,
                duration
            )
            db.addRecord(record)
            btnToggleRecord.text = "Начать"
        }
    }

    private fun startPlaying() {
        dictaphonePlayer.startPlaying(newRecordName)
        btnTogglePlay.text = "Остановить прослушивание"
    }


    private fun stopPlaying() {
        dictaphonePlayer.stopPlaying()
        btnTogglePlay.text = "Прослушать"
    }
}