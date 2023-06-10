package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentDetalheFilmeBinding
import java.io.File
import java.util.Calendar
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao

class DetalheFilmeFragment(avaliacao: Avaliacao) : Fragment() {
    
    private lateinit var binding: FragmentDetalheFilmeBinding
    val avaliacao = avaliacao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.detalhe)

        val view = inflater.inflate(
            R.layout.fragment_detalhe_filme, container, false
        )
        binding = FragmentDetalheFilmeBinding.bind(view)
        return binding.root
    }

    private var adapter = DetalheFilmeAdapter(::onOperationClick)

    private fun onOperationClick(img: File){
        val activity= view?.context as AppCompatActivity
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, avaliacao)
    }



    override fun onStart() {
        super.onStart()

            /*for(filmeImdb in FilmesIMDBParte1.getListFilmesImdb){
                if(filmeImdb.nomeImdb == filme.nome){
                    binding.generoValor.text  = filmeImdb.generoImdb
                    binding.dataLancamentoValor.text  = filmeImdb.dataImdb
                    binding.avaliacaoImdbValor.text  = filmeImdb.avaliacaoImdb
                    binding.sinopseValor.text = filmeImdb.sinopse

                    val imgCapa = context?.resources?.getIdentifier(filmeImdb.imgImdb, "drawable",  context?.packageName)
                    if (imgCapa != null) {
                        binding.imgDetalhe.setImageResource(imgCapa)
                    }
                }
            }



            binding.filme.text = filme.nome
            binding.cinemaValor.text = filme.cinema
            binding.dataVisualizacaoValor.text = filme.dataVisualizacao.get(Calendar.YEAR).toString() + "/" + (filme.dataVisualizacao.get(Calendar.MONTH) + 1) + "/" + filme.dataVisualizacao.get(Calendar.DAY_OF_MONTH)
            binding.avaliacaoValor.text = filme.avaliacao.toString()
            binding.observacoesValor.text = filme.observacoes




        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHistory?.adapter = adapter
        adapter.updateItems(filme.listImgGet)*/


    }

}