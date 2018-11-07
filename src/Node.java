import java.util.HashMap;

public class Node implements ChordNode {

    private int nodeId;
    private FingerTable table;
    private HashMap<Integer, Integer> map = new HashMap<>();

    Node(byte id) {
        System.out.println(id);
        nodeId = hash(id);
        System.out.println(nodeId);
        table = null;
    }

    public int getId() { return nodeId; }

    public void setId(byte id) { this.nodeId = hash(id); }

    public void setId(int id) { this.nodeId = id; }

    public void find() { }

    public void join(Node node) {
        /*
            If node is null, and that means network has been initialized.
            There are ways to do this mentioned in the paper apparently.
        */
    }

    public void insert(byte keyId, byte val) {
        int key = hash(keyId);
        int value = hash(val); // Yes we need to

        map.put(key, value);

        // Lots of extra stuff to be done
    }

    public void remove(byte key) {
        int keyId = hash(key);
    }

    public void prettyPrint() {
        System.out.println("Node ID: " + nodeId);
        this.table.prettyPrint();
    }

    private int hash(byte number) {
        /*
            Input should be unsigned integer. So I'm just converting the byte to an unsigned integer.
            May not be required/Ask TA
         */

        return number & 0xff;
    }

    public static void main(String[] args) {

        ChordNode node = new Node((byte) 0);

    }
}