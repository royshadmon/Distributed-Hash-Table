package Chord;

class FingerTable {
    private int hostNodeId;

    FingerTable(Node node) {
        this.hostNodeId = node.getId();
    }

    FingerTable(int hostNodeId) {
        this.hostNodeId = hostNodeId;
    }

    public int getHostNodeId() {
        return hostNodeId;
    }

    protected void prettyPrint() {

    }
}