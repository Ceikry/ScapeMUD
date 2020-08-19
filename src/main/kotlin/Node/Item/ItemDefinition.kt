package Node.Item

import Node.AttStyle

class ItemDefinition{
    var name: String? = ""
    var desc: String? = ""
    var value: Int? = 0
    var wieldable: Boolean? = false
    var wieldSlot: Int? = 0
    var equipMsg: String? = ""
    val stats = Array<Int>(9){0}
    var stackable = false
    var hasContainer = false
    var containerSize = 0
    var attStyle: AttStyle = AttStyle.MELEE
    var requirements = hashMapOf<Int,Int>()

}