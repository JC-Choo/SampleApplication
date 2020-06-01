package dev.chu.memo.ui.single_view_state

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.chu.memo.R
import dev.chu.memo.etc.extension.TAG
import kotlinx.android.synthetic.main.fragment_single_view_state.*

class SingleViewStateFragment: Fragment() {

    private lateinit var viewModel: SingleViewStateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_view_state, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = SingleViewStateViewModel()
//        viewModel = activity?.let {
//            ViewModelProvider(it)[SingleViewStateViewModel::class.java]
//        } ?: run {
//            throw Exception("Activity is null")
//        }

        viewModel.test.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "test = $it")
            if(it <= 10) {
                viewModel.setTest(it+1)
            }
        })

        uploadButton.setOnClickListener {
            findNavController().navigate(R.id.uploadFragment)
        }
        profileButton1.setOnClickListener {
            findNavController().navigate(R.id.profileFragment, bundleOf("shouldError" to false))
        }
        profileButton2.setOnClickListener {
            findNavController().navigate(R.id.profileFragment, bundleOf("shouldError" to true))
        }
        listButton.setOnClickListener {
            findNavController().navigate(R.id.listFragment)
        }
    }

}