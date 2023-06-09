package dev.accelerated.pro_writing_aid.integration

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiPlainText
import nl.hannahsten.texifyidea.lang.commands.LatexCommand
import nl.hannahsten.texifyidea.psi.LatexCommands
import nl.hannahsten.texifyidea.psi.LatexContent
import nl.hannahsten.texifyidea.psi.LatexNormalText
import nl.hannahsten.texifyidea.psi.LatexVisitor

class LatexTextVisitor : LatexVisitor() {

    private val LOG: Logger = Logger.getInstance(LatexTextVisitor::class.java)

//    override fun visitCommands(o: LatexCommands) {
//        super.visitCommands(o)
//        LOG.warn("Command: ${o.text}")
//    }

//    fun visitCommand(o: LatexCommand) {
//        LOG.warn("Command: ${o.command}")
//    }

    override fun visitNormalText(text: LatexNormalText) {
        super.visitNormalText(text)

        if (text.text.endsWith("world")) {
            text.text.replace("world", "WORLD")
        }

        LOG.warn("Normal text: ${text.text}")
    }

    override fun visitPlainText(content: PsiPlainText) {
        super.visitPlainText(content)

        if (content.text.endsWith("world")) {
            val replaced = content.text.replace("world", "WORLD")
            LOG.warn(replaced)
        }

        LOG.warn("Plain text: ${content.text}")
    }

    public override fun visitPsiElement(o: PsiElement) {
        super.visitPsiElement(o)

        LOG.warn("PsiElement: ${o.text}")
    }

    override fun visitContent(o: LatexContent) {
        super.visitContent(o)

        LOG.warn("Content: ${o.text}")
    }
}