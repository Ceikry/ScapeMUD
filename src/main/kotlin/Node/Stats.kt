package Node

enum class Stats {
    MELEE_ATK_BONUS,
    RANGE_ATK_BONUS,
    MAGE_ATK_BONUS,
    MELEE_DEF_BONUS,
    RANGE_DEF_BONUS,
    MAGE_DEF_BONUS,
    MELEE_DMG_BONUS,
    RANGE_DMG_BONUS,
    MAGE_DMG_BONUS;

    companion object{
        @JvmStatic
        fun attkForStyle(attStyle: AttStyle): Stats{
            return when(attStyle){
                AttStyle.MELEE -> MELEE_ATK_BONUS
                AttStyle.MAGE -> MAGE_ATK_BONUS
                AttStyle.RANGE -> RANGE_ATK_BONUS
            }
        }

        @JvmStatic
        fun dmgForStyle(attStyle: AttStyle): Stats{
            return when(attStyle){
                AttStyle.MELEE -> MELEE_DMG_BONUS
                AttStyle.RANGE -> RANGE_DMG_BONUS
                AttStyle.MAGE -> MAGE_DMG_BONUS
            }
        }

        @JvmStatic
        fun defAgainstStyle(attStyle: AttStyle): Stats{
            return when(attStyle){
                AttStyle.MELEE -> MELEE_DEF_BONUS
                AttStyle.MAGE -> MAGE_DEF_BONUS
                AttStyle.RANGE -> RANGE_DEF_BONUS
            }
        }
    }
}