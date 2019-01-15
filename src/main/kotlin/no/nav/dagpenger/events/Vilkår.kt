package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.RegelType
import no.nav.dagpenger.events.avro.Vilkår

fun Vilkår.getRegel(regelType: RegelType) = getRegler().find { regel -> regel.getType() == regelType }