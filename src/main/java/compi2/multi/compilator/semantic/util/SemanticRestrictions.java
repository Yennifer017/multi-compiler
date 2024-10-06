
package compi2.multi.compilator.semantic.util;

import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Setter
public class SemanticRestrictions {
    private boolean allowContinue;
    private boolean allowBreak;
    private String returnType;
    private String nameFunction;

    public SemanticRestrictions(boolean allowContinue, boolean allowBreak, 
            String returnType, String nameFunction) {
        this.allowContinue = allowContinue;
        this.allowBreak = allowBreak;
        this.returnType = returnType;
        this.nameFunction = nameFunction;
    }

    public boolean allowContinue() {
        return allowContinue;
    }

    public boolean allowBreak() {
        return this.allowBreak;
    }

    public String getNameFunction() {
        return this.nameFunction;
    }

    public String getReturnType() {
        return returnType;
    }
    
    
}
