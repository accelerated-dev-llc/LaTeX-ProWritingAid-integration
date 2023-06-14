package dev.accelerated.pro_writing_aid.integration.latex.command.handler

import dev.accelerated.pro_writing_aid.integration.latex.command.CommandFilter
import dev.accelerated.pro_writing_aid.integration.latex.visitor.LatexCommandTextExtractor
import nl.hannahsten.texifyidea.psi.LatexCommands

class LatexTextFinder(
    private val filter: CommandFilter,
    private val textExtractor: (command: LatexCommands) -> List<String>
) {
    fun filter(command: LatexCommands): Boolean = filter.invoke(command)
    fun extractText(command: LatexCommands): List<String> = textExtractor(command)

}