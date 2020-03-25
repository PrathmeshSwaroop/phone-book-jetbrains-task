package phonebook.search

import phonebook.data.Record

class LinearSearch(override val searchQueries: MutableList<String>,
                   override val phoneBook: MutableList<Record>) : ISearch<Record, String> {

    override fun performSearch(): Int {
        var entriesFound = 0
        for (name in searchQueries) {
            if (searchFor(name, phoneBook)) {
                entriesFound++
            }
        }
        return entriesFound
    }

    private fun searchFor(name: String?, phoneBook: MutableList<Record>): Boolean {
        if (name.isNullOrBlank()) {
            return false
        }
        phoneBook.forEach {
            if (it.name == name) {
                //println("${it.number} ${it.name}")
                return true
            }
        }
        return false
    }
}