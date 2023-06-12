package pt.ulusofona.deisi.cm2223.g22102554_22103941.data
import android.content.Context
import android.util.Base64InputStream
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.g22102554_22103941.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.CinemaDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Cinema
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.CinemaDao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.Result

class Repository (
    private val context: Context,
    private val local: Operacoes,
    private val remote: Operacoes,



    //JSON CINEMAS
    val inputStream: InputStream = context.assets.open("Cinemas.json"),
    val inputStreamReader: InputStreamReader = InputStreamReader(inputStream),
    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

    ) : Operacoes(){
    override fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirFilme(filme: Filme, avaliacao: Avaliacao, onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getFilmeIMDB(nome: String, onFinished: (Result<Filme>) -> Unit) {

         remote.getFilmeIMDB(nome){
             onFinished(it)
         }

    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        local.getAllAvaliacoes(){
            onFinished(it)
        }
    }

    override fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        local.getAvaliacao(id){
            onFinished(it)
        }
    }

    override fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit) {
        local.getFilme(id) {
            onFinished(it)
        }
    }

    override fun verificarFilme(nome: String, onFinished: (Int) -> Unit) {
        local.verificarFilme(nome){
            onFinished(it)
        }
    }

    override fun verificarCinema(nome: String, onFinished: (Int) -> Unit) {
        local.verificarCinema(nome){
            onFinished(it)
        }
    }

    override fun getCinemasJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        //var cinemaDao : CinemaDao? = null

        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null){
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        val jsonString = stringBuilder.toString()

        val jsonObject = JSONObject(jsonString)
        val jsonCinemaList = jsonObject["cinemas"] as JSONArray
        val cinemas = mutableListOf<Cinema>()
        for (i in 0 until jsonCinemaList.length()) {
            val cinema = jsonCinemaList[i] as JSONObject

            cinemas.add(
                Cinema(
                    cinema["cinema_id"].toString().toInt(),
                    cinema["cinema_name"].toString(),
                    cinema["latitude"].toString().toDouble(),
                    cinema["longitude"].toString().toDouble(),
                    cinema["address"].toString(),
                    cinema["county"].toString()

                    )
            )
        }

        local.clearAllCinemas {
            local.inserirCinemas(cinemas) {
                onFinished(Result.success(cinemas))
            }
        }

    }

    override fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        local.getCinemaById(idCinema) {
            onFinished(it)
        }
    }



    override fun getCinemaByNome(cinema: String, onFinished: (Result<Cinema>) -> Unit) {
        local.getCinemaByNome(cinema) {
            onFinished(it)
        }
    }

    override fun getAllCinemasNomes(onFinished: (Result<List<String>>) -> Unit) {
        local.getAllCinemasNomes {
            onFinished(it)
        }
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun countAvaliacoes(onFinished: (Result<Int>) -> Unit) {
        local.countAvaliacoes {
            onFinished(it)
        }
    }

    override fun top5Avaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        local.top5Avaliacoes {
            onFinished(it)
        }
    }

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {

        local.inserirFilme(filme, avaliacao) {
            onFinished(Result.success(filme))
        }


    }

    override fun getAvaliacaoIdFromFilmeName(nome: String, onFinished: (Result<String>) -> Unit) {
        local.getAvaliacaoIdFromFilmeName(nome) {
            onFinished(it)
        }
    }

    override fun inserirFotosAvaliacao(fotos: List<File>, idAvaliacao: String, onFinished: () -> Unit) {
        local.inserirFotosAvaliacao(fotos, idAvaliacao) {
            onFinished()
        }
    }

    override fun getAllFotosFromAvaliacao(id: String, onFinished: (Result<List<File>>) -> Unit) {
        local.getAllFotosFromAvaliacao(id) {
            onFinished(it)
        }
    }

    override fun getAvaliacaoCheckCinema(idCinema: Int, onFinished: (Result<Int>) -> Unit) {
        local.getAvaliacaoCheckCinema(idCinema){
            onFinished(it)
        }
    }


    companion object {

        private var instance: Repository? = null

        // Temos de executar o init antes do getInstance
        fun init(local: Operacoes, remote: Operacoes, context: Context) {
            if (instance == null) {
                instance = Repository(context, local, remote )
            }
        }


        fun getInstance(): Repository {
            if (instance == null) {
                // Primeiro temos de invocar o init, se não lança esta Exception
                throw IllegalStateException("singleton not initialized")
            }
            return instance as Repository
        }

    }

}