package el.ka.dictophone.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import el.ka.dictophone.R
import el.ka.dictophone.models.Record
import java.text.SimpleDateFormat
import java.util.*

class RecordsAdapter(
    private val onChangeRecordName: (Int) -> Unit,
    private val onDeleteRecord: (Int) -> Unit,
    private val onItemClickListener: (Record) -> Unit
) : RecyclerView.Adapter<RecordViewHolder>() {
    companion object {
        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("d/M/yy hh:mm")
    }

    private var records = mutableListOf<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return RecordViewHolder(view, onChangeRecordName, onDeleteRecord, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.setRecordInfo(records[position])
    }

    override fun getItemCount() = records.size

    @SuppressLint("NotifyDataSetChanged")
    fun setRecords(records: MutableList<Record>) {
        this.records = records
        notifyDataSetChanged()
    }

    fun changeDateById(idx: Int, record: Record) {
        records[idx] = record
        notifyItemChanged(idx)
    }

    fun deleteRecordById(idx: Int) {
        records = records.filterIndexed { index, _ -> index != idx }.toMutableList()
        notifyItemRemoved(idx)
    }
}