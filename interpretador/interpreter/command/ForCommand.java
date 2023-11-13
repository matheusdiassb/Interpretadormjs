package interpreter.command;

import java.util.List;
import java.util.Map;

import interpreter.InterpreterException;
import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.ListValue;
import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class ForCommand extends Command{

    private Variable var;
    private Expr expr;
    private Command cmds;
    
    public ForCommand (int line, Variable var, Expr expr, Command cmds){
        super(line);
        this.var = var;
        this.expr = expr;
        this.cmds = cmds;
    }

    @Override
    public void execute() {
        Value<?> v = this.expr.expr();

        if(v instanceof ListValue){
            ListValue lv = (ListValue) v;
            List <Value<?>> ex = lv.value();

            for (Value<?> vf : ex){
                var.setValue(vf);
                cmds.execute();
            }


        } else if (v instanceof ObjectValue){
            ObjectValue ov = (ObjectValue) v;
            Map<TextValue, Value<?>> map = ov.value();

            for (TextValue i : map.keySet()) {
                var.setValue(map.get(i));
                cmds.execute();
            }

        } else {
            throw new InterpreterException(super.getLine());
        }

    }
}
