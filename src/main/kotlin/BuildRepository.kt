import actions.Action
import buildActions.AddAction

enum class BuildRepository(var command: String, action: Action) {
    ADD("add",AddAction());

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