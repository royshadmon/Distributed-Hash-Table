public interface ChordNode {

    void join(Node node);
    int find();
    void insert(int key, int value);
    void remove(int key);
    void prettyPrint();
}