package phonebook.data

abstract class CustomMap<K, V> {
    abstract fun put(key: K?, value: V)
    abstract fun containsKey(key: K?): Boolean
    abstract fun get(key: K): V?
    abstract fun putAll(list: Array<Pair<K, V>>): CustomMap<K, V>
}