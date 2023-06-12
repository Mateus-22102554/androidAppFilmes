package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.OnLocationChangedListener
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentMapaBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes


class MapaFragment : Fragment(), OnLocationChangedListener {
    private lateinit var binding: FragmentMapaBinding
    private var map: GoogleMap? = null
    private val model = Repository.getInstance()
    private var lastMarker: Marker? = null

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

            CoroutineScope(Dispatchers.IO).launch {
                model.getAllAvaliacoes {
                    it.onSuccess { listAvaliacoes ->
                        listAvaliacoes.forEach { avaliacao ->
                            var grau: String = ""

                            when (avaliacao.avaliacao) {
                                in 1..2 -> grau = getString(R.string.avaliacaoFilmeMuitoFraco)
                                in 3..4 -> grau = getString(R.string.avaliacaoFilmeFraco)
                                    in 5..6 -> grau = getString(R.string.avaliacaoFilmeMedio)
                                in 7..8 -> grau = getString(R.string.avaliacaoFilmeBom)
                                in 9..10 -> grau = getString(R.string.avaliacaoFilmeExcelente)
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                map.addMarker(
                                    MarkerOptions()
                                        .position(
                                            LatLng(
                                                avaliacao.cinema.latitude,
                                                avaliacao.cinema.longitude
                                            )
                                        )
                                        .title(avaliacao.filme.nomeImdb)
                                        .snippet(grau)
                                )
                            }
                        }

                    }
                }
            }

            map.setOnMarkerClickListener { marker ->
                    marker.showInfoWindow()
                true
            }

            map.setOnInfoWindowClickListener  { marker ->
                model.getAvaliacaoIdFromFilmeName(marker.title.toString()) { result ->
                    result.onSuccess { idAvaliacao ->
                        val activity = view?.context as AppCompatActivity
                        NavigationManager.goToDetalheFilmeFragment(
                            activity.supportFragmentManager,
                            idAvaliacao
                        )
                    }
                }
                true
            }

            /*map.setOnMarkerClickListener { marker ->
                if (marker == lastMarker) {

                    model.getAvaliacaoIdFromFilmeName(marker.title.toString()) { result ->
                        result.onSuccess { idAvaliacao ->
                            val activity = view?.context as AppCompatActivity
                            NavigationManager.goToDetalheFilmeFragment(
                                activity.supportFragmentManager,
                                idAvaliacao
                            )
                        }
                    }


                } else {
                    marker.showInfoWindow()
                    lastMarker = marker
                }
                true
            }*/

            FusedLocation.registerListener(this)

        }

        return binding.root
    }

    // Este método será invocado sempre que a posição alterar
    override fun onLocationChanged(latitude: Double, longitude: Double) {
        placeCamera(latitude, longitude)
    }

    // Atualiza e faz zoom no mapa de acordo com a localização
    private fun placeCamera(latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(12f)
            .build()

        map?.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition)
        )
    }

    // Se o fragmento do mapa for destruído queremos parar de receber a
    // localização, se não podemos ter uma NullPointerException
    override fun onDestroy() {
        super.onDestroy()
        FusedLocation.unregisterListener()
    }


    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }


}