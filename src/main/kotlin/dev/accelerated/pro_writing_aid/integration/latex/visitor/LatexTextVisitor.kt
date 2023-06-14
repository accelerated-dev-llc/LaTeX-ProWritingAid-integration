package dev.accelerated.pro_writing_aid.integration.latex.visitor

import com.intellij.openapi.diagnostic.Logger
import dev.accelerated.pro_writing_aid.integration.latex.command.Command.Companion.aCommandWithName
import dev.accelerated.pro_writing_aid.integration.latex.command.Command.Companion.anyOtherCommand
import dev.accelerated.pro_writing_aid.integration.latex.command.CommandFilter
import dev.accelerated.pro_writing_aid.integration.latex.command.handler.LatexTextFinder
import dev.accelerated.pro_writing_aid.integration.latex.command.containsTextInParameterTextElements
import dev.accelerated.pro_writing_aid.integration.latex.command.warnsForMissingTextExtractor
import nl.hannahsten.texifyidea.psi.*

class LatexTextVisitor : LatexVisitor() {

    private val LOG: Logger = Logger.getInstance(LatexTextVisitor::class.java)

    private val commandTextFinders: List<LatexTextFinder> = listOf<LatexTextFinder>(

        // @todo Should the handle part (first part) be attached to a matcher abstraction?
        // @todo Should then the second part be an extension to the most abstract or a very concrete matcher?

        // titles and navigation
        aCommandWithName("chapter").containsTextInParameterTextElements(),
        aCommandWithName("section").containsTextInParameterTextElements(),
        aCommandWithName("subsection").containsTextInParameterTextElements(),
        aCommandWithName("subsection").containsTextInParameterTextElements(),

        // fallback to simple warning
        anyOtherCommand().warnsForMissingTextExtractor(),
    )

    // create an empty list of strings
    private var texts: List<String> = listOf<String>()

    fun appendTexts(): String = texts.joinToString("")

    override fun visitCommands(o: LatexCommands) {
//        super.visitCommands(o)

        val textOutput: List<String> = commandTextFinders.firstOrNull { it.filter(o) }?.extractText(o) ?: listOf<String>()
        texts = texts + textOutput
    }

//    fun visitCommand(o: LatexCommand) {
//        LOG.warn("Command: ${o.command}")
//    }

    override fun visitNormalText(text: LatexNormalText) {
        super.visitNormalText(text)

//        text.acceptChildren(this)

//        LOG.warn("Normal text: ${text.text}")
        texts = texts + text.text
    }

//    override fun visitEnvironmentContent(o: LatexEnvironmentContent) {
//        super.visitEnvironmentContent(o)
//
////        LOG.warn("Environment content: ${o.text}")
////
////        o.acceptChildren(this)
//    }

//    override fun visitContent(o: LatexContent) {
//        super.visitContent(o)
//
////        LOG.warn("Content: ${o.text}")
//    }

//    override fun visitPlainText(content: PsiPlainText) {
//        super.visitPlainText(content)
//
////        LOG.warn("Plain text: ${content.text}")
////        if (content.text.endsWith("world")) {
////            val replaced = content.text.replace("world", "WORLD")
////            LOG.warn(replaced)
////        }
//    }
}