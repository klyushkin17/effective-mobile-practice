package com.example.rxjava_practise

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_practise.data.remote.HhRuApi
import com.example.rxjava_practise.data.remote.dto.VacancyDto
import com.example.rxjava_practise.databinding.ActivityMainBinding
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://klyushkin17.github.io/hh-api/"
    private val TAG = "Vacancies List"

    private var compositeDisposable: CompositeDisposable? = null
    private var vacanciesList: MutableList<VacancyDto>? = null
    private var vacanciesListAdapter: VacancyListAdapter? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable = CompositeDisposable()

        initVacanciesListRecyclerView()
        getVacancies()
    }

    private fun initVacanciesListRecyclerView() {
        binding.rvVacanciesList.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvVacanciesList.layoutManager = layoutManager
    }

    private fun getVacancies() {
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(HhRuApi::class.java)

        compositeDisposable?.add(
            requestInterface.getVacancies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ responseList ->
                    vacanciesList = responseList.vacancyList.toMutableList()
                    vacanciesListAdapter = VacancyListAdapter(vacanciesList!!)

                    binding.rvVacanciesList.adapter = vacanciesListAdapter
                }, { error ->
                    error.localizedMessage?.let { Log.e(TAG, it) }
                    Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}
