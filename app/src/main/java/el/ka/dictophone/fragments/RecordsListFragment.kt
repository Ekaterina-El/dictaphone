package el.ka.dictophone.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import el.ka.dictophone.R
import el.ka.dictophone.adapters.RecordsAdapter
import el.ka.dictophone.models.Record
import el.ka.dictophone.objects.DictaphonePlayer
import el.ka.dictophone.utils.MAIN_ACTIVITY
import el.ka.dictophone.utils.db
import el.ka.dictophone.utils.deleteFile

class RecordsListFragment : Fragment(R.layout.fragment_records_list) {
    companion object {
        const val TAG = "RecordsListFragment"
    }

    private lateinit var adapter: RecordsAdapter
    private lateinit var records: MutableList<Record>
    private lateinit var dictaphonePlayer: DictaphonePlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dictaphonePlayer = DictaphonePlayer()

        records = db.getRecords()
        val recordsList = view.findViewById<RecyclerView>(R.id.recordsList)

        adapter = RecordsAdapter(
            {id ->
                MAIN_ACTIVITY.showDialogChangeName(id)},
            {id ->
                deleteFile(db.getRecordById(id)!!.filePath)
                db.deleteRecordById(id)
                val idx = getIndexOfRecord(id)!!
                adapter.deleteRecordById(idx)
            }
        ) { record ->
            dictaphonePlayer.startPlaying(record.filePath)
        }
        adapter.setRecords(records)
        recordsList.adapter = adapter
    }

    fun changeDataById(id: Int) {
        val idx = getIndexOfRecord(id)
        if (idx != null) {
            records[idx] = db.getRecordById(id)!!
            adapter.changeDateById(idx, records[idx])
        }
    }

    private fun getIndexOfRecord(id: Int): Int? {
        records.forEachIndexed { index, record ->
            if (record.id == id) return index
        }
        return null
    }
}