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
    "Conjured Mana Cake" -> Conjured(name, sellIn, quality)
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
        quality = quality.safeQualityChange(1)
        if (sellIn < 11) {
            quality = quality.safeQualityChange(1)
        }
        if (sellIn < 6) {
            quality = quality.safeQualityChange(1)
        }

        sellIn--
    }
}

class Sulfuras(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        // nothing happens here
    }
}

class Conjured(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        sellIn--
        quality = if (sellIn < 0) {
            quality.safeQualityChange(-4)
        } else {
            quality.safeQualityChange(-2)
        }
    }
}

class UnknownItem(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        sellIn--
        quality = if (sellIn < 0) {
            quality.safeQualityChange(-2)
        } else {
            quality.safeQualityChange(-1)
        }
    }
}

fun Int.safeQualityChange(change: Int): Int {
    var quality = this + change

    if (quality < 0) {
        quality = 0
    }

    if (quality > 50) {
        quality = 50
    }

    return quality
}
