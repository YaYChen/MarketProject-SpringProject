package com.springboot.project.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class LogAspect {

    /**
     * 日志
     * 用切面类创建日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切点
     * 配置需要添加切面通知的包路径
     */
    @Pointcut("(execution(* com.springboot.project.controller..*.*(..)))")
    public void pointCut(){}

    /**
     * 前置通知
     * @param joinPoint 切点
     * @throws Throwable 异常
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        LOGGER.info("类名：" + clazzName);
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("方法名：" + methodName);
//        String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
//        Object[] args = joinPoint.getArgs();
//        for(int k=0; k<args.length; k++){
//            LOGGER.info("参数名：" + paramNames[k] + "，参数值：" + JSON.toJSONString(args[k]));
//        }
    }

    /**
     * 后置通知
     * 打印返回值日志
     * @param ret 返回值
     * @throws Throwable 异常
     */
    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        LOGGER.info("类名：" + clazzName);
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("方法名：" + methodName);
        LOGGER.info("返回值 : " + JSON.toJSONString(ret));
    }

    /**
     * 异常通知
     * 打印异常日志
     * @param  ex 返回值
     * @throws Throwable 异常
     */
    @AfterThrowing(throwing = "ex",pointcut = "pointCut()")
    public void doAfterThrowing(JoinPoint joinPoint, Object ex) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        LOGGER.error("类名：" + clazzName);
        String methodName = joinPoint.getSignature().getName();
        LOGGER.error("方法名：" + methodName);
        LOGGER.error("异常值 : " + JSON.toJSONString(ex));
    }

    /**
     * 得到方法参数的名称
     * @param cls 类
     * @param clazzName 类名
     * @param methodName 方法名
     * @return 参数名数组
     * @throws NotFoundException 异常
     */
    private static String[] getFieldsName(Class<?> cls, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++){
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名
        }
        return paramNames;
    }
}
