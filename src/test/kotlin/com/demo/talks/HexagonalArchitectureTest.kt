package com.demo.talks

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AnalyzeClasses(packagesOf = [HexagonalArchitectureTest::class], importOptions = [DoNotIncludeTests::class])
class HexagonalArchitectureTest {

    @ArchTest
    val `the domain does not access the application and infrastructure` =
        noClasses()
            .that()
            .resideInAPackage("$DOMAIN_PACKAGE..")
            .should()
            .accessClassesThat()
            .resideInAnyPackage("$APPLICATION_PACKAGE..", "$INFRASTRUCTURE_PACKAGE..")


    companion object {
        private val BASE_PACKAGE = HexagonalArchitectureTest::class.java.`package`.name

        private val DOMAIN_PACKAGE = "$BASE_PACKAGE.domain"

        private val APPLICATION_PACKAGE = "$BASE_PACKAGE.application"

        private val INFRASTRUCTURE_PACKAGE = "$BASE_PACKAGE.infrastructure"
    }
}
