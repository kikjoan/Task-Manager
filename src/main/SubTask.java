package main;

import util.TaskProgress;;
import java.util.HashMap;
import java.util.Scanner;

public class SubTask extends Task{

    SubTask(String name, String description, boolean isCompleted, boolean isEpic, int id) {
        super(name, description, isCompleted, isEpic, id);
    }

    public void setSubTasks() {

        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;

        System.out.println("Введи название подзадачи");
        while (cycle) {
            String nameOfSubTask = scanner.nextLine();
            if (!(subTaskHash.isEmpty())) {
                for (String name : subTaskHash.keySet()) {
                    if (name.equals(nameOfSubTask)) {
                        System.out.println("Такая подзадача уже существует! \n");
                    } else {
                        subTaskHash.put(nameOfSubTask, TaskProgress.NEW);
                        break;
                    }
                }
            } else {
                subTaskHash.put(nameOfSubTask, TaskProgress.NEW);
            }
            System.out.println("Добавить еще одну подзадачу? y/n");
            switch (scanner.nextLine()) {
                case "y" -> System.out.println("Введите название");
                case "n" -> cycle = false;
                default -> {
                    System.out.println("Некорректный ввод. Добавть подзадачу можно в менеджере задач");
                    cycle = false;
                }
            }
        }
    }

    public SubTask() {}

    @Override
    public String toString() {
         return subTaskHash.toString();
    }

    public HashMap<String, TaskProgress> getSubTaskHash() {
        return subTaskHash;
    }
}
