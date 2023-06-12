package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.ItemExpressionBinding
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import java.text.SimpleDateFormat
import java.util.*


class ApresentacaoFilmesAdapter(private val onOperationClick: (String) -> Unit, private var items: List<Avaliacao> = listOf()) : RecyclerView.Adapter<ApresentacaoFilmesAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemExpressionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    }
    override fun getItemCount() = items.size

    fun updateItems(items: List<Avaliacao>) {
        this.items = items
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if (position % 2 != 0) {
            holder.binding.fundo.setBackgroundColor(Color.parseColor("#DDDDDD"))
        }

        holder.itemView.setOnClickListener { onOperationClick(items[position].id) }
        holder.binding.filme.text = items[position].filme.nomeImdb
        val dataVisualizacao: Date = Date(items[position].dataVisualizacao)
        holder.binding.dataVistaFilme.text = SimpleDateFormat("yyyy-MM-dd").format(dataVisualizacao)
        //holder.binding.dataVistaFilme.text = items[position].dataVisualizacao.get(Calendar.YEAR).toString() + "/" + (items[position].dataVisualizacao.get(Calendar.MONTH) + 1) + "/" + items[position].dataVisualizacao.get(Calendar.DAY_OF_MONTH).toString()
        holder.binding.cinema.text = items[position].cinema.cinema_name
        holder.binding.avaliacao.text = items[position].avaliacao.toString()
        holder.binding.infoObs?.text = items[position].observacoes


    }

}
