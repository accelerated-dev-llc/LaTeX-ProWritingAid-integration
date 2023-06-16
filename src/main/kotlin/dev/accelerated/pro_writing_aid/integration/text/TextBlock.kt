package dev.accelerated.pro_writing_aid.integration.text

sealed class TextBlock {

    abstract val content: List<TextEntry>
    fun text(): String = this.content.map { it.text() }.joinToString("")

    data class Open(
        override val content: List<TextEntry> = emptyList()
    ) : TextBlock() {
        fun append(content: TextEntry): TextBlock.Open =
            TextBlock.Open(this.content + content)
        fun close(): TextBlock.Closed = TextBlock.Closed(this.content)
    }

    data class Closed(
        override val content: List<TextEntry>
    ) : TextBlock()

//    public fun create(): TextBlock.Open = TextBlock.Open(emptyList())
}
