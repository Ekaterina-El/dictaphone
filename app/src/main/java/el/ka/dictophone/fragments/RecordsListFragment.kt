package el.ka.dictophone.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import el.ka.dictophone.R
import el.ka.dictophone.adapters.RecordsAdapter
import el.ka.dictophone.objects.DictaphonePlayer
import el.ka.dictophone.utils.db

class RecordsListFragment : Fragment(R.layout.fragment_records_list) {
    companion object {
        const val TAG = "RecordsListFragment"
    }

    private lateinit var dictaphonePlayer: DictaphonePlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dictaphonePlayer = DictaphonePlayer()

        val records = db.getRecords()

        val recordsList = view.findViewById<RecyclerView>(R.id.recordsList)
        val adapter = RecordsAdapter { record ->
            dictaphonePlayer.startPlaying(record.filePath)
        }
        adapter.setRecords(records)
        recordsList.adapter = adapter

    }
}