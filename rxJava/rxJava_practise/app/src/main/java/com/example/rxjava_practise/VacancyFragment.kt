package com.example.rxjava_practise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rxjava_practise.databinding.VacancyScreenBinding

class VacancyFragment: Fragment() {

    private lateinit var binding: VacancyScreenBinding

    companion object {
        fun getNewInstance(arguments: Bundle?): VacancyFragment {
            val vacancyFragment = VacancyFragment()
            vacancyFragment.arguments = arguments
            return vacancyFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VacancyScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.indexOfVacancy.text = arguments?.getInt("vacancyPosition").toString()
    }
}