package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.ItemDashboardBinding
import java.util.*
import kotlin.reflect.KFunction1
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao

class DashboardAdapter(private val onOperationClick: KFunction1<Avaliacao, Unit>, private var itemsTop: List<Avaliacao> = listOf()) : RecyclerView.Adapter<DashboardAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemDashboardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdapter.HistoryViewHolder {
        return DashboardAdapter.HistoryViewHolder(
            ItemDashboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    }


    override fun getItemCount() = itemsTop.size

    fun updateItems(items: List<Avaliacao>) {
        this.itemsTop = items
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: DashboardAdapter.HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onOperationClick(itemsTop[position]) }
        holder.binding.filme.text = itemsTop[position].filme.nomeImdb
        holder.binding.dataVistaFilme.text = itemsTop[position].dataVisualizacao.toString()
        holder.binding.cinema.text = itemsTop[position].cinema.cinema_name
        holder.binding.avaliacao.text = itemsTop[position].avaliacao.toString()


    }
}