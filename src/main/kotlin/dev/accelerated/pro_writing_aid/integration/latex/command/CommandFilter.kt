package dev.accelerated.pro_writing_aid.integration.latex.command

import com.intellij.openapi.diagnostic.Logger
import dev.accelerated.pro_writing_aid.integration.latex.visitor.LatexCommandTextExtractor
import dev.accelerated.pro_writing_aid.integration.latex.text.LatexTextFinder
import nl.hannahsten.texifyidea.psi.LatexCommands

typealias CommandPredicate = (command: LatexCommands) -> Boolean

class CommandFilter(
    val predicate: CommandPredicate
) {
    internal val LOG: Logger = Logger.getInstance(CommandFilter::class.java)

    operator fun invoke(command: LatexCommands): Boolean = predicate(command)
}

fun CommandFilter.warnsForMissingTextExtractor(): LatexTextFinder =
    LatexTextFinder(
        this,
        { command ->
            val tokenName = command.commandToken.text.substring(1)
            this.LOG.warn("Command token NOT HANDLED: ${tokenName}")

            listOf<String>()
        }
        )

fun CommandFilter.extractTextUsingExtractor(textExtractor: (command: LatexCommands) -> List<String>): LatexTextFinder =
    LatexTextFinder(this, textExtractor)

fun CommandFilter.containsTextInParameterTextElements(): LatexTextFinder =
    this.extractTextUsingExtractor(LatexCommandTextExtractor.handle)

