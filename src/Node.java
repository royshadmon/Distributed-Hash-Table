import java.util.ArrayList;
import java.util.List;

public class Node {

    private int nodeId;
    private Node predecessor;
    private FingerTable table;
    private List<Integer> keys;

    Node (int nodeId) {
        this.nodeId = hash(nodeId);
        this.table = new FingerTable(nodeId);
        this.keys = new ArrayList<>();
        this.predecessor = null;
    }

    public int getId() {
        return this.nodeId;
    }

    public FingerTable getFingerTable() {
        return this.table;
    }

    public List<Integer> getKeys() {
        return this.keys;
    }

    private void join(Node node) {
        System.out.println("Node " + this.getId() + " is joining");

        if (node == null) {
            this.initNetwork();
        } else {
            this.initFingerTable(node);
            this.update_others();
        }
    }

    public void update_others() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            System.out.println("compute upate index " + compute_update_index(i-1));
            Node pred;
            if (this.getId() == this.findSuccessor(this.compute_update_index(i)).getId()) {
                pred = this;
            }
            else {
                pred = this.findPredecessor(compute_update_index(i-1));
            }
            pred.update_finger_table(this, i, this);

        }
    }

    private void update_finger_table(Node node, int i, Node orig) {

        if (this.getId() == orig.getId()) return;
        if (inInterval(this.getId() + 1, node.getId(), this.getFingerTable().get(i).getId())) {
            System.out.println("Node " + this.getId() + " is getting updated in its entry number "+ i);
            this.getFingerTable().put(i, orig);
            node = this.predecessor;
            node.update_finger_table(this, i, orig);
        }
    }

    public int compute_update_index (int index) {
        return (this.getId() - (int)Math.pow(2, index) + FingerTable.MOD) % FingerTable.MOD;
    }


    private void initFingerTable(Node node) {
        this.getFingerTable().put(1, node.findSuccessor(computeStart(1)));
        this.predecessor = this.getSuccessor().predecessor;
        this.getSuccessor().predecessor = this;

        for (int i = 1; i <= FingerTable.MAX_ENTRIES - 1; i++) {
            if (inInterval(this.getId() + 1, computeStart(i+1), this.getFingerTable().get(i).getId())) {
                this.getFingerTable().put(computeStart(i), this.getFingerTable().get(i));
            } else {
                this.getFingerTable().put(i+1, node.findSuccessor(computeStart(i)));
            }
        }
    }

    private void initNetwork() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            this.getFingerTable().put(i, this);
        }

        this.predecessor = this;
    }

    private Node findSuccessor(int id) {
        if (this.getId() == id) return this.getSuccessor();

        else return this.findPredecessor(id).getSuccessor();
    }

    private Node findPredecessor(int id) {
        Node predecessor = this;

//        if (this.getSuccessor() == this) return this; // This means that I am the only node in the network
//
//        if (predecessor.getId() == id) {
//            return predecessor.getSuccessor().findPredecessor(id);
//        }

        while (!inInterval(predecessor.getId(), id, predecessor.getSuccessor().getId() + 1)) {
            Node temp = predecessor.findClosestPrecedingFinger(id);

            if (predecessor.getId() == temp.getId()) {
                return this.predecessor;
            }
            predecessor = temp;
        }
        return predecessor;
    }

    private Node findClosestPrecedingFinger(int nodeId) {
        for (int i = FingerTable.MAX_ENTRIES; i >= 1 ; i--) {
            Node node = this.getFingerTable().get(i);

            if (inInterval(this.getId(), node.getId(), nodeId)) {
                return node;
            }
        }

        return this;
    }

    private Node getSuccessor() {
        return this.getFingerTable().get(1);
    }

    private int computeStart(int entry) { return (this.getId() + (int) Math.pow(2, entry)) % FingerTable.MOD; }

    private void prettyPrint() {
        this.table.prettyPrint();
    }

    /************************************************************************************************
     MAIN METHOD
     ***********************************************************************************************/

    public static void main(String[] args) {
        Node node0 = new Node(0);
        Node node2 = new Node(2);
        Node node7 = new Node(7);

        node0.predecessor = node7;
        node2.predecessor = node0;
        node7.predecessor = node2;

        node0.table.put(1, node2);
        node0.table.put(2, node2);
        node0.table.put(3, node7);

        node2.table.put(1, node7);
        node2.table.put(2, node7);
        node2.table.put(3, node7);

        node7.table.put(1, node0);
        node7.table.put(2, node2);
        node7.table.put(3, node7);

        Node node5 = new Node(5);
        Node node3 = new Node(3);

        node5.join(node2);
//        node0.prettyPrint();
        node2.prettyPrint();
//        node3.prettyPrint();
//        node5.prettyPrint();
        node7.prettyPrint();


        node3.join(node0);

        //System.out.println(node5.predecessor.getId());
//        node0.prettyPrint();
        node2.prettyPrint();
//        node3.prettyPrint();
//        node5.prettyPrint();
//        node7.prettyPrint();
    }

    /************************************************************************************************
     PRIVATE STATIC HELPER FUNCTIONS
     ***********************************************************************************************/

    private static int hash(int number) { return number & 0xff; }

    private static boolean inInterval(int start, int nodeId, int end) {
        if (start == end) return false;

        if (start > end) return (start < nodeId || nodeId < end);
        else return start < nodeId && nodeId < end;
    }
}
