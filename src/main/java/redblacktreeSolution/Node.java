package redblacktreeSolution;

public class Node {
    private int value;
    private Color color;
    private Node leftChild;
    private Node rightChild;
    private Node parent;

    public Node (int value) {
        this.value = value;
    }
    public Node (int value, Color color) {
        this.value = value;
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Node getBrother() {
        Node parentNode = getParent();
        if (parentNode.getRightChild() != this) {
            return parentNode.getRightChild();
        }
        if (parentNode.getLeftChild() != this) {
            return parentNode.getLeftChild();
        }
        return null;
    }

    public void setRightChild(Node node) {
        this.rightChild = node;
    }

    public void setLeftChild(Node node) {
        this.leftChild = node;
    }

    public void setChild(int value) {
        if (value > this.value) {
            setRightChild(new Node(value, Color.RED));
        }
        else setLeftChild(new Node(value, Color.RED));
    }

    public Node getChild(int value) {
        if (value > this.value) {
            return getRightChild();
        }
        else return getLeftChild();
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public int getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
