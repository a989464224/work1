package cn.edu.sicnu.cs.stu.yuxingcai.work1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 第一个 Activity：输入两个数、选择运算，调用计算类执行运算并显示结果。
 */
class MainActivity : AppCompatActivity() {

    private val calculator = Calculator()

    private lateinit var firstInput: EditText
    private lateinit var secondInput: EditText
    private lateinit var operatorGroup: RadioGroup
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.main_title)

        firstInput = findViewById(R.id.et_first)
        secondInput = findViewById(R.id.et_second)
        operatorGroup = findViewById(R.id.rg_operator)
        resultText = findViewById(R.id.tv_result)
        findViewById<MaterialButton>(R.id.btn_calculate).setOnClickListener {
            performCalculation()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 屏幕旋转等配置变化后恢复结果，保证横竖屏都能正常显示结果
        savedInstanceState?.let {
            resultText.text = it.getString(KEY_RESULT)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RESULT, resultText.text.toString())
    }

    private fun performCalculation() {
        val first = firstInput.text.toString().toDoubleOrNull()
        val second = secondInput.text.toString().toDoubleOrNull()
        if (first == null || second == null) {
            resultText.text = getString(R.string.error_invalid_input)
            return
        }

        val operator = when (operatorGroup.checkedRadioButtonId) {
            R.id.rb_add -> "+"
            R.id.rb_subtract -> "-"
            R.id.rb_multiply -> "*"
            R.id.rb_divide -> "/"
            else -> null
        }
        if (operator == null) {
            resultText.text = getString(R.string.error_select_operator)
            return
        }

        val result = try {
            calculator.calculate(first, operator, second)
        } catch (e: ArithmeticException) {
            resultText.text = getString(R.string.error_divide_by_zero)
            return
        }

        val formatted = formatNumber(result)
        resultText.text = getString(R.string.result_prefix) + formatted
        HistoryStore.add(
            "${formatNumber(first)} $operator ${formatNumber(second)} = $formatted"
        )
    }

    private fun formatNumber(value: Double): String {
        return BigDecimal.valueOf(value)
            .setScale(10, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
    }

    companion object {
        private const val KEY_RESULT = "key_result"
    }
}
