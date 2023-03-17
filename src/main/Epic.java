package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Epic extends Task {

    List<SubTask> subTasksList = new ArrayList<>();

    public Epic(String name, String description, boolean isCompleted, boolean isEpic, int id,
                List<SubTask> subTasksList) {
        super(name, description, isCompleted, isEpic, id);
        this.subTasksList = subTasksList;
    }

    public void setEpic() {

        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        addSubTask();

        Main.tasks.put(id, (new Epic(name, description, false, true, id, subTasksList)));
    }

    public void addSubTask() {
        Scanner scanner = new Scanner(System.in);
        SubTask subTask = new SubTask();

        subTasksList.add(subTask.setSubTasks());

        boolean cycle = true;
        while (cycle) {
            System.out.println("Добавить еще одну подзадачу? y/n");
            switch (scanner.nextLine()) {
                case "y" -> subTasksList.add(subTask.setSubTasks());
                case "n" -> cycle = false;
                default -> {
                    System.out.println("Некорректный ввод. Добавть подзадачу можно в менеджере задач");
                    cycle = false;
                }
            }
        }
        System.out.println("Эпик - " + getName() + " записан с позадачами :" + getNamesOfSubTasks().toString() + "\n");

    }

    public List<String> getNamesOfSubTasks() {
        List<String> names = new ArrayList<>();
        for (SubTask subTask : subTasksList) {
            names.add(subTask.getName());
        }
        return names;
    }

    @Override
    public String toString() {
        return "Название - '" + name + "'; " +
                "ID - '" + id + "'; " +
                "Описание - '" + description + "'; " +
                "Выполнена - '" + isCompleted + "'; " +
                "Подзадачи - '" + getNamesOfSubTasks().toString() + "'";
    }

    public List<SubTask> getSubTasksList() {
        return subTasksList;
    }

    public SubTask getSubTask(String name) {
        for (SubTask subTask : subTasksList) {
            if(subTask.getName().equals(name)) {
                return subTask;
            }
        }
        return null;
    }

    @Override
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Epic() {
    }
}
