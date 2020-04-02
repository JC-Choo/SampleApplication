package dev.chu.memo.ui.motion_layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Page  : Fragment() {

    private var layoutId = 0

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        if (args != null) {
            layoutId = args.getInt("layout")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutId, container, false)
    }
}