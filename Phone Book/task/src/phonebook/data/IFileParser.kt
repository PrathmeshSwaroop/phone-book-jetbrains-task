package phonebook.data

interface IFileParser<out T> {
    fun getParsedData(filePath: String): T
}