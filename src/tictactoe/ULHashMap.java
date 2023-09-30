package tictactoe;

import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class ULHashMap<K,V> {
    private final int DEFAULT_SIZE = 31;

    public static class Mapping<K, V> {
        private final K key;
        private final V value;

        public Mapping(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public class PrimeNumberGenerator {

        public static boolean isPrime(int number) {
            boolean returnValue = false;
            if (number <= 1) {
                returnValue = false;
            }

            if (number <= 3) {
                returnValue = true;
            }

            if (number % 2 == 0 || number % 3 == 0) {
                returnValue = false;
            }

            int divisor = 5;
            while (divisor * divisor <= number) {
                if (number % divisor == 0 || number % (divisor + 2) == 0) {
                    returnValue = false;
                }
                divisor += 6;
            }

            return returnValue;
        }

        public static int nextPrime(int currentPrime) {
            int next = currentPrime + 1;
            while (true) {
                if (isPrime(next)) {
                    return next;
                }
                next++;
            }
        }
    }

    private LinkedList[] table;
    private int size;
    private int capacity;

    public ULHashMap() {
        // Default constructor for the map. Creates room for 31 entries, initially.
        // The map may grow as necessary.
        capacity = DEFAULT_SIZE;
        table = new LinkedList[capacity];
        size = 0;
    }

    public ULHashMap(int expectedSize) {
        // Constructor that creates a map with room for at least expectedSize entries.
        // If expectedSize is not a prime number, this constructor will have an initial
        // capacity of the next prime number that comes after expectedSize.
        capacity = PrimeNumberGenerator.nextPrime(expectedSize);
        table = new LinkedList[capacity];
        size = 0;
    }

    public ULHashMap<K, V> clone() {
        // Returns a shallow copy of this ULHashMap instance.
        // The hash table structure is copied, but the keys and values are not.
        return null; // Replace with your implementation.
    }

    public boolean equals(Object otherObject) {
        // Two ULHashMaps are considered equal if they have the same keys with the same values.
        return false; // Replace with your implementation.
    }

    public Iterator<Mapping<K, V>> iterator() {
        // Returns an iterator that will visit every key-value pair in this ULHashMap.
        return null; // Replace with your implementation.
    }

    public void insert(K key, V value) {
        // Inserts the key-value pair.
        // The key must not be null.
        // If the insertion causes the hash map's load factor to exceed one, it will
        // grow its capacity to the next prime number.
    }

    public void put(K key, V value) {
        // Overwrites the value stored at key with value.
        // If key is not in the map, it will be inserted.
        // If the insertion causes the hash map's load factor to exceed one, it will
        // grow its capacity to the next prime number.
    }

    public V lookup(K key) {
        // Returns the value associated with key, or null if this map contains no
        // mapping for the key.
        // If the key is not in the map, this will return null.
        // null may also be returned if the given key was mapped to null.
        // containsKey can be used to distinguish between the two cases.
        return null; // Replace with your implementation.
    }

    public boolean containsKey(K key) {
        // Returns true if this map contains key.
        return false; // Replace with your implementation.
    }

    public void erase(K key) {
        // Removes key and its associated value from this map, if the key was present.
        // Does nothing if the key was not in the map.
    }

    public void clear() {
        // Empties the map.
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public int size() {
        // Gives the number of key-value pairs in the map.
        return size; // Replace with your implementation.
    }

    public boolean empty() {
        // Checks whether the map is empty.
        return false; // Replace with your implementation.
    }

    public int tableSize() {
        // Gives the size of the underlying hash table.
        // Yes, this breaks encapsulation, but it's needed for testing.
        return capacity; // Replace with your implementation.
    }
}
