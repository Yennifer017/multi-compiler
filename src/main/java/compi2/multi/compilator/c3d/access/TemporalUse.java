
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.VarAssignable;
import compi2.multi.compilator.c3d.Memory;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class TemporalUse extends MemoryAccess implements VarAssignable{
    private PrimitiveType type;
    private int countTemp;
    private Memory memory;

    public TemporalUse(PrimitiveType type, int countTemp, Memory memory) {
        this.type = type;
        this.countTemp = countTemp;
        this.memory = memory;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append(memory.getMemoryName(type));
        builder.append("[");
        builder.append(countTemp);
        builder.append("]");
    }

    @Override
    public String getNasmPrintableCode(AssemblyComps ac) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getAssemblyRepresentation() {
        return "[rbp-4]";
    }

    @Override
    public PrimitiveType getTypeAsign() {
        return this.type;
    }

}
