package com.demo.talks.infrastructure.utils

import com.demo.talks.domain.model.Talk
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.util.UUID

class CsvProcessor {

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        fun readCsv(inputStream: InputStream): Pair<List<Talk>, List<String>> {
            val reader = inputStream.bufferedReader()
            reader.readLine() // Read Headers to avoid process this line
            val errors = mutableListOf<String>()
            val talks = mutableListOf<Talk>()
            reader.lineSequence()
                    .filter { it.isNotBlank() }
                    .forEachIndexed { index: Int, item: String ->
                        val columns = item.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                        try {
                            val talk = Talk(
                                    0L,
                                    columns[0].trim(),
                                    columns[1].trim(),
                                    columns[2].trim(),
                                    columns[3].trim().toLong(),
                                    columns[4].trim().toLong(),
                                    columns[5].trim()
                            )
                            talk.validateAllFields()
                            talks.add(talk)
                        } catch (e: Exception) {
                            val errorLine = "Line $index, ${e.message}, Content:, $item"
                            errors.add(errorLine)
                        }
                    }

            logger.error("Error when reading the file sent:\n\n $errors")
            return Pair(talks, errors)
        }
    }

}