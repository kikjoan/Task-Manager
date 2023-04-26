package util.managers.historyManager;

import main.Task;
import java.util.List;

public interface HistoryManager <T extends Task> {
    void add (T t);
    void remove(int id);

    public List<Integer> getHistory();
}
