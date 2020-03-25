package phonebook.data

import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class PhoneBookParser : IFileParser<MutableList<Record>> {
    override fun getParsedData(filePath: String): MutableList<Record> {
        val scanner = Scanner(File(filePath))
        val phoneBook: MutableList<Record> = LinkedList()
        while (scanner.hasNextLine()) {
            val inputArray = scanner.nextLine().split(' ')
            var phoneNumber = ""
            var name = ""
            for (i in inputArray.indices) {
                if (i == 0) {
                    phoneNumber = inputArray[i]
                } else {
                    name = name.plus(inputArray[i]).plus(" ")
                }
            }
            phoneBook.add(Record(name.trim(), phoneNumber.trim()))
        }
        return phoneBook
    }
}