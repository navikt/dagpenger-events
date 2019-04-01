package no.nav.dagpenger.events

import com.squareup.moshi.JsonDataException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

class Packet constructor(jsonString: String) {

    companion object {
        internal const val READ_COUNT = "system_read_count"
        internal const val STARTED = "system_started"
    }

    private val adapter = moshiInstance.adapter<MutableMap<String, Any?>>(MutableMap::class.java).lenient()
    private val json: MutableMap<String, Any?> =
        adapter.fromJson(jsonString) ?: throw JsonDataException("Could not parse JSON: $jsonString")

    init {
        if (!json.containsKey(READ_COUNT)) {
            json[READ_COUNT] = -1.0
        }
        if (!json.containsKey(STARTED)) {
            json[STARTED] = LocalDateTime.now()
        }
        json[READ_COUNT] = (json[READ_COUNT] as Double).toInt() + 1
    }

    private fun getValue(key: String): Any? = json[key]

    fun putValue(key: String, thing: Any) {
        put(key, thing)
    }

    private fun put(key: String, value: Any) {
        if (json.containsKey(key)) throw IllegalArgumentException("Cannot overwrite existing key: $key")
        json[key] = value
    }

    fun toJson(): String? = adapter.toJson(json)

    fun hasField(key: String): Boolean = json.containsKey(key)

    fun hasFields(vararg keys: String): Boolean = keys.all { hasField(it) }

    fun getNullableBigDecimalValue(key: String): BigDecimal? = getValue(key)?.let { BigDecimal(it.toString()) }

    fun getNullableIntValue(key: String): Int? = getValue(key)?.toString()?.toDouble()?.toInt()

    fun getNullableLongValue(key: String): Long? = getValue(key)?.toString()?.toLong()

    fun getNullableStringValue(key: String) = getValue(key)?.toString()

    fun getNullableLocalDate(key: String): LocalDate? = getValue(key)?.let { LocalDate.parse(it.toString()) }

    fun getNullableYearMonth(key: String): YearMonth? = getValue(key)?.let { YearMonth.parse(it.toString()) }

    fun <T> getNullableObjectValue(key: String, decode: (Any) -> T): T? =
        getValue(key)?.let(decode)

    fun getNullableBoolean(key: String): Boolean? {
        val v: Any? = getValue(key)
        return when (v.toString().toLowerCase()) {
            "null" -> null
            "true" -> true
            "false" -> false
            else -> throw IllegalArgumentException("Value $v cannot be parsed to an Boolean")
        }
    }

    fun getBigDecimalValue(key: String) = getNullableBigDecimalValue(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getIntValue(key: String) = getNullableIntValue(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getLongValue(key: String) = getNullableLongValue(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getStringValue(key: String) = getNullableStringValue(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getLocalDate(key: String) = getNullableLocalDate(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getYearMonth(key: String) = getNullableYearMonth(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun <T : Any> getObjectValue(key: String, decode: (Any) -> T): T = getNullableObjectValue(key, decode) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getBoolean(key: String) = getNullableBoolean(key) ?: throw IllegalArgumentException("Null value for key=$key")

    fun getMapValue(key: String): Map<String, Any> {
        return getValue(key).takeIf { it is Map<*, *> }.let { try { it as Map<String, Any> } catch (e: TypeCastException) { throw IllegalArgumentException("Not a map value for key=$key") } }
    }

    override fun toString(): String = "Packet(json=${toJson()})"
}