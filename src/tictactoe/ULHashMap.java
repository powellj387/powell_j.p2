package tictactoe;

import java.util.*;

public class ULHashMap<K, V> implements Iterable<ULHashMap.Mapping<K, V>> {
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

    private static class PrimeNumberGenerator {
        public static boolean isPrime(int number) {
            if (number <= 1 || (number > 2 && number % 2 == 0)) {
                return false;
            } else if (number == 2 || number == 3) {
                return true;
            } else {
                int divisor = 5;
                while (divisor * divisor <= number) {
                    if (number % divisor == 0 || number % (divisor + 2) == 0) {
                        return false;
                    }
                    divisor += 6;
                }
            }
            return true;
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

    private LinkedList<Mapping<K, V>>[] table;
    private int size;
    private int capacity;

    public ULHashMap() {
        capacity = DEFAULT_SIZE;
        table = new LinkedList[capacity];
        size = 0;
    }

    public ULHashMap(int expectedSize) {
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
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        ULHashMap<K, V> otherMap = (ULHashMap<K, V>) otherObject;
        if (size() != otherMap.size()) {
            return false;
        }

        for (LinkedList<Mapping<K, V>> list : table) {
            if (list != null) {
                for (Mapping<K, V> entry : list) {
                    K key = entry.getKey();
                    V value = entry.getValue();
                    if (!otherMap.containsKey(key) || !value.equals(otherMap.lookup(key))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<Mapping<K, V>> iterator() {
        return new Iterator<Mapping<K, V>>() {
            private int currentTableIndex = 0;
            private int currentListIndex = 0;

            @Override
            public boolean hasNext() {
                boolean hasMoreElements = false;
                while (currentTableIndex < capacity) {
                    if (table[currentTableIndex] != null && currentListIndex < table[currentTableIndex].size()) {
                        hasMoreElements = true;
                        break;
                    }
                    currentTableIndex++;
                    currentListIndex = 0;
                }
                return hasMoreElements;
            }

            @Override
            public Mapping<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[currentTableIndex].get(currentListIndex++);
            }
        };
    }


    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        } else if (containsKey(key)) {
            throw new DuplicateKeyException("Key already in map");
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
        int index = Math.abs(key.hashCode()) % capacity;
        LinkedList<Mapping<K, V>> list = table[index];

        if (list != null) {
            for (Mapping<K, V> entry : list) {
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
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public int tableSize() {
        return capacity;
    }
}