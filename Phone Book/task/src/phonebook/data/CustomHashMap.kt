package phonebook.data

class CustomHashMap<K, V> : CustomMap<K, V>() {

    val DEFAULT_INITIAL_CAPACITY = 16

    var hashTable: Array<Node<K, V>> = Array(DEFAULT_INITIAL_CAPACITY) { Node<K, V>() }

    override fun putAll(list: Array<Pair<K, V>>): CustomMap<K, V> {
        if (list.isNotEmpty()) {
            for (item in list) {
                put(item.first, item.second)
            }
        }
        return this@CustomHashMap
    }

    override fun put(key: K?, value: V) {
        putEntry(hashOf(key), key, value)
    }

    private fun putEntry(hashKey: Int, key: K?, value: V) {
        if (hashTable.size <= hashKey) {
            hashTable = resizeTable(hashKey)
        }
        hashTable[hashKey].insertItem(Entry(key, value))

    }

    private fun resizeTable(hashKey: Int): Array<Node<K, V>> {
        val newTable = Array<Node<K, V>>(hashKey + 1) { Node() }
        for (index in hashTable.indices) {
            newTable[index] = hashTable[index]
        }
        newTable[hashKey] = Node()
        return newTable
    }

    private fun hashOf(key: K?): Int {
        return if (key == null) 0 else {
            val value = key.toString()
            val buffer = StringBuffer()
            var index = 0
            for (i in value) {
                buffer.append(i.toInt())
            }
            val finalKey = (buffer.toString().toDouble() % 71).toInt()
            finalKey
        }
    }

    override fun containsKey(key: K?): Boolean {
        val hash = hashOf(key)
        if (hash >= hashTable.size) {
            return false
        }
        return hashTable[hashOf(key)].containsKey(key)
    }

    override fun get(key: K): V? {
        val hash = hashOf(key)
        if (hash >= hashTable.size) {
            return null
        }
        return hashTable[hashOf(key)].getValueAtKey(key)
    }

    class Node<K, V>() {
        var headEntry: Entry<K, V>? = null

        fun insertItem(item: Entry<K, V>) {
            if (headEntry == null) {
                headEntry = Entry(item.key, item.value)
                headEntry?.headLink = null
            } else {
                var currentNode = headEntry
                while (currentNode?.tailLink != null) {
                    if (currentNode.key == item.key) {
                        throw DuplicateKeyException()
                    }
                    currentNode = currentNode.tailLink
                }
                val headLink = currentNode
                currentNode?.tailLink = Entry(item.key, item.value)
                currentNode = currentNode?.tailLink
                currentNode?.headLink = headLink
            }
        }

        fun getValueAtKey(key: K): V? {
            var currentEntry: Entry<K, V>? = headEntry ?: return null
            while (currentEntry != null && currentEntry.key != key) {
                if (currentEntry.key == key) {
                    return currentEntry.value
                }
                currentEntry = currentEntry.tailLink
            }
            return null
        }

        fun containsKey(key: K?): Boolean {
            if (key == null || headEntry == null) {
                return false
            }
            var currentEntry = headEntry
            while (currentEntry != null) {
                if (currentEntry.key == key) {
                    return true
                }
                currentEntry = currentEntry.tailLink
            }
            return false
        }
    }

    class Entry<K, V>(val key: K?, val value: V) {
        var headLink: Entry<K, V>? = null
        var tailLink: Entry<K, V>? = null
    }

    class DuplicateKeyException : Exception() {
        override val message: String = "Duplicate Key Found"
    }


}