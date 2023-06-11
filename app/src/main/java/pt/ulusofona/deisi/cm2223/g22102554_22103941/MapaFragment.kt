package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentMapaBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes


class MapaFragment : Fragment() {
    private lateinit var binding: FragmentMapaBinding
    private var map: GoogleMap? = null
    private val model = Repository.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.mapa)

        val view = inflater.inflate(R.layout.fragment_mapa, container, false)
        binding = FragmentMapaBinding.bind(view)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { map ->

            this.map = map
            // Coloca um ponto azul no mapa com a localização do utilizador
            map.isMyLocationEnabled = true
            // Adiciona um marker na universidade com o nome ULHT ao clicar

            CoroutineScope(Dispatchers.IO).launch {
                model.getAllAvaliacoes {
                    it.onSuccess { listAvaliacoes->
                        listAvaliacoes.forEach { avaliacao->

                            var grau : String = ""

                            when (avaliacao.avaliacao){
                                in 1..2 -> grau = "Muito Fraco"
                                in 3..4 -> grau = "Fraco"
                                in 5..6 -> grau = "Médio"
                                in 7..8 -> grau = "Bom"
                                in 9..10 -> grau = "Excelente"
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                map.addMarker(
                                    MarkerOptions()
                                        .position(LatLng(avaliacao.cinema.latitude, avaliacao.cinema.longitude))
                                        .title(avaliacao.filme.nomeImdb)
                                        .snippet(grau)
                                )
                            }

                       }
                    }
                }
            }

        }

        map.setOnMarkerClickListener { marker->


        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    private var adapter = ApresentacaoFilmesAdapter(::onOperationClick)
    private fun onOperationClick(id: String){
        val activity= view?.context as AppCompatActivity
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, id)
    }

}