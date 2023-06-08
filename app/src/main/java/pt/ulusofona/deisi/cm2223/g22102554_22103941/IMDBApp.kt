package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.CinemaDatabase
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.OkHttp
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Room

class IMDBApp : Application(){
        override fun onCreate() {
            super.onCreate()
            Repository.init(initRoom(), initOkHttp(), this)
            Log.i("APP", "Initialized repository")
        }

    private fun initOkHttp(): OkHttp {
        return OkHttp(
            "https://www.omdbapi.com",
            "8284a743",
            OkHttpClient()
        )
    }

    // TODO substituir aqui a inicialização do Room
    private fun initRoom(): Room {
        return Room(
            CinemaDatabase.getInstance(applicationContext).filmeDao(),
            CinemaDatabase.getInstance(applicationContext).avaliacaoDao()
        )
    }
}
