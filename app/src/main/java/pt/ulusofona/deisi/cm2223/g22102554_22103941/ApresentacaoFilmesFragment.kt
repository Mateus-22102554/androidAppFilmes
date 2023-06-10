package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentApresentacaoFilmesBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao

class ApresentacaoFilmesFragment : Fragment() {
    private lateinit var binding: FragmentApresentacaoFilmesBinding
    private val model = Repository.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.list_filmes)
        val view = inflater.inflate(
            R.layout.fragment_apresentacao_filmes, container, false
        )
        binding = FragmentApresentacaoFilmesBinding.bind(view)
        return binding.root

    }

    private var adapter = ApresentacaoFilmesAdapter(::onOperationClick)


    private fun onOperationClick(id: String){
        val activity= view?.context as AppCompatActivity
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, id)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()
        val builder = StringBuilder()

        CoroutineScope(Dispatchers.IO).launch {
            var history : List<Avaliacao> = listOf()
                model.getAllAvaliacoes {
                    history = it.getOrNull()!!
                    CoroutineScope(Dispatchers.Main).launch{
                        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvHistory.adapter = adapter
                        adapter.updateItems(history)
                    }

                }
        }

    }
}