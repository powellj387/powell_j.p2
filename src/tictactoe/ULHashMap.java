package tictactoe;

import java.util.*;

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
            boolean isPrime = true;
            if (number <= 1 || (number > 2 && number % 2 == 0)) {
                isPrime = false; // if the value is less than or equal to 1 or an even number greater than 2 it
                                 // is not prime
            } else if (number==2|| number==3) {
                isPrime = true; // if the value is either 2 or 3 which are prime
                } else {
                int divisor = 5;
                while (divisor * divisor <= number) {
                    if (number % divisor == 0 || number % (divisor + 2) == 0) {
                        isPrime = false; // The number is divisible by divisor or divisor + 2, so it's not prime
                        break;
                    }
                    divisor += 6;
                }
            }
            return isPrime;
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
        boolean returnValue = true;

        if (this != otherObject) {
            if (otherObject == null || getClass() != otherObject.getClass()) {
                returnValue = false;
            } else {
                ULHashMap<K, V> otherMap = (ULHashMap<K, V>) otherObject;
                if (size() != otherMap.size()) {
                    returnValue = false;
                } else {
                    for (LinkedList<Mapping<K, V>> list : table) {
                        if (list != null) {
                            for (Mapping<K, V> entry : list) {
                                K key = entry.getKey();
                                V value = entry.getValue();
                                if (!otherMap.containsKey(key) || !value.equals(otherMap.lookup(key))) {
                                    returnValue = false;
                                    break; // No need to continue checking, we already know they are not equal
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnValue;
    }

    public Iterator<Mapping<K, V>> iterator() {
        return new Iterator<Mapping<K, V>>() {
            private int currentIndex = 0;
            private Iterator<Mapping<K, V>> currentIterator = null;

            @Override
            public boolean hasNext() {
                boolean returnVal = false;
                while (currentIndex < capacity) {
                    if (table[currentIndex] != null) {
                        if (currentIterator == null || !currentIterator.hasNext()) {
                            currentIterator = table[currentIndex].iterator();
                        }
                        if (currentIterator.hasNext()) {
                            returnVal = true;
                        }
                    }
                    currentIndex++;
                }
                return returnVal;
            }

            @Override
            public Mapping<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
            }
        };
    }

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }else if(containsKey(key)){
            throw new DuplicateKeyException("Key already in map");
        }else {

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
    }

    private void resizeTable() {
        int newCapacity = PrimeNumberGenerator.nextPrime(capacity);
        LinkedList<Mapping<K, V>>[] newTable = new LinkedList[newCapacity];

        for (LinkedList<Mapping<K, V>> list : table) {
            if (list != null) {
                for (Mapping<K, V> entry : list) {
                    int newIndex = Math.abs(entry.getKey().hashCode()) % newCapacity;
                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new LinkedList<>();
                    }
                    newTable[newIndex].add(entry);
                }
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    public void put(K key, V value) {
        erase(key);
        insert(key, value);
    }

    public V lookup(K key) {
        // Calculate the index for the given key
        int index = Math.abs(key.hashCode()) % capacity;

        // Get the linked list at the calculated index
        LinkedList<Mapping<K, V>> list = table[index];

        if (list != null) {
            // Iterate through the list to find the key
            for (Mapping<K, V> entry : list) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue(); // Return the associated value if the key is found
                }
            }
        }

        // Key not found, return null
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
        return size==0; // Replace with your implementation.
    }

    public int tableSize() {
        // Gives the size of the underlying hash table.
        // Yes, this breaks encapsulation, but it's needed for testing.
        return capacity; // Replace with your implementation.
    }
}
