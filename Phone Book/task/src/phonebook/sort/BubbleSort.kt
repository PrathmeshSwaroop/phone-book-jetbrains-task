package phonebook.sort

import phonebook.application.compareStrings
import phonebook.data.Record

class BubbleSort(private val linearSearchTime: Long) : IListSorter<MutableList<Record>, Triple<String, MutableList<Record>, Long>> {
    override fun performSort(list: MutableList<Record>): Triple<String, MutableList<Record>, Long> {
        val startTime = System.currentTimeMillis()
        var status = IListSorter.SUCCESS
        outer@ for (i in list.indices) {
            for (j in 0 until list.lastIndex - i) {
                val difference = System.currentTimeMillis() - startTime
                if (difference < (10000 * linearSearchTime)) {
                    val comparision = list[j].name.compareStrings(list[j + 1].name)
                    if (comparision > 0) {
                        val temp = list[j + 1]
                        list[j + 1] = list[j]
                        list[j] = temp
                    }
                } else {
                    status = IListSorter.FAILURE
                    break@outer
                }
            }
        }
        return Triple(status, list, (System.currentTimeMillis() - startTime))
    }
}