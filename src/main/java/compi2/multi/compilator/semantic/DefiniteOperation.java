
package compi2.multi.compilator.semantic;

/**
 *
 * @author blue-dragon
 */
public enum DefiniteOperation {
    Addition("Suma", "+"),
    Power("Potencia", "^"),
    Substraction("Resta", "-"),
    Multiplication("Multiplicacion", "*"),
    Division("Division", "/"),
    Module("Modulo", "%"),
    EqualsTo("Igual", "=="),
    DifferentTo("Diferent", "!="),
    GraterThan("Mayor que", ">"),
    GraterEq("Mayor o igual que", ">="),
    LessThan("Menor que", "<"),
    LessEq("Menor igual que", "<="),
    And("'y' logico", "&"),
    AndThen("'and then' logico", ""),
    Or("'o' logicp", "||"),
    OrElse("'or else' logico", ""),
    Not("Negacion", "!")
    ;
    
    private String name;
    private String sign;
    private DefiniteOperation(String name, String sign){
        this.name = name;
        this.sign = sign;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSign(){
        return this.sign;
    }
}
