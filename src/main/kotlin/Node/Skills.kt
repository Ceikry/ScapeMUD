package Node

import kotlin.math.abs
import kotlin.math.pow

class Skills {
    companion object {
        val WOODCUTTING = 0
        val MELEE = 1
    }
    val skills = arrayOf(
        Skill("woodcutting",83.0,1),
        Skill("melee",83.0,1)
    )
    class Skill(val name: String, var xpToLevel: Double, var level: Int, var totalXP: Double = 0.0, var previousXPToLevel: Double = 83.0)

    fun addXP(index: Int, amount: Double){
        if(index > skills.size -1){
            System.err.println("Skill index too high!").also { return }
        }

        val skill = skills[index]

        skill.xpToLevel -= amount
        skill.totalXP += amount
        if(skills[index].xpToLevel <= 0){
            GameConstants.addLine("CONGRATULATIONS! You have gained a ${skill.name} level!")
            GameConstants.addLine("You are now level ${skill.level + 1} ${skill.name}")
            val overflow = abs(skill.xpToLevel)
            skill.level += 1
            skill.xpToLevel = skill.previousXPToLevel + getXpToNextLevel(skill.level)
            skill.previousXPToLevel = skill.xpToLevel
            addXP(index,overflow)
        }
    }

    fun addXP(skill: Skill, amount: Double){
        skill.xpToLevel -= amount
        skill.totalXP += amount
        if(skill.xpToLevel <= 0){
            GameConstants.addLine("CONGRATULATIONS! You have gained a ${skill.name} level!")
            GameConstants.addLine("You are now level ${skill.level + 1} ${skill.name}")
            val overflow = abs(skill.xpToLevel)
            skill.level += 1
            skill.xpToLevel = skill.previousXPToLevel + getXpToNextLevel(skill.level)
            skill.previousXPToLevel = skill.xpToLevel
            addXP(skill,overflow)
        }
    }

    fun getXpToNextLevel(level: Int): Double{
        val e = (level - 1) / 7.0
        val n = (2.0).pow(e)
        val x = (300.0) * n
        val y = (level - 1) + x
        return y / 4
    }

    fun forStyle(attStyle: AttStyle): Skill{
        return when(attStyle){
            AttStyle.MELEE -> skills[MELEE]
            AttStyle.MAGE -> TODO()
            AttStyle.RANGE -> TODO()
        }
    }
}