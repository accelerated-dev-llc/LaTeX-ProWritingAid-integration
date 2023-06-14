package dev.accelerated.pro_writing_aid.integration.latex.visitor

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.util.PsiTreeUtil
import dev.accelerated.pro_writing_aid.integration.LatexCommandTextExtractor
import dev.accelerated.pro_writing_aid.integration.latex.command.Command.Companion.handleCommand
import dev.accelerated.pro_writing_aid.integration.latex.command.handler.LatexCommandHandler
import nl.hannahsten.texifyidea.psi.*

class LatexTextVisitor : LatexVisitor() {

    private val LOG: Logger = Logger.getInstance(LatexTextVisitor::class.java)

    private val commandHandlers: List<LatexCommandHandler> = listOf<LatexCommandHandler>(

        // @todo Should the handle part (first part) be attached to a matcher abstraction?
        // @todo Should then the second part be an extension to the most abstract or a very concrete matcher?

        // titles and navigation
        handleCommand("chapter").byExtractingParameterText(),
        handleCommand("section").byExtractingParameterText(),
        handleCommand("subsection").byExtractingParameterText(),
        handleCommand("subsection").byExtractingParameterText(),

        // fallback to simple warning
        LatexCommandHandler(
            { _ -> true },
            { command ->
                val tokenName = command.commandToken.text.substring(1)
                LOG.warn("Command token NOT HANDLED: ${tokenName}")

                listOf<String>()
            }
        ),
    )

    // create an empty list of strings
    private var texts: List<String> = listOf<String>()

    fun appendTexts(): String = texts.joinToString("")

    override fun visitCommands(o: LatexCommands) {
//        super.visitCommands(o)

        val textOutput: List<String> = commandHandlers.firstOrNull { it.match(o) }?.handle(o) ?: listOf<String>()
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