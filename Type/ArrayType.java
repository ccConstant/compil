package Type;
import java.util.HashMap;
import java.util.Map;

public class ArrayType extends Type{
    private Type tabType;
    
    /**
     * Constructeur
     * @param t type des éléments du tableau
     */
    public ArrayType(Type t) {
        this.tabType = t;
    }

    /**
     * Getter du type des éléments du tableau
     * @return type des éléments du tableau
     */
    public Type getTabType() {
       return tabType;
    }

    @Override
    public Map<UnknownType, Type> unify(Type t) {
    	Map<UnknownType,Type> sub = new HashMap<UnknownType,Type>();
    	if (t instanceof PrimitiveType) {
    		return null;
    	}
    	if (t instanceof ArrayType) {
    		return ((ArrayType)t).getTabType().unify(this.getTabType());
    	}
    	if (t instanceof FunctionType) {
    		return null;
    	}
    	if (t instanceof UnknownType) {
    		return t.unify(this);
    	}
    	return sub;
    }

    @Override
    public boolean equals(Object t) {
    	if (t instanceof ArrayType) {
    		return ((ArrayType)t).getTabType().equals(this.getTabType());
    	} else {
    		return false;
    	}
    }

    @Override
    public Type substitute(UnknownType v, Type t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'substitute'");
    }

    @Override
    public boolean contains(UnknownType v) {
        return this.tabType.contains(v);
    }


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
