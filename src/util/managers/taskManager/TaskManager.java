package util.managers.taskManager;

import main.Task;

import java.util.List;

interface TaskManager <E extends Task> {

    void getTypeOfTask();
    <E extends Task> void getAllTasks(List <E> eList);
    void getTaskById();

    void deleteAllTask();
    void updateTaskStatus();
    void deleteTaskById();

}
