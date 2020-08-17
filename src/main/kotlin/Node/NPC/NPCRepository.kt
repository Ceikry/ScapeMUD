package Node.NPC

class NPCRepository{
    companion object{
        val NPCs = hashMapOf<Int, NPCDefinition>()

        @JvmStatic
        fun add(id: Int, def: NPCDefinition){
            NPCs.put(id,def)
        }

        @JvmStatic
        fun forId(id: Int): NPCDefinition?{
            return NPCs.get(id)
        }
    }
}