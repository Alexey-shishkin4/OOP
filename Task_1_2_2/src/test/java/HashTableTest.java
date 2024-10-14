import org.example.HashTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    void testUpdateValue() {
        hashTable.put("key", 100);
        assertEquals(100, hashTable.get("key"));

        hashTable.put("key", 200); // update value
        assertEquals(200, hashTable.get("key"));
    }

    @Test
    void testRemove() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        assertEquals(2, hashTable.size());

        assertEquals(1, hashTable.remove("key1"));
        assertNull(hashTable.get("key1"));
        assertEquals(1, hashTable.size());

        assertEquals(2, hashTable.remove("key2"));
        assertNull(hashTable.get("key2"));
        assertEquals(0, hashTable.size());
    }

    @Test
    void testContainsKey() {
        hashTable.put("key1", 10);
        hashTable.put("key2", 20);

        assertTrue(hashTable.containsKey("key1"));
        assertTrue(hashTable.containsKey("key2"));
        assertFalse(hashTable.containsKey("key3"));
    }

    @Test
    void testSize() {
        assertEquals(0, hashTable.size());

        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        assertEquals(2, hashTable.size());

        hashTable.remove("key1");
        assertEquals(1, hashTable.size());

        hashTable.remove("key2");
        assertEquals(0, hashTable.size());
    }

    @Test
    void testResizeAndRehash() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals(i, hashTable.get("key" + i));
        }

        assertEquals(20, hashTable.size());
    }

    @Test
    void testIterator() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry1 = iterator.next();
        assertNotNull(entry1);

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry2 = iterator.next();
        assertNotNull(entry2);

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry3 = iterator.next();
        assertNotNull(entry3);

        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorConcurrentModificationException() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        hashTable.put("three", 3);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testIteratorNoSuchElementException() {
        hashTable.put("one", 1);
        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testToString() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        String expected = "{one=1, two=2}";
        assertEquals(expected, hashTable.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        HashTable<String, Integer> hashTable1 = new HashTable<>();
        HashTable<String, Integer> hashTable2 = new HashTable<>();

        hashTable1.put("one", 1);
        hashTable1.put("two", 2);

        hashTable2.put("one", 1);
        hashTable2.put("two", 2);

        assertEquals(hashTable1, hashTable2);
        assertEquals(hashTable1.hashCode(), hashTable2.hashCode());

        hashTable2.put("three", 3);
        assertNotEquals(hashTable1, hashTable2);
    }
}
