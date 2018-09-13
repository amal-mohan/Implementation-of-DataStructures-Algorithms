package axm179030;

import java.util.HashMap;
import java.util.Stack;

public class ShuntingYard {

    private String infix;
    private HashMap<Character,Integer> precedenceMap;

    ShuntingYard(String infix){
        this.infix=infix;
        precedenceMap=new HashMap<Character, Integer>();
        precedenceMap.put('*',1);
        precedenceMap.put('/',1);
        precedenceMap.put('+',2);
        precedenceMap.put('-',2);
        precedenceMap.put('(',3);

    }

    public String infixToPostfix(){
        Stack<Character> stack=new Stack<>();
        StringBuilder queue=new StringBuilder();
        for(int i=0;i<infix.length();i++){
            char ele=infix.charAt(i);
            if(Character.isDigit(ele)){
                queue.append(ele);
            }
            else {
                if(stack.isEmpty()){
                    stack.push(ele);
                }
                else if(ele==')'){
                    char tos=stack.pop();
                    while(tos!='('){
                        queue.append(tos);
                        if(stack.isEmpty()){
                            return null;
                        }
                        tos=stack.pop();
                    }
                }
                else{
                    while (isHigherPrecedence(ele,stack.peek())) {
                        char tos=stack.pop();
                        queue.append(tos);
                        if(stack.isEmpty()){
                            break;
                        }
                    }
                    stack.push(ele);
                }
            }
        }
        while(!stack.isEmpty()){
            queue.append(stack.pop());
        }
        return queue.toString();
    }

    boolean isHigherPrecedence(char a,char b){
        if(a=='('){
            return false;
        }
        if(precedenceMap.get(a)>precedenceMap.get(b)){
            return true;
        }
        return false;
    }

    public static void main(String args[]){
        ShuntingYard preToPost=new ShuntingYard("1*(2+3)");
        String postFix=preToPost.infixToPostfix();
        System.out.println(postFix);
    }
}
