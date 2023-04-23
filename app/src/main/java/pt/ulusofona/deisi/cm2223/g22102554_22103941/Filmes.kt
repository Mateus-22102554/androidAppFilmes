package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.app.Activity
import android.util.Log
import android.widget.Toast
import java.io.File
import java.util.*

object Filmes {

    private val _listImg = mutableListOf<File>()

    val listImgGet get() = _listImg.toList()

    fun imagemSet (imgFile: File){
        _listImg.add(imgFile)
    }

    fun imagensListClear (){
        _listImg.clear()
    }

    private val _history = mutableListOf<Filme>(
        Filme("John Wick 4","Cinema São Jorge",8 , Calendar.getInstance(), listOf(), ""), Filme("Avatar 2", "Cinema do Parque", 7, Calendar.getInstance(),listOf(),"")
    )

    val history get() = _history.toList()

    fun historySet (nome:String, cinema:String, avaliacao:Int, data: Calendar, imgList: List<File>, obs:String):Int{
        var sizeFilme = 0
        var sizeCinema = 0

        for(filmeImdb in FilmesIMDB.getListFilmesImdb){
            if(filmeImdb.nomeImdb == nome) {
                for (filme in _history){
                    sizeFilme +=1
                    if (filme.nome == nome){
                        return 2
                    }else{
                        if(sizeFilme == _history.size){
                            for (cinemaList in Cinemas.getListCinemas){
                                sizeCinema +=1
                                if(cinemaList.cinemaName == cinema){
                                    _history.add(Filme(nome,cinema,avaliacao, data, imgList, obs))
                                    return 0
                                }else if(sizeCinema == Cinemas.getListCinemas.size){
                                    return 3
                                }
                            }

                        }

                    }
                }

            }
        }

        /*Log.i("", Filmes._history[2].nome)
        Log.i("", Filmes._history[2].cinema)
        Log.i("", Filmes._history[2].avaliacao.toString())
        Log.i("", Filmes._history[2].dataVisualizacao.toString())
        Log.i("", Filmes._history[2].observacoes)*/

        return 1
    }

    /*fun historyUpgrade (nome:String, cinema:String, avaliacao:Int, data: Calendar, obs:String){


        for (filmeUpgrade in history){
            if(filmeUpgrade.nome == nome){

                filmeUpgrade.countAvaliacoes = filmeUpgrade.countAvaliacoes.plus(avaliacao).toMutableList()

                val media = filmeUpgrade.countAvaliacoes.map { it }.sum() / filmeUpgrade.countAvaliacoes.size


                filmeUpgrade.nome = nome
                filmeUpgrade.cinema = cinema
                filmeUpgrade.avaliacao = media
                filmeUpgrade.dataVisualizacao = data
                filmeUpgrade.observacoes = obs

            }
        }

        Log.i("", _history[1].nome)
        Log.i("", _history[1].cinema)
        Log.i("", _history[1].avaliacao.toString())
        Log.i("", _history[1].countAvaliacoes.toString())
        Log.i("", _history[1].dataVisualizacao.toString())
        Log.i("", _history[1].observacoes)
    }*/

    val top5Filmes get() = _history.sortedByDescending { it.avaliacao }.take(5)

    val countFilmes get() = _history.size

}