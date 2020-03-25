package phonebook.application

fun String.compareStrings(rhs: String): Int {
    val length = if (length < rhs.length) length else rhs.length
    for (i in 0 until length) {
        if (this[i] != rhs[i]) {
            return this[i] - rhs[i]
        }
    }
    return (length - rhs.length)
}