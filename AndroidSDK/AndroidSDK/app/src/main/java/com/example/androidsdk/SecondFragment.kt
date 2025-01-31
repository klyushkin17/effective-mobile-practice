package com.example.androidsdk

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.example.androidsdk.databinding.FragmentSecondBinding
import com.example.androidsdk.routers.NavigationRouter


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var navigationRouter: NavigationRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationRouter = activity as NavigationRouter

        binding.toFirstFragment.setOnClickListener {
            navigationRouter.navigateTo(FirstFragment())
        }

        binding.toThirdFragment.setOnClickListener {
            navigationRouter.navigateTo(ThirdFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}