package interpreter.expr;

public class ObjectItem {
    public String key;
    public Expr value;

    public ObjectItem(String key, Expr value){
        this.key = key;
        this.value = value;
    }
}