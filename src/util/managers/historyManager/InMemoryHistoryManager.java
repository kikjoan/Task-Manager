package util.managers.historyManager;

import main.Task;
import util.managers.historyManager.HistoryManager;
import util.managers.historyManager.MyLinkedList;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager<Task> {

    protected static MyLinkedList<Object> mll = new MyLinkedList<>();
    protected static HashMap<Integer,MyLinkedList.Nodes<Object>> nm = new HashMap<>();


    @Override
    public void add(Task task) {
        int id = task.getId();
//        if (nm.containsKey(id)) {
//            remove(id);
//            nm.put(id, mll.linkLast(id));
//        } else {
//            nm.put(id, mll.linkLast(id));
//        }
        remove(id);
        nm.put(id, mll.linkLast(id));
    }

    @Override
    public void remove(int id) {
        if (nm.containsKey(id)) {
            mll.removeNode(nm.get(id));
            nm.remove(id);
        }
    }

    public void clear() {
        List list = getHistory();
        list.forEach((o) -> remove((Integer) o));
    }


    @Override
    public List<Integer> getHistory() {
        return mll.getTasks();
    }

    public static MyLinkedList<Object> getMll() {
        return mll;
    }
    public static HashMap<Integer, MyLinkedList.Nodes<Object>> getNm() {
        return nm;
    }

    public static void setMll(MyLinkedList<Object> mll) {
        InMemoryHistoryManager.mll = mll;
    }

    public static void setNm(HashMap<Integer, MyLinkedList.Nodes<Object>> nm) {
        InMemoryHistoryManager.nm = nm;
    }
}