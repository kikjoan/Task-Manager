package util.managers.taskManager;

import main.Epic;
import main.Main;
import main.Task;
import util.exception.ManagerSaveException;
import util.TaskType;
import util.managers.SaverManager;
import util.managers.historyManager.InMemoryHistoryManager;

import java.io.IOException;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{
    SaverManager<Task> saverManager = new SaverManager<>();
    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteTaskById() {
        super.deleteTaskById();
        save();
    }

    @Override
    public void getTaskById() {
        super.getTaskById();
        save();
    }

    @Override
    public void updateTaskStatus() {
        super.updateTaskStatus();
        save();
    }

    @Override
    protected void setCompleted(Epic epic) {
        super.setCompleted(epic);
        save();
    }

    @Override
    public void editSubTasks(Epic epic) {
        super.editSubTasks(epic);
        save();
    }

    public void save() {
        saverManager.uploadData(Main.getEpicList(), TaskType.EPIC);
        saverManager.uploadData(Main.getTaskList(), TaskType.TASK);
        saverManager.uploadData(InMemoryHistoryManager.getMll(), InMemoryHistoryManager.getNm(), TaskType.HISTORY);
    }

    public void load() {
        Main.setEpicList((List<Epic>) saverManager.downloadData(TaskType.EPIC));
        System.out.println("OK");
        Main.setTaskList((List<Task>) saverManager.downloadData(TaskType.TASK));
        System.out.println("OK");
        saverManager.downloadHistoryData();
        System.out.println("OK");

    }
}
