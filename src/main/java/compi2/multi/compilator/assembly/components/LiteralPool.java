/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly.components;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.AssemblyTransformable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class LiteralPool implements AssemblyTransformable{ 
    private final Map<String, Literal> literals;
    
    public LiteralPool(){
        this.literals = new LinkedHashMap<>();
    }
    
    public Literal getOrCreate(String value) {

        Literal literal = literals.get(value);

        if (literal != null)
            return literal;

        literal = new Literal(value, literals.size());
        literals.put(value, literal);
        return literal;
    }
    
    public Literal get(String value){
        return literals.get(value);
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        this.literals.forEach((key, literal) -> {
            builder.append(literal.getLabel())
                    .append(":\n")
                    .append("    db \"")
                    .append(literal.getValue())
                    .append("\"\n")
                    ;
            builder.append(literal.getLenghtLabel())
                    .append(" equ $-")
                    .append(literal.getLabel())
                    .append("\n\n");
        });
    }
    
}
