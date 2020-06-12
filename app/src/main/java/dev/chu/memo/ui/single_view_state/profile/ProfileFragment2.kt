package dev.chu.memo.ui.single_view_state.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dev.chu.memo.R
import dev.chu.memo.ui.single_view_state.exhaustive
import kotlinx.android.synthetic.main.fragment_profile2.*

class ProfileFragment2 : Fragment() {

    private object Flipper {
        const val LOADING = 0
        const val CONTENT = 1
        const val ERROR = 2
    }

    private lateinit var viewModel2: ProfileViewModel2

    private val args: ProfileFragment2Args by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel2 = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel2::class.java)

        viewModel2.viewState.observe(viewLifecycleOwner, Observer { state ->
            render(state)
        })

        viewModel2.load(args.shouldError)
    }

    private fun render(viewState: ProfileViewState) {
        when (viewState) {
            Loading -> {
                viewFlipper.displayedChild = Flipper.LOADING
            }
            Error -> {
                viewFlipper.displayedChild = Flipper.ERROR
            }
            is ProfileLoaded -> {
                viewFlipper.displayedChild = Flipper.CONTENT
                profileNameText.text = viewState.name
                profileEmailText.text = viewState.email
            }
        }.exhaustive
    }
}