import com.sun.istack.internal.Nullable;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleNode implements ChordNode {
    private int nodeId;
    private SimpleNode successor;
    private Map<Integer, Integer> map = new LinkedHashMap<>();

    SimpleNode(int id) {
        System.out.println(id);
        nodeId = hash(id);
        System.out.println(nodeId);
    }

    public int getId() { return nodeId; }

    public void setId(int id) { this.nodeId = id; }

    private SimpleNode getSuccessor() {
        return this.successor;
    }

    private void setSuccessor(SimpleNode successor) {
        this.successor = successor;
    }

    public int find(int keyId) {
        int key = hash(keyId);
        /*
         * Finds the node which has the key and returns that node's node Id.
         */
        return 0;
    }

    @Nullable public void join(SimpleNode node) {
        /*
            If node is null, and that means network has to be initialized (fingertable init).
            There are ways to do this mentioned in the paper apparently.
        */

        if (node != null) {
            SimpleNode nodeI = node;
            SimpleNode nodePrev = node;
            //System.out.println("ID: " + this.nodeId);
            while (nodeI.successor.nodeId > this.nodeId && nodeI.nodeId < this.nodeId) {
                nodePrev = nodeI;
                nodeI = nodeI.successor;
            }

            SimpleNode newSuccessor = nodeI.successor;
            this.successor = nodeI;
            nodePrev.successor = this;



        }
        else {
            this.successor = this;
        }

    }

    public void insert(int keyId) {
        /*
         *
         * Insert the key into the correct node.
         *
         * */

        int key = hash(keyId);

        // Somehow find the correct node Id and then insert there

    }

    public void remove(int key) {
        int keyId = hash(key);
    }

    public void prettyPrint() {
        System.out.println("Node ID: " + nodeId);
    }

    protected int hash(int number) { return number & 0xff; }
}
