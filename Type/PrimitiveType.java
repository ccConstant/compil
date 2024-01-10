package Type;
import java.util.HashMap;
import java.util.Map;

public  class PrimitiveType extends Type {
    private Type.Base type; 
    
    /**
     * Constructeur
     * @param type type de base
     */
    public PrimitiveType(Type.Base type) {
        this.type = type;
    }

    /**
     * Getter du type
     * @return type
     */
    public Type.Base getType() {
        return type;
    }

    @Override
    public Map<UnknownType, Type> unify(Type t) {
    	Map<UnknownType,Type> sub = new HashMap<UnknownType,Type>();
    	if (t instanceof PrimitiveType) {
    		if(((PrimitiveType)t).getType() != this.type) {
    			return null;
    		}
    	}
    	if (t instanceof ArrayType) {
    		return null;
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
    	if (t instanceof PrimitiveType) {
        	return this.type == ((PrimitiveType)t).getType();
        }
        else {
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
        return false;
    }


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
