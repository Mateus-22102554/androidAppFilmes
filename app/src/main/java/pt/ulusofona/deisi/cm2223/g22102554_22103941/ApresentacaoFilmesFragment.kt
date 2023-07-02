package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.annotation.SuppressLint
import android.content.IntentSender.OnFinished
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentApresentacaoFilmesBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import java.lang.Math.*
import kotlin.math.pow

class ApresentacaoFilmesFragment : Fragment() {
    private lateinit var binding: FragmentApresentacaoFilmesBinding
    private val model = Repository.getInstance()
    private lateinit var adapterFilmes: ArrayAdapter<String>
    private var ordenacao = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.list_filmes)
        val view = inflater.inflate(
            R.layout.fragment_apresentacao_filmes, container, false
        )
        binding = FragmentApresentacaoFilmesBinding.bind(view)
        return binding.root

    }

    private var adapter = ApresentacaoFilmesAdapter(::onOperationClick)


    private fun onOperationClick(id: String) {
        val activity = view?.context as AppCompatActivity
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, id)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()




        CoroutineScope(Dispatchers.IO).launch {
            var history: List<Avaliacao> = listOf()
            model.getAllAvaliacoes {
                history = it.getOrNull()!!
                CoroutineScope(Dispatchers.Main).launch {

                    val searchEditText = binding.nomeFilme

                    searchEditText?.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            // Filtrar a lista de objetos history com base no texto de pesquisa
                            val filteredList = history.filter {
                                it.filme.nomeImdb.contains(
                                    s.toString(),
                                    ignoreCase = true
                                )
                            }

                            // Atualizar o adaptador da RecyclerView com os itens filtrados
                            adapter.updateItems(filteredList)
                        }

                        override fun afterTextChanged(s: Editable?) {

                        }
                    })


                }

                //PROXIMIDADE
                CoroutineScope(Dispatchers.Main).launch {
                    binding.buttonProximidade500?.setOnClickListener {

                        binding.buttonProximidade500!!.isEnabled = false
                        binding.buttonProximidade1000!!.isEnabled = true

                        //1 quilometro = 1000 metros
                        distanciaFilmes(500, history) {
                            it.onSuccess {
                                history = it
                                adapter.updateItems(history)
                            }
                        }


                    }

                    binding.buttonProximidade1000?.setOnClickListener {

                        binding.buttonProximidade1000!!.isEnabled = false
                        binding.buttonProximidade500!!.isEnabled = true

                        //1 quilometro = 1000 metros
                        distanciaFilmes(1000, history) {
                            it.onSuccess {
                                history = it
                                adapter.updateItems(history)
                            }
                        }


                    }

                }

                //ORDENAR
                CoroutineScope(Dispatchers.Main).launch {
                    binding.buttonCrescente?.setOnClickListener {
                        history = history.sortedBy { it.avaliacao }

                        binding.buttonCrescente!!.isEnabled = false
                        binding.buttonDecrescente!!.isEnabled = true
                        ordenacao = "crescente"

                        adapter.updateItems(history)
                    }
                    binding.buttonDecrescente?.setOnClickListener {
                        history = history.sortedByDescending { it.avaliacao }

                        binding.buttonCrescente!!.isEnabled = true
                        binding.buttonDecrescente!!.isEnabled = false
                        ordenacao = "decrescente"

                        adapter.updateItems(history)
                    }
                    binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvHistory.adapter = adapter
                    adapter.updateItems(history)

                }

            }
        }


    }

    fun calcularDistancia(pontoA: LatLng, pontoB: LatLng): Int {
        val raioTerra = 6371 * 1000 // Raio médio da Terra em metros

        val latA = Math.toRadians(pontoA.latitude)
        val lonA = Math.toRadians(pontoA.longitude)
        val latB = Math.toRadians(pontoB.latitude)
        val lonB = Math.toRadians(pontoB.longitude)

        val dlon = lonB - lonA
        val dlat = latB - latA

        val a = sin(dlat / 2).pow(2) + cos(latA) * cos(latB) * sin(dlon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distancia = raioTerra * c

        return distancia.toInt()
    }

    fun distanciaFilmes(distancia: Int, listOrdenada: List<Avaliacao>, onFinished: (Result<List<Avaliacao>>) -> Unit) {

        var listFilmesFiltrados: MutableList<Avaliacao> = mutableListOf<Avaliacao>()

        CoroutineScope(Dispatchers.IO).launch {
            model.getAllAvaliacoes {
                it.onSuccess {
                    CoroutineScope(Dispatchers.Main).launch {

                            it.forEach { avaliacao ->
                                val minhaPosicao = LatLng(FusedLocation.getLatitude(), FusedLocation.getLongitude())
                                val outraPosicao =
                                    LatLng(avaliacao.cinema.latitude, avaliacao.cinema.longitude)

                               // Log.i("minhaPosicao: ", minhaPosicao.toString())
                                //Log.i("outraPosicao: ", outraPosicao.toString())

                                val distanciaCalculada =
                                    calcularDistancia(minhaPosicao, outraPosicao)

                                //Log.i("distanciaCalculada: ", distanciaCalculada.toString())
                                //Log.i("distancia: ", distancia.toString())
                                if (distanciaCalculada <= distancia) {
                                    listFilmesFiltrados.add(avaliacao)
                                }
                            }

                        if(ordenacao == "crescente"){
                            listFilmesFiltrados = listFilmesFiltrados.sortedBy { it.avaliacao }.toMutableList()

                        }else if(ordenacao == "decrescente"){
                            listFilmesFiltrados = listFilmesFiltrados.sortedByDescending { it.avaliacao }.toMutableList()

                        }

                        //Log.i("tamanho: ", listFilmesFiltrados.size.toString())
                        onFinished(Result.success(listFilmesFiltrados))
                    }
                }
            }


        }

    }


}