package com.mehrbod.refactor

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

fun Item.asAbstractItem(): AbstractItem = when (name) {
    "Aged Brie" -> AgedBrie(name, sellIn, quality)
    "Backstage passes to a TAFKAL80ETC concert" -> BackstagePass(name, sellIn, quality)
    "Sulfuras, Hand of Ragnaros" -> Sulfuras(name, sellIn, quality)
    else -> UnknownItem(name, sellIn, quality)
}

abstract class AbstractItem(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
    abstract fun update()
}

class AgedBrie(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        sellIn--
        if (quality < 50) {
            quality++
        }
    }
}

class BackstagePass(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        if (quality < 50) {
            quality++
        }

        if (sellIn < 11) {
            if (quality < 50) {
                quality++
            }
        }

        if (sellIn < 6) {
            if (quality < 50) {
                quality++
            }
        }
        sellIn--
    }
}

class Sulfuras(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        // nothing happens here
    }
}

class UnknownItem(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        sellIn--
        if (sellIn < 0) {
            quality -= 2
        } else {
            quality--
        }

        if (quality < 0) quality = 0
    }
}
