package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.g22102554_22103941.databinding.ItemImgDetalhesBinding
import java.io.File
import kotlin.reflect.KFunction1

class DetalheFilmeAdapter(private val onOperationClick: KFunction1<File, Unit>, private var itemsImg: List<File> = listOf()) : RecyclerView.Adapter<DetalheFilmeAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemImgDetalhesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalheFilmeAdapter.HistoryViewHolder {
        return DetalheFilmeAdapter.HistoryViewHolder(
            ItemImgDetalhesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    }


    override fun getItemCount() = itemsImg.size

    fun updateItems(items: List<File>) {
        this.itemsImg = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DetalheFilmeAdapter.HistoryViewHolder, position: Int) {

        holder.itemView.setOnClickListener { onOperationClick(itemsImg[position]) }
        holder.binding.fotoDetalhe.setImageURI(itemsImg[position].toUri())

    }
}