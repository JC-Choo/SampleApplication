package dev.chu.memo.ui.rv_simple

import android.os.Bundle
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity2
import dev.chu.memo.databinding.ActivityRvSimpleBinding
import dev.chu.memo.ui.rv_simple.amimation.usage.Post
import dev.chu.memo.ui.rv_simple.amimation.usage.PostsAdapter
import java.util.*

/**
 * 참고 : https://medium.com/@jermainedilao/powerful-and-reusable-listadapter-using-databinding-for-simple-lists-55ac377fa750
 */

class RvSimpleActivity : BaseActivity2<ActivityRvSimpleBinding>(R.layout.activity_rv_simple) {

    override fun initView(savedInstanceState: Bundle?) {
        binding.list.apply {
//            adapter = SimpleListAdapter<Article, ItemRvSimpleBinding>(
//                ArticleDiffUtil(),
//                R.layout.item_rv_simple
//            ) { item ->
//                showToast("item = ${item.title}")
//            }.apply {
//                submitList(getItems())
//            }


            val selectAdapter = SelectAdapter().apply {
                list = createRandomIntList()
            }
            adapter = selectAdapter
            selectAdapter.notifyDataSetChanged()
        }

        binding.postsRecycler.apply {
            adapter = PostsAdapter().apply {
                submitList(createPosts())
            }
        }
    }

    private fun createRandomIntList(): List<Int> {
        val random = Random()
        return (1..10).map { random.nextInt() }
    }

    private fun createPosts(): List<Post> =
        List(100) { Post("Body $it", "Title $it", "Email $it")}

    private fun getItems(): List<Article> {
        val items: MutableList<Article> = mutableListOf()
        for(i in 0 until 30) {
            items.add(Article("title $i 번째"))
        }
        return items
    }
}