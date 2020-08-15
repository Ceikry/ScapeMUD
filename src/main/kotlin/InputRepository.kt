import RoomSystem.Room
import actions.*

enum class InputRepository(var command: String, action: Action) {
    LOOK("look", LookAction()),
    TAKE("take",TakeAction()),
    INV("inventory",InventoryAction()),
    DROP("drop",DropAction()),
    EXAM("examine",ExamineAction()),
    EQUIP("equip",EquipAction()),
    GEAR("gear",GearAction()),
    STATS("stats",StatsAction()),
    HELP("help",HelpAction()),
    CHOP("chop",ChopAction());

    var action: Action? = action

    companion object {
        @JvmStatic
        fun search(key: String): Action?{
            for (command in InputRepository.values()) {
                if (command.command == key) {
                    return command.action
                }
            }
            return null
        }
    }
}