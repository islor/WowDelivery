package com.ianlor.wowdelivery.feature_delivery.util

import org.junit.Test

class NumberFormatUtilTest {

    @Test
    fun `sum of 2 numbers`() {
        val sum = NumberFormatUtil.sumOfCurrency("$24.99", "$104.2")
        assert(sum == "129.19")
    }

    @Test
    fun `sum of 1 number`() {
        var sum = NumberFormatUtil.sumOfCurrency("", "$104.2")
        assert(sum == "104.20")
        sum = NumberFormatUtil.sumOfCurrency("$104.2", "")
        assert(sum == "104.20")
    }

    @Test
    fun `sum of empty numbers`() {
        var sum = NumberFormatUtil.sumOfCurrency("", "")
        assert(sum == "0.00")
    }

    @Test
    fun `sum of numbers with 3 decimals`() {
        var sum =
            NumberFormatUtil.sumOfCurrency("$24.993", "$104.268")

        assert(sum == "129.26")
    }

    @Test
    fun `sum of numbers with no decimals`() {
        var sum = NumberFormatUtil.sumOfCurrency("$24", "$104")

        assert(sum == "128.00")
    }

    @Test
    fun `sum of number with 0`() {
        var sum = NumberFormatUtil.sumOfCurrency("$24", "$0")

        assert(sum == "24.00")
    }
}