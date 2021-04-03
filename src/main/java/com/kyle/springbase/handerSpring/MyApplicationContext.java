package com.kyle.springbase.handerSpring;

import com.kyle.springbase.MyBeanPostProcessor;
import com.kyle.springbase.handerSpring.annotation.MyAutowire;
import com.kyle.springbase.handerSpring.annotation.MyComponent;
import com.kyle.springbase.handerSpring.annotation.MyComponentScan;
import com.kyle.springbase.handerSpring.annotation.MyLazy;
import com.kyle.springbase.handerSpring.annotation.MyScope;
import com.kyle.springbase.handerSpring.domain.MyBeanDefinition;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunkai-019
 * @title: MyApplicationContext
 * @projectName springbase
 * @description: 应用上下文
 * @date 2021/4/3 17:22
 */
public class MyApplicationContext {

    private Class clz;

    /**
     * BeanDefinitionMap
      */
    private Map<String, MyBeanDefinition> myBeanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * 单例池
     */
    private Map<String, Object> mySingletonBeanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 放spring扫描到的所有bean后置处理器
     */
    List<MyBeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public MyApplicationContext(Class clz) {
        this.clz = clz;
        List<Class> classList = scanClass2List(clz);

//        基于class创建BeanDefinition对象
//        只有类上设置了 @MyComponent注解的才能被spring管理
        for (Class aClass : classList) {
            if (aClass.isAnnotationPresent(MyComponent.class)) {
//                装载所有的bean后置处理器
                if (MyBeanPostProcessor.class.isAssignableFrom(aClass)) {
                    try {
                        MyBeanPostProcessor instance = (MyBeanPostProcessor) aClass.getDeclaredConstructor().newInstance();
                        beanPostProcessorList.add(instance);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }

                MyComponent annotation = (MyComponent) aClass.getAnnotation(MyComponent.class);
                String beanName = annotation.value();
                myBeanDefinitionMap.put(beanName, getMyBeanDefinition(aClass));
            }
        }

//        创建单例对象
        for (String beanName : myBeanDefinitionMap.keySet()) {
            MyBeanDefinition myBeanDefinition = myBeanDefinitionMap.get(beanName);
            if ("singleton".equals(myBeanDefinition.getScope())) {
                //先要判断单例池中有没有这个bean
                if (!mySingletonBeanDefinitionMap.containsKey(beanName)) {
                    //没有才创建
                    mySingletonBeanDefinitionMap.put(beanName, doCreateBean(beanName, myBeanDefinition));
                }
            }
        }
    }

    private Object doCreateBean(String beanName, MyBeanDefinition myBeanDefinition) {
        Class clz = myBeanDefinition.getClz();
        try {
            //1、使用反射实例化对象
            Object o = clz.getDeclaredConstructor().newInstance();

//            2、设置bean中的属性
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MyAutowire.class)) {
                    //属性有autowired注解才能被注入
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(o, bean);
                }
            }
//           3、 aware，设置bean的beanName
            if (o instanceof MyBeanNameAware) {
                ((MyBeanNameAware)o).setBeanName(beanName);
            }

//            初始化前调用bean的后置处理器
            for (MyBeanPostProcessor myBeanPostProcessor : beanPostProcessorList) {
                o = myBeanPostProcessor.postProcessBeforeInitialization(o, beanName);
            }

//            4、初始化,也是和aware一样会回调afterPropertiesSet方法
            if (o instanceof MyInitializingBean) {
                ((MyInitializingBean) o).afterPropertiesSet();
            }

//            初始化调用bean的后置处理器
            for (MyBeanPostProcessor myBeanPostProcessor : beanPostProcessorList) {
                o = myBeanPostProcessor.postProcessAfterInitialization(o, beanName);
            }

            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MyBeanDefinition getMyBeanDefinition(Class aClass) {
        //将beanName存入BeanDefinitionMap中作为key，value为MyBeanDefinition对象
        MyBeanDefinition myBeanDefinition = new MyBeanDefinition();
//                看class上有没有scope注解
        if (aClass.isAnnotationPresent(MyScope.class)) {
            MyScope myScopeAnnotaion = (MyScope) aClass.getAnnotation(MyScope.class);
            myBeanDefinition.setScope(myScopeAnnotaion.value());
        } else {
            //如果没有scope，默认是单例
            myBeanDefinition.setScope("singleton");
        }

        //                看class上有没有MyLazy注解，如果没有就是true
        if (aClass.isAnnotationPresent(MyLazy.class)) {
            MyLazy myLazyAnnotation = (MyLazy) aClass.getAnnotation(MyLazy.class);
            myBeanDefinition.setLazy(myLazyAnnotation.value());
        }else {
            myBeanDefinition.setLazy(true);
        }
        myBeanDefinition.setClz(aClass);
        return myBeanDefinition;
    }

    private List<Class> scanClass2List(Class clz) {
        List<Class> classList = new ArrayList<>();

//        扫描获取class(扫描的是编译后的class文件夹)
        MyComponentScan myComponentScan = (MyComponentScan) clz.getAnnotation(MyComponentScan.class);
        String path = myComponentScan.value();
        String[] pathArray = path.split(",");
        for (int i = 0; i < pathArray.length; i++) {
            path= pathArray[i];
            path = path.replace(".", "/");

            ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
            URL url = classLoader.getResource(path);
            File file = new File(url.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    absolutePath = absolutePath.substring(absolutePath.indexOf("com"),absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace("\\", ".");
                    //拿到类路径后，将其类放入到list中
                    try{
                        Class<?> loadClass = classLoader.loadClass(absolutePath);
                        classList.add(loadClass);
                    }catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return classList;
    }

    public Object getBean(String beanName) {
        //要看beanName是原型的还是单例的
        MyBeanDefinition myBeanDefinition = myBeanDefinitionMap.get(beanName);
        String scope = myBeanDefinition.getScope();
        if ("prototype".equals(scope)) {
            //创建对象
            return doCreateBean(beanName, myBeanDefinition);
        } else if ("singleton".equals(scope)){
            //单例的话从单例池中拿
//            如果单例池中没有这个bean，则需要重新创建
            if (!mySingletonBeanDefinitionMap.containsKey(beanName)) {
                Object o = doCreateBean(beanName, myBeanDefinition);
                mySingletonBeanDefinitionMap.put(beanName, o);
                return o;
            }
            return mySingletonBeanDefinitionMap.get(beanName);
        }
        return null;
    }
}
