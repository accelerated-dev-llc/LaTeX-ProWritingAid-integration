package dev.accelerated.pro_writing_aid.integration.latex.visitor

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType.WHITE_SPACE
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import dev.accelerated.pro_writing_aid.integration.text.TextDocumentBuilder
import dev.accelerated.pro_writing_aid.integration.text.TextEntry
import nl.hannahsten.texifyidea.psi.*
import nl.hannahsten.texifyidea.psi.impl.LatexNormalTextImpl
import nl.hannahsten.texifyidea.psi.LatexTypes.*

class NoMathContentVisitor (
    var builder: TextDocumentBuilder
) : LatexVisitor() {

    private val LOG: Logger = Logger.getInstance(NoMathContentVisitor::class.java)

    override fun visitNormalText(text: LatexNormalText) {

    }

    override fun visitElement(o: PsiElement) {
        // @todo verify it is the right type of element

        when (o.elementType) {
            NORMAL_TEXT_WORD -> {
                builder = builder.addBlockEntry(
                    TextEntry(o.text)
                )
            }
            WHITE_SPACE -> {
                val whiteSpace = o.text.replace(" {2,}".toRegex(), " ")
                if (whiteSpace.length > 1) {
                    LOG.warn("Whitespace of length ${o.text.length}: ${o.text}")
                }
                builder = builder.addBlockEntry(
                    TextEntry(whiteSpace)
                )
            }
        }
    }

    // @todo capture other content that needs to be handled inline or in separate blocks

    companion object {
        fun buildTextDocument(o: LatexNoMathContent, b: TextDocumentBuilder): TextDocumentBuilder {
            val visitor = NoMathContentVisitor(b)
            PsiTreeUtil
                .findChildrenOfAnyType(
                    o,
                    LatexNormalTextImpl::class.java,
                    PsiElement::class.java,
//                    PsiWhiteSpaceImpl::class.java
                ).forEach {
                    it.accept(visitor)
                }

            return visitor.builder
        }

    }
}