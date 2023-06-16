package dev.accelerated.pro_writing_aid.integration.text

/**
 * When to print a space:
 * - when last output character was not a space
 * When to print 2 newline:
 * - when last character encountered was a newline, but only 1 such newline was counted
 *
 */
data class Printer(
    val output: String = ""
) {

    fun print(input: String, limit: Int? = null): String =
        printCharactersOneByOne(input, limit).output

    private fun printCharactersOneByOne(input: String, limit: Int?): Printer = when (input) {
        "" -> this
        else -> {
            val stop = limit?.let {
                output.length >= it
            } ?: false
            if (stop) {
                this
            } else {

                val next1 = input.substring(0, 1)
                val next2 = input.substring(1, minOf(2, input.length))
                val rest = input.substring(1)
                this.append(previous(), next1, next2).printCharactersOneByOne(rest, limit)
            }
        }
    }

    private fun previous(): String = when (output.length) {
        0 -> ""
        else -> output.substring(output.length - 1)
    }

    private fun trimOutput(o: String): String = o

    private fun append(previous: String, next1: String, next2: String): Printer {
        val output = trimOutput(this.output)

        return if (next1 == " " && next2 == " ") {
            this
        } else if (next1 == " " && previous == " "){
            this
        } else if (next1 == "\n") {
            when (next2) {
                "\n" -> {
                    when (previous) {
                        "\n" -> this
                        else -> Printer(output + "\n")
                    }
                }
                "-" -> {
                    when (previous) {
                        "\n" -> Printer(output + "\n")
                        else -> Printer(output + "\n")
                    }
                }
                else -> {
                    when (previous) {
                        "\n" -> Printer(output + "\n")
                        else -> this
                    }
                }
            }
        } else {
            Printer(output + next1)
        }
    }

    companion object {

    }
}