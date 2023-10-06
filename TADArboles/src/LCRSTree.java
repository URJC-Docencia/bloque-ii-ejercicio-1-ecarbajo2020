import material.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * An implementation of the NAryTree interface using left-child, right-sibling representation.
 *
 * @param <E> the type of elements stored in the tree
 */
public class LCRSTree<E> extends DrawableTree<E> {

    /**
     * A reference to the root node of the tree.
     */
    private LCRSnode<E> root;

    /**
     * The number of nodes in the tree.
     */
    private int size;


    private class LCRSnode<T> implements Position<T> {

        /**
         * The element stored at this position.
         */
        private T element;

        /**
         * A reference to the parent of this node.
         */
        private LCRSnode<T> parent;

        /**
         * A reference to the left child of this node.
         */
        private LCRSnode<T> leftChild;

        /**
         * A reference to the right sibling of this node.
         */
        private LCRSnode<T> rightSibling;


        /**
         * A constructor that allows you to set all the attributes of the nodes.
         *
         * @param element      the element to store at this position
         * @param parent       the parent of the new node
         * @param leftChild    the left child of the new node
         * @param rightSibling the right sibling of the new node
         */
        public LCRSnode(T element, LCRSnode<T> parent, LCRSnode<T> leftChild, LCRSnode<T> rightSibling) {
            this.element = element;
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightSibling = rightSibling;
        }

        /**
         * A constructor that allows you to set the element to store at this position.
         *
         * @param element the element to store at this position
         */
        public LCRSnode(T element) {
            this(element, null, null, null);
        }

        /**
         * A constructor that allows you to set the element to store at this position and specify its parent node.
         *
         * @param element the element to store at this position
         * @param parent  the parent node of this node
         */
        public LCRSnode(T element, LCRSnode<T> parent) {
            this(element, parent, null, null);
        }

        /**
         * Returns the element stored at this position.
         *
         * @return the stored element
         * @throws IllegalStateException if position no longer valid
         */
        @Override
        public T getElement() {
            return element;
        }

        /**
         * Sets the element stored at this position.
         *
         * @param o the element to store
         */
        public void setElement(T o) {
            element = o;
        }

        /**
         * Returns the parent of this node.
         *
         * @return the parent of this node
         */
        public LCRSnode<T> getParent() {
            return parent;
        }

        /**
         * Sets the parent of this node.
         *
         * @param parent the parent of this node
         */
        public void setParent(LCRSnode<T> parent) {
            this.parent = parent;
        }

        /**
         * Returns the left child of this node.
         *
         * @return the left child of this node
         */
        public LCRSnode<T> getLeftChild() {
            return leftChild;
        }

        /**
         * Sets the left child of this node.
         *
         * @param leftChild the left child of this node
         */
        public void setLeftChild(LCRSnode<T> leftChild) {
            this.leftChild = leftChild;
        }

        /**
         * Returns the right sibling of this node.
         *
         * @return the right sibling of this node
         */
        public LCRSnode<T> getRightSibling() {
            return rightSibling;
        }

        /**
         * Sets the right sibling of this node.
         *
         * @param rightSibling the right sibling of this node
         */
        public void setRightSibling(LCRSnode<T> rightSibling) {
            this.rightSibling = rightSibling;
        }

        /**
         * Returns a string representation of the node.
         *
         * @return a string representation of the node
         */
        @Override
        public String toString() {
            return element.toString();
        }

    }


    @Override
    public Position<E> addRoot(E e) {
        if (!isEmpty()) {
            throw new RuntimeException("Tree already has a root");
        }
        this.root = new LCRSnode<>(e);
        this.size = 1;
        return this.root;
    }

    /**
     * Add a new node whose parent is pointed by a given position.
     *
     * @param element The element stored in the new created node.
     * @param p       The position of the parent
     * @return The position of the new node.
     */
    @Override
    public Position<E> add(E element, Position<E> p) {
        LCRSnode<E> parent = checkPosition(p);
        LCRSnode<E> newNode = new LCRSnode<>(element, parent);
        if (parent.getLeftChild() == null) {
            parent.setLeftChild(newNode);
        } else {
            LCRSnode<E> leftChild = parent.getLeftChild();
            while (leftChild.getRightSibling() != null) {
                leftChild = leftChild.getRightSibling();
            }
            leftChild.setRightSibling(newNode);
        }
        this.size++;
        return newNode;
    }

    private LCRSnode<E> checkPosition(Position<E> p) {
        if (!(p instanceof LCRSnode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (LCRSnode<E>) p;
    }


    /**
     * Add a new node whose parent is pointed by a given position, and set the
     * child at the position n if possible.
     *
     * @param element The element stored in the new created node.
     * @param p       The position of the parent
     * @param n       The position of the child
     * @return The position of the new node.
     */
    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        LCRSnode<E> parent = checkPosition(p);
        LCRSnode<E> newNode = new LCRSnode<>(element, parent);
        if (n < 0) {
            throw new RuntimeException("The position is invalid");
        } else if (n == 0) {
            newNode.setRightSibling(parent.getLeftChild());
            parent.setLeftChild(newNode);
        } else {
            LCRSnode<E> leftChild = parent.getLeftChild();
            int i = 1;
            while (i < n && leftChild.getRightSibling() != null) {
                leftChild = leftChild.getRightSibling();
                i++;
            }
            newNode.setRightSibling(leftChild.getRightSibling());
            leftChild.setRightSibling(newNode);
        }
        this.size++;
        return newNode;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        var node1 = checkPosition(p1);
        var node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        var node = checkPosition(p);
        E old = node.getElement();
        node.setElement(e);
        return old;

    }


    @Override
    public void remove(Position<E> p) {
        LCRSnode<E> node = checkPosition(p);
        if (node == root) {
            root = null;
            size = 0;
        } else {
            LCRSnode<E> parent = node.getParent();
            if (parent.getLeftChild() == node) {
                parent.setLeftChild(node.getRightSibling());
            } else {
                LCRSnode<E> leftChild = parent.getLeftChild();
                while (leftChild.getRightSibling() != node) {
                    leftChild = leftChild.getRightSibling();
                }
                leftChild.setRightSibling(node.getRightSibling());
            }
            // Now we have to compute the size of the subtree rooted at the removed node
            size -= computeSize(node);

        }
    }

    /**
     * Compute the size of the subtree rooted at the given node.
     *
     * @param node The root node of the subtree.
     * @return The size of the subtree.
     */
    private int computeSize(LCRSnode<E> node) {
        if (node == null) {
            return 0;
        } else {
            int size = 1;
            LCRSnode<E> child = node.getLeftChild();
            while (child != null) {
                size += computeSize(child);
                child = child.getRightSibling();
            }
            return size;
        }
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        var node = checkPosition(v);
        var tree = new LCRSTree<E>();
        tree.root = node;
        tree.size = computeSize(node);
        return tree;

    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        var node = checkPosition(p);
        var tree = (LCRSTree<E>) t;
        var leftChild = node.getLeftChild();
        // if leftChild is null, then we set it directly
        if (leftChild == null) {
            node.setLeftChild(tree.root);
        } else {
            // otherwise, we have to get the first child
            // of the tree and set it as the right sibling of the left child
            while (leftChild.getRightSibling() != null) {
                leftChild = leftChild.getRightSibling();
            }
            leftChild.setRightSibling(tree.root);
        }
        this.size += tree.size;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public Position<E> root() {
        return this.root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        var node = checkPosition(v);
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        var node = checkPosition(v);
        var list = new ArrayList<Position<E>>();
        var child = node.getLeftChild();
        while (child != null) {
            list.add(child);
            child = child.getRightSibling();
        }
        return list;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        var node = checkPosition(v);
        return node.getLeftChild() != null;
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        var node = checkPosition(v);
        return node.getLeftChild() == null;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        var node = checkPosition(v);
        return node == this.root;
    }


    /**
     * Return an iterator of all elements stored at the nodes of the tree.
     * The iterator traverses the tree in breadth-first order.
     * @return an iterator of the tree's elements
     */
    @Override
    public Iterator<Position<E>> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator<Position<E>> iteratorPreOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public Iterator<Position<E>> iteratorPostOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void breadthFirstTraversal(LCRSnode<E> root, List<Position<E>> positions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void postOrderTraversal(LCRSnode<E> root, List<Position<E>> positions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void preOrderTraversal(LCRSnode<E> node, List<Position<E>> positions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public int size() {
        return this.size;
    }

}
