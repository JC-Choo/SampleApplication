package dev.chu.drag_and_drop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.*
import dev.chu.drag_and_drop.adapter.SentenceAdapter
import dev.chu.drag_and_drop.adapter.WordsAdapter
import dev.chu.drag_and_drop.databinding.ActivityMainBinding
import dev.chu.drag_and_drop.listener.DropListener

/**
 * https://proandroiddev.com/drag-and-drop-in-android-all-you-need-to-know-6df8babfb507
 *
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // 드래그할 뷰의 값들 (보통 데이터 구조로 온다.)
    private val words = mutableListOf("world", "a", "!", "What", "wonderful")

    // 마지막 선택한 단어
    private var selectedWord: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val sentenceAdapter = SentenceAdapter()
        val wordsAdapter = WordsAdapter {
            selectedWord = it
        }.apply {
            submitList(words)
        }

        binding.rvSentence.adapter = sentenceAdapter

        binding.rvSentence.setOnDragListener(
            DropListener {
                wordsAdapter.removeItem(selectedWord)
                sentenceAdapter.addItem(selectedWord)
            }
        )

        binding.rvWords.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP).apply {
            justifyContent = JustifyContent.SPACE_EVENLY
            alignItems = AlignItems.CENTER
        }

        binding.rvWords.adapter = wordsAdapter
    }
}