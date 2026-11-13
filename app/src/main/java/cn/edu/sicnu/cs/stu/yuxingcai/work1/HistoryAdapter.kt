package cn.edu.sicnu.cs.stu.yuxingcai.work1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 历史记录列表的适配器。
 */
class HistoryAdapter(
    private val items: List<HistoryEntry>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expressionText: TextView = itemView.findViewById(R.id.tv_expression)
        val timeText: TextView = itemView.findViewById(R.id.tv_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val entry = items[position]
        holder.expressionText.text = entry.expression
        holder.timeText.text = entry.time
    }

    override fun getItemCount(): Int = items.size
}
