package util;

import main.Task;

import java.io.*;
import java.util.List;

public class Saver <E extends Task>{

    public <E extends Task> void uploadData(List<E> eList, String type) throws FileNotFoundException {

        FileOutputStream fos = null;

        if (type.equals("task")){
            fos = new FileOutputStream("tasks.ser");
        } else if (type.equals("epic")){
            fos = new FileOutputStream("epic.ser");
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(eList);
            out.close();
            fos.close();
            System.out.printf("Serialized data is saved in tasks.ser \n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public List<E> downloadData(String type) throws FileNotFoundException {
        List<E> eList;
        FileInputStream fis;


        if (type.equals("task")){
            fis = new FileInputStream("tasks.ser");
        } else {
            fis = new FileInputStream("epic.ser");
        }

        try {
            if (fis.available() > 0) {
                ObjectInputStream in = new ObjectInputStream(fis);
                eList = (List<E>) in.readObject();
                in.close();
                return eList;
            }
            fis.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
