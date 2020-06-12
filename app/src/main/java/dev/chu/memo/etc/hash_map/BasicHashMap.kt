package dev.chu.memo.etc.hash_map

import java.util.*

// https://medium.com/swlh/building-a-hash-map-in-kotlin-73c9b23d19ba

class BasicHashMap<K, V> {

    data class Entry<K, V>(val key: K, val value: V)

    private var arraySize = 16
//    private val entries: Array<Entry<K, V>?> = arrayOfNulls(arraySize)
    private var entries: Array<LinkedList<Entry<K, V>>> = Array(arraySize) { LinkedList<Entry<K, V>>() }

    private val loadFactor = 0.75
    private var numberOfEntries = 0

    fun put(key: K, value: V) {
//        val index = calculateHash(key)
//        entries[index] = Entry(key, value)

        // 밑 함수로 이동
//        val index = calculateHash(key)
//        val listAtArraySlot = entries[index]
//        val newEntry = Entry(key, value)
//        // LinkedList entries 안에 key가 이미 존재한다면 해당 인덱스 첫번째를 리턴하고 없으면 -1을 리턴
//        val indexOfEntryInList = listAtArraySlot.indexOfFirst { it.key == key }
//        if(indexOfEntryInList >= 0) {
//            listAtArraySlot[indexOfEntryInList] = newEntry
//        } else {
//            listAtArraySlot.offer(newEntry) // 마지막 엘리먼트에 특정 요소를 추가
//        }

        numberOfEntries++

        if (numberOfEntries > arraySize * loadFactor) {
            increaseCapacity()
        }

        put(key, value, entries)
    }

    private fun put(key: K, value: V, localEntries: Array<LinkedList<Entry<K, V>>>) {
        val index = calculateHash(key)
        val listAtArraySlot = localEntries[index]

        val newEntry = Entry(key, value)

        // LinkedList entries 안에 key가 이미 존재한다면 해당 인덱스 첫번째를 리턴하고 없으면 -1을 리턴
        val indexOfEntryInList = listAtArraySlot.indexOfFirst { it.key == key }
        if(indexOfEntryInList >= 0) {
            listAtArraySlot[indexOfEntryInList] = newEntry
        } else {
            listAtArraySlot.offer(newEntry) // 마지막 엘리먼트에 특정 요소를 추가
        }
    }

    fun get(key: K): V? {
//        val index = calculateHash(key)
//        return entries[index]?.value

        val index = calculateHash(key)
        val listAtArraySlot = entries[index]

        // LinkedList entries 안에 {it.key == key} 에 매칭되는 첫 번째 요소를 리턴하고 없으면 null 리턴
        return listAtArraySlot.find { it.key == key }?.value
    }

    fun remove(key: K) {
        val index = calculateHash(key)
//        entries[index] = null
        entries[index].clear()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        entries.forEach {
            if(it.isNotEmpty()) sb.append(it).append('\n')
        }
        return sb.toString()
    }

    private fun calculateHash(key: K): Int {
        return key.hashCode() % arraySize
    }

    private fun increaseCapacity() {
        // Increase size
        arraySize *= 2

        // Create new array and add exiting items to the bigger table
        val localEntries: Array<LinkedList<Entry<K, V>>> = Array(arraySize) { LinkedList<Entry<K, V>>() }

        numberOfEntries = 0

        entries.forEach {
            it.forEach { entry ->
                put(entry.key, entry.value, localEntries)
            }
        }

        // Make the local copy the new entry array
        entries = localEntries
    }
}