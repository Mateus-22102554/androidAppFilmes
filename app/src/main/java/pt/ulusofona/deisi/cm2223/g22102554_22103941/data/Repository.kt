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

    override fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit) {
        local.getFilme(id) {
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


    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {

        local.inserirFilme(filme, avaliacao) {
            onFinished(Result.success(filme))
        }

        /*if (ConnectivityUtil.isOnline(context)) {
            // Se tenho acesso à Internet, vou buscar os registos ao web service
            // e atualizo a base de dados com os novos registos eliminando os
            // antigos, porque podem ter eliminado o filme do web service

            local.inserirFilme(filme, avaliacao) {
                onFinished(Result.success(filme))
            }

            *//*remote.getFilmeIMDB(id) { result ->

                if (result.isSuccess) {
                    result.getOrNull()?.let { filme ->
                        // Se tiver personagens para apresentar entra aqui
                        Log.i("APP", "Got ${filme} characters from the server")
                        // Retirar esta linha quando forem fazer o exercício 1 da ficha
                        //onFinished(Result.success(characters))

                        Log.i("APP", "Cleared DB")
                        local.inserirFilme(filme, avaliacao) {
                            onFinished(Result.success(filme))
                        }

                    }
                } else {
                    Log.w("APP", "Error getting characters from server")
                    onFinished(result)  // propagate the remote failure
                }
            }*//*

        } else {
            // O que fazer se não houver Internet?
            // Devolver os personagens que estão guardados na base de dados
            Log.i("APP", "App is offline. Getting characters from the database")
            local.getFilme(filme.id, onFinished)
        }*/
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