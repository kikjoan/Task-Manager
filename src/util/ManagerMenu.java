package util;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Scanner;

public class ManagerMenu {
    private static void delete(HashMap<Integer, Object> tasks) {
        tasks.clear();
    }

    public void checkToDelete(HashMap<Integer, Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;
        System.out.println("Ты уверен, что хочешь удалить все задачи? y/n");
        while (cycle) {
            switch (scanner.nextLine()) {
                case "y" -> {
                    delete(tasks);
                    cycle = false;
                }
                case "n" -> cycle = false;
                default -> System.out.println("Ожидается y/n");
            }
        }
    }

    public void getTaskById(HashMap<Integer,Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи: ");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id).toString() + "\n");
        } else {
            System.out.println("Задача с таким id не найдена \n");
        }
    }

    public void deleteTaskById(HashMap<Integer,Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи: ");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Задача с таким id не найдена \n");
        }
    }
}
