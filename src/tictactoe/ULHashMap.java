package tictactoe;

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
        ULHashMap<K, V> newMap = new ULHashMap<>(capacity);
        for (LinkedList<Mapping<K, V>> list : table) {
            if (list != null) {
                for (Mapping<K, V> entry : list) {
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return newMap;
    }

    public boolean equals(Object otherObject) {
       boolean returnValue = false;
        if (this == otherObject) {
            returnValue = true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            returnValue = false;
        }

        ULHashMap<K, V> otherMap = (ULHashMap<K, V>) otherObject;
        if (size() != otherMap.size()) {
            returnValue = false;
        }

        for (LinkedList<Mapping<K, V>> list : table) {
            if (list != null) {
                for (Mapping<K, V> entry : list) {
                    K key = entry.getKey();
                    V value = entry.getValue();
                    if (otherMap.containsKey(key) || value.equals(otherMap.lookup(key))) {
                        returnValue = true;
                    }
                }
            }
        }
        return returnValue;
    }

    public Iterator<Mapping<K, V>> iterator() {
        // Returns an iterator that will visit every key-value pair in this ULHashMap.
        return null; // Replace with your implementation.
    }

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int index = Math.abs(key.hashCode()) % capacity;
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        table[index].add(new Mapping<>(key, value));
        size++;

        if ((double) size / capacity > 1.0) {
            resizeTable();
        }
    }

    public void put(K key, V value) {
        insert(key, value);
    }

    public V lookup(K key) {
        int index = Math.abs(key.hashCode()) % capacity;
        if (table[index] != null) {
            for (Object entry : table[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return lookup(key) != null;
    }

    public void erase(K key) {
        int index = Math.abs(key.hashCode()) % capacity;
        if (table[index] != null) {
            Iterator<Mapping<K, V>> iterator = table[index].iterator();
            while (iterator.hasNext()) {
                Mapping<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    iterator.remove();
                    size--;
                    return;
                }
            }
        }
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
