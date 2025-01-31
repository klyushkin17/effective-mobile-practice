package com.example.androidsdk

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.example.androidsdk.databinding.FragmentThirdBinding
import com.example.androidsdk.routers.NavigationRouter


class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var _binding: FragmentThirdBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var navigationRouter: NavigationRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationRouter = activity as NavigationRouter

        binding.toSecondButon.setOnClickListener {
            navigationRouter.navigateTo(SecondFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}