public interface ChordNode {

    void join(Node node);
    void find();
    void insert(byte key, byte value);
    void remove(byte key);
    void prettyPrint();
}