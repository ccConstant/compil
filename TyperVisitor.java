import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import Type.*;

public class TyperVisitor extends AbstractParseTreeVisitor<Type> implements grammarTCLVisitor<Type> {

    private Map<UnknownType,Type> types = new HashMap<UnknownType,Type>();

    public Map<UnknownType, Type> getTypes() {
        return types;
    }

    
    @Override
    public Type visitNegation(grammarTCLParser.NegationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitNegation'");
    }

    @Override
    public Type visitComparison(grammarTCLParser.ComparisonContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitComparison'");
    }

    @Override
    public Type visitOr(grammarTCLParser.OrContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitOr'");
    }

    @Override
    public Type visitOpposite(grammarTCLParser.OppositeContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitOpposite'");
    }

    @Override
    public Type visitInteger(grammarTCLParser.IntegerContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitInteger'");
    }

    @Override
    public Type visitTab_access(grammarTCLParser.Tab_accessContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTab_access'");
    }

    @Override
    public Type visitBrackets(grammarTCLParser.BracketsContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBrackets'");
    }
    
    

    @Override
    public Type visitCall(grammarTCLParser.CallContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCall'");
    }

    @Override
    public Type visitBoolean(grammarTCLParser.BooleanContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitBoolean'");
    }

    @Override
    public Type visitAnd(grammarTCLParser.AndContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitAnd'");
    }

    @Override
    public Type visitVariable(grammarTCLParser.VariableContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitVariable'");
    }

    @Override
    public Type visitMultiplication(grammarTCLParser.MultiplicationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMultiplication'");
    }

    @Override
    public Type visitEquality(grammarTCLParser.EqualityContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEquality'");
    }

    @Override
    public Type visitTab_initialization(grammarTCLParser.Tab_initializationContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTab_initialization'");
    }
    
    
    

    @Override
    public Type visitAddition(grammarTCLParser.AdditionContext ctx) {
    	Type tExp1 = visit(ctx.getChild(0));
    	Type tExp2 = visit(ctx.getChild(0));
    	Type integer = new PrimitiveType(Type.Base.INT);
    	if (tExp1.equals(integer) && tExp2.equals(integer)) {
    		return integer;
    	}
    	else {
            throw new UnsupportedOperationException("Les types des expressions de l'addition doivent être des int");
    	}
    }

    @Override
    public Type visitBase_type(grammarTCLParser.Base_typeContext ctx) {
    	String type = ctx.getText();
    	switch(type) {
    	case "auto" : return new UnknownType();
    	case "int" : return new PrimitiveType(Type.Base.INT);
    	case "bool" : return new PrimitiveType(Type.Base.BOOL);
    	default : return null;
    	}
    }

    @Override
    public Type visitTab_type(grammarTCLParser.Tab_typeContext ctx) {
    	Type t = visit(ctx.getChild(0));
    	return new ArrayType(t) ;
    }

    @Override
    public Type visitDeclaration(grammarTCLParser.DeclarationContext ctx) {
    	Type tVar = visit(ctx.getChild(0));
    	
        if (ctx.getChild(2).getText().equals("=")) {
        	Type tExp = visit(ctx.getChild(3));
        	if (tVar.equals(tExp)) {
        		types.put(new UnknownType(ctx.getChild(1).getText(), 0), tVar);
        		return tVar;
        	}else {
        		Map<UnknownType, Type> sub = tVar.unify(tExp);
        		if (sub != null) {
        			tVar.substituteAll(sub);
        			types.put(new UnknownType(ctx.getChild(1).getText(), 0), tVar);
        			return tVar;
        		} else {
        			throw new UnsupportedOperationException("Les types de la déclaration ne sont pas compatible");
        		}
        	}
        } else {
        	types.put(new UnknownType(ctx.getChild(1).getText(), 0), tVar);
        	return tVar;
        }
        
        
    }

    @Override
    public Type visitPrint(grammarTCLParser.PrintContext ctx) {
        return null;
    }

    @Override
    public Type visitAssignment(grammarTCLParser.AssignmentContext ctx) {
    	
    	if (ctx.getChild(3).getText().equals("[")) {
    		Type integer = new PrimitiveType(Type.Base.INT);
    		if (!visit(ctx.getChild(3)).equals(integer)) {
    			throw new UnsupportedOperationException("L'appel d'une valeur d'un tableau ce fait avec un int");
    			//TODO unifier
    		}
    	}
    	
    	UnknownType var = new UnknownType(ctx.getChild(0).getText(), 0);//TODO index ???
    	
    	for (Map.Entry<UnknownType,Type> entry : types.entrySet()) {
    		UnknownType sVar = entry.getKey();
    		Type oldType = entry.getValue();
    		if(var.equals(sVar)){
    			Type tExp = visit(ctx.getChild(3));
    			if (!oldType.equals(tExp)) {
    				Map<UnknownType, Type> sub = oldType.unify(tExp);
    				if (sub != null) {
    					Type newType = oldType.substituteAll(sub);
    					types.replace(var, oldType, newType);
            			return oldType;
            		} else {
            			throw new UnsupportedOperationException("Les types d'assignation ne sont pas compatible");
            		}
    			}
    		}
    	}
    	return null;
        
    }

    @Override
    public Type visitBlock(grammarTCLParser.BlockContext ctx) {
       return visit(ctx.getChild(1));
    }

    
    
    
    @Override
    public Type visitIf(grammarTCLParser.IfContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitIf'");
    }

    @Override
    public Type visitWhile(grammarTCLParser.WhileContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitWhile'");
    }

    @Override
    public Type visitFor(grammarTCLParser.ForContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitFor'");
    }

    @Override
    public Type visitReturn(grammarTCLParser.ReturnContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitReturn'");
    }

    @Override
    public Type visitCore_fct(grammarTCLParser.Core_fctContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCore_fct'");
    }

    @Override
    public Type visitDecl_fct(grammarTCLParser.Decl_fctContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitDecl_fct'");
    }

    @Override
    public Type visitMain(grammarTCLParser.MainContext ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitMain'");
    }

    
}
