package phonebook.sort

interface IListSorter<in T, out R> {
    companion object {
        const val SUCCESS = "SUCCESS"
        const val FAILURE = "FAILURE"
    }

    fun performSort(list: T): R
}