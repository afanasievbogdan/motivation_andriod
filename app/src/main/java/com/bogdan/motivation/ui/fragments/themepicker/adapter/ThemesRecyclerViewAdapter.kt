package com.bogdan.motivation.ui.fragments.themepicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.databinding.ItemThemeButtonBinding

class ThemesRecyclerViewAdapter :
    RecyclerView.Adapter<ThemesRecyclerViewAdapter.ThemeViewHolder>() {

    private val themeList = mutableListOf<String>()
    lateinit var onClickListenerThemes: OnClickListenerThemes

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

    fun setData(newList: List<String>) {
        themeList.clear()
        themeList.addAll(newList)
        notifyDataSetChanged()
    }
// TODO по нажатию на кнопку некст сделай сохранение всех выбранных итемов, а не по клику на каждый добавляй отнимай
    inner class ThemeViewHolder(private val binding: ItemThemeButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(themeName: String) {
            with(binding) {
                btnTheme.text = themeName
                btnTheme.setOnClickListener {
                    it.isSelected = !it.isSelected
                    onClickListenerThemes.onThemeClickListener(
                        themeName, it.isSelected
                    )
                }
            }
        }
    }
}