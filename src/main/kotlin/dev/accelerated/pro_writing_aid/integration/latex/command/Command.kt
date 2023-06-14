package dev.accelerated.pro_writing_aid.integration.latex.command

import dev.accelerated.pro_writing_aid.integration.latex.command.handler.LatexCommandHandler

class Command(
    private val name: String
) {
    fun byExtractingParameterText(): LatexCommandHandler=
        LatexCommandHandler.extractParameterText(name)

    companion object  {
        fun handleCommand(name: String): Command = Command(name)


    }
}
