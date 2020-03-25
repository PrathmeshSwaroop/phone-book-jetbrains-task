package phonebook.data

import java.io.File
import java.util.*

class QueryParser : IFileParser<MutableList<String>> {
    override fun getParsedData(filePath: String): MutableList<String> {
        val queries = LinkedList<String>()
        val findScanner = Scanner(File(filePath))
        while (findScanner.hasNextLine()) {
            queries.add(findScanner.nextLine())
        }
        return queries
    }
}