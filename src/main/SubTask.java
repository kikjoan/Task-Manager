package main;

import util.TaskProgress;;
import java.util.HashMap;
import java.util.Scanner;

public class SubTask extends Task{

     protected void setSubTasks(HashMap<String, TaskProgress> subTasks) {

        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;

        System.out.println("Введи название подзадачи");
        while (cycle) {
            String nameOfSubTask = scanner.nextLine();
            if (!(subTasks.isEmpty())) {
                for (String name : subTasks.keySet()) {
                    if (name.equals(nameOfSubTask)) {
                        System.out.println("Такая подзадача уже существует! \n");
                    } else {
                        subTasks.put(nameOfSubTask, TaskProgress.NEW);
                        break;
                    }
                }
            } else {
                subTasks.put(nameOfSubTask, TaskProgress.NEW);
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
}
