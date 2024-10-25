
package compi2.multi.compilator.c3d.util;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.CodeTransformable;

/**
 *
 * @author blue-dragon
 */
public enum Register implements CodeTransformable{
    AX_INT(PrimitiveType.IntegerPT, "AX_INT"),
    BX_INT(PrimitiveType.IntegerPT, "BX_INT"),
    CX_INT(PrimitiveType.IntegerPT, "CX_INT"),
    AX_STRING(PrimitiveType.StringPT, "AX_STRING"),
    BX_STRING(PrimitiveType.StringPT, "BX_STRING"),
    CX_STRING(PrimitiveType.StringPT, "CX_STRING"),
    AX_FLOAT(PrimitiveType.RealPT, "AX_FLOAT"),
    BX_FLOAT(PrimitiveType.RealPT, "BX_FLOAT"),
    CX_FLOAT(PrimitiveType.RealPT, "CX_FLOAT"),
    AX_CHAR(PrimitiveType.CharPT, "AX_CHAR"),
    BX_CHAR(PrimitiveType.CharPT, "BX_CHAR"),
    CX_CHAR(PrimitiveType.CharPT, "CX_CHAR");
    
    private PrimitiveType type;
    private String name;
    
    private Register(PrimitiveType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append(this.type.getCName());
        builder.append(" ");
        builder.append(name);
        builder.append(";\n");
    }
    
    public String getName(){
        return this.name;
    }
}
