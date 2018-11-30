import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node {

    private int nodeId;
    private FingerTable table;
    private static int m = FingerTable.MAX_ENTRIES;

    private List<Integer> keys;

    Node(int id) {
        this.nodeId = hash(id);
        this.table = new FingerTable(this.nodeId);
        this.keys = new ArrayList<>();
    }

    public void join(Node node) {
        if (node == null) {
            System.out.println("Initialized Network");
            System.out.println("Node " + this.getId() + " has joined");
            System.out.println("----------------------");
            this.initNetwork();
        }
        else {
            this.initializeNode(node);
            System.out.println("Node " + this.getId() + " has joined");
            System.out.println("Migrating keys to node " + this.getId());
            System.out.println();

            this.migrateKeys();
        }
    }

    private void migrateKeys() {
        // 1. This function should find the successor of the node, from the finger table,
        // 2. Update the successor's key set to remove keys it should no longer manage.
        // 3. Add those keys to this node's key set

        // Should work even when there are no keys in the system

        List<Integer> newKeys = this.getSuccessor().updateKeys(this.getId());
        System.out.println("Adding them to new node " + this.getId());
        this.getKeys().addAll(newKeys);
        System.out.println("----------------------");
        System.out.println();
    }

    private List<Integer> updateKeys(int ID) {
        System.out.println("Updating keys of Node " + this.getId());
        System.out.println();
        List<Integer> removedKeys = new ArrayList<>();

        for (int i=0; i < this.getKeys().size(); i++) {
            /* if the key is less than or equal to the ID, but not equal to the current node's ID,
             it should be removed from this node's keyset */
            int key = this.getKeys().get(i);
            if (key <= ID && key !=this.getId()) {
                System.out.println("Removing key with ID: " + key);
                removedKeys.add(key);
                this.getKeys().remove(i);
                i--;
            }

        }
        return removedKeys;
    }

    private void initializeNode(Node node) {
        List<Node> nodeList = node.getActiveNodes();
        nodeList.add(this);
        nodeList.sort(new NodeComparator());

        this.initFingerTable(nodeList);
        this.updateOthers(nodeList);
    }

    private void updateOthers(List<Node> list) { list.forEach(node -> node.updateFingerTable(list)); }

    private void updateFingerTable(List<Node> list) {
        this.initFingerTable(list);
    }

    private void initFingerTable(List<Node> list) {
        for (int i =1; i <= m; i++) {
            int val = FingerTable.hash(this.nodeId, i);
            Node successor = this.findSuccessor(val, list);
            this.table.put(i, successor);
        }
//        this.table.prettyPrint(); // Need this in final submission but commented out for debugging
    }

    private Node findSuccessor(int val, List<Node> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getId() == val) return list.get(i); // May not need this condition
            if (list.get(i).getId() < val && val <= list.get(i+1).getId()) return list.get(i+1);
        }
        return list.get(0);
    }

    private List<Node> getActiveNodes(){
        List<Node> list = new ArrayList<>();

        Node temp = this;
        while (temp.getSuccessor() != this) {
            list.add(temp);
            temp = temp.getSuccessor();
        }
        list.add(temp);

        list.sort(new NodeComparator()); //

        return list;
    }

    // initialize Finger Table of first node joining the network
    private void initNetwork() {
        for (int i = 1; i <= m; i++) {
            this.table.put(i, this);
        }
    }

    /************************************************************************************************

       GETTERS AND SETTERS

     ***********************************************************************************************/

    public int getId() { return this.nodeId; }

    public void setId(int id) { this.nodeId = id; }

    public FingerTable getFingerTable() { return this.table; }

    public void setFingerTable(FingerTable fingerTable) { this.table = fingerTable; }

    public List<Integer> getKeys() { return keys; }

    public void setKeys(List<Integer> keys) { this.keys = keys; }


    /************************************************************************************************

     PRIVATE METHODS

     ***********************************************************************************************/

    private Node getSuccessor() { return this.table.get(1); }

    private int hash(int number) { return number & 0xff; }

    class NodeComparator implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.getId() - b.getId();
        }
    }
}