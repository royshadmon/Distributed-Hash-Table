import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Node {

    private int nodeId;
    private HashMap<Integer, Node> fingerTable = new HashMap<>();
    private Map<Integer, Integer> map = new LinkedHashMap<>();

    private int m = 3;

    Node(int id) {
        System.out.println(id);
        nodeId = hash(id);
        System.out.println(nodeId);
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

    private void initializeFirstNode() {
        for (int i = 0; i < m; i++) {
            this.fingerTable.put((this.nodeId + 2^i) % (2^m), this);
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

    protected int hash(int number) { return number & 0xff; }



}