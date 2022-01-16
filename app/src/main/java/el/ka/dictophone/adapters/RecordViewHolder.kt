package el.ka.dictophone.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import el.ka.dictophone.R
import el.ka.dictophone.models.Record
import el.ka.dictophone.utils.durationFormat
import java.util.*

class RecordViewHolder(
    itemView: View,
    val onChangeRecordName: (Int) -> Unit,
    val onDeleteRecord: (Int) -> Unit,
    private val onItemClickListener: (Record) -> Unit
) : RecyclerView.ViewHolder(itemView) {

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
        tvDate.text = RecordsAdapter.sdf.format(Date(record.createAt))
        tvDuration.text = durationFormat(record.duration)
    }
}