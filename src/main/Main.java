package main;

import util.Saver;
import util.managers.taskManager.Managers;
import util.managers.historyManager.*;
import util.managers.taskManager.*;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;



public class Main <E extends Task> implements Serializable {
    private static boolean programStatus = true;
    private static List<Task> taskList = new ArrayList<>();
    private static List<Epic> epicList = new ArrayList<>();
    private static List<Integer> ids = new ArrayList<>();

    private static final InMemoryHistoryManager defaultHistoryManager = new InMemoryHistoryManager();
    private static final InMemoryTaskManager defaultTaskManager = new Managers<>().defaultManager();

    public static void main(String[] args) {
        try {
            Saver saver = new Saver();
            taskList = Optional.ofNullable(saver.downloadData("task")).orElse(new ArrayList<>());
            epicList = Optional.ofNullable(saver.downloadData("epic")).orElse(new ArrayList<>());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        getAllIds();
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
        try {
            while (programStatus) {
                System.out.println("1.Добавить новую задачу");
                System.out.println("2.Получить список задач");
                System.out.println("3.Открыть менеджер задач");
                System.out.println("4.Завершить программу");
                switch (scanner.nextInt()) {
                    case 1 -> {
                        getAllIds();
                        defaultTaskManager.getTypeOfTask();
                    }
                    case 2 -> {
                        defaultTaskManager.getAllTasks(taskList);
                        defaultTaskManager.getAllTasks(epicList);
                        if (taskList.isEmpty() && epicList.isEmpty()) {
                            System.out.println("Задач нет! \n");
                        }
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
        try {
            while (cycle) {
                System.out.println("1.Удалить все задачи");
                System.out.println("2.Найти задачу по id");
                System.out.println("3.Обновить статус задачи, изменить подзадачу");
                System.out.println("4.Удалить задачу по id");
                System.out.println("5.История выбора id");
                System.out.println("6.Вернуться в основоное меню");
                switch (scanner.nextInt()) {
                    case 1 -> defaultTaskManager.deleteAllTask(); // work
                    case 2 -> defaultTaskManager.getTaskById(); //work
                    case 3 -> defaultTaskManager.updateTaskStatus(); //semi work
                    case 4 -> defaultTaskManager.deleteTaskById(); // work
                    case 5 -> System.out.println(defaultHistoryManager.getHistory());
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
        int id = 1;
        while (ids.contains(id)) {
            id++;
        }
        ids.add(id);
        return id;
    }

    public static void getAllIds() {

        List<Integer> list = new ArrayList<>();

        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                list.add(task.getId());
            }
        }

        if (!epicList.isEmpty()) {
            for (Epic epic : epicList) {
                for (SubTask subTask : epic.subTasksList) {
                    list.add(subTask.getId());
                }
                list.add(epic.getId());
            }
        }
        ids = list;
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

    public static InMemoryHistoryManager getDefaultHistoryManager() {
        return defaultHistoryManager;
    }
}

