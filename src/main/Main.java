package main;

import util.Saver;
import util.taskManager.InMemoryTaskManager;
import util.taskManager.Managers;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
//  счетчик для подзадач, когда они реальзуются впервые, реализовать историю

public class Main <E extends Task> implements Serializable {
    static private boolean programStatus = true;
    static protected List<Task> taskList = new ArrayList<>();
    static protected List<Epic> epicList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Saver saver = new Saver();
            if (saver.downloadData("task") == null) {
                System.out.println("Файл пуст, отдыхаем");
            } else {
                taskList = saver.downloadData("task");
            }

            if (saver.downloadData("epic") == null) {
                System.out.println("Файл пуст2222222 оттттдыъх");
            } else {
                epicList = saver.downloadData("epic");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        getMainMenu();
        try {
            new Saver<>().uploadData(taskList, "task");
            new Saver<>().uploadData(epicList, "epic");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getMainMenu() {
        Scanner scanner = new Scanner(System.in);
        Managers<InMemoryTaskManager> defaultTaskManager = new Managers<>();
        try {
            while (programStatus) {
                System.out.println("1.Добавить новую задачу");
                System.out.println("2.Получить список задач");
                System.out.println("3.Открыть менеджер задач");
                System.out.println("4.Завершить программу");
                switch (scanner.nextInt()) {
                    case 1 -> defaultTaskManager.getDefault().getTypeOfTask();
                    case 2 -> {
                        defaultTaskManager.getDefault().getAllTasks(taskList);
                        defaultTaskManager.getDefault().getAllTasks(epicList);
                    }
                    case 3 -> new Main().getTaskMenu();
                    case 4 -> programStatus = false;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Ожидаю выбор пункта \n");
            getMainMenu();
        }
        scanner.close();
    }

    public void getTaskMenu() {
        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
        Managers<InMemoryTaskManager> defaultTaskManager = new Managers<>();
        try {
            while (cycle) {
                System.out.println("1.Удалить все задачи");
                System.out.println("2.Найти задачу по id");
                System.out.println("3.Обновить статус задачи, изменить подзадачу");
                System.out.println("4.Удалить задачу по id");
                System.out.println("5.Изиенить, удалить подзадачу");
                System.out.println("6.Вернуться в основоное меню");
                switch (scanner.nextInt()) {
                    case 1 -> defaultTaskManager.getDefault().deleteAllTask(); // work
                    case 2 -> defaultTaskManager.getDefault().getTaskById();
                    case 3 -> defaultTaskManager.getDefault().updateTaskStatus();
                    case 4 -> defaultTaskManager.getDefault().deleteTaskById();
                    case 6 -> cycle = false;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Ожидаю выбор пункта \n");
            getTaskMenu();
        }
    }

    public boolean checkForDuplicate(String name) {

        List<String> namesList = new ArrayList<>();

        for(Epic epic : epicList) {
            namesList.add(epic.getName());
            for (SubTask subTask : epic.getSubTasksList()) {
                namesList.add(subTask.getName());
            }
        }

        for (Task task : taskList) {
            namesList.add(task.getName());
        }

        if (namesList.contains(name)) {
            return false;
        }
        return true;
    }

    public static Integer setId() {
        int i = 1;
        List<Integer> ids = new ArrayList<>();

        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                if (!ids.contains(task.getId())) {
                    ids.add(task.getId());
                }
            }
        }

        if (!epicList.isEmpty()) {
            for (Epic epic : epicList) {

                for (SubTask subTask : epic.subTasksList) {
                    if (!ids.contains(subTask.getId())) {
                        ids.add(subTask.getId());
                    }
                }
                if(!ids.contains(epic.getId())) {
                    ids.add(epic.getId());
                }
            }
        }

        while (ids.contains(i)) {
            i++;
        }
        System.out.println(ids);
        ids.add(i);
        return i;
    }

    public Task getAvailability(int id) {

        for (Epic epic : epicList) {
            if (epic.getId() == id) {
                return epic;
            } else {
                for (SubTask subTask : epic.subTasksList) {
                    if (subTask.getId() == id) {
                        return subTask;
                    }
                }
            }
        }

        for (Task task : taskList) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public static List<Task> getTaskList() {
        return taskList;
    }

    public static List<Epic> getEpicList() {
        return epicList;
    }
}

