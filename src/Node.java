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
            this.initNetwork();
        }
        else {
            this.initializeNode(node);
        }
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

    // Possible optimisations. Don't know if they work correctly
    public Node findPredecessor(Node node, List<Node> list) {

        int index = Collections.binarySearch(list, node, new NodeComparator());

        if (index == 0) return list.get(list.size() - 1);
        else return list.get(index - 1);
    }

    // Possible optimisations. Don't know if they work correctly
    public Node findPredecessorWithId(int nodeId, List<Node> list) {

        if (list.get(0).getId() == nodeId) return list.get(list.size() - 1);
        int index = 0;
        for (int i = 1; i < list.size(); i++) {

            if (list.get(i).getId() == nodeId) {
                index = i - 1;
                break;
            }
        }

        return list.get(index);
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

    // May be needed when doing the optimisations
    public Node worseFindPredecessor(Node node) {
        Node temp = node;
        while (temp.getSuccessor() != node) {
            temp = temp.getSuccessor();
        }

        return temp;
    }

}