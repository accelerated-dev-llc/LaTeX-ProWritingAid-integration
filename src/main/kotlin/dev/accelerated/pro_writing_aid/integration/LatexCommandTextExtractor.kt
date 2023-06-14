package dev.accelerated.pro_writing_aid.integration

import com.intellij.psi.util.PsiTreeUtil
import nl.hannahsten.texifyidea.psi.LatexCommands
import nl.hannahsten.texifyidea.psi.LatexParameterGroupText
import nl.hannahsten.texifyidea.psi.LatexParameterText
import nl.hannahsten.texifyidea.psi.LatexVisitor

class LatexCommandTextExtractor : LatexVisitor() {

    private var texts: List<String> = listOf<String>()

    fun appendTexts(): List<String> {
        val output = listOf("\n") + texts + listOf("\n\n")
        texts = listOf<String>()

        return output
    }

    override fun visitParameterText(o: LatexParameterText) {
        texts = texts + o.text
    }

    companion object {
        val handle: (LatexCommands) -> List<String> =  {
            val commandVisitor = LatexCommandTextExtractor()
            PsiTreeUtil
                .findChildrenOfAnyType(
                    it,
                    false,
                    LatexParameterText::class.java
                )
                .forEach { it.accept(commandVisitor) }

            commandVisitor.appendTexts()
        }
    }
}
