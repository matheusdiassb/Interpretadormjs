package interpreter;



import java.util.HashMap;
import java.util.Map;

import interpreter.command.Command;
import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.function.NativeFunction;
import interpreter.function.NativeFunction.NativeOp;
import interpreter.value.FunctionValue;
import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;
import lexical.Token;


public class Interpreter {

    public final static Environment globals;

    static {
        globals = new Environment();

        Map<TextValue, Value<?>> obj = new HashMap<TextValue, Value<?>>();


        for (NativeOp op : NativeOp.values()) {

            Environment fn = new Environment(globals);

            Variable params = fn.declare(new Token("params", Token.Type.NAME, null), false);
                
                TextValue tv = new TextValue(op.name());
                NativeFunction nf = new NativeFunction(params, op);
                FunctionValue fv = new FunctionValue(nf);
           
                obj.put(tv, fv);

        }

        ObjectValue cons = new ObjectValue(obj);

        Variable console = globals.declare(new Token("console", Token.Type.NAME, null), true);

        console.initialize(cons);
        
    }

    private Interpreter() {
    }

    public static void interpret(Command cmd) {
        cmd.execute();
    }

    public static void interpret(Expr expr) {
        Value<?> v = expr.expr();
        if (v == null)
            System.out.println("undefined");
        else
            System.out.println(v);
    }

}
