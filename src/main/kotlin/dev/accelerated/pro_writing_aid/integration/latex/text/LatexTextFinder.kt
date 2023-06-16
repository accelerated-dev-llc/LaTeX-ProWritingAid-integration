package dev.accelerated.pro_writing_aid.integration.latex.text

import dev.accelerated.pro_writing_aid.integration.latex.command.CommandFilter
import nl.hannahsten.texifyidea.psi.LatexCommands

class LatexTextFinder(
    private val filter: CommandFilter,
    private val textExtractor: (command: LatexCommands) -> List<String>
) {
    fun canExtractTextIn(command: LatexCommands): Boolean = filter.invoke(command)
    fun extractText(command: LatexCommands): List<String> = textExtractor(command)

    fun andAddsToNewTextBlock(): DocumentBuildingInstructor = DocumentBuildingInstructor.WriteBlock(this)

    fun andAddsToExistingTextBlock(): DocumentBuildingInstructor = DocumentBuildingInstructor.WriteInline(this)

    fun andAddsNothing(): DocumentBuildingInstructor = DocumentBuildingInstructor.WriteNothing(this)
}