package com.adesso.movee.internal.extension

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class DoubleKtTest {

    @Test
    fun `verify formatting decimal numbers when the input is null`() {
        val input = null
        val expectedOutput = null

        assertEquals(expectedOutput, input?.toDecimalizedString())
    }

    @Test
    fun `verify formatting decimal numbers when the decimal point shorter than the expected and locale is english`() {
        Locale.setDefault(Locale.ENGLISH)
        val input = 12.0
        val expectedOutput = "12.00"

        assertEquals(expectedOutput, input.toDecimalizedString())
    }

    @Test
    fun `verify formatting decimal numbers when device locale is english`() {
        Locale.setDefault(Locale.ENGLISH)
        val input = 12345.54321
        val expectedOutput = "12,345.543"

        assertEquals(expectedOutput, input.toDecimalizedString(3))
    }

    @Test
    fun `verify formatting decimal numbers when device locale is english for united kingdom region`() {
        Locale.setDefault(Locale.UK)

        val input = 12345.54321
        val expectedOutput = "12,345.543"

        assertEquals(expectedOutput, input.toDecimalizedString(3))
    }

    @Test
    fun `verify formatting decimal numbers when device locale is english for united states region`() {
        Locale.setDefault(Locale.US)

        val input = 12345.54321
        val expectedOutput = "12,345.543"

        assertEquals(expectedOutput, input.toDecimalizedString(3))
    }

    @Test
    fun `verify formatting decimal numbers when device locale is german`() {
        Locale.setDefault(Locale.GERMAN)
        val input = 12345.54321
        val expectedOutput = "12.345,543"

        assertEquals(expectedOutput, input.toDecimalizedString(3))
    }

    @Test
    fun `verify formatting decimal numbers when device locale is germany`() {
        Locale.setDefault(Locale.GERMANY)
        val input = 12345.54321
        val expectedOutput = "12.345,543"

        assertEquals(expectedOutput, input.toDecimalizedString(3))
    }
}
