package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tictactoe.DuplicateKeyException;
import tictactoe.ULHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ULHashMapTest {

    private ULHashMap<String, Integer> hashMap;

    @BeforeEach
    public void setUp() {
        hashMap = new ULHashMap<>();
    }

    @Test
    public void testInsertAndLookup() {
        // Test inserting and looking up values
        hashMap.insert("key1", 1);
        hashMap.insert("key2", 2);

        assertEquals(Integer.valueOf(1), hashMap.lookup("key1"));
        assertEquals(Integer.valueOf(2), hashMap.lookup("key2"));
    }

    @Test
    public void testInsertDuplicateKey() {
        // Test inserting a duplicate key
        hashMap.insert("key1", 1);
        assertThrows(DuplicateKeyException.class, () -> hashMap.insert("key1", 2));
    }

    @Test
    public void testInsertNullKey() {
        // Test inserting a null key
        assertThrows(IllegalArgumentException.class, () -> hashMap.insert(null, 1));
    }

    @Test
    public void testInsertResizeTable() {
        // Test inserting to trigger table resizing
        for (int i = 1; i <= 31; i++) {
            hashMap.insert("key" + i, i);
        }

        assertEquals(Integer.valueOf(31), hashMap.lookup("key31"));
    }

    @Test
    public void testClear() {
        // Test clearing the map
        hashMap.insert("key1", 1);
        hashMap.clear();

        assertEquals(0, hashMap.size());
        assertNull(hashMap.lookup("key1"));
    }

    @Test
    public void testSize() {
        // Test size() method
        assertEquals(0, hashMap.size());

        hashMap.insert("key1", 1);
        hashMap.insert("key2", 2);

        assertEquals(2, hashMap.size());
    }

    @Test
    public void testEmpty() {
        // Test empty() method
        assertTrue(hashMap.empty());

        hashMap.insert("key1", 1);
        assertFalse(hashMap.empty());
    }

/*    @Test
    public void testTableSize() {
        // Test tableSize() method
        assertEquals(31, hashMap.tableSize());

        hashMap = new ULHashMap<>(10);
        assertEquals(11, hashMap.tableSize());
    }*/

/*    @Test
    public void testIterator() {
        // Test iterator
        hashMap.insert("key1", 1);
        hashMap.insert("key2", 2);

        int sum = 0;
        for (ULHashMap.Mapping<String, Integer> entry : hashMap) {
            sum += entry.getValue();
        }

        assertEquals(3, sum); // 1 + 2 (values from key1 and key2)
    }*/

    @Test
    public void testEquals() {
        // Test equals() method
        ULHashMap<String, Integer> hashMap2 = new ULHashMap<>();

        hashMap.insert("key1", 1);
        hashMap.insert("key2", 2);
        hashMap2.insert("key1", 1);
        hashMap2.insert("key2", 2);

        assertTrue(hashMap.equals(hashMap2));

        hashMap2.insert("key3", 3);

        assertFalse(hashMap.equals(hashMap2));
    }
}
