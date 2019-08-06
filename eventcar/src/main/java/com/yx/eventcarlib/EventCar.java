package com.yx.eventcarlib;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by YX, Date on 2019/8/6.
 */
public class EventCar {

    //装载应用内所有组件中所有的订阅方法的容器
    private Map<Object,List<MethodManager>> methodMap;

    private Handler handler;

    private ExecutorService executorService;

    public static EventCar getDefault(){
        return SingleEventCar.car;
    }

    private static class SingleEventCar{
         private static final EventCar car = new EventCar();
    }

    private EventCar() {
        methodMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    /**
     * 注册订阅者,收集当前组件的订阅方法
     * @param obj
     */
    public void register(Object obj){
        List<MethodManager> methodManagers = methodMap.get(obj);
        if( methodManagers==null || methodManagers.size()==0){
            methodManagers = findMethodByObj(obj);
            methodMap.put(obj,methodManagers);
        }
    }

    /**
     * 找方法
     * @param obj
     */
    private List<MethodManager> findMethodByObj(Object obj) {
        List<MethodManager> methodManagers = new ArrayList<>();
        //获取类对象
        Class<?> objClass = obj.getClass();
        //获取类中所有的方法
        Method[] declaredMethods = objClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            Subscribe subscribe = declaredMethod.getAnnotation(Subscribe.class);
            if(subscribe == null){
                continue;
            }
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            if(parameterTypes!=null && parameterTypes.length > 1){
                continue;
            }
            ThreadMode threadMode = subscribe.threadMode();
            methodManagers.add(new MethodManager(declaredMethod,parameterTypes[0],threadMode));
        }

        return methodManagers;
    }

    public void post(final Object setter){
        Iterator<Object> iterator = methodMap.keySet().iterator();
        while (iterator.hasNext()){
            final Object key = iterator.next();
            List<MethodManager> methodManagers = methodMap.get(key);
            for (final MethodManager methodManager : methodManagers) {
                if(setter.getClass().isAssignableFrom(methodManager.getType())){
                    switch (methodManager.getThreadMode()){
                        case MAIN:
                            if(Looper.myLooper()==Looper.getMainLooper()){
                                invokeMethod(key,methodManager.getMethod(),setter);
                            }else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invokeMethod(key,methodManager.getMethod(),setter);
                                    }
                                });
                            }
                            break;
                        case POSTING:
                            invokeMethod(key,methodManager.getMethod(),setter);
                            break;
                        case BACKGROUND:
                            if(Looper.myLooper()==Looper.getMainLooper()){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invokeMethod(key,methodManager.getMethod(),setter);
                                    }
                                });
                            }else {
                                invokeMethod(key,methodManager.getMethod(),setter);
                            }
                            break;
                    }

                }
            }
        }
    }

    public void unregister(Object setter){
        if(methodMap.get(setter)!=null){
            methodMap.remove(setter);
        }
    }

    private void invokeMethod(Object key, Method method, Object setter) {
        try {
            method.invoke(key,setter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
