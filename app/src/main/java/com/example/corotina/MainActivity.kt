import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var coroutineCount = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countText: TextView = findViewById(R.id.countText)
        val seekBar: SeekBar = findViewById(R.id.seekBar)
        val statusText: TextView = findViewById(R.id.statusText)
        val startButton: Button = findViewById(R.id.startButton)

        // Обработка изменения положения SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                coroutineCount = progress
                countText.text = "$coroutineCount coroutines"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Обработка нажатия кнопки
        startButton.setOnClickListener {
            launchCoroutines(statusText)
        }
    }

    private fun launchCoroutines(statusText: TextView) {
        (1..coroutineCount).forEach { taskNumber ->
            coroutineScope.launch {
                statusText.text = "Started Coroutine $taskNumber"
                val result = performTask(taskNumber)
                statusText.text = result
            }
        }
    }

    private suspend fun performTask(taskNumber: Int): String {
        delay(5000)
        return "Finished Coroutine $taskNumber"
    }
}
