package com.stage.springboot_kotlin

import java.util.*

fun String.toSlug() =
    lowercase(Locale.getDefault())
        .replace("\n", " ")
        .replace(Regex("[^a-zA-Z\\d\\s]"), " ")
        .split(" ")
        .joinToString("-")
        .replace(Regex("-{2,}"), "-")