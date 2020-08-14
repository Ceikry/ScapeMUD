package Entity

class Item(val id: Int, var amount: Int){
    val definition = ItemRepository.forId(id)
}