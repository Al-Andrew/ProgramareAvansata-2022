package pa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        Main exe = new Main();

        exe.compulsory();
    }

    public void compulsory() throws ClassNotFoundException, MalformedURLException {
        var classLoader = new MyClassLoader();
        String pathname = "C:\\Users\\Andrei\\school\\PorgramareAvanstata\\ProgramareAvansata-2022\\Laboratorul12\\src\\main\\java";
        String classname = "pa.MyClass";
        File path = new File(pathname);
        URL url = path.toURI().toURL();
        classLoader.addURL(url);

        System.out.println("Methods:");
        for (Method m : Class.forName(classname).getMethods()) {
            System.out.printf("%s %s(", m.getReturnType(), m.getName());
            for (var parameter : m.getParameterTypes())
                System.out.printf("%s, ", parameter.getName());
            System.out.println(")");
        }

        System.out.println();
        System.out.println("Test methods");
        for (Method m : Class.forName(classname).getMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                try {
                    m.invoke(null);
                } catch (Throwable ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
