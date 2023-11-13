package interpreter.function;

import java.util.Random;
import java.util.Scanner;

import interpreter.expr.Variable;
import interpreter.value.NumberValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class NativeFunction extends Function {
    
    private NativeOp op;

    public static enum NativeOp {
        log,
        read,
        random
    }


   public NativeFunction (Variable params, NativeOp op){
        super(params);
        this.op = op;
   }

    @Override
    public Value<?> call(){


        Value<?> res = null;

        switch (op) {
            case log:
                logOp(getParams().expr());
                break;

            case read:
                res = readOp();
                break;

            case random:
            default:
                res = randomOp();
                break;
        }

        return res;
    }

    private void logOp(Value<?>... valueParams){
        StringBuilder sb = new StringBuilder();
       
        for (Value<?> v : valueParams){
                sb.append(v.value());
        }

        String exit = sb.toString().substring(1, sb.length() - 1);
        
        if(exit.equals("null")){
            System.out.println("undefined");
        } else {
            System.out.println(exit);
        }
 
    }


    private Value<?> readOp(){
        Scanner in = new Scanner( System.in );
        String exit = in.nextLine();
        in.close();

        return new TextValue(exit);
    }

    private Value<?> randomOp(){
        Random r = new Random();
        int aux = r.nextInt(2);
        double d = (double) aux;
        return new NumberValue(d);
    }
    
}
