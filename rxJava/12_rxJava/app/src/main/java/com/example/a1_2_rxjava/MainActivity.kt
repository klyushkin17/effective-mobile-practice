package com.example.a1_2_rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

const val TAG = "HAHAHA"


class MainActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // ЗАДАНИЕ 1

        // ОТВЕТ:
        /*
            Объяснение:
            Работа observable цепочки делится на четыре основных этапа: создание, подписка и эмит данных:
            - на этапе создания объявляется observable и все операторы, в который кешируются переданные
              значения, а так же которым присваивается ссылка на оператор выше
            - этап подписки запускается после того, как только создается оператор subscribе. Данные этап
              проходит снизу вверх и конкретно в этом примере отрабатывает subscribeOn(), который переводит
              рабту оператора doOnSubscribe в computation поток. Оператор subscribeOn(io) никак не влияет на
              работу цепочки
            - этап эмита проходит сверху вниз и запускает работу большинства операторов. Оператор timer()
              по умолчанию отрабатывает на computation пуле, но тут мы его явно меняем на newThread.
              Это влияет на map(), который так же отрабатывает на newThread. doOnSubscribe пропускается, так
              как уже отработал и просто переадет значение. observeOn() меняет поток на single, именно поэтому
              внутри flatMap() лог выводит этот тип потока. Дальше создается новый observable, который
              проходит те же самые этапы и на выходе дает значение, которое отрабатывается в io() потоке из-за
              subscribeOn(io)
        */

        val observable = Observable.timer(10, TimeUnit.MILLISECONDS, Schedulers.newThread())
            .subscribeOn(Schedulers.io())
            .map {
                Log.e(TAG, "mapThread = ${Thread.currentThread().name}")
            }
            .doOnSubscribe {
                Log.e(TAG, "onSubscribe = ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.single())
            .flatMap {
                Log.e(TAG, "flatMapThread = ${Thread.currentThread().name}")
                Observable.just(it)
                    .subscribeOn(Schedulers.io())
            }
            .map {
                Log.e(TAG, "secondMapThread = ${Thread.currentThread().name}")
            }
            .subscribe {
                Log.e(TAG, "subscribeThread = ${Thread.currentThread().name}")
            }


        // ЗАДАНИЕ 2
        // ОТВЕТ:

        // Данный код не выведет ничего, так как Subject -- горячий поток, а подписываемся мы уже
        // после эмитов
        val subject = PublishSubject.create<String>()
        subject.onNext("1")
        subject.onNext("2")
        subject.onNext("3")
        subject.subscribe { Log.d(TAG, it) }

        // Чтобы вывести все:
        // Можно переместить логику подписки ДО эмитов
        val subject1 = PublishSubject.create<String>()
        subject1.subscribe { Log.d(TAG, it) }
        subject1.onNext("1")
        subject1.onNext("2")
        subject1.onNext("3")

        // Можно заменить PublishSubject на Replay, который будет кешировать все заэмиченные
        // значения и передавать их новым подписчикам
        val subject2 = ReplaySubject.create<String>()
        subject2.onNext("1")
        subject2.onNext("2")
        subject2.onNext("3")
        subject2.subscribe { Log.d(TAG, it) }
    }
}