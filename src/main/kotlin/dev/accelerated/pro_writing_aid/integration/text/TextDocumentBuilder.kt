package dev.accelerated.pro_writing_aid.integration.text



/**
 * Builds a text document consisting of text extracted from a LaTeX file.
 * For this purpose, text blocks are opened, stacked, written to, and closed.
 * Composing these together constructs a collection of texts that can be sent to
 * a grammar checker API for analysis.
 */
data class TextDocumentBuilder(
    val openTextBlocks: TextBlockStack = TextBlockStack.Empty(),
    val closedTextBlockEntries: List<TextEntry> = emptyList()
) {
    fun openBlock(): TextDocumentBuilder =
        TextDocumentBuilder(
            openTextBlocks.push(TextBlock.Open()),
            closedTextBlockEntries
        )

    fun addBlockEntry(entry: TextEntry): TextDocumentBuilder = when (this.openTextBlocks) {
        is TextBlockStack.Empty -> throw Exception("Cannot add entry to empty stack")
        is TextBlockStack.Stack -> {
            val (block, stack) = this.openTextBlocks.shift()
            TextDocumentBuilder(
                stack.push(block.append(entry)),
                this.closedTextBlockEntries
            )
        }
    }

    fun closeBlock(): TextDocumentBuilder = when (this.openTextBlocks) {
        is TextBlockStack.Empty -> this
        is TextBlockStack.Stack -> {
            val (block, stack) = this.openTextBlocks.shift()
            TextDocumentBuilder(stack, this.closedTextBlockEntries + block.content)
        }
    }

    fun build(): String = when (openTextBlocks) {
        is TextBlockStack.Empty -> closedTextBlockEntries.map { it.text() }.joinToString("")
        else -> throw Exception("Cannot build text document with unclosed blocks")
    }
}

sealed class TextBlockStack {

    fun create(): TextBlockStack = Empty().push(TextBlock.Open())
    fun push(block: TextBlock.Open): TextBlockStack = Stack(block, this)
    abstract fun shift(): Pair<TextBlock.Open,TextBlockStack>?

    class Empty : TextBlockStack() {
        override fun shift() = null
    }
    data class Stack(
        val block: TextBlock.Open,
        val stack: TextBlockStack
    ) : TextBlockStack() {
        override fun shift(): Pair<TextBlock.Open, TextBlockStack> = Pair(block, stack)
    }
}