package com.demo.talks.infrastructure.api

import com.demo.talks.application.TalkService
import com.demo.talks.domain.exceptions.ErrorMessageModel
import com.demo.talks.domain.exceptions.TalkException
import com.demo.talks.domain.model.Talk
import com.demo.talks.infrastructure.api.dto.TalkSearch
import com.demo.talks.infrastructure.api.dto.UploadFromFile
import com.demo.talks.infrastructure.utils.CsvProcessor
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/talk")
@Tag(name = "TalkController", description = "Operations for Ted Talks")
class TalkController(private val talkService: TalkService) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    @Operation(summary = "list paginated of Talks", description = "Return a Page for talk with the info of the page")
    fun getTalks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Talk>> {
        return ResponseEntity.ok(talkService.getTalks(page, size))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a talk by ID", description = "Return the information for a talk by the ID specified")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Talk found by ID", content = arrayOf(Content(schema = Schema(implementation = Talk::class)))),
            ApiResponse(responseCode = "404", description = "Talk not found by ID", content = arrayOf(Content(schema = Schema(implementation = ErrorMessageModel::class))))
        ]
    )
    fun getTalksById(@PathVariable id: String): ResponseEntity<Talk> {
        return ResponseEntity.ok(talkService.getTalkById(id))
    }

    @GetMapping("/search")
    @Operation(summary = "Search all the talks when it match with filters", description = "Return a list of talks with the talk that matches with the filter specified")
    fun getTalk(
        @Parameter(description = "Part of the title of the talk. example = \"The myth\"")
        @RequestParam(required = false) title: String?,
        @Parameter(description = "Part of the Author of the talk. example = \"Luis\"")
        @RequestParam(required = false) author: String?,
        @Parameter(description = "Minimum of Views of the talk to filter. defaultValue = \"0\"")
        @RequestParam(required = false) minViews: Long?,
        @Parameter(description = "Maximum of Views of the talk to filter. defaultValue = \"${Long.MAX_VALUE}\"")
        @RequestParam(required = false) maxViews: Long?,
        @Parameter(description = "Minimum of Likes of the talk to filter. defaultValue = \"0\"")
        @RequestParam(required = false) minLikes: Long?,
        @Parameter(description = "Maximum of Likes of the talk to filter. defaultValue = \"${Long.MAX_VALUE}\"")
        @RequestParam(required = false) maxLikes: Long?
    ): ResponseEntity<List<Talk>> {
        val talkSearch = TalkSearch(title, author, minViews, maxViews, minLikes, maxLikes)
        talkSearch.validate()
        return ResponseEntity.ok(talkService.search(talkSearch))
    }

    @PostMapping
    @Operation(summary = "Create a new talk by ID", description = "Create a new talk with the information sent in the body")
    fun create(@RequestBody talk: Talk): ResponseEntity<Talk> {
        return ResponseEntity(talkService.createTalk(talk), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing talk by ID", description = "Update the info specified in the body for the talk match with the ID")
    fun update(@PathVariable id: String, @RequestBody talk: Talk): ResponseEntity<Talk> {
        return ResponseEntity.ok(talkService.updateTalk(id, talk))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a talk by ID", description = "Delete a talk from the DB by the ID specified")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        talkService.deleteTalk(id)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/upload")
    @Operation(
        summary = "Upload the data in the csv file sent",
        description = "populate the DB with the information specified in the CSV file specified, if there are some error related to each row, the result will show the succeeded talk added to the DB and the rows with errors to review."
    )
    fun uploadTalks(@RequestPart("file") file: MultipartFile): ResponseEntity<UploadFromFile> {
        val fileName = file.originalFilename
        if (fileName != null && !fileName.endsWith(".csv")) {
            throw TalkException(HttpStatus.BAD_REQUEST, "Only .csv extension files are allowed")
        }
        logger.debug("uploading talks from csv file: $fileName")
        if (file.isEmpty) {
            throw TalkException(HttpStatus.BAD_REQUEST, "File to upload should not be empty")
        }
        val (talks, errors) = CsvProcessor.readCsv(file.inputStream)
        talkService.loadTalks(talks)
        logger.debug("Talks from file: $fileName loaded")
        val response = UploadFromFile(
            "Talks loaded successful: ${talks.size}",
            "Talks not loaded due to errors : ${errors.size}.",
            errors
        )
        return ResponseEntity.ok(response)
    }

}
