package dev.accelerated.pro_writing_aid.integration

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiPlainText
import com.intellij.psi.util.elementType
import nl.hannahsten.texifyidea.lang.commands.LatexCommand
import nl.hannahsten.texifyidea.psi.*
import nl.hannahsten.texifyidea.util.labels.extractLabelName

class LatexTextVisitor : LatexVisitor() {

    private val LOG: Logger = Logger.getInstance(LatexTextVisitor::class.java)

//    override fun visitCommands(o: LatexCommands) {
//        super.visitCommands(o)
//        LOG.warn("Command: ${o.text}")
//    }

//    fun visitCommand(o: LatexCommand) {
//        LOG.warn("Command: ${o.command}")
//    }

    override fun visitNoMathContent(o: LatexNoMathContent) {
        super.visitNoMathContent(o)

        LOG.warn("NoMathContent: ${o.text}")

        // @todo verify this is a document module
        val commandToken = o.commands?.commandToken?.text?.substring(1)
        LOG.warn("Token: ${commandToken}")
        val fc = o.commands?.firstChild
        LOG.warn(fc?.text)
        LOG.warn(fc?.extractLabelName())
        LOG.warn(fc.toString())

//        if (fc?.elementType == LatexTypes.) {
//            LOG.warn("Command: ${fc.text}")
//        }

        o.acceptChildren(this)
    }

    override fun visitNormalText(text: LatexNormalText) {
        super.visitNormalText(text)

        text.acceptChildren(this)

        LOG.warn("Normal text: ${text.text}")
    }

    override fun visitEnvironmentContent(o: LatexEnvironmentContent) {
        super.visitEnvironmentContent(o)

        LOG.warn("Environment content: ${o.text}")

        o.acceptChildren(this)
    }

    override fun visitContent(o: LatexContent) {
        super.visitContent(o)

        LOG.warn("Content: ${o.text}")
    }

    override fun visitPlainText(content: PsiPlainText) {
        super.visitPlainText(content)

        LOG.warn("Plain text: ${content.text}")
        if (content.text.endsWith("world")) {
            val replaced = content.text.replace("world", "WORLD")
            LOG.warn(replaced)
        }
    }
}