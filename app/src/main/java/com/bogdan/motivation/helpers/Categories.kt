package com.bogdan.motivation.helpers

enum class Categories(val fullName: String) {
    LETTING_GO("Letting go"),
    HAPPINESS("Happiness"),
    PHYSICAL_HEALTH("Physical Health"),
    SELF_ESTEEM("Self-esteem"),
    FAITH_SPIRITUALITY("Faith & Spirituality"),
    STRESS_ANXIETY("Stress & Anxiety"),
    ACHIEVING_GOALS("Achieving goals"),
    RELATIONSHIPS("Relationships"),
    motavation("motivation");

    companion object {
        fun valueOfThemeName(themeName: String): Categories {
            return when (themeName) {
                LETTING_GO.fullName -> LETTING_GO
                HAPPINESS.fullName -> HAPPINESS
                PHYSICAL_HEALTH.fullName -> PHYSICAL_HEALTH
                SELF_ESTEEM.fullName -> SELF_ESTEEM
                FAITH_SPIRITUALITY.fullName -> FAITH_SPIRITUALITY
                STRESS_ANXIETY.fullName -> STRESS_ANXIETY
                ACHIEVING_GOALS.fullName -> ACHIEVING_GOALS
                RELATIONSHIPS.fullName -> RELATIONSHIPS
                motavation.fullName -> motavation
                else -> throw IllegalArgumentException()
            }
        }
    }
}