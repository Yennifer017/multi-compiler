
package compi2.multi.compilator.c3d.util;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.interfaces.CodeTransformable;
import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public enum Register implements CodeTransformable{
    AX_INT(PrimitiveType.IntegerPT, "AX_INT", "R12"),
    BX_INT(PrimitiveType.IntegerPT, "BX_INT", "R13"),
    CX_INT(PrimitiveType.IntegerPT, "CX_INT", "R14"),
    AX_STRING(PrimitiveType.StringPT, "AX_STRING", "R12"),
    BX_STRING(PrimitiveType.StringPT, "BX_STRING", "R13"),
    CX_STRING(PrimitiveType.StringPT, "CX_STRING", "R14"),
    AX_FLOAT(PrimitiveType.RealPT, "AX_FLOAT", "R12"),
    BX_FLOAT(PrimitiveType.RealPT, "BX_FLOAT", "R13"),
    CX_FLOAT(PrimitiveType.RealPT, "CX_FLOAT", "R14"),
    AX_CHAR(PrimitiveType.CharPT, "AX_CHAR", "R12"),
    BX_CHAR(PrimitiveType.CharPT, "BX_CHAR", "R13"),
    CX_CHAR(PrimitiveType.CharPT, "CX_CHAR", "R14"),
    AX_BOOL(PrimitiveType.BooleanPT, "AX_BOOL", "R12"),
    BX_BOOL(PrimitiveType.BooleanPT, "BX_BOOL", "R13"),
    CX_BOOL(PrimitiveType.BooleanPT, "CX_BOOL", "R14");
    
    private PrimitiveType type;
    private String name;
    private String nasmRegister;
    
    private Register(PrimitiveType type, String name, String nasmRegister) {
        this.type = type;
        this.name = name;
        this.nasmRegister = nasmRegister;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append(this.type.getCName());
        builder.append(" ");
        builder.append(name);
        builder.append(";\n");
    }
    
}
