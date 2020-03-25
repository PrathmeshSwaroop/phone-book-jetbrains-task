package phonebook


import phonebook.data.CustomHashMap
import phonebook.data.PhoneBookParser
import phonebook.data.QueryParser
import phonebook.data.Record
import phonebook.search.BinarySearch
import phonebook.search.ISearch
import phonebook.search.JumpSearch
import phonebook.search.LinearSearch
import phonebook.sort.BubbleSort
import phonebook.sort.IListSorter
import phonebook.sort.QuickSort
import kotlin.system.measureTimeMillis

private const val DIRECTORY = "D:/Projects/Phone Book/directory.txt"
private const val FIND = "D:/Projects/Phone Book/find.txt"
private val phoneBookList by lazy {
    PhoneBookParser().getParsedData(DIRECTORY)
}
private val queries by lazy {
    QueryParser().getParsedData(FIND)
}
lateinit var search: ISearch<Record, String>
lateinit var sorter: IListSorter<MutableList<Record>, Triple<String, MutableList<Record>, Long>>
private var entriesFound = 0

fun main() {

    /**LINEAR SEARCH START**/
    search = LinearSearch(queries, phoneBookList)
    println("Start searching (linear search)...")
    val linearSearchTime = measureTimeMillis {
        entriesFound = search.performSearch()
    }
    println("Found $entriesFound / ${queries.size} entries. " +
            "Time taken: ${getMeasureTimeString(linearSearchTime)} \n")

    /**LINEAR SEARCH END**/

    /**BUBBLE SORT + JUM SEARCH START**/
    println("Start searching (bubble sort + jump search)...")
    sorter = BubbleSort(linearSearchTime)
    var result = sorter.performSort(phoneBookList)
    search = if (result.first == IListSorter.FAILURE) {
        LinearSearch(queries, result.second)
    } else {
        JumpSearch(queries, result.second)
    }

    val jumpSearchTime = measureTimeMillis {
        entriesFound = search.performSearch()
    }
    println("Found $entriesFound / ${queries.size} entries." +
            " Time taken: ${getMeasureTimeString((jumpSearchTime + result.third))}")
    println("Sorting time: ${getMeasureTimeString(result.third)} " +
            if (result.first == IListSorter.FAILURE) "- STOPPED, moved to linear search" else "")
    println("Searching time: ${getMeasureTimeString(jumpSearchTime)}\n")
    /**BUBBLE SORT + JUM SEARCH END**/


    /**QUICK SORT + BINARY SEARCH START**/
    println("Start searching (quick sort + binary search)...")
    sorter = QuickSort()
    result = sorter.performSort(phoneBookList)
    search = BinarySearch(queries, result.second)
    val binarySearchTime = measureTimeMillis {
        entriesFound = search.performSearch()
    }
    println("Found $entriesFound / ${queries.size} entries." +
            " Time taken: ${getMeasureTimeString((binarySearchTime + result.third))}")
    println("Sorting time: ${getMeasureTimeString(result.third)}")
    println("Searching time: ${getMeasureTimeString(binarySearchTime)}\n")
    /**QUICK SORT + BINARY SEARCH END**/


    /**HASH TABLE START**/
    println("Start searching (hash table)...")

    val currentTime = System.currentTimeMillis()
    val customMap = CustomHashMap<String, String>()
    for (contact in phoneBookList) {
        customMap.put(contact.name, contact.number)
    }
    val hashTableCreationTime = System.currentTimeMillis() - currentTime

    val hashSearchTime = measureTimeMillis {
        entriesFound = 0
        for (query in queries) {
            if (customMap.containsKey(query)) {
                entriesFound++
            }
        }
    }
    println("Found $entriesFound / ${queries.size} entries." +
            " Time taken: ${getMeasureTimeString((hashSearchTime + hashTableCreationTime))}")

    println("Creating time : ${getMeasureTimeString(hashTableCreationTime)}")
    println("Searching time : ${getMeasureTimeString(hashSearchTime)}")
}

private fun getMeasureTimeString(measuredTime: Long): String {
    var seconds = measuredTime / 1000
    val millis = measuredTime % 1000
    val min = seconds / 60
    seconds %= 60
    return "$min min. $seconds sec. $millis ms."
}

