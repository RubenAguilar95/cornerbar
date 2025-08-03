package com.dam.proyectodam.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestAspect {
	
//	@Before
	@Around("execution(* com.dam.proyectodam.rest.*.*(..))")
	public Object info(ProceedingJoinPoint pjp) throws Throwable{
		
	    final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final Method method = signature.getMethod();
        final Class<?> c = pjp.getTarget().getClass();
        final String cname = c.getName();
        final String mname = method.getName();
        final Object[] args = pjp.getArgs();
        

        System.out.println(new StringBuilder().append("Entrando en ").append(cname)
                .append(".").append(mname).append("...").toString());
       
       if (0 < args.length) {
    	   ParameterNameDiscoverer pnp = new LocalVariableTableParameterNameDiscoverer();
    	   String[] pNames = pnp.getParameterNames(method);
			System.out.println(new StringBuilder()
					.append("##### Con los siguientes parámetros (")
					.append(args.length).append(") : ").toString());
			int i = 0;
			for (Object arg : args) {
				if (null != arg) {
				System.out.println(new StringBuilder().append("##### Parámetro : ")
						.append(arg.getClass().getSimpleName()).append(" ")
						.append(pNames[i++])
						.append(" ------ Valor : ").append(arg.toString()));
				} else
					System.out.println(new StringBuilder().append("##### Parámetro : null"));
			}
		}
		System.out.println("\n");
		Object obj = pjp.proceed();
		
		return obj;
		
	}
	
	@AfterReturning(value = "execution(* com.dam.proyectodam.rest.*.*(..))", returning = "returnValue")
	public void afterReturning(JoinPoint jp, Object returnValue) {
		System.out.println("Devolviendo : ");
		if(null == returnValue){
			System.out.println("void");
		} else {
			System.out.println(returnValue.getClass().getSimpleName() + " " + returnValue);
		}
		System.out.println();
		
	}

}
