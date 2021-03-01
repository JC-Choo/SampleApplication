package dev.chu.rv_restoring_scroll_position.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.chu.rv_restoring_scroll_position.databinding.FragmentOtherBinding

class OtherFragment : Fragment() {

    companion object {
        fun newInstance() = OtherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentOtherBinding.inflate(layoutInflater).root
    }
}