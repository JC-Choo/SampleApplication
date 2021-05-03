package dev.chu.rv_horizontal_calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import dev.chu.rv_horizontal_calendar.adapter.CalendarAdapter
import dev.chu.rv_horizontal_calendar.databinding.ActivityMainBinding
import dev.chu.rv_horizontal_calendar.model.CalendarDateModel
import dev.chu.rv_horizontal_calendar.utils.HorizontalItemDecoration
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * https://tejas-soni.medium.com/horizontal-calendar-using-recylerview-android-f07f666f2da5
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()

    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val calendarList2 = ArrayList<CalendarDateModel>()
    private lateinit var adapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        setUpAdapter()
        setUpClickListener()
        setUpCalendar()
    }

    /**
     * Set up click listener
     */
    private fun setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar()
        }
    }

    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.single_calendar_margin)
        binding.list.addItemDecoration(HorizontalItemDecoration(spacingInPixels))

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.list)
        adapter = CalendarAdapter { _: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            adapter.setData(calendarList2)
        }
        binding.list.adapter = adapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        binding.tvDateMonth.text = sdf.format(cal.time) // 현재 달력의 월과 년을 제공
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth =
            cal.getActualMaximum(Calendar.DAY_OF_MONTH)    // 현재 월에 있는 일들의 최대 수. 즉, 며칠까지 있는지 가져오는 코드.
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1) // 시작하는 현재 달력 월 설정.

        // 현재 월의 최대 일의 사이즈까지 계속 루프를 돌아 array 에 dates를 저장한다.
        val calendarList = ArrayList<CalendarDateModel>()
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            val data =
                if (currentDay == monthCalendar[Calendar.DAY_OF_MONTH]
                    && currentMonth == monthCalendar[Calendar.MONTH]) {
                    CalendarDateModel(monthCalendar.time, true)
                } else {
                    CalendarDateModel(monthCalendar.time)
                }
            calendarList.add(data)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setData(calendarList)
    }
}