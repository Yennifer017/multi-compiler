
package compi2.multi.compilator.analysis.symbolt;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class InfParam {
    private String type;
    private String name;
    
    public InfParam(String type, String name){
        this.type = type;
        this.name = name;
    }
    
    public InfParam(String type){
        this.type = type;
    }
}
