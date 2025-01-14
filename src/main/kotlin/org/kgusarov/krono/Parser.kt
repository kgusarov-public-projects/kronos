package org.kgusarov.krono

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

sealed interface ParserResult {
    operator fun invoke(
        context: ParsingContext,
        index: Int,
        input: TextOrEndIndexInput,
    ): ParsedResult
}

@SuppressFBWarnings("EI_EXPOSE_REP", "EI_EXPOSE_REP2")
internal data class RawResult(
    val value: ParsedResult,
) : ParserResult {
    override fun invoke(
        context: ParsingContext,
        index: Int,
        input: TextOrEndIndexInput,
    ) = value
}

internal data class ParsingComponentsResult(private val value: ParsedComponents) : ParserResult {
    override fun invoke(
        context: ParsingContext,
        index: Int,
        input: TextOrEndIndexInput,
    ): ParsedResult {
        val result = context.createParsingResult(index, input)
        result.start = value
        return result
    }
}

internal data class ComponentsInputResult(
    val value: ComponentsInput?,
) : ParserResult {
    override fun invoke(
        context: ParsingContext,
        index: Int,
        input: TextOrEndIndexInput,
    ): ParsedResult = context.createParsingResult(index, input, value)
}

object ParserResultFactory {
    operator fun invoke(value: ParsedResult): ParserResult = RawResult(value)

    operator fun invoke(value: ParsedComponents): ParserResult = ParsingComponentsResult(value)

    operator fun invoke(value: ComponentsInput?): ParserResult = ComponentsInputResult(value)

    operator fun invoke(vararg value: Pair<KronoComponent, Int?>): ParserResult = ComponentsInputResult(ComponentsInputFactory(*value))
}

interface Parser {
    fun pattern(context: ParsingContext): Regex

    operator fun invoke(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult?
}
