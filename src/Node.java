import java.util.LinkedHashMap;
import java.util.Map;

public class Node implements ChordNode {

    private int nodeId;
    private FingerTable table;
    private Map<Integer, Integer> map = new LinkedHashMap<>();

    Node(int id) {
        System.out.println(id);
        nodeId = hash(id);
        System.out.println(nodeId);
        table = null;
    }

    public int getId() { return nodeId; }

    public void setId(int id) { this.nodeId = id; }

    public int find(int keyId) {
        int key = hash(keyId);
        /*
         * Finds the node which has the key and returns that node's node Id.
         */
        return 0;
    }

    public void join(Node node) {
        /*
            If node is null, and that means network has to be initialized (fingertable init).
            There are ways to do this mentioned in the paper apparently.
        */
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
        this.table.prettyPrint();
    }

    protected int hash(int number) { return number & 0xff; }
}