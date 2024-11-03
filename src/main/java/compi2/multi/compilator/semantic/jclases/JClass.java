package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.semantic.DefObjsAst;
import compi2.multi.compilator.semantic.util.Label;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter
@Setter
public class JClass extends DefObjsAst {

    public static final String FATHER_OBJECT_CLASS = "Object";

    private Label herence;
    private List<JDef> definitions;

    private ClassST classST;

    public JClass(Label name, Label herence, List<JDef> definitions) {
        super();
        super.name = name;
        if (herence == null) {
            herence = new Label(FATHER_OBJECT_CLASS, name.getPosition());
        }
        this.herence = herence;
        this.definitions = definitions;
    }

    /**
     * Genera una representacion de la clase para que pueda ser insertada en una
     * tabla de simbolos
     *
     * @param globalST
     * @param symbolTable
     * @param typeTable
     * @param semanticErrors
     * @return
     */
    @Override
    public RowST generateRowST(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, List<String> semanticErrors) {
        this.classST = new ClassST(
                name.getName(),
                new SymbolTable(),
                new SymbolTable(),
                new NodeJerarTree(null, null), 
                new LinkedList<>()
        );
        classST.getJerar().setClassST(classST);

        if (isValidName(semanticErrors)
                && super.refAnalyzator.canInsert(name, globalST, semanticErrors)) {
            return this.classST;
        }
        return null;
    }

    public void completeFieldsAndMethods(JSymbolTable globalST,
            TypeTable typeTable, List<String> semanticErrors) {
        NodeJerarTree fatherJerarNode = this.validateAndGetFromHerence(globalST, semanticErrors);
        this.classST.getJerar().setFather(fatherJerarNode);
        if (this.definitions != null && !this.definitions.isEmpty()) {
            boolean implementEmptyConstruct = true;
            for (JDef definition : definitions) {
                try {
                    SymbolTable st = null;
                    if (definition instanceof JFunction) {
                        JFunction constructor = (JFunction) definition;
                        constructor.setNameClass(name.getName());
                        st = classST.getMethodsST();
                        if(implementEmptyConstruct && (definition instanceof JConstructor)){
                            implementEmptyConstruct = false;
                        }
                    } else {
                        st = classST.getFieldsST();
                    }
                    
                    RowST rowST = definition.generateRowST(
                            st,
                            typeTable,
                            semanticErrors
                    );
                    if (rowST != null) {
                        if(rowST instanceof FieldST){
                            classST.getFieldsOrdered().add(rowST.getName());
                        }
                        st.put(rowST.getName(), rowST);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                }
            }
            if(implementEmptyConstruct){
                this.addEmptyConstruct(typeTable, semanticErrors);
            }
        }
    }
    
    private void addEmptyConstruct(TypeTable typeTable, List<String> semanticErrors){
        JConstructor emptyConstruct = new JConstructor(
                name, AccessMod.PUBLIC, new LinkedList<>(), new LinkedList<>()
        );
        emptyConstruct.setNameClass(name.getName());
        definitions.add(emptyConstruct);
        RowST rowST = emptyConstruct.generateRowST(
                classST.getMethodsST(),
                typeTable,
                semanticErrors
        );
        classST.getMethodsST().put(rowST.getName(), rowST);
    }

    public void validateInternal(JSymbolTable globalST,
            TypeTable typeTable, List<String> semanticErrors) {
        if (this.definitions != null && !this.definitions.isEmpty()) {
            for (JDef definition : this.definitions) {
                if (definition instanceof JField) {
                    JField field = (JField) definition;
                    field.setSymbolTable(classST.getFieldsST());
                }
                definition.setJerar(classST.getJerar());
                definition.validateInternal(globalST, typeTable, semanticErrors);
            }
        }
    }

    private boolean isValidName(List<String> semanticErrors) {
        boolean forbiden = name.getName().equals(FATHER_OBJECT_CLASS);
        if (forbiden) {
            semanticErrors.add(super.errorsRep.
                    ObjectClassError(herence.getPosition(), FATHER_OBJECT_CLASS)
            );
        }
        return !forbiden;
    }

    private NodeJerarTree validateAndGetFromHerence(JSymbolTable globalST, List<String> semanticErrors) {
        if (herence.getName().equals(FATHER_OBJECT_CLASS)) {
            return globalST.getInitJerarTree();
        } else if (globalST.containsKey(herence.getName())) {
            return globalST.get(herence.getName()).getJerar();
        } else {
            semanticErrors.add(super.errorsRep.undefiniteClassError(
                    herence.getName(), herence.getPosition()
            ));
            return globalST.getInitJerarTree();
        }
    }
    
    public void generateCuartetas(AdmiMemory admiMemory){
        for (JDef definition : definitions) {
            if(definition instanceof JFunction){
                JFunction function = (JFunction) definition;
                function.generateCuartetas(admiMemory, classST);
            }
        }
    }

}
