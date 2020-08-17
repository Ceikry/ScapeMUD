package Node.Item

class ItemRepository {
    companion object{
        val items = hashMapOf<Int, ItemDefinition>()

        @JvmStatic
        fun add(id: Int, def: ItemDefinition){
            items.put(id,def)
        }

        @JvmStatic
        fun forId(id: Int): ItemDefinition?{
            return items.get(id)
        }
    }
}