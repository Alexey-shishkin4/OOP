package org.example;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A parameterized hash table implementation that stores key-value pairs and
 * provides constant-time operations for inserting, deleting, and searching.
 * This implementation handles collisions and optimizes memory usage.
 *
 * @param <K> the type of keys maintained by this hash table
 * @param <V> the type of mapped values
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size = 0;
    private int modCount = 0; // для ConcurrentModificationException

    /**
     * Constructs an empty hash table with the default initial capacity (16)
     * and load factor (0.75).
     */
    @SuppressWarnings("unckecked")
    public HashTable() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    /**
     * Inner class representing an entry in the hash table (a key-value pair).
     */
    public  static class Entry<K, V> {
        K key;
        V value;

        /**
         * Constructs a new entry with the specified key and value.
         *
         * @param key the key
         * @param value the value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key associated with this entry.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with this entry.
         *
         * @return the value
         */
        public V getValue() {
            return value;
        }

        /**
         * Returns a string representation of the entry in the form "key=value".
         *
         * @return a string representation of the entry
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Inserts the specified key-value pair into the hash table.
     * If the key already exists, the value is updated.
     *
     * @param key the key to insert or update
     * @param value the value to associate with the key
     */
    public void put(K key, V value) {
        ensureCapacity();
        int currentIndex = indexFor(hash(key), table.length);

        while (table[currentIndex] != null) {
            if (table[currentIndex].key.equals(key)) {
                table[currentIndex].value = value;
                return;
            }
            currentIndex = nextIndex(currentIndex);
        }

        // Вставка нового элемента
        table[currentIndex] = new Entry<>(key, value);
        size++;
        modCount++;
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or {@code null}
     *         if this table contains no mapping for the key
     */
    public V get(K key) {
        int index = indexFor(hash(key), table.length);

        for (int i = index; table[i] != null; i = nextIndex(i)) {
            if (table[i].key.equals(key)) {
                return table[i].value;
            }
        }

        return null;
    }

    /**
     * Removes the key-value pair for the specified key from the hash table,
     * if it exists.
     *
     * @param key the key whose mapping is to be removed
     * @return the previous value associated with the key, or {@code null}
     *         if there was no mapping for the key
     */
    public V remove(K key) {
        int index = indexFor(hash(key), table.length);
        for (int i = index; table[i] != null; i = nextIndex(i)) {
            if (table[i].key.equals(key)) {
                V oldValue = table[i].value;
                table[i] = null;
                size--;
                modCount++;
                rehash(i);
                return oldValue;
            }
        }

        return null;
    }

    /**
     * Checks if the specified key is present in the hash table.
     *
     * @param key the key whose presence is to be tested
     * @return {@code true} if the key exists in the table, {@code false} otherwise
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Updates the value for the specified key if it exists in the hash table.
     *
     * @param key the key to update
     * @param value the new value to associate with the key
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * Ensures the capacity of the hash table is sufficient. Resizes the table
     * if necessary.
     */
    private void ensureCapacity() {
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Resizes the hash table to double its current capacity and rehashes all
     * existing entries.
     */
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    /**
     * Rehashes the hash table starting from the specified index to resolve
     * any gaps caused by removals.
     *
     * @param start the index from which to rehash
     */
    private void rehash(int start) {
        int index = nextIndex(start);

        while (table[index] != null) {
            Entry<K, V> entry = table[index];
            table[index] = null;
            size--;
            put(entry.key, entry.value);
            index = nextIndex(index);
        }
    }

    /**
     * Returns the next index in the hash table (with wrap-around).
     *
     * @param index the current index
     * @return the next index
     */
    private int nextIndex(int index) {
        return (index + 1) % table.length;
    }

    /**
     * Computes the index for the given hash value and table length.
     *
     * @param hash   the hash value
     * @param length the length of the table
     * @return the index in the table
     */
    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    /**
     * Computes the hash value for the given key, incorporating an additional
     * shift to reduce collisions.
     *
     * @param key the key to hash
     * @return the hash value
     */
    private int hash(Object key) {
        return (key == null) ? 0 : key.hashCode() ^ (key.hashCode() >>> 16);

    }

    /**
     * Iterates over all key-value pairs in the hash table.
     *
     * @return an iterator over the key-value pairs
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                while (index < table.length && table[index] == null) {
                    index++;
                }

                return index < table.length;
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return table[index++];
            }
        };
    }

    /**
     * Returns a string representation of this hash table. The string
     * representation consists of a list of key-value mappings in the
     * order they are stored in the table.
     *
     * @return a string representation of this hash table
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (Entry<K, V> entry : this) {
            sb.append(entry.toString()).append(", ");
        }

        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);  // удалить последнюю запятую
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Compares the specified object with this hash table for equality.
     * Returns {@code true} if the specified object is also a hash table and
     * the two hash tables contain identical key-value mappings.
     *
     * @param obj the object to be compared for equality with this hash table
     * @return {@code true} if the specified object is equal to this hash table
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        // Убедимся, что obj — это хеш-таблица
        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        // Приведём obj к типу HashTable<K, V> с проверкой типов
        HashTable<K, ?> other = (HashTable<K, ?>) obj;

        // Проверим размеры таблиц
        if (this.size != other.size) {
            return false;
        }

        // Проходим по текущей таблице и сравниваем ключи и значения с другой таблицей
        for (Entry<K, V> entry : this) {
            Object otherValue = other.get(entry.getKey());

            // Если в другой таблице нет значения для ключа или значения не равны
            if (!entry.getValue().equals(otherValue)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the hash code value for this hash table. The hash code is
     * calculated based on the entries in the table.
     *
     * @return the hash code value for this hash table
     */
    @Override
    public int hashCode() {
        int result = 1;
        for (Entry<K, V> entry : this) {
            result = 31 * result + (entry.key == null ? 0 : entry.key.hashCode());
            result = 31 * result + (entry.value == null ? 0 : entry.value.hashCode());
        }
        return result;
    }

    /**
     * Returns the number of key-value pairs in the hash table.
     *
     * @return the number of key-value pairs
     */
    public int size() {
        return size;
    }
}
