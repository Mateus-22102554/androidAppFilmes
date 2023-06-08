package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacoes
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmeIMDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmesIMDB

class Room (
    private val filmeDao: FilmeDao,
    private val avaliacaoDao: AvaliacaoDao
) : FilmesIMDB() {
    override fun getAllFilmes(onFinished: (Result<List<FilmeIMDB>>) -> Unit) {
        filmeDao.getAllFilmes()
    }

    override fun inserirAvaliacao(avaliacao: Avaliacao, idImdb: String, onFinished: (Result<Avaliacao>) -> Unit) {
        val avaliacaoDB = avaliacao.let {
            AvaliacaoDB(
                id = avaliacao.id,
                nome = avaliacao.nome,
                avaliacao = avaliacao.avaliacao,
                dataVisualizacao = avaliacao.dataVisualizacao.toString(),
                observacoes = avaliacao.observacoes,
                idImdb = idImdb,
                idFotos = null,
                idCinema = null
            )
        }
        avaliacaoDao.inserirAvaliacao(avaliacaoDB)
    }

    override fun inserirFilme(filme: FilmeIMDB, onFinished: () -> Unit) {
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
     override fun getFilme(id : String, avaliacao: Avaliacao, onFinished: (Result<FilmeIMDB>) -> Unit) {

         CoroutineScope(Dispatchers.IO).launch {
             val filme = filmeDao.getFilme(id)
             val filmeIMDB = FilmeIMDB(
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

    override fun deleteFilme(id : String, onFinished: (Result<FilmeIMDB>) -> Unit) {
        filmeDao.deleteFilme(id)
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