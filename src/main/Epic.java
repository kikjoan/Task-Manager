package main;

import util.TaskProgress;

import java.util.HashMap;
import java.util.Scanner;

public class Epic extends Task {

    public HashMap<String, TaskProgress> subTasks = new HashMap<>();

    public Epic(String name, String description, boolean isCompleted, boolean isEpic, int id,
                HashMap<String, TaskProgress> subTasks) {
        super(name, description, isCompleted, isEpic, id);
        this.subTasks = subTasks;
    }

    public Epic() {
    }

    public void setEpic() {

        SubTask subTask = new SubTask();
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        subTask.setSubTasks(subTasks);

        Main.tasks.put(id, (new Epic(name, description, false, true, id, subTasks)));
    }

    @Override
    public String toString() {
        return "Название - '" + name + "'; " +
                "ID - '" + id + "'; " +
                "Описание - '" + description + "'; " +
                "Выполнена - '" + isCompleted + "'; " +
                "Подзадачи - '" + subTasks.toString() + "'";
    }

    public HashMap<String, TaskProgress> getSubTasks() {
        return subTasks;
    }
}
