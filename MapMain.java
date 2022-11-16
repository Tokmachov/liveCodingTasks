package com.monkeyhide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MapMain {

    public static void main(String[] args) {
        Map<String, Integer> map = new Map<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put("Four", 4);
        map.put("Five", 5);
        map.put("Six", 6);
        map.put("Seven", 7);
        System.out.println(map);
        map.put("One", 11);
        map.put("Two", 22);
        map.put("Three", 33);
        map.put("Four", 44);
        map.put("Five", 55);
        map.put("Six", 66);
        map.put("Seven", 77);
        System.out.println(map);
        System.out.println("----------get test----------");
        System.out.println("get by Key one: " + map.get("One"));
        System.out.println("get by Key two: " + map.get("Two"));
        System.out.println("get by Key three: " + map.get("Three"));
        System.out.println("get by Key four: " + map.get("Four"));
        System.out.println("get by non existing Key : " + map.get("42"));
    }
}

class Map<K, V> {
    private static class Entry<K, V> {
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        private K key;
        private V value;

        @Override
        public String toString() {
            return "{" + key +
                    ", " + value +
                    '}';
        }
    }
    private static int STORAGE_SIZE = 10;
    private List<List<Entry<K, V>>> storage = new LinkedList<>();
    {
        for (int i = 0; i < STORAGE_SIZE; i++) {
            storage.add(new ArrayList<>());
        }
    }

    public V put(K key, V value) {
        int index = key.hashCode() % STORAGE_SIZE;
        List<Entry<K,V>> entryList = storage.get(index);
        Entry<K, V> foundEntry = null;
        for (Entry<K, V> entry : entryList) {
            if (entry.getKey().hashCode() == key.hashCode()) {
                foundEntry = entry;
            }
        }
        if (foundEntry == null) {
            entryList.add(new Entry<>(key, value));
            return null;
        } else {
            V prevValue = foundEntry.getValue();
            foundEntry.setValue(value);
            return prevValue;
        }
    }

    public V get(K key) {
        int index = key.hashCode() % STORAGE_SIZE;
        List<Entry<K,V>> entryList = storage.get(index);
        Entry<K, V> foundEntry = null;
        for (Entry<K, V> entry : entryList) {
            if (entry.getKey().hashCode() == key.hashCode()) {
                foundEntry = entry;
            }
        }
        return foundEntry != null ? foundEntry.getValue() : null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Entry<K, V>> entryList : storage) {
            stringBuilder.append(entryList);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}

