package org.kgusarov.krono.locales.en.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ComponentsInputFactory
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractMonthNameMiddleEndianParser
import org.kgusarov.krono.locales.en.EnConstants
import org.kgusarov.krono.locales.en.parseOrdinalNumberPattern
import org.kgusarov.krono.locales.en.parseYear
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class EnMonthNameMiddleEndianParser(
    private val shouldSkipYearLikeDate: Boolean,
) : AbstractMonthNameMiddleEndianParser() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult? {
        val month = EnConstants.MONTH_DICTIONARY[match[MONTH_NAME_GROUP]!!.lowercase()]
        val day = parseOrdinalNumberPattern(match[DATE_GROUP]!!)
        if (day > 31) {
            return null
        }

        if (shouldSkipYearLikeDate) {
            if (
                match[DATE_TO_GROUP].isNullOrEmpty() &&
                match[YEAR_GROUP].isNullOrEmpty() &&
                match[DATE_GROUP]!!.matches(YEAR_LIKE_DATE)
            ) {
                return null
            }
        }

        val components =
            context.createParsingComponents(
                ComponentsInputFactory(
                    KronoComponents.Day to day,
                    KronoComponents.Month to month,
                ),
            ).addTag("parser/ENMonthNameMiddleEndianParser")

        return parse(
            context,
            match,
            components,
            YEAR_GROUP,
            DATE_TO_GROUP,
            day,
            month,
            ::parseOrdinalNumberPattern,
            ::parseYear,
        )
    }

    @Suppress("RegExpRedundantEscape")
    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(${matchAnyPattern(EnConstants.MONTH_DICTIONARY)})" +
                    "(?:-|/|\\s*,?\\s*)" +
                    "(${EnConstants.ORDINAL_NUMBER_PATTERN})(?!\\s*(?:am|pm))\\s*" +
                    "(?:" +
                    "(?:to|\\-)\\s*" +
                    "(${EnConstants.ORDINAL_NUMBER_PATTERN})\\s*" +
                    ")?" +
                    "(?:" +
                    "(?:-|/|\\s*,\\s*|\\s+)" +
                    "(${EnConstants.YEAR_PATTERN})" +
                    ")?" +
                    "(?=\\W|$)(?!\\:\\d)",
                RegexOption.IGNORE_CASE,
            )

        @JvmStatic
        private val YEAR_LIKE_DATE = Regex("^2[0-5]$")

        private const val MONTH_NAME_GROUP = 1
        private const val DATE_GROUP = 2
        private const val DATE_TO_GROUP = 3
        private const val YEAR_GROUP = 4
    }
}
