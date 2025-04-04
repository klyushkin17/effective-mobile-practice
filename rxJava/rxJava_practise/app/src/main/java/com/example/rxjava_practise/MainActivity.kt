package com.example.rxjava_practise

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_practise.data.remote.HhRuApi
import com.example.rxjava_practise.data.remote.dto.DiscountCardDto
import com.example.rxjava_practise.data.remote.dto.VacancyDto
import com.example.rxjava_practise.databinding.ActivityMainBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://klyushkin17.github.io/hh-api/"
    private val TAG = "VacanciesList"

    private var compositeDisposable: CompositeDisposable? = null
    private var timerDisposable: CompositeDisposable? = null
    private var debouncedEditTestDisposable: Disposable? = null
    private var vacanciesList: MutableList<VacancyDto>? = null
    private var vacanciesListAdapter: VacancyListAdapter? = null
    private var discountCardsList: List<DiscountCardDto> = emptyList()
    private var discountCardsListAdapter: DiscountCardsListAdapter? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable = CompositeDisposable()
        timerDisposable = CompositeDisposable()

        initVacanciesListRecyclerView()
        initDiscountCardsRecyclerView()
        getVacancies()
        getDiscountCards()

        binding.startTimerButton.setOnClickListener {
            startTimerObservable()
        }

        debouncedEditTestDisposable = RxTextView
            .textChangeEvents(binding.debouncedEditText)
            .debounce(3000L, TimeUnit.MILLISECONDS)
            .subscribe {
                Log.d(TAG, it.text().toString())
            }
    }

    private fun initVacanciesListRecyclerView() {
        binding.rvVacanciesList.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvVacanciesList.layoutManager = layoutManager
    }

    private fun initDiscountCardsRecyclerView() {
        binding.rvDiscountCardList.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvDiscountCardList.layoutManager = layoutManager
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

                    setupVacancyItemClick()
                }, { error ->
                    error.localizedMessage?.let { Log.e(TAG, it) }
                    Toast.makeText(
                        this,
                        "Error ${error.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        )
    }

    private fun getDiscountCards() {
        compositeDisposable?.add(
            getDiscountCardsFirstSource()
                .mergeWith(getDiscountCardsSecondSource())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ receivedDiscountCardsList ->
                    discountCardsList = discountCardsList + receivedDiscountCardsList
                    discountCardsListAdapter = DiscountCardsListAdapter(discountCardsList)

                    binding.rvDiscountCardList.adapter = discountCardsListAdapter
                }, {
                    Log.d(TAG, "Getting discount cards error: ${it.localizedMessage}")
                })
        )
    }

    // Для реализации задания 5 б) была добавлена функция .onErrorResumeNext, которая передает
    // пустой лист получателю, если произошла ошибка.
    // Для реализации задания 5 а) достаточно закомментировать использование .onErrorResumeNext
    private fun getDiscountCardsFirstSource(
    ): Observable<List<DiscountCardDto>> = Observable.create { emitter ->
        throw IllegalArgumentException()
        emitter.onNext(listOf(
            DiscountCardDto("Magnit"),
            DiscountCardDto("Sony"),
            DiscountCardDto("Ashan")
        ))
    }.onErrorResumeNext { Observable.just(emptyList()) }

    private fun getDiscountCardsSecondSource(
    ): Observable<List<DiscountCardDto>> = Observable.create { emitter ->
        //throw IllegalArgumentException()
        emitter.onNext(listOf(
            DiscountCardDto("Pyatorochka"),
            DiscountCardDto("DNS"),
            DiscountCardDto("MVideo")
        ))
    }.onErrorResumeNext { Observable.just(emptyList()) }

    private fun startTimerObservable() {
        timerDisposable?.clear()
        timerDisposable?.add(Observable.interval(1000L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { timeCount ->
                binding.timerText.text = timeCount.toString()
            }
        )
    }

    private fun setupVacancyItemClick() {
        vacanciesListAdapter?.apply {
            compositeDisposable?.add(vacancyClickEvent
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { vacancy ->
                    val vacancyBundle = Bundle()
                    vacancyBundle.putInt("vacancyPosition", vacancy)
                    supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.vacancyFragmentContainerView,
                            VacancyFragment.getNewInstance(vacancyBundle)
                        )
                        addToBackStack(null)
                        commit()
                    }
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        debouncedEditTestDisposable?.dispose()
    }
}
