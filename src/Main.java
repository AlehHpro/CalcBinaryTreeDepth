import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        int[] values = new int[] { 3, 6, 8, 43, 21, 98 };

        Node tree = buildTree(values);
        //System.out.println(tree.calculateDepth());
        System.out.println(tree.calculateDepthAlt());
        System.out.println(tree.calculateDepthWithStack());

    }

    public static Node buildTree(int[] arr) {
        Node root = new Node(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            //root.addNode(arr[i]);
            root.addNodeAlt(arr[i]);
        }
        return root;
    }

    public static class Node {
        int value;
        Node left;
        Node right;
        int nodeDepth;

        public Node(int value) {
            this.value = value;
        }

        public void addNode(int value) {
            if (this.value > value) {
                if (this.left == null) {
                    this.left = new Node(value);
                } else {
                    this.left.addNode(value);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(value);
                } else {
                    this.right.addNode(value);
                }
            }

        }

        //every recursion can be written as cycle, but not vice versa
        public void addNodeAlt(int value) {
            boolean insertCompleted = false;
            Node currNode = this;
            while (!insertCompleted) {
                if (value < currNode.value) {
                    if (currNode.left == null) {
                        currNode.left = new Node(value);
                        insertCompleted = true;
                    } else {
                        currNode = currNode.left;
                    }
                } else {
                    if (currNode.right == null) {
                        currNode.right = new Node(value);
                        insertCompleted = true;
                    } else {
                        currNode = currNode.right;
                    }
                }
            }

        }

        public int calculateDepth() {
            int depth = 0;
            int leftDepth = 0;
            int rightDepth = 0;

            if (this.left != null) {
                leftDepth = this.left.calculateDepth();
            }
            if (this.right != null) {
                rightDepth = this.right.calculateDepth();
            }

            if (leftDepth > rightDepth) {
                depth = leftDepth + 1;
            } else {
                depth = rightDepth + 1;
            }

            return depth;
        }

        public int calculateDepthAlt() {
            int depth = 0;
            Deque<Node> myQueue = new ArrayDeque<>();
            myQueue.add(this);

            while (!myQueue.isEmpty()) {
                Deque<Node> nextQueue = new ArrayDeque<>();
                depth++;
                for (Node node : myQueue) {
                    if (node.left != null) {
                        nextQueue.add(node.left);
                    }
                    if (node.right != null) {
                        nextQueue.add(node.right);
                    }
                }
                myQueue = nextQueue;
            }

            return depth;
        }

        public int calculateDepthWithStack() {
            //int depthCurr = 0;
            int depthMax = 0;
            Deque<Node> myStack = new ArrayDeque<>();
            this.nodeDepth = 1;
            myStack.push(this);

            while(!myStack.isEmpty()) {
                Node currentNode = myStack.pop();
                if(currentNode.right != null) {
                    currentNode.right.nodeDepth = currentNode.nodeDepth + 1;
                    myStack.push(currentNode.right);
                }
                if(currentNode.left != null) {
                    currentNode.left.nodeDepth = currentNode.nodeDepth + 1;
                    myStack.push(currentNode.left);
                }
                if(currentNode.nodeDepth > depthMax) {
                    depthMax = currentNode.nodeDepth;
                }
            }

            return depthMax;
        }

    }

}
