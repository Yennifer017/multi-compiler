
package compi2.multi.compilator.analysis.jerarquia;

import java.util.HashMap;
import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public class NodeJerarTree {
    public static final String PRINCIPAL_NODE = "$OBJECT";
    
    private NodeJerarTree father;
    private String name;
    private HashMap<String, NodeJerarTree> children;
    
    public NodeJerarTree(String name){
        this.name = name;
        children = new HashMap<>();
    }
    
    public void setFather(NodeJerarTree father){
        this.father = father;
    }
    
    public void addChild(NodeJerarTree child){
        children.put(child.getName(), father);
    }
}
