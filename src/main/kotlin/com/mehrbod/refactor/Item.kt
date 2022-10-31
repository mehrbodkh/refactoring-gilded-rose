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

const val NORMAL_DAY_DEPRECIATION = -1
const val MAX_QUALITY = 50
const val MIN_QUALITY = 0

abstract class AbstractItem(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
    abstract fun update()
}

class AgedBrie(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        sellIn--
        quality = quality.safeQualityChange(-NORMAL_DAY_DEPRECIATION)
    }
}

class BackstagePass(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        quality = quality.safeQualityChange(-NORMAL_DAY_DEPRECIATION)
        if (sellIn < 11) {
            quality = quality.safeQualityChange(-NORMAL_DAY_DEPRECIATION)
        }
        if (sellIn < 6) {
            quality = quality.safeQualityChange(-NORMAL_DAY_DEPRECIATION)
        }

        sellIn--

        if (sellIn < 0) {
            quality = MIN_QUALITY
        }
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
            quality.safeQualityChange(NORMAL_DAY_DEPRECIATION * 4)
        } else {
            quality.safeQualityChange(NORMAL_DAY_DEPRECIATION * 2)
        }
    }
}

class UnknownItem(name: String, sellIn: Int, quality: Int) : AbstractItem(name, sellIn, quality) {
    override fun update() {
        sellIn--
        quality = if (sellIn < 0) {
            quality.safeQualityChange(NORMAL_DAY_DEPRECIATION * 2)
        } else {
            quality.safeQualityChange(NORMAL_DAY_DEPRECIATION)
        }
    }
}

fun Int.safeQualityChange(change: Int): Int {
    var quality = this + change

    if (quality < MIN_QUALITY) {
        quality = MIN_QUALITY
    }

    if (quality > MAX_QUALITY) {
        quality = MAX_QUALITY
    }

    return quality
}
