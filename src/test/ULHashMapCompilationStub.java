package test;

import java.util.Iterator;

import tictactoe.ULHashMap;


public class ULHashMapCompilationStub {

	public static void main(String[] args) {
		
		ULHashMap<String, Integer> aMap = new ULHashMap<>();
		ULHashMap<String, Integer> bMap = new ULHashMap<>(128);
		ULHashMap<String, Integer> cMap = aMap.clone();
		boolean equals = aMap.equals(bMap);
		
		
		aMap.insert("hello", 7);
		bMap.put("hello", 8);
		Integer intValue = aMap.lookup("hello");
		boolean contains = cMap.containsKey("hello");
		bMap.erase("hello");
		bMap.clear();
		int cSize = cMap.size();
		boolean empty = aMap.empty();
		int tableSize = bMap.tableSize();
		
		Iterator<ULHashMap.Mapping<String, Integer>> iter = aMap.iterator();
		boolean hasNext = iter.hasNext();
		ULHashMap.Mapping<String, Integer> aMapping = iter.next();
		
		for(ULHashMap.Mapping<String, Integer> mapping : aMap) {
			String key = mapping.getKey();
			Integer value = mapping.getValue();
		}
	}
}
