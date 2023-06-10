package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.CinemaDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Cinema
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes
import java.util.Calendar

class Room (
    private val filmeDao: FilmeDao,
    private val avaliacaoDao: AvaliacaoDao,
    private val cinemaDao: CinemaDao
) : Operacoes() {
    override fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit) {

    }
    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit){
        avaliacaoDao.getAllAvaliacoes()


        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = avaliacaoDao.getAllAvaliacoes().map {
                val cinema = cinemaDao.getCinema(it.idCinema).let { Cinema(it.id, it.nome) }
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
                    dataVisualizacao = Calendar.getInstance(),
                    fotos = null ,
                    observacoes = it.observacoes
                )
            }

            onFinished(Result.success(avaliacaoDB))
        }

    }

    override fun inserirAvaliacao(id: String, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
        TODO("Not yet implemented")
    }

/*    override fun getAllAvaliacoesNomes(onFinished: (Result<List<String>>) -> Unit) {
        val nomeAvaliacao = mutableListOf<String>()
        CoroutineScope(Dispatchers.IO).launch {

            val avaliacaoDB = avaliacaoDao.getAllAvaliacoes().map {
                Avaliacao(
                    id = it.id,
                    nome = it.nome,
                    cinema = "cinema",
                    avaliacao = it.avaliacao,
                    dataVisualizacao = Calendar.getInstance(),
                    fotos = null,
                    observacoes = it.observacoes
                )
            }.forEach{
                nomeAvaliacao.add(it.nome)
            }

            onFinished(Result.success(nomeAvaliacao))

        }
    }*/

    override fun getFilme(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        filmeDao.getFilme(id)
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
                dataVisualizacao = avaliacao.dataVisualizacao.toString(),
                observacoes = avaliacao.observacoes,
                idImdb = filmeDB.id,
                idFotos = null,
                idCinema = avaliacao.cinema.id
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

         CoroutineScope(Dispatchers.IO).launch {
             val filme = filmeDao.getFilme(nome)
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