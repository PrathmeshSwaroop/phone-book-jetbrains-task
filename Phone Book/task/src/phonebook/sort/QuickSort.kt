package phonebook.sort

import phonebook.application.compareStrings
import phonebook.data.Record

class QuickSort : IListSorter<MutableList<Record>, Triple<String, MutableList<Record>, Long>> {
    override fun performSort(list: MutableList<Record>): Triple<String, MutableList<Record>, Long> {
        val startTime = System.currentTimeMillis()
        val status = IListSorter.SUCCESS
        performOperation(list, 0, list.lastIndex)
        return Triple(status, list, (System.currentTimeMillis() - startTime))
    }

    private fun performOperation(list: MutableList<Record>, startIndex: Int, lastIndex: Int) {
        if (startIndex >= lastIndex) {
            return
        }
        var pivot = (startIndex + lastIndex) / 2
        var i = 0
        while (i <= lastIndex) {
            var temp: Record
            if (i < pivot && list[pivot].name.compareStrings(list[i].name) < 0) {
                temp = list[i]
                list[i] = list[pivot]
                list[pivot] = temp
                pivot = i
            } else if (i > pivot && list[pivot].name.compareStrings(list[i].name) > 0) {
                temp = list[pivot]
                list[pivot] = list[i]
                list[i] = list[pivot + 1]
                list[pivot + 1] = temp
                pivot += 1
            }
            i += 1
        }
        performOperation(list, startIndex, pivot - 1)
        performOperation(list, pivot + 1, lastIndex)
    }
}