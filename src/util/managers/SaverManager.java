package util.managers;

import main.Task;
import util.TaskType;
import util.exception.ManagerSaveException;
import util.managers.historyManager.InMemoryHistoryManager;
import util.managers.historyManager.MyLinkedList;

import java.io.*;
import java.util.*;

public class SaverManager<E extends Task> {

    public <E extends Task> void uploadData(List<E> eList, TaskType type)  {
        try {
            FileOutputStream fos = null;
            if (type.equals(TaskType.TASK))
                fos = new FileOutputStream("tasks.ser");
            else
                fos = new FileOutputStream("epic.ser");

            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(eList);
            out.close();
            fos.close();
            System.out.printf("Serialized data is saved in " + type + ".ser \n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void uploadData(MyLinkedList<Object> list, HashMap<Integer, MyLinkedList.Nodes<Object>> hashMap,
                           TaskType type)
    {
        try {
            FileOutputStream fos1 = new FileOutputStream("mll.ser");
            FileOutputStream fos2 = new FileOutputStream("nm.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos1);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos.writeObject(list);
            oos2.writeObject(hashMap);
            oos2.close();
            oos.close();
            System.out.printf("Serialized data is saved in " + type + ".ser \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<? extends Task> downloadData(TaskType type) {
        FileInputStream fis;
        List<E> eList;
        try {
            if (type.equals(TaskType.TASK))
                fis = new FileInputStream("tasks.ser");
            else
                fis = new FileInputStream("epic.ser");


            if (fis.available() > 0) {
                ObjectInputStream in = new ObjectInputStream(fis);
                eList = (List<E>) in.readObject();
                in.close();
                fis.close();
                return eList;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadHistoryData() {
        try {
            FileInputStream fis = new FileInputStream("mll.ser");
            FileInputStream fis2 = new FileInputStream("nm.ser");
            if (fis.available() > 0) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                InMemoryHistoryManager.setMll((MyLinkedList<Object>) ois.readObject());
                InMemoryHistoryManager.setNm((HashMap<Integer, MyLinkedList.Nodes<Object>>) ois2.readObject());
                ois2.close();
                ois.close();
            }
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
