package model.expr;
import utils.*;

public class ArithmeticExpr implements Expression {
    private char operator;
    private Expression operand1,operand2;
    public ArithmeticExpr(char oper,Expression first,Expression second){
        operator=oper;
        operand1 =first;
        operand2=second;
    }
    public int evaluate(ISymbolTable table,IHeap heap){
        int resultFirst=operand1.evaluate(table,heap);
        int resultSecond=operand2.evaluate(table,heap);

        switch (operator){
            case '+': return resultFirst+resultSecond;
            case '-': return resultFirst-resultSecond;
            case '*': return resultFirst*resultSecond;
            case '/':
                if(resultSecond==0)
                    throw new ArithmeticException("Cannot divide by zero !");
            default:
                throw new RuntimeException("No valid operator !");
        }
    }
    public String toString(){
        return " "+operand1+operator+operand2+" ";
    }
}
