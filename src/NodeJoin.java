public class NodeJoin {

    public static void main(String[] args) {
        OldNode oldNode1 = new OldNode(1);
        OldNode oldNode2 = new OldNode(2);

        oldNode1.join(null);
        //oldNode1.getFingerTable().prettyPrint();
        oldNode2.join(oldNode1);
    }
}
