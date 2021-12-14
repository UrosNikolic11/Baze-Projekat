package resource;

public abstract class DBNode {
    private String name;
    private DBNode parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBNode getParent() {
        return parent;
    }

    public void setParent(DBNode parent) {
        this.parent = parent;
    }
}
