package huffman;

import org.jetbrains.annotations.NotNull;

public class SymbolCodeTreeNode implements Comparable<SymbolCodeTreeNode> {

    Character symbol;
    int weight;
    SymbolCodeTreeNode leftChild;
    SymbolCodeTreeNode rightChild;

    public SymbolCodeTreeNode(Character symbol, int weight) {
        this.symbol = symbol;
        this.weight = weight;
    }

    public SymbolCodeTreeNode(
        Character symbol,
        int weight,
        SymbolCodeTreeNode leftChild,
        SymbolCodeTreeNode rightChild
    ) {
        this.symbol = symbol;
        this.weight = weight;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public String getSymbolCode (Character symbolToFind, String resultPath) {
        if (symbol == symbolToFind) {
            return resultPath;
        } else {
            if (leftChild != null) {
                String path = leftChild.getSymbolCode(symbolToFind, resultPath + 0);
                if (path != null) return path;
            }
            if (rightChild != null) {
                String path = rightChild.getSymbolCode(symbolToFind, resultPath + 1);
                if (path != null) return path;
            }
        }

        return null;
    }

    @Override
    public int compareTo(@NotNull SymbolCodeTreeNode o) {
        return o.weight - weight;
    }
}
