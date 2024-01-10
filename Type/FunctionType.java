package Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FunctionType extends Type {
    private Type returnType;
    private ArrayList<Type> argsTypes;
    
    /**
     * Constructeur
     * @param returnType type de retour
     * @param argsTypes liste des types des arguments
     */
    public FunctionType(Type returnType, ArrayList<Type> argsTypes) {
        this.returnType = returnType;
        this.argsTypes = argsTypes;
    }

    /**
     * Getter du type de retour
     * @return type de retour
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * Getter du type du i-eme argument
     * @param i entier
     * @return type du i-eme argument
     */
    public Type getArgsType(int i) {
        return argsTypes.get(i);
    }

    /**
     * Getter du nombre d'arguments
     * @return nombre d'arguments
     */
    public int getNbArgs() {
        return argsTypes.size();
    }

    @Override
    //TODO vérifié fonctionnement putAll et doublons 
    public Map<UnknownType, Type> unify(Type t) {
    	Map<UnknownType,Type> sub = new HashMap<UnknownType,Type>();
    	if (t instanceof PrimitiveType) {
    		return null;
    	}
    	if (t instanceof ArrayType) {
    		return null;
    	}
    	if (t instanceof FunctionType) {
    		FunctionType tf = (FunctionType)t;
    		if(this.getNbArgs() == tf.getNbArgs()) {
    			if (this.getReturnType().unify(tf.getReturnType()) == null) {
    				return null;
    			} else {
    				sub.putAll(this.getReturnType().unify(tf.getReturnType()));
    			}
    			for (int i = 0; i < this.getNbArgs(); i++) 
    			{
    				if (this.getArgsType(i).unify(tf.getArgsType(i)) == null) {
    					return null;
    				}
    				else {
    					sub.putAll(this.getReturnType().unify(tf.getReturnType()));
    				}
    			}
    			return sub;
    		}else {
    			return null;
    		}
    	}
    	if (t instanceof UnknownType) {
    		return t.unify(this);
    	}
    	return sub;
    }

    @Override
    public boolean equals(Object t) {
    	if (t instanceof FunctionType) {
    		FunctionType tf = (FunctionType)t;
    		if(this.getNbArgs() == tf.getNbArgs()) {
    			for (int i = 0; i < this.getNbArgs(); i++) 
    			{
    				if (!this.getArgsType(i).equals(tf.getArgsType(i))) {
    					return false;
    				}
    			}
    			if (!this.getReturnType().equals(tf.getReturnType())){
    				return false;
    			}
    			return true;
    		}else {
    			return false;
    		}
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
    	for (int i = 0; i < this.getNbArgs(); i++) 
		{
			if (this.getArgsType(i).contains(v)) {
				return true;
			}
		}
		if (this.getReturnType().contains(v)){
			return true;
		}
		return false;
    }


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
