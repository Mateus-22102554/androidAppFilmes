package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.Repository
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.FragmentDetalheFilmeBinding
import java.io.File
import java.util.Calendar
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import java.text.SimpleDateFormat
import com.bumptech.glide.Glide

class DetalheFilmeFragment(id: String) : Fragment() {
    
    private lateinit var binding: FragmentDetalheFilmeBinding
    val id = id
    private val model = Repository.getInstance()

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
        NavigationManager.goToDetalheFilmeFragment(activity.supportFragmentManager, id)
    }



    override fun onStart() {
        super.onStart()




            CoroutineScope(Dispatchers.IO).launch {
                model.getAvaliacao(id){
                    it.onSuccess {avaliacao ->

                        CoroutineScope(Dispatchers.Main).launch{
                            //Imagem Filme IMDB Detalhe
                            Glide.with(requireContext())
                                .load(avaliacao.filme.imgImdb)
                                .into(binding.imgDetalhe)


                            binding.filme.text = avaliacao.filme.nomeImdb
                            binding.generoValor.text = avaliacao.filme.generoImdb
                            binding.dataLancamentoValor.text = avaliacao.filme.dataImdb.toString()
                            binding.avaliacaoImdbValor.text = avaliacao.filme.avaliacaoImdb
                            binding.sinopseValor.text = avaliacao.filme.sinopse
                            binding.cinemaValor.text = avaliacao.cinema.cinema_name
                            binding.dataVisualizacaoValor.text = SimpleDateFormat("yyyy-MM-dd").format(avaliacao.dataVisualizacao)
                            binding.avaliacaoValor.text = avaliacao.avaliacao.toString()
                            binding.observacoesValor.text = avaliacao.observacoes
                        }

                    }
                }

            }


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