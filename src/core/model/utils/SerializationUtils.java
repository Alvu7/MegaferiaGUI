package core.model.utils;

import java.io.*;

public class SerializationUtils {

    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            byte[] bytes = bos.toByteArray();

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(bis);
            return (T) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
