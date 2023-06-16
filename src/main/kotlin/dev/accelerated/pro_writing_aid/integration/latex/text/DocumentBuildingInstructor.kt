package dev.accelerated.pro_writing_aid.integration.latex.text

import dev.accelerated.pro_writing_aid.integration.text.TextDocumentBuilder
import dev.accelerated.pro_writing_aid.integration.text.TextEntry
import nl.hannahsten.texifyidea.psi.LatexCommands

sealed class DocumentBuildingInstructor {

    abstract val finder: LatexTextFinder
    abstract fun write(command: LatexCommands, builder: TextDocumentBuilder): TextDocumentBuilder

    data class WriteBlock(
        override val finder: LatexTextFinder
    ) : DocumentBuildingInstructor() {
        override fun write(command: LatexCommands, builder: TextDocumentBuilder): TextDocumentBuilder =
            builder.openBlock()
                .addBlockEntry(
                    TextEntry(
                        finder.extractText(command).joinToString("")
                    )
                )
                .closeBlock()
    }

    data class WriteInline(
        override val finder: LatexTextFinder
    ) : DocumentBuildingInstructor(){
        override fun write(command: LatexCommands, builder: TextDocumentBuilder): TextDocumentBuilder =
            builder.addBlockEntry(
                TextEntry(
                    finder.extractText(command).joinToString("")
                )
            )
    }

    data class WriteNothing(
        override val finder: LatexTextFinder
    ) : DocumentBuildingInstructor() {
        override fun write(command: LatexCommands, builder: TextDocumentBuilder): TextDocumentBuilder =
            builder
    }

}
