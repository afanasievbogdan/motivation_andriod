package com.bogdan.motivation.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.motivation.databinding.FragmentThemeButtonBinding
import com.bogdan.motivation.interfaces.OnClickListenerThemes

class ThemesRecyclerViewAdapter :
    RecyclerView.Adapter<ThemesRecyclerViewAdapter.ThemeViewHolder>() {

    private val themeList = mutableListOf<String>()
    lateinit var onClickListenerThemes: OnClickListenerThemes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        return ThemeViewHolder(
            FragmentThemeButtonBinding.inflate(
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

    inner class ThemeViewHolder(private val binding: FragmentThemeButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(themeName: String) {
            with(binding) {
                btnTheme.text = themeName
                var isPressed = false
                btnTheme.setOnClickListener {
//                    TODO: it.isSelected = !it.isSelected
                    isPressed = !isPressed
                    it.isSelected = isPressed
//                    TODO: val ??? = if (it.isSelected) "1" else "0"
                    if (it.isSelected) {
                        onClickListenerThemes.onThemeClickListener("1")
                    } else {
                        onClickListenerThemes.onThemeClickListener("0")
                    }
                }
            }
        }
    }
}