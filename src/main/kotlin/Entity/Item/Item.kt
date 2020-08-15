package Entity.Item

class Item(val id: Int, var amount: Int){
    val definition = ItemRepository.forId(id)

    fun getName(): String {
        var s = definition?.name
        s ?: return "unnamed"
        if(!s.endsWith("s")){
            if(s.endsWith("y")) {
                s = s.removeSuffix("y")
                s += "ies"
            } else {
                if (amount > 1) s += "s"
            }
        }
        return s
    }
}