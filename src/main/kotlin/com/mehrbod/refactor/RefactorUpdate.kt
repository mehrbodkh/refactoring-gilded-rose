package com.mehrbod.refactor

class RefactorUpdate(val items: Array<Item>) {
    fun updateQuality() {
        items
            .map { it.asAbstractItem() }
            .forEachIndexed { index, abstractItem ->
                abstractItem.update()
                items[index] = abstractItem
            }
    }
}