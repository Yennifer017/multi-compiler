
package compi2.multi.compilator.analysis.jerarquia;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.semantic.jclases.JClass;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class NodeJerarTree {
    
    private boolean isObject;
    private NodeJerarTree father;
    private ClassST classST;
    
    public NodeJerarTree(NodeJerarTree father, ClassST classST){
        this.father = father;
        this.classST = classST;
        this.isObject = false;
    }
    
    public NodeJerarTree(){
        this.father = null;
        this.classST = new ClassST(JClass.FATHER_OBJECT_CLASS, new SymbolTable(), this);
        this.isObject = true;
    }
}
