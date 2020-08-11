package dev.chu.memo.ui.rv_sticky_header

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.ui.rv_sticky_header.test01.*
import kotlinx.android.synthetic.main.activity_recycler_view_sticky.*
import java.util.*


class ActivityRecyclerViewSticky : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_sticky)

        recycler_view.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val people = getPeople()
            val sectionItemDecoration =
                RecyclerSectionItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
                    true,
                    getSectionCallback(people)!!
                )
            addItemDecoration(sectionItemDecoration)
            adapter = PersonAdapter(
                layoutInflater,
                people,
                R.layout.recycler_row
            )



//            val simpleAdapter = SimpleRecyclerView()
//            adapter = simpleAdapter
//            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(StickHeaderItemDecoration(simpleAdapter))
        }
    }

    private fun getPeople(): List<Person> {
        val personRepo = PersonRepo()
        val people: List<Person> = personRepo.people
        Collections.sort(people)
        return people
    }

    private fun getSectionCallback(people: List<Person>): SectionCallback? {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
//                return (position == 0 ||
//                        people[position].lastName[0] != people[position - 1].lastName[0])

                return position == 3
            }

            override fun getSectionHeader(position: Int): CharSequence {
//                return people[position].lastName.subSequence(0, 1)
                return "Header_"
            }
        }
    }
}