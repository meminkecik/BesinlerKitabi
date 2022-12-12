package com.mek.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mek.besinlerkitabi.model.Besin
import com.mek.besinlerkitabi.servis.BesinAPIServis
import com.mek.besinlerkitabi.servis.BesinDataBase
import com.mek.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private val besinAPIServis = BesinAPIServis()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
        verileriSQLitedenAL()
        }else{
            verileriInternettenAl()
        }


    }
    fun refreshFromInternet(){
        verileriInternettenAl()
    }
    fun verileriSQLitedenAL(){
        besinYukleniyor.value = true
        launch {
            val besinListesi = BesinDataBase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri SQLiteden aldık",Toast.LENGTH_LONG).show()
        }
    }
    private fun verileriInternettenAl(){
        besinYukleniyor.value = true
        disposable.add(
            besinAPIServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                    sqLiteKaydet(t)
                    }

                    override fun onError(e: Throwable) {
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }

                })
        )
        Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()
    }
    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinYukleniyor.value = false
        besinHataMesaji.value = false
    }
    private fun sqLiteKaydet(besinlerListesi: List<Besin>){
    launch {
        val dao = BesinDataBase(getApplication()).besinDao()
        dao.deleteAll()
        val uuidList = dao.insertAll(*besinlerListesi.toTypedArray())
        var i = 0
        while (i<besinlerListesi.size){
            besinlerListesi[i].uuid = uuidList[i].toInt()
            i = i+1
        }
        besinleriGoster(besinlerListesi)
    }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}