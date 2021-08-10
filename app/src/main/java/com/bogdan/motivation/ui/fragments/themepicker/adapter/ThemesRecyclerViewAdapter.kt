package com.bogdan.motivation.ui.fragments.themepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.data.entities.local.PickedCategories
import com.bogdan.motivation.databinding.ItemThemeButtonBinding

class ThemesRecyclerViewAdapter :
    RecyclerView.Adapter<ThemesRecyclerViewAdapter.ThemeViewHolder>() {

    private val themeList = mutableListOf<PickedCategories>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        return ThemeViewHolder(
            ItemThemeButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = themeList.count()

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(themeList[position])
    }

    fun setData(newList: List<PickedCategories>) {
        print("DATA SETTER")
        themeList.clear()
        themeList.addAll(newList)
        notifyDataSetChanged()
    }

    fun getData() = themeList

    inner class ThemeViewHolder(private val binding: ItemThemeButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theme: PickedCategories) {
            with(binding) {
                btnTheme.text = theme.name
                btnTheme.setOnClickListener {
                    theme.isPicked = !theme.isPicked
                    it.isSelected = !it.isSelected
                }
            }
        }
    }
}