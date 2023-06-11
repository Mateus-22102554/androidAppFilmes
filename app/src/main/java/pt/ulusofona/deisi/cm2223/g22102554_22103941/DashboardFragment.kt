package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentDashboardBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val model = Repository.getInstance()

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

        CoroutineScope(Dispatchers.IO).launch(){

                model.countAvaliacoes {
                    it.onSuccess {
                        CoroutineScope(Dispatchers.Main).launch {

                            binding.count.text = it.toString()
                        }
                    }
                }
        }

        CoroutineScope(Dispatchers.IO).launch(){
            model.top5Avaliacoes {
                it.onSuccess {
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvHistory.adapter = adapter
                        adapter.updateItems(it)
                    }

                }
            }
        }

    }

}