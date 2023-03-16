package util.taskManager;

import java.util.HashMap;

interface TaskManager {

    void getTypeOfTask();
    void getAllTasks(HashMap<Integer, Object> tasks);
    void getTaskById(HashMap<Integer, Object> tasks);

    void deleteAllTask(HashMap<Integer, Object> tasks);
    void updateTaskStatus(HashMap<Integer, Object> tasks);
    void deleteTaskById(HashMap<Integer, Object> tasks);

}
