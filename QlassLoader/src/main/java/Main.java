import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class Main {
    public static void main(String[] args){
        log.info("Version of Java: {}",System.getProperty("java.version"));
        log.info("Name of JVM: {} ", System.getProperty("java.vm.name"));
        log.info("Version of JVM:{} ", System.getProperty("java.vm.version"));
        log.info("Specification name of JVM: {} ",
                System.getProperty("java.vm.specification.name"));
        log.info("Specification version of JVM: {}",
                System.getProperty("java.vm.specification.version"));
        QlassLoader qlassLoader = new QlassLoader(System.getProperty("user.dir")+"/QlassLoader/src/main/resources");
        try{
            Class helloClass = qlassLoader.findClass("Hello");
            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(helloClass.newInstance());
        }catch (Exception e){
            log.error("Fail to invoke hello method",e);
        }
    }
}
