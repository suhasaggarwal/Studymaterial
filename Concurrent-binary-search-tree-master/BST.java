package algorithms;

import main.BSTInterface;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;

public class BST implements BSTInterface {

    private class Node {
        volatile Node left, right;
        int key;
        volatile boolean marked = false;
        private final Lock lock = new ReentrantLock();

        public Node(int key) {
            this.key = key;
            this.left = this.right = null;
        }

        @Override
        public String toString() {
            return "" + key + " left " + (this.left == null ? "null" : this.left.key)+ " right " + (this.right == null ? "null" : this.right.key);
        }
    }

    private volatile Node head = null;
    private final Node sentinel = new Node(0);

    public BST() {
    }

    private Node[] findPlaceToInsert(final int key){
        Node parent_first_iter = null;
        while(true) {
            if(this.head == null) {return new Node[]{sentinel,null};}
            Node curr = this.head, parent = null;

            while (curr != null) {
                if (key == curr.key && !curr.marked) {
                    return new Node[]{parent,curr};
                }
                parent = curr;

                if (key < curr.key) {
                    curr = curr.left;
                } else if (key > curr.key) {
                    curr = curr.right;
                } else if (curr.marked){
                    break;
                }
            }

            if (curr != null && curr.marked){
                continue;
            }
            if(parent != null && parent_first_iter != null && parent_first_iter == parent){
                return new Node[]{parent,curr};
            }else {
                parent_first_iter = parent;
            }
        }
    }

    public final boolean contains(final int key) {
        Node[] parent_curr = findPlaceToInsert(key);
        if(parent_curr[1] == null) {return false;}
        return true;
    }

    public final boolean insert(final int key) {
        while (true) {
            Node curr = this.head, parent = null;

            if (this.head == null) {
                sentinel.lock.lock();
                try {
                    if (this.head == null) {
                        this.head = new Node(key);
                        return true;
                    } else {
                        curr = this.head;
                    }
                } finally {
                    sentinel.lock.unlock();
                }

            }

            Node[] parent_curr= findPlaceToInsert(key);
            if(parent_curr[1] != null) {return false;}
            if(parent_curr[0] == sentinel) {continue;}
            parent = parent_curr[0];
            int intermediate_parent_key = parent.key;
            parent.lock.lock();
            try {
                if (parent.marked == false && intermediate_parent_key == parent.key ) {
                    if (key < parent.key) {
                        if (parent.left == null) {
                            parent.left = new Node(key);
                            return true;
                        }
                    } else if (key > parent.key) {
                        if (parent.right == null) {
                            parent.right = new Node(key);
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            } finally {
                parent.lock.unlock();
            }
        }
    }

    public Node[] remove_successor(final int key, boolean is_successor){
        while (true) {
            Node[] parent_curr = findPlaceToInsert(key);
            Node parent = parent_curr[0];
            Node curr = parent_curr[1];
            if (curr == null) {

                return null;
            }

            if (parent != null) {
                int intermediate_parent_key = parent.key, intermediate_node_key = curr.key;
                parent.lock.lock();
                if ((curr.marked == false && parent.marked == false) &&
                        (parent.left == curr || parent.right == curr)) {
                    curr.lock.lock();
                    if (intermediate_node_key == curr.key && intermediate_parent_key == parent.key)
                        //LEAF
                        if (curr.right == null && curr.left == null) {
                            return new Node[]{parent,curr};
                        }

                        // for successor
                        else if(is_successor && curr.left != null){
                            parent.lock.unlock();
                            curr.lock.unlock();
                            return null;
                        }

                        //One node
                        else if (curr.right == null || curr.left == null) {
                            return new Node[]{parent,curr};

                        }
                    curr.lock.unlock();
                }else{
                    parent.lock.unlock();
                }
            }
        }
    }

    public final boolean remove(final int key) {
        while (true) {
            Node[] parent_curr = findPlaceToInsert(key);
            Node parent = parent_curr[0];
            Node curr = parent_curr[1];
            if (curr == null) {
                return false;
            }

            if (parent != null) {
                int intermediate_parent_key = parent.key, intermediate_node_key = curr.key;
                parent.lock.lock();
                try {
                    if ((curr.marked == false && parent.marked == false) &&
                            (parent.left == curr || parent.right == curr)) {
                        curr.lock.lock();
                        try {
                            if (intermediate_node_key == curr.key && intermediate_parent_key == parent.key)
                                //LEAF
                                if (curr.right == null && curr.left == null) {
                                    curr.marked = true;
                                    if (key < parent.key) {
                                        parent.left = null;
                                    } else {
                                        parent.right = null;
                                    }
                                    return true;
                                }

                                // for successor
                                else if(curr.left != null){
                                    return false;
                                }

                                //One node
                                else if (curr.right == null || curr.left == null) {
                                    curr.marked = true;
                                    if (key < parent.key) {
                                        parent.left = curr.right != null ? curr.right : curr.left;
                                    } else {
                                        parent.right = curr.right != null ? curr.right : curr.left;
                                    }
                                    return true;
                                }

                                //Two nodes
                                else {
                                    //We need to get the successor to the deleted node place, and delete the successor,
                                    //Because it's a reentrant lock, no deadlock will occur
                                    if (handleTwoNodes(key, curr)) return true;
                                }

                        } finally {
                            curr.lock.unlock();
                        }
                    }
                } finally {
                    parent.lock.unlock();
                }
            } else {
                //We want to delete the head
                int intermediate_node_key = curr.key;
                sentinel.lock.lock();
                try {
                    if (curr.marked == false) {
                        curr.lock.lock();
                        try {
                            if (intermediate_node_key == curr.key) {
                                //LEAF
                                if (curr.right == null && curr.left == null) {
                                    curr.marked = true;
                                    this.head = null;
                                }

                                // for successor
                                else if(curr.left != null){
                                    return false;
                                }

                                //One node
                                else if (curr.right == null || curr.left == null) {
                                    curr.marked = true;
                                    head = curr.right == null ? curr.left : curr.right;
                                }

                                //Two nodes
                                else {
                                    //We need to get the successor to the deleted node place, and delete the successor,
                                    //Because it's a reentrant lock, no deadlock will occur
                                    if (handleTwoNodes(key, curr)) return true;
                                }
                            }
                        } finally {
                            curr.lock.unlock();
                        }
                    }

                } finally {
                    sentinel.lock.unlock();
                }
            }
        }
    }

    private boolean handleTwoNodes(int key, Node curr) {
        int successorKey = findSuccessorKey(curr);
        Node[] parent_curr;
        if ((parent_curr = remove_successor(successorKey, true)) != null) {
            curr.key = successorKey;
            parent_curr[1].marked = true;
            if (key < parent_curr[0].key) {
                if(parent_curr[1].left == null && parent_curr[1].right == null)
                    parent_curr[0].left = null;
                else
                    parent_curr[0].left = parent_curr[1].right != null ? parent_curr[1].right : parent_curr[1].left;
            } else {
                if(parent_curr[1].left == null && parent_curr[1].right == null)
                    parent_curr[0].right = null;
                else
                    parent_curr[0].right = parent_curr[1].right != null ? parent_curr[1].right : parent_curr[1].left;
            }
            parent_curr[0].lock.unlock();
            parent_curr[1].lock.unlock();
            return true;
        }
        return false;
    }

    // root has right child
    int findSuccessorKey(Node root) {
        Node successor = root.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        return successor.key;
    }

    // Return your ID #
    public String getName() {
        return "308106707";
    }

    // Returns size of the tree.
    public final int size() {
        return sizeImp(head);
    }

    // Returns size of the tree.
    public final int sizeImp(Node head) {
        if (head == null)
            return 0;
        else
            return (sizeImp(head.left) + 1 + sizeImp(head.right));
    }

    public final long getKeysum() {
        return getKeysumImp(head);
    }

    // Returns the sum of keys in the tree
    public final long getKeysumImp(Node head) {
        if (head == null)
            return 0;
        else {
            long key = (long) head.key;
            return (getKeysumImp(head.left) + key + getKeysumImp(head.right));
        }
    }
}