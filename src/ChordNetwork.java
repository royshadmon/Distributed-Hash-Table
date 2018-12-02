import java.util.ArrayList;

public class ChordNetwork {

    public static void main(String[] args) {
//
//        // Testing findSuccessor, findPredecessor and closestPrecedingFinger
//        OldNode oldNode0 = new OldNode(0);
//        OldFingerTable ftable0 = new OldFingerTable(oldNode0.getId());
//        OldNode oldNode1 = new OldNode(1);
//        OldFingerTable ftable1 = new OldFingerTable(oldNode1.getId());
//        OldNode oldNode3 = new OldNode(3);
//        OldFingerTable ftable3 = new OldFingerTable(oldNode3.getId());
//
//        ftable0.put(1, oldNode1);
//        ftable0.put(2, oldNode3);
//        ftable0.put(3, oldNode0);
//
//        ftable1.put(1, oldNode3);
//        ftable1.put(2, oldNode3);
//        ftable1.put(3, oldNode0);
//
//        ftable3.put(1, oldNode0);
//        ftable3.put(2, oldNode0);
//        ftable3.put(3, oldNode0);
//
//        ftable0.prettyPrint();
//        ftable1.prettyPrint();
//        ftable3.prettyPrint();
//
//        oldNode0.setFingerTable(ftable0);
//        oldNode1.setFingerTable(ftable1);
//        oldNode3.setFingerTable(ftable3);
//
//        OldNode oldNode6 = new OldNode(6);
//        oldNode6.join(oldNode3);
//
//        oldNode0.getFingerTable().prettyPrint();
//        oldNode1.getFingerTable().prettyPrint();
//        oldNode3.getFingerTable().prettyPrint();

        OldNode oldNode0 = new OldNode(0);
        OldNode oldNode1 = new OldNode(1);
        OldNode oldNode3 = new OldNode(3);

        ArrayList<Integer> keys0 = new ArrayList<>();
        keys0.add(7);
        keys0.add(3);
        keys0.add(2);
        keys0.add(1);
        keys0.add(4);
        keys0.add(0);
        keys0.add(6);
        keys0.add(5);

        oldNode0.setKeys(keys0);

        oldNode0.join(null);
        oldNode1.join(oldNode0);
        oldNode3.join(oldNode1);


//        oldNode0.getFingerTable().prettyPrint();
//        oldNode1.getFingerTable().prettyPrint();
//        oldNode3.getFingerTable().prettyPrint();

//        System.out.println("Now node 6 joins. Let's see the finger tables again.");

        OldNode oldNode6 = new OldNode(6);
        oldNode6.join(oldNode3);

//        oldNode0.getFingerTable().prettyPrint();
//        oldNode1.getFingerTable().prettyPrint();
//        oldNode3.getFingerTable().prettyPrint();
//        oldNode6.getFingerTable().prettyPrint();

        System.out.println("Printing new keys in OldNode 6");
        oldNode6.getKeys().forEach(key -> System.out.println(key));

        System.out.println("Printing new keys in OldNode 0");
        oldNode0.getKeys().forEach(key -> System.out.println(key));

        System.out.println("Printing new keys in OldNode 1");
        oldNode1.getKeys().forEach(key -> System.out.println(key));

        System.out.println("Printing new keys in OldNode 3");
        oldNode3.getKeys().forEach(key -> System.out.println(key));

    }
}
