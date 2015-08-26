package corejava.ch6.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TraceHandler implements InvocationHandler {
	private Object target;
	
	public TraceHandler(Object target) {
		super();
		this.target = target;
	}

	 
	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		System.out.print(target);
		System.out.print("."+m.getName()+"(");
		if(args!=null&&args.length>0){
			for( int i=0; i<args.length; i++){
				Object arg = args[i];
				System.out.print(arg.getClass()+ " "+arg);
				if(i <args.length-1){
					System.out.print(", ");
				}
			}
		}
		
		System.out.println(")");
		return m.invoke(target, args);
	}

}
