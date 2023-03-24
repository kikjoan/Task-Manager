package util.historyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager extends HistoryManager implements Serializable {
    private static Integer[] historyOfChoiceIds = new Integer[10];
    private static int count = historyOfChoiceIds.length;

    public static void checkIn(int id) {
        if (count == -1) {
            for (int i = 9; i > 0 ; i--) {
                historyOfChoiceIds[i] =  historyOfChoiceIds[i - 1];
            }
            historyOfChoiceIds[0] = id;
        } else {
            historyOfChoiceIds[count] = id;
            count--;
        }
    }

    @Override
    public String toString() {

        List<Object> s = new ArrayList<>();

        for (Object i : historyOfChoiceIds ) {
            if (i != null) {
                s.add(i);
            }
        }

        return " История изменений задач по id: " + s.toString();
    }

    public static void setHistoryOfChoiceIds(Integer[] historyOfChoiceIds) {
        InMemoryHistoryManager.historyOfChoiceIds = historyOfChoiceIds;
    }

    public static Integer[] getHistoryOfChoiceIds() {
        return historyOfChoiceIds;
    }
}
