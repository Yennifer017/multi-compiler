package compi2.multi.compilator.analysis.typet.convert;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class PrimConvert {
    
    private ErrorsRep errorsRep;
    protected PrimConvert(ErrorsRep errorsRep){
        this.errorsRep = errorsRep;
    }

    public PrimitiveType mayorType(PrimitiveType leftType, PrimitiveType rightType) {
        if (leftType.getId() > rightType.getId()) {
            return leftType;
        } else {
            return rightType;
        }
    }

    public void penalize(PrimFollowType primT, PrimitiveType penalizeType, List<String> semanticErrors) {
        if (primT.getPrimitiveType() == penalizeType) {
            semanticErrors.add(errorsRep.incorrectTypeError(
                    primT.primitiveType.getName(), 
                    primT.getPosition())
            );
        }
    }
}
