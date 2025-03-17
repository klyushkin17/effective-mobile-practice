package huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class HuffmanAlgorithm {

    public static void main (String[] args) {
        String stringToCompress = "Once upon a younger year";

        TreeMap<Character, Integer> symbolFrequencyMap = countFrequency(stringToCompress);

        ArrayList<SymbolCodeTreeNode> symbolCodeTreeNodesList = new ArrayList<>();

        for (Character symbol : symbolFrequencyMap.keySet()) {
            symbolCodeTreeNodesList.add(new SymbolCodeTreeNode(symbol, symbolFrequencyMap.get(symbol)));
        }

        SymbolCodeTreeNode symbolCodeTree = createHuffmanTree(symbolCodeTreeNodesList);

        TreeMap<Character, String> huffmanCodesMap = new TreeMap<>();

        for (Character symbol : symbolFrequencyMap.keySet()) {
            huffmanCodesMap.put(symbol, symbolCodeTree.getSymbolCode(symbol, ""));
        }

        System.out.println(huffmanCodesMap);

        StringBuilder encodedString = new StringBuilder();
        for (int i = 0; i < stringToCompress.length(); i++) {
            encodedString.append(huffmanCodesMap.get(stringToCompress.charAt(i)));
        }

        System.out.println(encodedString);
    }

    public static SymbolCodeTreeNode createHuffmanTree(ArrayList<SymbolCodeTreeNode> symbolCodeTreeNodeList) {
        while (symbolCodeTreeNodeList.size() > 1) {
            Collections.sort(symbolCodeTreeNodeList);

            SymbolCodeTreeNode leftChild = symbolCodeTreeNodeList.removeLast();
            SymbolCodeTreeNode rightChild = symbolCodeTreeNodeList.removeLast();

            SymbolCodeTreeNode parent = new SymbolCodeTreeNode(
                null,
                leftChild.weight + rightChild.weight,
                leftChild,
                rightChild
            );

            symbolCodeTreeNodeList.add(parent);
        }

        return symbolCodeTreeNodeList.getFirst();
    }

    public static TreeMap<Character, Integer> countFrequency(String string) {
        TreeMap<Character, Integer> charactersFrequencyMap = new TreeMap<>();

        for (int i = 0; i < string.length(); i++) {
            Character symbol = string.charAt(i);
            charactersFrequencyMap.compute(symbol, (k, symbolFrequency) -> symbolFrequency != null ? symbolFrequency + 1 : 1);
        }

        return charactersFrequencyMap;
    }
}

