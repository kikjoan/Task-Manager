package util.taskManager;

import main.*;
import util.TaskProgress;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager{

    public void getTypeOfTask() {
        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Задача имеет подзадачи? y/n, для выхода exit");
        try {
            while (cycle) {
                switch (scanner.nextLine()) {
                    case "y" -> {
                        cycle = false;
                        new Epic().setEpic();
                    }
                    case "n" -> {
                        cycle = false;
                        new Task().setTask();
                    }
                    case "exit" -> cycle = false;
                    default -> System.out.println("Некорректный ввод. Ожидаю ввод вида y/n");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ожидаю y/n, для выхода exit");
            getTypeOfTask();
        }
    }

    @Override
    public void getAllTasks(HashMap<Integer, Object> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач! \n");
        }
        tasks.forEach((id, task) -> {
            System.out.println(task.toString());
        });
    }

    @Override
    public void deleteAllTask(HashMap<Integer, Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;
        System.out.println("Ты уверен, что хочешь удалить все задачи? y/n");
        while (cycle) {
            switch (scanner.nextLine()) {
                case "y" -> {
                    tasks.clear();
                    cycle = false;
                }
                case "n" -> cycle = false;
                default -> System.out.println("Ожидается y/n");
            }
        }
    }


    @Override
    public void deleteTaskById(HashMap<Integer, Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Введите id задачи: ");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            System.out.println("Ты уверен, что хочешь удалить задачу " + tasks.get(id) + "? y/n");
            boolean cycle = true;
            while (cycle) {
                String choice = scanner1.nextLine();
                switch (choice) {
                    case "y" -> {
                        System.out.println("Задача " + tasks.get(id) + " удалена \n");
                        tasks.remove(id);
                        cycle = false;
                    }
                    case "n" -> {
                        System.out.println("Задача " + tasks.get(id) + " не удалена \n");
                        cycle = false;
                    }
                    default -> System.out.println("Неизвестная команда. Ожидаю y/n");
                }
            }
        } else {
            System.out.println("Задача с таким id не найдена \n");
        }
    }

    @Override
    public void getTaskById(HashMap<Integer, Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи: ");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id).toString() + "\n");
        } else {
            System.out.println("Задача с таким id не найдена \n");
        }
    }

    @Override
    public void updateTask(HashMap<Integer, Object> tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи, которую нужно обновить: ");
        getAllTasks(tasks);

        int taskId = scanner.nextInt();
        Task taskToUpdate = (Task) tasks.get(taskId);

        if (taskToUpdate != null) {
            if (!taskToUpdate.isEpic()){
                System.out.println("Текущий статус задачи: " + taskToUpdate.isCompleted());
                System.out.println("Введите новый статус задачи (true/false): ");
                boolean newStatus = scanner.nextBoolean();
                taskToUpdate.setCompleted(newStatus);
            } else {
                Epic epicToUpdate = (Epic) tasks.get(taskId);
                System.out.println("Текущий статус задачи: " + epicToUpdate.isCompleted());
                setCompleted(epicToUpdate.getSubTasks(), epicToUpdate);
            }
        } else {
            System.out.println("Задачи с id " + taskId + " не найдено");
        }
    }

    protected void setCompleted(HashMap<String, TaskProgress> subTasks, Epic epicToUpdate) {

        boolean cycle = true;
        boolean cycle2 = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вот подзадачи данной задачи: " + subTasks);
        if (subTasks.size() == 1) {
            while (cycle) {
                System.out.println("Введите новый статус для подзадачи. done/in progress");
                String status = scanner.nextLine();
                if (status.equals("done")) {
                    for (String name : subTasks.keySet()) {
                        subTasks.replace(name, TaskProgress.DONE);
                        cycle = false;
                        System.out.println("Терерь позадача " + name + " имеет статус Done \n");
                    }
                } else if (status.equals("in progress")) {
                    for (String name : subTasks.keySet()) {
                        subTasks.replace(name, TaskProgress.IN_PROGRESS);
                        cycle = false;
                        System.out.println("Терерь позадача " + name + " имеет статус In_Progress \n");
                    }
                } else {
                    System.out.println("Неизвестная команда. Одидаю done/in progress \n");
                }
            }
        } else {
            while (cycle2) {
                System.out.println("Введите название подзадачи, которую нужно изменить");
                String subTask = scanner.nextLine();
                if (subTasks.containsKey(subTask)) {
                    System.out.println("Введите новый статус для подзадачи. done/in progress");
                    String status = scanner.nextLine();
                    if (status.equals("done")) {
                        subTasks.replace(subTask, TaskProgress.DONE);
                    } else if (status.equals("in progress")) {
                        subTasks.replace(subTask, TaskProgress.IN_PROGRESS);
                    } else {
                        System.out.println("Неизвестная команда. Одидаю done/in progress \n");
                    }
                } else {
                    System.out.println("Такой подзадачи нет, попробуйте вновь! \n");
                }
                System.out.println("Изменить еще одну подзадачу? y/n");
                boolean cycleOfChoice = true;
                while (cycleOfChoice) {
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "y" -> {
                            cycleOfChoice = false;
                        }
                        case "n" -> {
                            cycle2 = false;
                            cycleOfChoice = false;
                        }
                        default -> System.out.println("Неизвестная команда. Ожидаю y/n \n");
                    }
                }
            }
        }

        int numberOfCompletedSubTasks = 0;
        for (String name : subTasks.keySet()) {
            if (subTasks.get(name).equals(TaskProgress.DONE)) {
                numberOfCompletedSubTasks++;
            }
        }
        boolean setCompleted = numberOfCompletedSubTasks == subTasks.size();
        epicToUpdate.setCompleted(setCompleted);
    }
}
