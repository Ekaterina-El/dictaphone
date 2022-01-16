package el.ka.dictophone.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import el.ka.dictophone.R
import el.ka.dictophone.models.Record
import java.text.SimpleDateFormat
import java.util.*

class RecordsAdapter(
    private val onChangeRecordName: (Int) -> Unit,
    private val onDeleteRecord: (Int) -> Unit,
    private val onItemClickListener: (Record) -> Unit
) : RecyclerView.Adapter<RecordsAdapter.ViewHolder>() {
    companion object {
        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("d/M/yy hh:mm")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.recordName)
        private val tvDate = itemView.findViewById<TextView>(R.id.recordDate)
        private val tvDuration = itemView.findViewById<TextView>(R.id.recordDuration)
        private val icDelete = itemView.findViewById<ImageView>(R.id.delete_record)

        fun setRecordInfo(record: Record) {
            itemView.setOnClickListener {
                onItemClickListener(record)
            }
            itemView.setOnLongClickListener {
                onChangeRecordName(record.id)
                true
            }

            icDelete.setOnClickListener {
                onDeleteRecord(record.id)
            }

            tvName.text = record.name
            tvDate.text = sdf.format(Date(record.createAt))
            tvDuration.text = record.duration.toString()
        }
    }

    private var records = mutableListOf<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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