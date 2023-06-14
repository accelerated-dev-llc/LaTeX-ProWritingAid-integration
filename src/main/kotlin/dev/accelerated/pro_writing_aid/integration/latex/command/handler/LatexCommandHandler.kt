package dev.accelerated.pro_writing_aid.integration.latex.command.handler

import com.intellij.psi.util.PsiTreeUtil
import dev.accelerated.pro_writing_aid.integration.LatexCommandTextExtractor
import nl.hannahsten.texifyidea.psi.LatexCommands
import nl.hannahsten.texifyidea.psi.LatexParameterText

class LatexCommandHandler(
    private val matcher: (command: LatexCommands) -> Boolean,
    private val handler: (command: LatexCommands) -> List<String>
) {
    fun match(command: LatexCommands): Boolean = matcher(command)
    fun handle(command: LatexCommands): List<String> = handler(command)

    companion object {
        fun extractParameterText(
            commandName: String
        ): LatexCommandHandler = LatexCommandHandler(
            { command -> command.commandToken.text.substring(1) == commandName },
            LatexCommandTextExtractor.handle
        )
    }

}