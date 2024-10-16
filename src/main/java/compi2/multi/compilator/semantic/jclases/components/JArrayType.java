
package compi2.multi.compilator.semantic.jclases.components;

import compi2.multi.compilator.semantic.util.Label;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JArrayType extends JType{
    private int dims;
    
    public JArrayType(Label name, JReferType refType, int dims){
        super(refType, name);
        this.dims = dims;
    }
    
    @Override
    public int getArrayDimensions(){
        return this.dims;
    }
}
