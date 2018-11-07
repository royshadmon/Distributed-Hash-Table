public interface ChordNode {

    public void join(Node node);
    public void find();
    public void insert(byte key, byte value);
    public void remove(byte key);
    public void prettyPrint();
}