package main;

import util.taskManager.InMemoryTaskManager;
import util.taskManager.Managers;

import java.io.*;
import java.util.*;
// реализовать сохранение, добавить возможность удалять и добавлять подзадачи, реализовать историю

public class Main {
    static protected final int id = 1;
    static private boolean programStatus = true;
    static protected HashMap<Integer, Object> tasks = new HashMap<>();

    public static void main(String[] args) {
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] task = line.split(":");
//                char c = task[1].charAt(task[1].length() - 1);
//                char g = '}';
//                if (g == c){
//                    HashMap<String, TaskProgress> hashMap = new HashMap<>();
//                    String [] strings = task[1].split("`");
//                    strings[5] = strings[5].substring(1,strings[5].length() - 1);
//                    String[] subTasks = strings[5].split(", ");
//                    for(String tasks : subTasks) {
//                       String[] t = tasks.split("=");
//
//                       if(TaskProgress.DONE.equals(t)) {
//                           hashMap.put(t[0],TaskProgress.DONE);
//                       } else if (t.equals(TaskProgress.IN_PROGRESS)) {
//                           hashMap.put(t[0],TaskProgress.IN_PROGRESS);
//                       } else {
//                           hashMap.put(t[0],TaskProgress.NEW);
//                       }
//
//                    }
//                    SubTask subTask = new SubTask();
//                    subTask.subTasks = hashMap;
//                    Epic epic = new Epic(strings[0],strings[1],Boolean.parseBoolean(strings[2]),
//                            Boolean.parseBoolean(strings[3]), Integer.parseInt(strings[4]), subTask);
//                    tasks.put(Integer.parseInt(task[0]), epic);
//                } else {
//                    String [] strings = task[1].split("`");
//                    Task tasq = new Task(strings[0],strings[1],Boolean.parseBoolean(strings[2]),
//                            Boolean.parseBoolean(strings[3]), Integer.parseInt(strings[4]));
//                    tasks.put(Integer.parseInt(task[0]), tasq);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        getMainMenu();
//        try {
//            FileWriter writer = new FileWriter("tasks.txt");
//            for (Integer key : tasks.keySet()) {
//                Task task = (Task) tasks.get(key);
//                if (!task.isEpic) {
//                    String line = key + ":" + task.name + "`" + task.description + "`" + task.isCompleted +
//                            "`" + task.isEpic + "`" + task.id + "\n";
//                    writer.write(line);
//                } else {
//                    Epic epic = (Epic) tasks.get(key);
//                    String line = key + ":" + epic.name + "`" + epic.description + "`" + epic.isCompleted +
//                            "`" + epic.isEpic + "`" + epic.id + "`" + epic.subTasks + "\n";
//                    writer.write(line);
//                }
//            }
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
                    case 2 -> defaultTaskManager.getDefault().getAllTasks(tasks);
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
                System.out.println("3.Обновить статус задачи");
                System.out.println("4.Удалить задачу по id");
                System.out.println("5.Изиенить, удалить подзадачу");
                System.out.println("6.Вернуться в основоное меню");
                switch (scanner.nextInt()) {
                    case 1 -> defaultTaskManager.getDefault().deleteAllTask(tasks);
                    case 2 -> defaultTaskManager.getDefault().getTaskById(tasks);
                    case 3 -> defaultTaskManager.getDefault().updateTaskStatus(tasks);
                    case 4 -> defaultTaskManager.getDefault().deleteTaskById(tasks);
                    case 5 -> defaultTaskManager.getDefault().editSubTasks(tasks);
                    case 6 -> cycle = false;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Ожидаю выбор пункта \n");
            getTaskMenu();
        }
    }

    static Integer setId() {
        int idForTasks = 1;
        boolean cycle = true;
        while (cycle){
            if (tasks.containsKey(idForTasks)){
                idForTasks++;
            } else {
                cycle = false;
            }
        }
        return idForTasks;
    }
}

