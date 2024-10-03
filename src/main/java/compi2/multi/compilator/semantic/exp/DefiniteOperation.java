
package compi2.multi.compilator.semantic.exp;

/**
 *
 * @author blue-dragon
 */
public enum DefiniteOperation {
    Addition("Suma"),
    Power("Potencia"),
    Substraction("Resta"),
    Multiplication("Multiplicacion"),
    Division("Division"),
    IntDivision("Division entera"),
    Module("Modulo"),
    EqualsTo("Igual"),
    DifferentTo("Diferent"),
    GraterThan("Mayor que"),
    GraterEq("Mayor o igual que"),
    LessThan("Menor que"),
    LessEq("Menor igual que"),
    And("'y' logico"),
    AndThen("'and then' logico"),
    Or("'o' logicp"),
    OrElse("'or else' logico"),
    Not("Negacion")
    ;
    
    private String name;
    private DefiniteOperation(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
}
