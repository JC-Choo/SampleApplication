package dev.chu.rv_restoring_scroll_position

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_restoring_scroll_position.databinding.ItemBannerBinding
import dev.chu.rv_restoring_scroll_position.databinding.ItemCarouselBinding
import dev.chu.rv_restoring_scroll_position.store_position.Content
import dev.chu.rv_restoring_scroll_position.store_position.NestedRecyclerViewStateRecoverAdapter
import dev.chu.rv_restoring_scroll_position.store_position.NestedRecyclerViewViewHolder

private enum class ViewType {
    BANNER, CAROUSEL
}

class ContentAdapter :
    NestedRecyclerViewStateRecoverAdapter<Content, ContentAdapter.ViewHolder>(ContentAdapterDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewType.BANNER.ordinal -> {
                ViewHolder.BannerViewHolder(
                    binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            ViewType.CAROUSEL.ordinal -> {
                ViewHolder.CarouselViewHolder(
                    binding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> throw ClassNotFoundException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Content.Banner -> ViewType.BANNER.ordinal
            is Content.Carousel -> ViewType.CAROUSEL.ordinal
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.BANNER.ordinal -> (holder as ViewHolder.BannerViewHolder).bind(getItem(position) as Content.Banner)
            ViewType.CAROUSEL.ordinal -> (holder as ViewHolder.CarouselViewHolder).bind(getItem(position) as Content.Carousel)
            else -> throw ClassNotFoundException()
        }

        super.onBindViewHolder(holder, position)
    }

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class BannerViewHolder(
            private val binding: ItemBannerBinding
        ) : ViewHolder(binding.root) {

            fun bind(item: Content.Banner) {
                with(binding) {
                    txtBanner.text = root.context.getString(R.string.banner_text, item.id)
                }
            }
        }

        class CarouselViewHolder(
            private val binding: ItemCarouselBinding
        ): ViewHolder(binding.root), NestedRecyclerViewViewHolder {

            private lateinit var content: Content.Carousel

            init {
                binding.carousel.apply {
                    adapter = CarouselAdapter()
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                }
            }

            fun bind(item: Content.Carousel) {
                this.content = item
                with(binding) {
                    (carousel.adapter as CarouselAdapter).submitList(content.list)
                }
            }

            override fun getId() = content.id
            override fun getLayoutManager(): RecyclerView.LayoutManager? = binding.carousel.layoutManager
        }
    }

    private class ContentAdapterDiffUtil: DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem::class.simpleName == newItem::class.simpleName
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }

    }
}