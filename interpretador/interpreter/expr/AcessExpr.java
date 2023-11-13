package interpreter.expr;

import java.util.List;
import java.util.Map;

import interpreter.InterpreterException;
import interpreter.value.ListValue;
import interpreter.value.NumberValue;
import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class AcessExpr extends SetExpr {
    private SetExpr base;
    private Expr index;

    public AcessExpr(int line, SetExpr base, Expr index){
        super(line);
        this.base = base;
        this.index = index;
    }

    @Override
    public Value<?> expr(){

        Value<?> v = base.expr();
        if (v instanceof ObjectValue) {
            ObjectValue ov = (ObjectValue) v;

            Value<?> i = index.expr();
            String si = TextValue.convert(i);

            TextValue tv = new TextValue(si);

            Map<TextValue, Value<?>> value = ov.value();

            if(value.containsKey(tv)){
                return value.get(tv);

            } else{
                return null;
            }

        } else if (v instanceof ListValue) {
            ListValue lv = (ListValue) v;

            List<Value<?>> a = lv.value();

            Value<?> i = index.expr();
            double idx = NumberValue.convert(i);

            int idxlv = (int) idx;

            if(idxlv < 0 || idxlv >= a.size()){
                return null;

            } else {
                return a.get(idxlv);
            } 
            
        } else {
            throw new InterpreterException(super.getLine());
        }

    }

    public void setValue(Value<?> value){
        Value<?> v = base.expr();
        if (v instanceof ObjectValue) {
            ObjectValue ov = (ObjectValue) v;

            Value<?> i = index.expr();
            String si = TextValue.convert(i);

            TextValue tv = new TextValue(si);

            Map<TextValue, Value<?>> mapv = ov.value();
           
            mapv.put(tv, value);

        } else if (v instanceof ListValue) {
            ListValue lv = (ListValue) v;

            List<Value<?>> a = lv.value();

            Value<?> i = index.expr();
            double idx = NumberValue.convert(i);

            int idxlv = (int) idx;

            if(idxlv < 0 || idxlv > a.size())
                throw new InterpreterException(super.getLine());
            else
                a.add(idxlv, value);
                


        } else {
            throw new InterpreterException(super.getLine());
        }
       
    }
}
