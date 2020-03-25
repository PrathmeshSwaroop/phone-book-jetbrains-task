package phonebook.search

import phonebook.application.compareStrings
import phonebook.data.Record
import kotlin.math.sqrt

class JumpSearch(override val searchQueries: MutableList<String>,
                 override val phoneBook: MutableList<Record>
) : ISearch<Record, String> {
    override fun performSearch(): Int {
        val bucketSize = sqrt(phoneBook.size.toDouble()).toInt()
        val remainingElements = phoneBook.size % bucketSize
        var entriesFound = 0
        for (name in searchQueries) {
            if (foundUsingJump(name, bucketSize, remainingElements)) {
                entriesFound++
            }
        }
        return entriesFound
    }

    private fun foundUsingJump(name: String, bucketSize: Int, remainingElements: Int): Boolean {
        var index = 0
        var comparision = name.compareStrings(phoneBook[index].name)
        when {
            comparision < 0 -> {
                return false
            }
            comparision == 0 -> {
                return true
            }
            else -> {
                index += bucketSize
                while (index <= phoneBook.lastIndex) {
                    comparision = name.compareStrings(phoneBook[index].name)
                    if (comparision < 0) {
                        var counter = index
                        while (counter >= 0 && counter >= index - bucketSize) {
                            if (name.compareStrings(phoneBook[counter].name) == 0) {
                                return true
                            }
                            counter--
                        }
                        return false
                    } else if (comparision == 0) {
                        return true
                    } else {
                        index += bucketSize
                    }
                }
                for (i in phoneBook.lastIndex downTo (phoneBook.lastIndex - remainingElements)) {
                    comparision = name.compareStrings(phoneBook[i].name)
                    if (comparision == 0) {
                        return true
                    } else if (comparision > 0) {
                        return false
                    }
                }
                return false
            }
        }
    }
}