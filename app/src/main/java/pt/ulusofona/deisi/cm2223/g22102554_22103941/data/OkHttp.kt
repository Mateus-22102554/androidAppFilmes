package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filmes

class OkHttp (
    private val baseUrl: String,
    private val apiKey: String,
    private val client: OkHttpClient
) : Filmes() {

    override fun inserirFilme(
        characters: List<Filme>, onFinished: () -> Unit) {
        Log.e("APP", "web service is not able to insert characters")
    }

    override fun deleteFilme(onFinished: () -> Unit) {
        Log.e("APP", "web service is not able to clear all characters")
    }

    override fun getFilme(
        onFinished: (Result<Filme>) -> Unit) {

        // Aqui estamos a preparar o pedido. Precisamos da apiKey e do url
        val request: Request = Request.Builder()
            .url("$baseUrl/character")
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        // Continua no próximo quadro

        // Nesta linha executamos o pedido ao servidor
        // em caso caso de erro, o método onFailure será invocado (ex: timeout)
        // se tudo correr bem, teremos a resposta ao pedido no método onResponse
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                onFinished(Result.failure(e))
            }

            // Processar a resposta ao pedido
            override fun onResponse(call: Call, response: Response) {
                // Se a resposta devolver um erro, ex: 403 acesso negado ao web service
                if (!response.isSuccessful) {
                    onFinished(Result.failure(IOException("Unexpected code $response")))
                } else {
                    val body = response.body?.string()
                    if (body != null) {
                        // Estamos a guardar o objeto assinalado a amarelo no exemplo aqui
                        val jsonObject = JSONObject(body)
                        // Aqui vamos guardar o array ainda em formato json dos personagens
                        val jsonCharactersList = jsonObject["docs"] as JSONArray
                        val lotrCharacters = mutableListOf<LOTRCharacter>()

                        for (i in 0 until jsonCharactersList.length()) {
                            val jsonCharacter = jsonCharactersList[i] as JSONObject
                            lotrCharacters.add(
                                LOTRCharacter(
                                    jsonCharacter.getString("_id"),
                                    jsonCharacter.getString("birth"),
                                    jsonCharacter.getString("death"),
                                    // optString devolve null se o personagem não tiver género
                                    jsonCharacter.optString("gender"),
                                    jsonCharacter.getString("name")
                                )
                            )
                        }

                        // Devolve a lista de personagens já com em objetos Kotlin
                        onFinished(Result.success(lotrCharacters))

                    }
                }
            }
        })
    }
}