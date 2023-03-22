package main;

import util.TaskProgress;
import java.util.Scanner;

public class SubTask extends Task{
    TaskProgress taskProgress = TaskProgress.NEW;

    private SubTask(String name, int id, String type, TaskProgress taskProgress) {
        super(name,id, type);
        this.taskProgress = taskProgress;
    }

    public SubTask setSubTasks() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введи название подзадачи");
        String name = scanner.nextLine();
        id = Main.setId();

        return new SubTask(name, id, "Subtask" , taskProgress);

    }

    public SubTask() {}

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public void setName(String name) {
        this.name = name;
    }
}
