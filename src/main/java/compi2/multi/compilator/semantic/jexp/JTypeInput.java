
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.typet.PrimitiveType;

/**
 *
 * @author blue-dragon
 */
public enum JTypeInput {
    Char(PrimitiveType.CharPT),
    Int(PrimitiveType.IntegerPT),
    Float(PrimitiveType.RealPT);
    
    private PrimitiveType primType;
    private JTypeInput(PrimitiveType type){
        this.primType = type;
    }
    
    public PrimitiveType getPrimType(){
        return this.primType;
    }
}
