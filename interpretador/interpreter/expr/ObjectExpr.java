package interpreter.expr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class ObjectExpr extends Expr{
    
    private List<ObjectItem> items;

    public ObjectExpr (int line, List<ObjectItem> items){
        super(line);
        this.items = items;
    }

    @Override
    public Value<?> expr(){
        Map<TextValue, Value<?>> value = new HashMap<TextValue, Value<?>>();
        TextValue tv;
        Value<?> v;

        for(ObjectItem obj : items){
            v = obj.value.expr();
            tv = new TextValue(obj.key);
            value.put(tv, v);
        }

        ObjectValue obj = new ObjectValue(value);

        return obj;
    }
    
}