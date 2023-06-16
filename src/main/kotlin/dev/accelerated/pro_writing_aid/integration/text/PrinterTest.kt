package dev.accelerated.pro_writing_aid.integration.text

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PrinterTest {
    @Test
    fun printTestString() {
        val printer = Printer()
        val result = printer.print("test")
        assertEquals("test", result)
    }

    @Test
    fun printingStringWithMultipleSpaces() {
        val printer = Printer()
        val result = printer.print("test    test")
        assertEquals("test test", result)
    }

    @Test
    fun doNotPrintSingleNewlines1() {
        val printer = Printer()
        val result = printer.print("test\n    test")
        assertEquals("test test", result)
    }

    @Test
    fun doNotPrintSingleNewlines2() {
        val printer = Printer()
        val result = printer.print("test  \n    test")
        assertEquals("test test", result)
    }

    @Test
    fun doPrintDoubleNewlines1() {
        val printer = Printer()
        val result = printer.print("test\n\ntest")
        assertEquals("test\n\ntest", result)
    }

    @Test
    fun doPrintDoubleNewlines2() {
        val printer = Printer()
        val result = printer
            .print("test\n\n" +
                    "test", limit = 5)
        assertEquals("test\n", result)
    }

    @Test
    fun doNotPrintMoreThanTwoNewlines1() {
        val printer = Printer()
        val result = printer
            .print("test\n\n\ntest")
        assertEquals("test\n\ntest", result)
    }

    @Test
    fun doNotPrintMoreThanTwoNewlines2() {
        val printer = Printer()
        val result = printer
            .print("test\n\n\n\ntest")
        assertEquals("test\n\ntest", result)
    }

    @Test
    fun printNewlinesInAList(){
        val printer = Printer()
        val result = printer.print("- item a\n- item b\n- item c")
        assertEquals("- item a\n- item b\n- item c", result)
    }

    @Test
    fun printNewlinesInAListWithAnIntro(){
        val printer = Printer()
        val result = printer.print("Intro:\n\n- item a\n- item b\n- item c")
        assertEquals("Intro:\n" +
                "\n- item a\n- item b\n- item c", result)
    }
}