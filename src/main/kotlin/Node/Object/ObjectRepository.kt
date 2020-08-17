package Node.Object

class ObjectRepository {
    companion object{
        val objects = hashMapOf<Int, ObjectDefinition>()

        @JvmStatic
        fun add(id: Int, def: ObjectDefinition){
            objects.put(id,def)
        }

        @JvmStatic
        fun forId(id: Int): ObjectDefinition?{
            return objects.get(id)
        }
    }
}