package phonebook.search

interface ISearch<T, U> {
    val searchQueries: MutableList<U>
    val phoneBook: MutableList<T>
    fun performSearch(): Int
}