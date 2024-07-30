package org.kgusarov.krono

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.common.parsers.ISOFormatParser
import org.kgusarov.krono.common.refiners.ExtractTimezoneAbbrRefiner
import org.kgusarov.krono.common.refiners.ExtractTimezoneOffsetRefiner
import org.kgusarov.krono.common.refiners.MergeWeekdayComponentRefiner
import org.kgusarov.krono.common.refiners.OverlapRemovalRefiner

@SuppressFBWarnings("EI_EXPOSE_REP", "EI_EXPOSE_REP2")
data class KronoConfiguration(
    val parsers: MutableList<Parser>,
    val refiners: MutableList<Refiner>,
)

fun includeCommonConfiguration(
    configuration: KronoConfiguration,
    strictMode: Boolean = false,
): KronoConfiguration {
    configuration.parsers.addFirst(ISOFormatParser())

    configuration.refiners.addFirst(MergeWeekdayComponentRefiner())
    configuration.refiners.addFirst(ExtractTimezoneOffsetRefiner())
    configuration.refiners.addFirst(OverlapRemovalRefiner())

    configuration.refiners.addLast(ExtractTimezoneAbbrRefiner())
    configuration.refiners.addLast(OverlapRemovalRefiner())

//    export function includeCommonConfiguration(configuration: Configuration, strictMode = false): Configuration {
//    configuration.parsers.unshift(new ISOFormatParser());
//
//    configuration.refiners.unshift(new MergeWeekdayComponentRefiner());
//    configuration.refiners.unshift(new ExtractTimezoneOffsetRefiner());
//    configuration.refiners.unshift(new OverlapRemovalRefiner());
//
//    // Unlike ExtractTimezoneOffsetRefiner, this refiner relies on knowing both date and time in cases where the tz
//    // is ambiguous (in terms of DST/non-DST). It therefore needs to be applied as late as possible in the parsing.
//    configuration.refiners.push(new ExtractTimezoneAbbrRefiner());
//    configuration.refiners.push(new OverlapRemovalRefiner());
//    configuration.refiners.push(new ForwardDateRefiner());
//    configuration.refiners.push(new UnlikelyFormatFilter(strictMode));
//    return configuration;
//    }

    return configuration
}
