import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * self defined class loader
 * */
@Slf4j
public class QlassLoader extends ClassLoader{

    private final String classPath;

    public QlassLoader(String classPath){
        super(null);
        log.info("Load class from QlassLoader! classPath={}",classPath);
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String pathOfFile2Read = classPath + "/"+ name +".xlass";
        log.info("Path of the file to be read: {}",pathOfFile2Read);
        File file = new File(pathOfFile2Read);
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try{
            byteArrayOutputStream = new ByteArrayOutputStream();
            fileInputStream = new FileInputStream(file);
            int byteData = 0;
            while((byteData = fileInputStream.read())!=-1){
                byteArrayOutputStream.write(255- byteData);
            }
            byteArrayOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return defineClass(name, bytes,0, bytes.length);
        }catch (FileNotFoundException e){
            log.error("Can not find file: {}", pathOfFile2Read);
        }catch (IOException e){
            log.error("Fail to read from file: {}", pathOfFile2Read);
        }finally {
            try{
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
                if(byteArrayOutputStream!=null){
                    byteArrayOutputStream.close();
                }
            }catch (IOException e){
                log.error("Fail to close streams");
            }
        }
        log.error("Fail to load class from self-defined class loader!");
        return super.findClass(name);
    }
}
