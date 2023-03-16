package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Epic extends Task {

    public Epic(String name, String description, boolean isCompleted, boolean isEpic, int id, List<SubTask> subTasks) {
        super(name, description, isCompleted, isEpic, id);
        this.subTasks = subTasks;
    }

    public Epic() {
    }

    public void setEpic() {

        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        subTask.setSubTasks();

        Main.tasks.put(id, (new Epic(name, description, false, true, id, subTasks)));
    }

    @Override
    public String toString() {
        return "Название - '" + name + "'; " +
                "ID - '" + id + "'; " +
                "Описание - '" + description + "'; " +
                "Выполнена - '" + isCompleted + "'; " +
                "Подзадачи - '" + subTask.toString() + "'";
    }

    public SubTask getSubTask() {
        return subTasks;
    }
}
