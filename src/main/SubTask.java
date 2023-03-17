package main;

import util.TaskProgress;;
import java.util.List;
import java.util.Scanner;

public class SubTask extends Task{
    TaskProgress taskProgress;

    private SubTask(String name, int id, TaskProgress taskProgress) {
        this.id = Main.setId();
        this.taskProgress = TaskProgress.NEW;
    }

    public SubTask setSubTasks() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введи название подзадачи");
        String name = scanner.nextLine();

        return new SubTask(name, id, taskProgress);
    }

    public SubTask() {}

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }
}
