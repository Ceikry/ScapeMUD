import actions.Action
import buildActions.*

enum class BuildRepository(var command: String, action: Action) {
    ADD("add",AddAction()),
    ST("st",SetTitleAction()),
    SD("sd",SetDescAction()),
    SAVE("save",SaveAction()),
    ADDITEM("additem",AddItemAction()),
    ADDOBJ("addobj",AddObjectAction());

    var action: Action? = action

    companion object {
        @JvmStatic
        fun search(key: String): Action?{
            for (command in BuildRepository.values()) {
                if (command.command == key) {
                    return command.action
                }
            }
            return null
        }
    }
}