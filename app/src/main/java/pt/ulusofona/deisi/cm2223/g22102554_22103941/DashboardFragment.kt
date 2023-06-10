package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentDashboardBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.dashboard)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        return binding.root

    }
    private var adapter = DashboardAdapter(::onOperationClick)

    private fun onOperationClick(id: String){
        val activity= view?.context as AppCompatActivity
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, id)
    }

    override fun onStart() {
        super.onStart()
        /*val builder = StringBuilder()
        val history = FilmesParte1.history


        history.forEach {builder.append("${it.nome} ${it.cinema}\n")}
        //binding.tvHistory.text = builder.toString()
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory?.adapter = adapter
        adapter.updateItems(FilmesParte1.top5Filmes)
        binding.count.text = FilmesParte1.countFilmes.toString()*/

    }

}