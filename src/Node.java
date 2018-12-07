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
        if (node == null) this.initNetwork();
        else {
            this.initFingerTable(node);
            this.updateOthers();
            this.migrateKeys();
        }
    }

    private void initFingerTable(Node node) {

        this.put(1, node.findSuccessor(this.computeStart(1)));

        this.predecessor = this.getSuccessor().predecessor;
        this.getSuccessor().predecessor = this;

        for (int i = 2; i <= FingerTable.MAX_ENTRIES; i++) {
            if (inLeftIncludedInterval(this.getId(), this.computeStart(i), this.get(i-1).getId()))
                this.put(i, this.getFingerTable().get(i-1));

            else {
                Node successor = node.findSuccessor(this.computeStart(i));

                if (inClosedInterval(this.computeStart(i), this.getId(), successor.getId()))
                    this.put(i, this);
                else
                    this.put(i, successor);
            }
        }
    }

    public void updateOthers() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            int updateIndex = computeUpdateIndex(i-1);

            Node pred = this.findPredecessor(updateIndex);

            if (pred.getSuccessor().getId() == updateIndex) pred = pred.getSuccessor();

            pred.updateFingerTable(this, i);

        }
    }

    private void updateFingerTable(Node node, int i) {
        if (node.getId() == this.getId()) return;
        if (inLeftIncludedInterval(this.getId(), node.getId(), this.getFingerTable().get(i).getId())) {
//            System.out.println("Node " + this.getId() + " is getting updated and its precedessor is " + node.getId());
            this.getFingerTable().put(i, node);
            Node pred = this.predecessor;
            pred.updateFingerTable(node, i);
        }
        else if (this.computeStart(i) == node.nodeId) {
//            System.out.println("Node " + this.getId() + " is getting updated with node " + node.getId());
            this.getFingerTable().put(i, node);
            Node pred = this.predecessor;
            pred.updateFingerTable(node, i);
        }
    }

    private void initNetwork() {
        for (int i = 1; i <= FingerTable.MAX_ENTRIES; i++) {
            this.getFingerTable().put(i, this);
        }

        this.predecessor = this;
    }

    /************************************************************************************************
     BASIC FOUNDATIONAL METHODS
     ***********************************************************************************************/

    private Node findSuccessor(int id) {
        return this.findPredecessor(id).getSuccessor();
    }

    private Node findPredecessor(int id) {

        Node predecessor = this;

        while (!inRightIncludedInterval(predecessor.getId(), id, predecessor.getSuccessor().getId())) {
            Node temp = predecessor.findClosestPrecedingFinger(id);

            predecessor = temp;
        }
        return predecessor;
    }

    private Node findClosestPrecedingFinger(int id) {

        for (int i = FingerTable.MAX_ENTRIES; i >= 1 ; i--) {
            Node node = this.getFingerTable().get(i);

            if (inOpenInterval(this.getId(), node.getId(), id)) {
                return node;
            }
        }

        return this;
    }

    /************************************************************************************************
     KEY METHODS
     ***********************************************************************************************/

    private void insert (int keyId) {
        int key = hash(keyId);
        Node node = this.findSuccessor(key);
        node.getKeys().add(key);
    }

    private void migrateKeys() {
        // 1. This function should find the successor of the node, from the finger table,
        // 2. Update the successor's key set to remove keys it should no longer manage.
        // 3. Add those keys to this node's key set

        // Should work even when there are no keys in the system

        List<Integer> newKeys = this.getSuccessor().updateKeys(this.getId());

        if (newKeys.size() != 0) {
            System.out.println("Adding them to new node " + this.getId());
            this.getKeys().addAll(newKeys);
            System.out.println("----------------------");
            System.out.println();
        }
    }

    private List<Integer> updateKeys(int id) {
        List<Integer> removedKeys = new ArrayList<>();

        for (int i=0; i < this.getKeys().size(); i++) {
            /* if the key is less than or equal to the id, but not equal to the current node's id,
             it should be removed from this node's keyset */
            int key = this.getKeys().get(i);

            if (inRightIncludedInterval(this.getId(), key, id)) {
                System.out.println("Updating keys of Node " + this.getId());
                System.out.println();
                System.out.println("Removing key with id: " + key);

                removedKeys.add(key);
                this.getKeys().remove(i);
                i--;
            }

        }

        return removedKeys;
    }


    /************************************************************************************************
     HELPER METHODS
     ***********************************************************************************************/

    private Node getSuccessor() {
        return this.getFingerTable().get(1);
    }

    private int computeStart(int entryNumber) {
        return FingerTable.computeStart(this.getId(), entryNumber);
    }

    private int computeUpdateIndex(int index) {
        int result = this.getId() - (int) Math.pow(2, index) + FingerTable.MOD;
        result = result % FingerTable.MOD;
        return result;
    }

    private void prettyPrint() {
        this.table.prettyPrint();
    }

    public String toString() {
        return "" + this.getId();
    }

    private void put(int entryNumber, Node node) {
        this.getFingerTable().put(entryNumber, node);
    }

    private Node get(int entryNumber) {
        return this.getFingerTable().get(entryNumber);
    }

    /************************************************************************************************
     MAIN METHOD
     ***********************************************************************************************/

    public static void main(String[] args) {
        Node node0 = new Node(255);
        node0.join(null);

//        node0.insert(0);

        Node node1 = new Node(2);
        node1.join(node0);

        Node node2 = new Node(4);
        node2.join(node1);

        Node node3 = new Node(8);
        node3.join(node2);

        Node node4 = new Node(16);
        node4.join(node3);

        Node node5 = new Node(32);
        node5.join(node4);

        Node node6 = new Node(67);
        node6.join(node5);

        Node node7 = new Node(128);
        node7.join(node6);

        Node node8 = new Node(129);
        node8.join(node0);

        Node node9 = new Node(254);
        node9.join(node7);

        node9.insert(0);

        Node node10 = new Node(0);
        node10.join(node9);

        node0.prettyPrint();
        node1.prettyPrint();
        node2.prettyPrint();
        node3.prettyPrint();
        node4.prettyPrint();
        node5.prettyPrint();
        node6.prettyPrint();
        node7.prettyPrint();
        node8.prettyPrint();
        node9.prettyPrint();
        node10.prettyPrint();

        node5.insert(255);

        System.out.println(node0.getKeys().get(0));
        System.out.println(node10.getKeys().get(0));
    }

    /************************************************************************************************
     PRIVATE STATIC HELPER FUNCTIONS
     ***********************************************************************************************/

    private static int hash(int number) { return number & 0xff; }

    private static boolean inClosedInterval(int a, int c, int b) {

        a = a % FingerTable.MOD;
        b = b % FingerTable.MOD;
        c = c % FingerTable.MOD;

        if (a <= b) return (a <= c && c <= b);
        else return a <= c || c <= b;
    }

    private static boolean inOpenInterval(int a, int c, int b) {
        return inClosedInterval(a+1, c,b-1);
    }

    private static boolean inLeftIncludedInterval(int a, int c, int b) {
        return inClosedInterval(a, c, b-1);
    }

    private static boolean inRightIncludedInterval(int a, int c, int b) {
        return inClosedInterval(a+1, c, b);
    }
}
