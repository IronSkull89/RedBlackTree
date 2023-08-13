package redblacktreeSolution;

public class RedBlackTree {
    private Node root;
    private BalanceStatus balance;

    public RedBlackTree (int value) {
        root = new Node(value, Color.BLACK);
        balance = BalanceStatus.balanced;
    }

    public void add(int value) {
        climb(root, value);
        balance = BalanceStatus.balanced;
    }

    private void climb(Node node, int value) {
        if (node.getChild(value) == null) {
            node.setChild(value);
            node.getChild(value).setParent(node);
            if (node.getColor() == Color.RED) {
                balance = BalanceStatus.unbalanced;
            }
        } else {
            climb(node.getChild(value), value);
        }
        if (balance == BalanceStatus.unbalanced && node.getChild(value).getColor() == Color.RED && node.getColor() == Color.RED) {
            rebalanced(node.getChild(value));
        }
    }

    private void rebalanced(Node node) {
        Node parentNode = node.getParent();
        Node uncleNode = parentNode.getBrother();

        if (uncleNode == null || uncleNode.getColor() == Color.BLACK) turn(node);
        else swap(parentNode, parentNode.getParent(), uncleNode);
    }

    private void turn(Node node) {
        Node parentNode = node.getParent();
        Node grandparentNode = node.getParent().getParent();
        Node grandgrandparentNode = grandparentNode.getParent();

        Branch newBranch;

        if (grandparentNode.getValue() > parentNode.getValue()) {
            newBranch = rightTurn(node);
        }
        else {
            newBranch = leftTurn(node);
        }

        if (grandgrandparentNode != null) {
            if (grandgrandparentNode.getValue() > grandparentNode.getValue()) {
                grandgrandparentNode.setLeftChild(newBranch.parentNode);
            } else grandgrandparentNode.setRightChild(newBranch.parentNode);
            newBranch.parentNode.setParent(grandgrandparentNode);
        }

        newBranch.parentNode.setLeftChild(newBranch.leftNode);
        newBranch.leftNode.setParent(newBranch.parentNode);

        newBranch.parentNode.setRightChild(newBranch.rightNode);
        newBranch.rightNode.setParent(newBranch.parentNode);

        newBranch.parentNode.setColor(Color.BLACK);
        newBranch.leftNode.setColor(Color.RED);
        newBranch.rightNode.setColor(Color.RED);

        if (grandparentNode == root) {
            root = newBranch.parentNode;
            newBranch.parentNode.setParent(null);
        }

        balance = BalanceStatus.balanced;
    }

    private void swap(Node parentNode, Node grandParentNode, Node uncleNode) {
        uncleNode.setColor(Color.BLACK);
        parentNode.setColor(Color.BLACK);
        if (grandParentNode != root) {
            grandParentNode.setColor(Color.RED);
            balance = BalanceStatus.unbalanced;
        } else balance = BalanceStatus.balanced;
    }


    private Branch rightTurn (Node node) {
        Node parentNode = node.getParent();
        Node grandparentNode = parentNode.getParent();

        Branch newBranch = new Branch();

        if (grandparentNode.getValue() > parentNode.getValue()) {
            if (parentNode.getValue() > node.getValue()) {
                newBranch.parentNode = parentNode;
                newBranch.leftNode = node;
                newBranch.rightNode = grandparentNode;
                newBranch.childRightNode = parentNode.getRightChild();

                newBranch = setChildRightNode(newBranch);
            } else {
                newBranch.parentNode = node;
                newBranch.leftNode = parentNode;
                newBranch.rightNode = grandparentNode;
                newBranch.childLeftNode = node.getLeftChild();
                newBranch.childRightNode = node.getRightChild();

                newBranch = setChildLeftNode(newBranch);
                newBranch = setChildRightNode(newBranch);
            }
        }
        return newBranch;
    }

    private Branch leftTurn (Node node) {
        Node parentNode = node.getParent();
        Node grandparentNode = parentNode.getParent();

        Branch branch = new Branch();

        if (parentNode.getValue() < node.getValue()) {
            branch.parentNode = parentNode;
            branch.leftNode = grandparentNode;
            branch.rightNode = node;
            branch.childLeftNode = parentNode.getLeftChild();

            branch = setChildLeftNode(branch);
        }
        else {
            branch.parentNode = node;
            branch.leftNode = grandparentNode;
            branch.rightNode = parentNode;
            branch.childLeftNode = node.getLeftChild();
            branch.childRightNode = node.getRightChild();

            branch = setChildLeftNode(branch);
            branch = setChildRightNode(branch);
        }
        return branch;
    }

    private Branch setChildRightNode (Branch branch) {
        if (branch.childRightNode != null) {
            branch.rightNode.setLeftChild(branch.childRightNode);
            branch.childRightNode.setParent(branch.rightNode);
        } else branch.rightNode.setLeftChild(null);
        return branch;
    }

    private Branch setChildLeftNode (Branch branch) {
        if (branch.childLeftNode != null) {
            branch.leftNode.setRightChild(branch.childLeftNode);
            branch.childLeftNode.setParent(branch.leftNode);
        } else branch.leftNode.setRightChild(null);
        return branch;
    }
}
