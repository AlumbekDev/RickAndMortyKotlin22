package com.example.rickandmortykotlin22.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortykotlin22.data.network.dto.character.CharacterDto
import com.example.rickandmortykotlin22.databinding.CharacterItemBinding
import com.example.rickandmortykotlin22.keeper.base.BaseComparator

class CharacterAdapter(
    private val onItemClick: (id: Int) -> Unit,
) : PagingDataAdapter<CharacterDto, CharacterAdapter.CharacterViewHolder>(
    BaseComparator()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    inner class CharacterViewHolder(
        private val binding: CharacterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener{
                getItem(absoluteAdapterPosition)?.let {
                    onItemClick(it.id)
                }
            }
        }
        fun onBind(item: CharacterDto) = with(binding) {
            titleIm.load(item.image)
            characterName.text = item.name
        }
    }
}