package dev.accelerated.pro_writing_aid.integration.text


data class Latex2TextMap(
    val map: List<Latex2TextMapEntry> = emptyList()
) {

    companion object {

        fun fromTextEntries(entries: List<TextEntry>, accumulator: Latex2TextMap = Latex2TextMap()): Latex2TextMap  = when (entries.size) {
            0 -> accumulator
            1 -> {
                val current = entries[0]
                val printed = Printer().print(current.text, limit = current.text.length)
                val newEntry = Latex2TextMapEntry(current, RawTextEntry(printed))
                Latex2TextMap(accumulator.map + newEntry)
            }
            else -> {
                val current = entries[0]
                val next = entries[1]
                val printed = Printer().print(current.text + next.text, limit = current.text.length)
                val newEntry = Latex2TextMapEntry(current, RawTextEntry(printed))
                val newAccumulator = Latex2TextMap(accumulator.map + newEntry)
                Latex2TextMap.fromTextEntries(entries.drop(1), newAccumulator)
            }
        }

//        fun fromTextEntries(entries: List<TextEntry>): Latex2TextMap {
//
//
//
//            // each entry needs a text reprentation
//            for (x in 1..entries.size) {
//                val current = entries[x - 1]
//                val text = if (x < entries.size) {
//                    // we append other text in order to print correctly
//                    val next = entries[x]
//                    current.text + next.text
//                } else {
//                    // the last entry cannot be completed with additional future text for context
//                    current.text
//                }
//
//                val printed = Printer().printString(text, limit = current.text.length).output
//            }
//
//
//        }
    }
}

data class Latex2TextMapEntry(
    val latexEntry: TextEntry,
    val textEntry: RawTextEntry
)

data class RawTextEntry(
    val text: String
)