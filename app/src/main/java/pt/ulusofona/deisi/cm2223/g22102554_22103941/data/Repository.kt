package pt.ulusofona.deisi.cm2223.g22102554_22103941.data
import android.content.Context
import android.util.Log
import pt.ulusofona.deisi.cm2223.g22102554_22103941.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filmes

abstract class Repository (
    private val context: Context,
    private val local: Filmes,
    private val remote: Filmes
    ) : Filmes(){
    override fun getFilme(id: String,

        onFinished: (Result<Filme>) -> Unit) {
        if (ConnectivityUtil.isOnline(context)) {
            // Se tenho acesso à Internet, vou buscar os registos ao web service
            // e atualizo a base de dados com os novos registos eliminando os
            // antigos, porque podem ter eliminado personagens do web service

            remote.getFilme(id) { result ->
                if (result.isSuccess) {
                    result.getOrNull()?.let { filme ->
                        // Se tiver personagens para apresentar entra aqui
                        Log.i("APP", "Got ${filme} characters from the server")
                        // Retirar esta linha quando forem fazer o exercício 1 da ficha
                        //onFinished(Result.success(characters))
                        local.deleteFilme(id) {
                            Log.i("APP", "Cleared DB")
                            local.inserirFilme(filme) {
                                onFinished(Result.success(filme))
                            }
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
            local.getFilme(id, onFinished)
        }
    }

}