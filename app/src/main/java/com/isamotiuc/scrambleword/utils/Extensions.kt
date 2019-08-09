package com.isamotiuc.scrambleword.utils

fun String.shuffleString() = this.map { it }.shuffled().joinToString("")
