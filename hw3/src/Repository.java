import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Repository {
    
    public static <T> void write(T obj) throws FileNotFoundException, IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(obj.getClass().getName() + ".bin"))) {
            output.writeObject(obj);
        }
    }

    public static Object load(String fileName) throws ClassNotFoundException, IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName + ".bin"))) {
            return in.readObject();
        }
    }
}
