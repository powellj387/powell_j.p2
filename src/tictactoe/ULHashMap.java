//@author Julian Powell

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
            boolean isPrime = true;
            //if the value is less than or equal to 1 or it's greater than 2 and divisible by 2 it is not prime
            if (number <= 1 || (number > 2 && number % 2 == 0)) {
                isPrime = false;
                //so long as the number is not equal to 2 or 3, we're gonna check to see if it's prime
            } else if (number != 2 && number != 3) {
                int divisor = 5;
                while (divisor * divisor <= number) {
                    if (number % divisor == 0 || number % (divisor + 2) == 0) {
                        isPrime = false;
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
        //goes through the entire map and copies over every item into the new map
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
        boolean isEqual = true;
        //if the objects are the same, they must be equal
        if (this == otherObject) {
            isEqual = true;
            //if the object being compared is null or doesnt belong to the same class, they cant be equal
        } else if (otherObject == null || getClass() != otherObject.getClass()) {
            isEqual = false;
        } else {
            ULHashMap<K, V> otherMap = (ULHashMap<K, V>) otherObject;
            //if the size of the maps don't match, they cant be equal
            if (size() != otherMap.size()) {
                isEqual = false;
            } else {
                //go through every item in the maps to check if they are the same
                for (LinkedList<Mapping<K, V>> list : table) {
                    if (list != null) {
                        for (Mapping<K, V> entry : list) {
                            K key = entry.getKey();
                            V value = entry.getValue();
                            //if the keys do not match, they can't be equal so break out of the loop
                            if (!otherMap.containsKey(key) || !value.equals(otherMap.lookup(key))) {
                                isEqual = false;
                                break;
                            }
                        }
                        //as soon as the boolean value initialized at the beginning as true gets set to false
                        //break out of the loop because there's no need to search anymore
                        if (!isEqual) {
                            break;
                        }
                    }
                }
            }
        }
        return isEqual;
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
                    //so long as the current index isn't null and it is also less than the size of the table, there
                    //are more elements
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
        //if the key is either null or already in the map, throw an exception
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        } else if (containsKey(key)) {
            throw new DuplicateKeyException("Key already in map");
        }

        //find location to place the new item in by calling the hachCode() function
        int index = Math.abs(key.hashCode()) % capacity;
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        table[index].add(new Mapping<>(key, value));
        size++;

        //if the table size gets over the desired value, resize
        if ((double) size / capacity > 1.0) {
            resizeTable();
        }
    }

    private void resizeTable() {
        //fins the closest larger prime number
        int newCapacity = PrimeNumberGenerator.nextPrime(capacity);
        LinkedList<Mapping<K, V>>[] newTable = new LinkedList[newCapacity];

        //go through the entire map and copy contents into the new one
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
        V result = null;

        // Find the hashCode to which the key would be stored
        int index = Math.abs(key.hashCode()) % capacity;
        LinkedList<Mapping<K, V>> list = table[index];

        // Search through that list until the item is either found or it isn't
        if (list != null) {
            for (Mapping<K, V> entry : list) {
                if (entry.getKey().equals(key)) {
                    result = entry.getValue();
                    break;
                }
            }
        }
        return result;
    }

    public boolean containsKey(K key) {
        return lookup(key) != null;
    }

    public void erase(K key) {
        //find the hashCode to which the key would be stored

        int index = Math.abs(key.hashCode()) % capacity;

        //search through that list until the item is found,then get rid of it and decrement the size
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
        //go through every index in the map and delete everything
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