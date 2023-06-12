package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.CinemaDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FotoDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Cinema
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI
import java.util.Calendar
import java.util.UUID

class Room (

    private val filmeDao: FilmeDao,
    private val avaliacaoDao: AvaliacaoDao,
    private val cinemaDao: CinemaDao,
    private val fotoDao: FotoDao,



) : Operacoes() {
    override fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit) {

    }
    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit){

        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = avaliacaoDao.getAllAvaliacoes().map {
                val cinema = cinemaDao.getCinemaById(it.idCinema).let { Cinema(it.id, it.nome,
                    it.latitude,
                    it.longitude,
                    it.morada,
                    it.localidade
                ) }
                val filme = filmeDao.getFilme(it.idImdb).let {
                    Filme(
                        it.id,
                        it.nome,
                        it.genero,
                        it.data,
                        it.avaliacao,
                        it.poster,
                        it.sinopse,
                    )
                }

                Avaliacao(
                    id = it.id,
                    filme = filme,
                    cinema = cinema,
                    avaliacao = it.avaliacao,
                    dataVisualizacao = it.dataVisualizacao,
                    fotos = null ,
                    observacoes = it.observacoes
                )
            }

            onFinished(Result.success(avaliacaoDB))
        }

    }

    override fun getAvaliacao(id : String, onFinished: (Result<Avaliacao>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

           val avaliacao = avaliacaoDao.getAvaliacao(id).let { avaliacaoDB ->
                filmeDao.getFilme(avaliacaoDB.idImdb).let {filmeDB ->
                    val filme = Filme(
                        filmeDB.id,
                        filmeDB.nome,
                        filmeDB.genero,
                        filmeDB.data,
                        filmeDB.avaliacao,
                        filmeDB.poster,
                        filmeDB.sinopse
                    )
                    cinemaDao.getCinemaById(avaliacaoDB.idCinema).let{cinemaDB ->
                        val cinema = Cinema(
                            cinemaDB.id,
                            cinemaDB.nome,
                            cinemaDB.latitude,
                            cinemaDB.longitude,
                            cinemaDB.morada,
                            cinemaDB.localidade
                        )

                        var fotos = listOf<File>()
                        getAllFotosFromAvaliacao(avaliacaoDB.id) {result ->
                            result.onSuccess {
                                fotos = it
                            }
                            result.onFailure {
                                fotos = emptyList()
                            }
                        }

                        Avaliacao(
                            id = avaliacaoDB.id,
                            filme = filme,
                            cinema = cinema,
                            avaliacao = avaliacaoDB.avaliacao,
                            dataVisualizacao = avaliacaoDB.dataVisualizacao,
                            fotos = fotos,
                            observacoes = avaliacaoDB.observacoes

                        )

                    }
                }

            }
            onFinished(Result.success(avaliacao))
        }

    }

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAvaliacaoIdFromFilmeName(nome: String, onFinished: (Result<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch() {
            filmeDao.getFilmeId(nome).let { idFilme ->
                avaliacaoDao.getIdByImbdId(idFilme).let { idAvaliacao ->
                    onFinished(Result.success(idAvaliacao))
                }
            }
        }
    }

    override fun inserirFotosAvaliacao(fotos: List<File>, idAvaliacao: String, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch() {
            fotos.forEach { foto ->
                val caminho: String = foto.toURI().toString()
                val fotoDb = FotoDB(
                    UUID.randomUUID().toString(),
                    caminho,
                    idAvaliacao
                )
                fotoDao.inserirFoto(fotoDb)
            }
        }
    }

    override fun getAllFotosFromAvaliacao(id: String, onFinished: (Result<List<File>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fotos = fotoDao.getAllFotosFromAvaliacao(id).map {
                File(URI(it.caminho))
            }

            onFinished(Result.success(fotos))
        }
    }

    override fun getAvaliacaoCheckCinema(idCinema: Int, onFinished: (Result<Int>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            onFinished(Result.success(avaliacaoDao.getAvaliacaoCheckCinema(idCinema)))
        }
    }

    override fun getCinemasJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            cinemas.map {
                CinemaDB(
                    it.cinema_id,
                    it.cinema_name,
                    it.latitude,
                    it.longitude,
                    it.morada,
                    it.localidade
                )
            }.forEach {
                cinemaDao.inserirCinema(it)
            }
            onFinished()
        }
    }

    override fun getCinemaByNome(cinema: String, onFinished: (Result<Cinema>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemaDB = cinemaDao.getCinemaByNome(cinema)
            val cinema = Cinema(
                cinemaDB.id,
                cinemaDB.nome,
                cinemaDB.latitude,
                cinemaDB.longitude,
                cinemaDB.morada,
                cinemaDB.localidade
            )
            onFinished(Result.success(cinema))
        }
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun verificarCinema(nome: String, onFinished: (Int) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
           val verificarCinema = cinemaDao.verificarCinema(nome)
            onFinished(verificarCinema)
        }
    }


    override fun getAllCinemasNomes(onFinished: (Result<List<String>>) -> Unit) {
        val nomeCinema = mutableListOf<String>()
        CoroutineScope(Dispatchers.IO).launch {

            val cinemaDB = cinemaDao.getAllCinema().map {
                Cinema(
                    it.id,
                    it.nome,
                    it.latitude,
                    it.longitude,
                    it.morada,
                    it.localidade
                )
            }.forEach{
                nomeCinema.add(it.cinema_name)
            }

            onFinished(Result.success(nomeCinema))

        }
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            cinemaDao.deleteAll()
            onFinished()
        }
    }

    override fun countAvaliacoes(onFinished: (Result<Int>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            onFinished(Result.success(avaliacaoDao.countAvaliacoes()))
        }
    }

    override fun top5Avaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            onFinished(Result.success( avaliacaoDao.top5().map{avaliacaoDB->
                filmeDao.getFilme(avaliacaoDB.idImdb).let{filmeDB ->
                    val filme =  Filme(
                       filmeDB.id,
                       filmeDB.nome,
                       filmeDB.genero,
                       filmeDB.data,
                       filmeDB.avaliacao,
                       filmeDB.poster,
                        filmeDB.sinopse
                    )
                    cinemaDao.getCinemaById(avaliacaoDB.idCinema).let { cinemaDB->
                        val cinema = Cinema(
                            cinemaDB.id,
                            cinemaDB.nome,
                            cinemaDB.latitude,
                            cinemaDB.longitude,
                            cinemaDB.morada,
                            cinemaDB.localidade
                        )
                        Avaliacao(
                            avaliacaoDB.id,
                            filme,
                            cinema,
                            avaliacaoDB.avaliacao,
                            avaliacaoDB.dataVisualizacao,
                            null,
                            avaliacaoDB.observacoes

                        )
                    }
                }


            }))


        }
    }

    override fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val filme = filmeDao.getFilme(id)
            val filmeIMDB = Filme(
                filme.id,
                filme.nome,
                filme.genero,
                filme.data,
                filme.avaliacao,
                filme.poster,
                filme.sinopse
            )
            onFinished(Result.success(filmeIMDB))
        }
    }

    override fun verificarFilme(nome: String, onFinished: (Int) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val verificarFilme = filmeDao.verificarFilme(nome)
            onFinished(verificarFilme)
        }

    }

    override fun inserirFilme(filme: Filme, avaliacao: Avaliacao, onFinished: () -> Unit) {
        val filmeDB = filme.sinopse?.let {
            FilmeDB(
                id = filme.id,
                nome = filme.nomeImdb,
                genero = filme.generoImdb,
                data = filme.dataImdb,
                avaliacao = filme.avaliacaoImdb,
                poster = filme.imgImdb,
                sinopse = filme.sinopse
            )
        }
        if (filmeDB != null) {
            filmeDao.inserirFilme(filmeDB)
            val avaliacaoDb = AvaliacaoDB(
                id = avaliacao.id,
                avaliacao = avaliacao.avaliacao,
                dataVisualizacao = avaliacao.dataVisualizacao,
                observacoes = avaliacao.observacoes,
                idImdb = filmeDB.id,
                idFotos = null,
                idCinema = avaliacao.cinema.cinema_id.toInt()
            )
            avaliacaoDao.inserirAvaliacao(avaliacaoDb)
        }

        /*CoroutineScope(Dispatchers.IO).launch {
            filme.map {
                it.sinopse?.let { it1 ->
                    FilmeDB(
                        id = it.id,
                        nome = it.nomeImdb,
                        genero = it.generoImdb,
                        data = it.dataImdb,
                        avaliacao = it.avaliacaoImdb,
                        poster = it.imgImdb,
                        sinopse = it1,

                        )
                }
            }.forEach {
                if (it != null) {
                    dao.inserirFilme(it)
                }
                if (it != null) {
                    Log.i("APP", "Inserted ${it.id} in DB")
                }
            }
            onFinished()
        }*/
    }



    override fun getFilmeIMDB(nome : String, onFinished: (Result<Filme>) -> Unit) {
        throw Exception("Illegal operation")
    }



   /* override fun getFilme(id : String, onFinished: (Result<FilmeIMDB>) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val filme = dao.getFilme(id)


                val filmes = dao.getAllFilmes().map {
                    FilmeIMDB(
                        id = it.id,
                        nomeImdb = it.nome,
                        generoImdb = it.genero,
                        dataImdb = it.data,
                        avaliacaoImdb = it.avaliacao,
                        imgImdb = it.poster,
                        sinopse = it.sinopse,
                    )
                }

                onFinished(Result.success(filme))
            }
    }

    override fun deleteFilme(id : String, onFinished: (Result<FilmeIMDB>) {
            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteAll()
                onFinished()
            }
    }*/

}