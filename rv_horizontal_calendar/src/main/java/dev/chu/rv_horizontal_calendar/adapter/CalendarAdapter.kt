package dev.chu.rv_horizontal_calendar.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.chu.extensions.getColorById
import dev.chu.rv_horizontal_calendar.R
import dev.chu.rv_horizontal_calendar.databinding.ItemDateBinding
import dev.chu.rv_horizontal_calendar.model.CalendarDateModel

class CalendarAdapter(
    private val listener: (calendarDateModel: CalendarDateModel, position: Int) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.MyViewHolder>() {

    private val list = ArrayList<CalendarDateModel>()

    fun setData(calendarList: ArrayList<CalendarDateModel>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemDateBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_date, parent, false)
        val viewHolder = MyViewHolder(binding)
        binding.cardCalendar.setOnClickListener {
            listener.invoke(
                list[viewHolder.adapterPosition],
                viewHolder.adapterPosition
            )
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.count()

    inner class MyViewHolder(
        private val binding: ItemDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalendarDateModel) {
            binding.item = item
            binding.executePendingBindings()

            val numberCircle = binding.tvCalendarDate.background as GradientDrawable
            numberCircle.setColor(getNumberColor(adapterPosition))
        }

        // https://masadchatthaa.medium.com/reuse-vector-drawable-background-in-android-6a1528fdf25b
        private fun getNumberColor(position: Int): Int {
            val numberColorResourceId: Int = when (position % 4) {
                1 -> R.color.number1
                2 -> R.color.number2
                3 -> R.color.number3
                else -> R.color.number_default
            }
            return binding.root.context.getColorById(numberColorResourceId)
        }
    }
}