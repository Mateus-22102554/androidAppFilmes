package pt.ulusofona.deisi.cm2223.g22102554_22103941.data
import android.content.Context
import android.util.Log
import pt.ulusofona.deisi.cm2223.g22102554_22103941.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmeIMDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmesIMDB
import kotlin.Result

class Repository (
    private val context: Context,
    private val local: FilmesIMDB,
    private val remote: FilmesIMDB
    ) : FilmesIMDB(){
    override fun getAllFilmes(onFinished: (Result<List<FilmeIMDB>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirFilme(filme: FilmeIMDB, avaliacao: Avaliacao, onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirAvaliacao(
        avaliacao: Avaliacao,
        idImdb: String,
        onFinished: (Result<Avaliacao>) -> Unit
    ) {
        local.inserirAvaliacao(avaliacao,idImdb) {
            onFinished(Result.success(avaliacao))
        }
    }

    override fun getFilme(id: String, avaliacao: Avaliacao, onFinished: (Result<FilmeIMDB>) -> Unit) {
        if (ConnectivityUtil.isOnline(context)) {
            // Se tenho acesso à Internet, vou buscar os registos ao web service
            // e atualizo a base de dados com os novos registos eliminando os
            // antigos, porque podem ter eliminado o filme do web service

            remote.getFilme(id, avaliacao) { result ->

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
            }

        } else {
            // O que fazer se não houver Internet?
            // Devolver os personagens que estão guardados na base de dados
            Log.i("APP", "App is offline. Getting characters from the database")
            local.getFilme(id,avaliacao, onFinished)
        }
    }

    override fun deleteFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit) {
        TODO("Not yet implemented")
    }



    companion object {

        private var instance: Repository? = null

        // Temos de executar o init antes do getInstance
        fun init(local: FilmesIMDB, remote: FilmesIMDB, context: Context) {
            if (instance == null) {
                instance = Repository(context, local, remote)
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