package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.Inntekt
import no.nav.dagpenger.events.avro.Inntektstype
import no.nav.dagpenger.events.avro.Maned
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class InntektTest {

    @Test fun `create inntekt`() {
        val inntekt = Inntekt.newBuilder().apply {
            virksomhet = "193984823"
            belop = BigDecimal(100)
            ar = 2018
            maned = Maned.april
            inntektstype = Inntektstype.lonnsinntekt
            beskrivelse = "Beskrivelse"
        }.build()

        assertEquals(inntekt.getBelop(), BigDecimal(100))
    }
}