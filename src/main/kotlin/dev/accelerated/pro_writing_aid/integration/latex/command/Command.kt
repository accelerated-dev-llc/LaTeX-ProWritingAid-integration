package dev.accelerated.pro_writing_aid.integration.latex.command


class Command(
    private val name: String
) {
    companion object  {
        fun aCommandWithName(name: String): CommandFilter =
            CommandFilter { command -> command.commandToken.text.substring(1) == name }

        fun anyOtherCommand(): CommandFilter =
            CommandFilter { _ -> true }
    }
}