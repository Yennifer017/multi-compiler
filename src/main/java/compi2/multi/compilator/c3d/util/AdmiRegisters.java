
package compi2.multi.compilator.c3d.util;

import compi2.multi.compilator.analysis.typet.PrimitiveType;

/**
 *
 * @author blue-dragon
 */
public class AdmiRegisters {
    public Register findRegister(PrimitiveType type, int number){
        switch (type) {
            case PrimitiveType.IntegerPT -> {
                return switch (number) {
                    case 1 -> Register.AX_INT;
                    case 2 -> Register.BX_INT;
                    default -> Register.CX_INT;
                };
            }
            case PrimitiveType.StringPT -> {
                return switch (number) {
                    case 1 -> Register.AX_STRING;
                    case 2 -> Register.BX_STRING;
                    default -> Register.CX_STRING;
                };
            }
            case PrimitiveType.RealPT -> {
                return switch (number) {
                    case 1 -> Register.AX_FLOAT;
                    case 2 -> Register.BX_FLOAT;
                    default -> Register.CX_FLOAT;
                };
            }
            
            case PrimitiveType.BooleanPT -> {
                return switch (number) {
                    case 1 -> Register.AX_CHAR;
                    case 2 -> Register.BX_CHAR;
                    default -> Register.CX_CHAR;
                };
            }
            default -> throw new AssertionError();
        }
    }
}
