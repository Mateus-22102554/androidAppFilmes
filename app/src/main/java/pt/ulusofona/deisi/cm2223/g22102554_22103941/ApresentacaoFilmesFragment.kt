package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentApresentacaoFilmesBinding


class ApresentacaoFilmesFragment : Fragment() {
    private lateinit var binding: FragmentApresentacaoFilmesBinding


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

    private fun onOperationClick(filme: Filme){
        val activity= view?.context as AppCompatActivity
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, filme)
    }

    override fun onStart() {
        super.onStart()
        val builder = StringBuilder()
        val history = Filmes.history
        history.forEach {builder.append("${it.nome} ${it.cinema}\n")}
        //binding.tvHistory.text = builder.toString()
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory?.adapter = adapter
        adapter.updateItems(Filmes.history)

    }
}