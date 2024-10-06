
package compi2.multi.compilator.semantic.util;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ReturnCase {
    private boolean allScenaries;

    public ReturnCase(boolean allScenaries) {
        this.allScenaries = allScenaries;
    }
    
    
}
