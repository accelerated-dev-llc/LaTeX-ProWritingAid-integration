package dev.accelerated.pro_writing_aid.integration.latex.visitor

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiWhiteSpace
import dev.accelerated.pro_writing_aid.integration.latex.command.Command.Companion.aCommandWithName
import dev.accelerated.pro_writing_aid.integration.latex.command.Command.Companion.anyOtherCommand
import dev.accelerated.pro_writing_aid.integration.latex.command.containsTextInParameterTextElements
import dev.accelerated.pro_writing_aid.integration.latex.command.warnsForMissingTextExtractor
import dev.accelerated.pro_writing_aid.integration.latex.text.DocumentBuildingInstructor
import dev.accelerated.pro_writing_aid.integration.text.TextDocumentBuilder
import dev.accelerated.pro_writing_aid.integration.text.TextEntry
import nl.hannahsten.texifyidea.psi.*

class LatexTextVisitor : LatexVisitor() {

    private val LOG: Logger = Logger.getInstance(LatexTextVisitor::class.java)

    private var documentBuilder: TextDocumentBuilder = TextDocumentBuilder()

    private val commandTextWriters: List<DocumentBuildingInstructor> = listOf<DocumentBuildingInstructor>(

        // @todo Should the handle part (first part) be attached to a matcher abstraction?
        // @todo Should then the second part be an extension to the most abstract or a very concrete matcher?

        aCommandWithName("href").containsTextInParameterTextElements().andAddsToExistingTextBlock(),

        // titles and navigation
        aCommandWithName("chapter").containsTextInParameterTextElements().andAddsToNewTextBlock(),
        aCommandWithName("section").containsTextInParameterTextElements().andAddsToNewTextBlock(),
        aCommandWithName("subsection").containsTextInParameterTextElements().andAddsToNewTextBlock(),
        aCommandWithName("subsection").containsTextInParameterTextElements().andAddsToNewTextBlock(),

        // fallback to simple warning
        anyOtherCommand().warnsForMissingTextExtractor().andAddsNothing(),
    )

    // create an empty list of strings
    private var texts: List<String> = listOf<String>()

    fun appendTexts(): String = documentBuilder.build()

    override fun visitCommands(o: LatexCommands) {
        documentBuilder = commandTextWriters
            .firstOrNull { it.finder.canExtractTextIn(o) }
            ?.write(o, documentBuilder)
            ?: documentBuilder
    }

//    fun visitCommand(o: LatexCommand) {
//        LOG.warn("Command: ${o.command}")
//    }


    /**
     * Every "no math content" is contained in a new text block that is not shared with content outside of
     * its context.
     */
    override fun visitNoMathContent(o: LatexNoMathContent) {
        documentBuilder = documentBuilder.openBlock()
        documentBuilder = NoMathContentVisitor.buildTextDocument(o, documentBuilder)
        documentBuilder = documentBuilder.closeBlock()
    }

    override fun visitWhiteSpace(space: PsiWhiteSpace) {
        documentBuilder = documentBuilder.openBlock()
        documentBuilder = documentBuilder.addBlockEntry(
            TextEntry(space.text)
        )
        documentBuilder = documentBuilder.closeBlock()
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