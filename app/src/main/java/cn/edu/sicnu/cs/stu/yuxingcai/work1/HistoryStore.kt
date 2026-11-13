package cn.edu.sicnu.cs.stu.yuxingcai.work1

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 一条历史记录：运算表达式及其执行时间。
 */
data class HistoryEntry(
    val expression: String,
    val time: String
)

/**
 * 内存中的历史记录仓库，用于保存本次运行期间执行过的运算。
 */
object HistoryStore {

    private val entries = mutableListOf<HistoryEntry>()
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun add(expression: String) {
        entries.add(0, HistoryEntry(expression, formatter.format(Date())))
    }

    fun all(): List<HistoryEntry> = entries.toList()
}
