package com.example.humasdirectoryxml

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.humasdirectoryxml.databinding.ItemProkerBinding

class ProkerAdapter(
    private val listProker: List<Proker>,
    private val isHorizontal: Boolean = false,
    private val onDetailClick: (String) -> Unit
) : RecyclerView.Adapter<ProkerAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ItemProkerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (isHorizontal) {
            val layoutParams = binding.root.layoutParams
            layoutParams.width = (parent.context.resources.displayMetrics.density * 310).toInt()
            binding.root.layoutParams = layoutParams
        }
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val proker = listProker[position]
        with(holder.binding) {
            imgProker.setImageResource(proker.photo)
            tvName.text = proker.name
            tvDesc.text = proker.description

            btnInstagram.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(proker.link))
                holder.itemView.context.startActivity(intent)
            }

            btnDetail.setOnClickListener {
                onDetailClick(proker.name)
            }
        }
    }

    override fun getItemCount(): Int = listProker.size
}