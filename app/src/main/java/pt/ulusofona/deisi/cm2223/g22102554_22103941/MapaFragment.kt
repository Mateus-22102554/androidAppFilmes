package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity



class MapaFragment : Fragment() {
    private lateinit var binding: MapaFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.mapa)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa, container, false)

        // Inflate the layout for this fragment



    }


    }