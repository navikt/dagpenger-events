package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.RegelType
import no.nav.dagpenger.events.avro.Regelbehov

fun Regelbehov.getRegel(regelType: RegelType) = getRegler().find { regel -> regel.getType() == regelType }