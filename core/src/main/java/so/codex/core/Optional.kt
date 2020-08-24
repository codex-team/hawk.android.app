package so.codex.core

import java.io.Serializable
import java.util.NoSuchElementException

/**
 * A container object which may or may not contain a non-null value.
 * If a value is present, `isPresent()` will return `true` and
 * `get()` will return the value.
 */
class Optional<T> : Serializable {

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private val value: T?

    /**
     * Return `true` if there is a value present, otherwise `false`.
     *
     * @return `true` or `false`
     */
    val isPresent: Boolean
        get() = value != null

    /**
     * Return `true` if there is no value present, otherwise `false`.
     *
     * @return `true` or `false`
     */
    val isNotPresent: Boolean
        get() = value == null

    /**
     * Constructs an empty instance.
     */
    private constructor() {
        this.value = null
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     */
    private constructor(value: T) {
        this.value = value
    }

    /**
     * If a value is present in this `Optional`, returns the value,
     * otherwise throws `NoSuchElementException`.
     *
     * @return the non-null value held by this `Optional`
     * @throws java.util.NoSuchElementException if there is no value present
     * @see Optional.isPresent
     */
    fun get() = value ?: throw NoSuchElementException("No value present")

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val optional = o as Optional<*>?

        return if (value != null) value == optional?.value else optional?.value == null
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Optional{value=$value}"
    }

    companion object {
        private val EMPTY = Optional<Any>()

        /**
         * Returns an `Optional` with null value instance.
         * It always will return @{code false} for [.isPresent] method
         *
         * @param <T> the class of the value
         * @return an `Optional` with no the value present
         */
        @JvmStatic
        fun <T> nil() = EMPTY as Optional<T>

        /**
         * Returns an `Optional` describing the specified value, if non-null,
         * otherwise returns an empty `Optional`.
         *
         * @param <T> the class of the value
         * @param value the possibly-null value to describe
         * @return an `Optional` with a present value if the specified value
         * is non-null, otherwise an empty `Optional`
         */
        @JvmStatic
        fun <T> of(value: T?) = value?.let { Optional(it) } ?: nil()
    }
}
