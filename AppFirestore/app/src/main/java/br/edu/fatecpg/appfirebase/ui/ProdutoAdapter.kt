package br.edu.fatecpg.appfirebase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.appfirebase.R
import br.edu.fatecpg.appfirebase.model.Produto
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter : ListAdapter<Produto, ProdutoAdapter.ViewHolder>(DIFF) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtNome: TextView = view.findViewById(R.id.txt_nome)
        private val txtCategoria: TextView = view.findViewById(R.id.txt_categoria)
        private val txtPreco: TextView = view.findViewById(R.id.txt_preco)

        fun bind(produto: Produto) {
            txtNome.text = produto.nome
            txtCategoria.text = produto.categoria
            txtPreco.text = MOEDA.format(produto.preco)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_produto, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    private companion object {
        val MOEDA: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

        val DIFF = object : DiffUtil.ItemCallback<Produto>() {
            override fun areItemsTheSame(old: Produto, new: Produto) = old.id == new.id
            override fun areContentsTheSame(old: Produto, new: Produto) = old == new
        }
    }
}
