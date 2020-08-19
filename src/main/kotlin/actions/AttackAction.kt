package actions

import ActionPulse.CombatActionPulse
import Node.Player

class AttackAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            player.addLine("Attack what?").also { return }
        if(player.currentRoom!!.hasNPC(tokens[1])){
            GameConstants.queue(
                CombatActionPulse(
                    player,
                    player.currentRoom!!.cachedNPC!!
                )
            )
            System.out.println("Queued combat action.")
        } else {
            player.addLine("You see no such thing here.")
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: attack npc_name")
        player.addLine("Attack allows you to attack certain NPCs.")
        player.addLine("You will probably die if you attack something much")
        player.addLine("stronger than you.")
    }
}