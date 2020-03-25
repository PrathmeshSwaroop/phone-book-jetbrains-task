package phonebook.search

import phonebook.application.compareStrings
import phonebook.data.Record

class BinarySearch(override val searchQueries: MutableList<String>,
                   override val phoneBook: MutableList<Record>
) : ISearch<Record, String> {
    override fun performSearch(): Int {
        var entriesFound = 0
        for (name in searchQueries) {
            if (foundUsingBinary(name, 0, phoneBook.lastIndex)) {
                entriesFound++
            }
        }
        return entriesFound
    }

    private fun foundUsingBinary(name: String, startIndex: Int, lastIndex: Int): Boolean {
        if (startIndex >= lastIndex) {
            return (name.compareStrings(phoneBook[lastIndex].name) == 0)
        }
        val mid = (startIndex + lastIndex) / 2
        val comparisionValue = name.compareStrings(phoneBook[mid].name)
        if (comparisionValue < 0) {
            return foundUsingBinary(name, startIndex, mid)
        } else if (comparisionValue > 0) {
            return foundUsingBinary(name, mid + 1, lastIndex)
        }
        return true
    }
}