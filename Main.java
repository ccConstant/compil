import Type.*;

public class Main {
	public static void main(String[] args) {
		PrimitiveType p = new PrimitiveType(Type.Base.INT);
		UnknownType p1 =  new UnknownType();
		System.out.println(p.unify(p1));
	}
};
