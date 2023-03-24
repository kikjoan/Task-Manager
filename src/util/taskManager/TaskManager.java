package util.taskManager;

import main.Epic;
import main.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

interface TaskManager <E extends Task> {

    void getTypeOfTask();
    <E extends Task> void getAllTasks(List <E> eList);
    void getTaskById();

    void deleteAllTask();
    void updateTaskStatus();
    void deleteTaskById();

}
