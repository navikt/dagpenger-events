package no.nav.dagpenger.events

import org.junit.jupiter.api.Test
import java.net.URI
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class ProblemTest {
    @Test
    fun toJson() {
        Problem(
            type = URI.create("urn:error"),
            title = "A problem",
            status = 404,
            detail = "An detailed error message",
            instance = URI.create("urn:error:404")
        ).toJson.apply {
            assertNotNull(this)
            assertEquals("""{"type":"urn:error","title":"A problem","status":404,"detail":"An detailed error message","instance":"urn:error:404"}""", this)
        }
    }
}
