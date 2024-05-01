package com.demo.talks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TalksApplication

fun main(args: Array<String>) {
	runApplication<TalksApplication>(*args)
}
