package compi2.multi.compilator.semantic;

import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public enum DefiniteOperation {
    Addition(
            "Suma",
            "+",
            "add"
    ),
    Power(
            "Potencia", 
            "^", 
            ""),
    Substraction(
            "Resta",
            "-", 
            "sub"),
    Multiplication(
            "Multiplicacion", 
            "*", 
            "imul"),
    Division(
            "Division", 
            "/", 
            "idiv"),
    Module(
            "Modulo", 
            "%", 
            ""),
    EqualsTo(
            "Igual", 
            "==", 
            ""),
    DifferentTo(
            "Diferent", 
            "!=", 
            ""),
    GraterThan(
            "Mayor que", 
            ">", 
            ""),
    GraterEq(
            "Mayor o igual que", 
            ">=", 
            ""),
    LessThan(
            "Menor que", 
            "<", 
            ""),
    LessEq(
            "Menor igual que", 
            "<=", 
            ""),
    And(
            "'y' logico", 
            "&", 
            ""
    ),
    AndThen(
            "'and then' logico", 
            "", 
            ""),
    Or(
            "'o' logicp", 
            "||", 
            ""),
    OrElse(
            "'or else' logico", 
            "", 
            ""
    ),
    Not(
            "Negacion", 
            "!", 
            ""
    );

    private String name;
    private String sign;
    private String nasm;

    private DefiniteOperation(String name, String sign, String nasm) {
        this.name = name;
        this.sign = sign;
        this.nasm = nasm;
    }

}
