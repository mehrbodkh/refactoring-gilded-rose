package com.mehrbod.refactor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun foo() {
        val items = arrayOf<Item>(Item("foo", 0, 0))
        val app = RefactorUpdate(items)
        app.updateQuality()
        assertEquals("fixme", app.items[0].name)
    }

}
