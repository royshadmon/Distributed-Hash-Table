import java.util.*;

public class Node {

    private int nodeId;
    private FingerTable table;
    private static int m = FingerTable.MAX_ENTRIES;

    private List<Integer> keys = new ArrayList<>();

    Node(int id) {
        this.nodeId = hash(id);
        this.table = new FingerTable(this.nodeId);
    }

    public void join(Node node) {
        if (node == null) {
            this.initNetwork();
        }
        else {
            List<Node> nodeList = node.getActiveNodes();
            nodeList.add(this);
            Collections.sort(nodeList, new SortbyId());

            this.initFingerTable(nodeList);
            this.updateOtherTables(nodeList);
        }
    }

    private void updateOtherTables(List<Node> nodeList) {

    }

    private void initFingerTable(List<Node> list) {

        for (int i =1; i <= m; i++) {
            int val = FingerTable.hash(this.nodeId, i);
            Node successor = this.findSuccessor(val, list);
            this.table.put(i, successor);
        }

        this.table.prettyPrint();
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

    private class SortbyId implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.getId() - b.getId();
        }
    }
}