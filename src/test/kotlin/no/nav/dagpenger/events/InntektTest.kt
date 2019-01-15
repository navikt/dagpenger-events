package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.Inntekt
import no.nav.dagpenger.events.avro.Inntektstype
import no.nav.dagpenger.events.avro.Måned
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class InntektTest {

    @Test fun `create inntekt`() {
        val inntekt = Inntekt.newBuilder().apply {
            virksomhet = "193984823"
            beløp = BigDecimal(100)
            År = 2018
            måned = Måned.april
            inntektstype = Inntektstype.lønnsinntekt
            beskrivelse = "Beskrivelse"
        }.build()

        assertEquals(inntekt.getBeløp(), BigDecimal(100))
    }
}