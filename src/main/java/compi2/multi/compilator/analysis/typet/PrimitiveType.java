
package compi2.multi.compilator.analysis.typet;

/**
 *
 * @author blue-dragon
 */
public enum PrimitiveType {
    IntegerPT("integer", 2, true, true, "int"),
    RealPT("real", 4, false, true, "float"),
    BooleanPT("boolean", 0, true, true, "int"),
    CharPT("char", 1, true, true, "char"),
    StringPT("string", 5, false, false, "std::string"),
    LongintPT("longint", 3, false, true, "long")
    ;
    
    private String name;
    private Type type;
    private int id;
    private boolean isIntegerNumeric;
    private boolean isNumeric;
    private String cName;
    
    private PrimitiveType(String name, int id, boolean isIntegerNumeric, boolean isNumeric, String cName){
        this.name = name;
        this.type = new Type(name, 1);
        this.id = id;
        this.isIntegerNumeric = isIntegerNumeric;
        this.isNumeric = isNumeric;
        this.cName = cName;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getCName(){
        return this.cName;
    }
    
    public Type getType(){
        return this.type;
    }
    
    public int getId(){
        return this.id;
    }
    
    public boolean isIntegerNumeric(){
        return this.isIntegerNumeric;
    }
   
    public boolean isNumeric(){
        return this.isNumeric;
    }
    
}   
