package dev.accelerated.pro_writing_aid.integration.text

data class TextEntry(
    val text: String
) {
     fun text(): String = this.text
}