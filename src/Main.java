import util.ManagerMenu;

import java.io.*;
import java.util.*;
// по возможности реализовать вывод подзадач без скобок

public class Main {
    static protected int id = 1;
    static private boolean programStatus = true;
    static protected HashMap<Integer, Object> tasks = new HashMap<>();

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] task = line.split(":");
                char c = task[1].charAt(task[1].length() - 1);
                char g = '}';
                if (g == c){
                    HashMap<String,String> hashMap = new HashMap<>();
                    String [] strings = task[1].split("`");
                    strings[5] = strings[5].substring(1,strings[5].length() - 1);
                    String[] subTasks = strings[5].split(", ");
                    for(String tasks : subTasks) {
                       String[] t = tasks.split("=");
                       hashMap.put(t[0],t[1]);
                    }
                    Epic epic = new Epic(strings[0],strings[1],Boolean.parseBoolean(strings[2]),
                            Boolean.parseBoolean(strings[3]), Integer.parseInt(strings[4]),hashMap);
                    tasks.put(Integer.parseInt(task[0]), epic);
                } else {
                    String [] strings = task[1].split("`");
                    Task tasq = new Task(strings[0],strings[1],Boolean.parseBoolean(strings[2]),
                            Boolean.parseBoolean(strings[3]), Integer.parseInt(strings[4]));
                    tasks.put(Integer.parseInt(task[0]), tasq);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getMainMenu();
        try {
            FileWriter writer = new FileWriter("tasks.txt");
            for (Integer key : tasks.keySet()) {
                Task task = (Task) tasks.get(key);
                if (!task.isEpic) {
                    String line = key + ":" + task.name + "`" + task.description + "`" + task.isCompleted +
                            "`" + task.isEpic + "`" + task.id + "\n";
                    writer.write(line);
                } else {
                    Epic epic = (Epic) tasks.get(key);
                    String line = key + ":" + epic.name + "`" + epic.description + "`" + epic.isCompleted +
                            "`" + epic.isEpic + "`" + epic.id + "`" + epic.subTasks + "\n";
                    writer.write(line);
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                    case 1 -> new Main().getTypeOfTask();   // Добавить новую задачу
                    case 2 -> new Main().getAllTasks();     //получить список задач
                    case 3 -> new Main().getTaskMenu();     // открыть менеджер задач
                    case 4 -> programStatus = false;        // закрыть прогу
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Ожидаю выбор пункта \n");
            getMainMenu();
        }
        scanner.close();
    }

    private void getTaskMenu() {
        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
        try {
            while (cycle) {
                System.out.println("1.Удалить все задачи");
                System.out.println("2.Найти задачу по id");
                System.out.println("3.Обновить задачу");
                System.out.println("4.Удалить задачу по id");
                System.out.println("5.Вернуться в основоное меню");
                switch (scanner.nextInt()) {
                    case 1 -> new ManagerMenu().checkToDelete(tasks);
                    case 2 -> new ManagerMenu().getTaskById(tasks);
                    case 3 -> {
                        System.out.println("Введите id задачи, которую нужно обновить: ");
                        getAllTasks();
                        int taskId = scanner.nextInt();
                        Task taskToUpdate = (Task) (tasks.get(taskId));
                        if (taskToUpdate != null) {
                            if (!taskToUpdate.isEpic){
                                System.out.println("Текущий статус задачи: " + taskToUpdate.isCompleted);
                                System.out.println("Введите новый статус задачи (true/false): ");
                                boolean newStatus = scanner.nextBoolean();
                                taskToUpdate.setCompleted(newStatus);
                            } else {
                                Epic epicToUpdate = (Epic) tasks.get(taskId);
                                System.out.println("Текущий статус задачи: " + epicToUpdate.isCompleted);
                                epicToUpdate.setCompleted();
                            }
                        } else {
                            System.out.println("Задачи с id " + taskId + " не найдено");
                        }
                    }
                    case 4 -> new ManagerMenu().deleteTaskById(tasks);
                    case 5 -> cycle = false;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Ожидаю выбор пункта \n");
            getTaskMenu();
        }

    }

    static Integer setId() {
        boolean cycle = true;
        while (cycle){
            if (tasks.containsKey(id)){
                id++;
            } else {
                cycle = false;
            }
        }
        return id;
    }

    private void getTypeOfTask() {
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

    private void getAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач! \n");
        }
        tasks.forEach((id, task) -> {
            System.out.println(task.toString());
        });
    }
}

class Task {
    protected String name;
    protected String description;
    protected boolean isCompleted;
    protected int id;
    protected boolean isEpic;

    Task(String name, String description, boolean isCompleted, boolean isEpic, Integer id){
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.id = id;
        this.isEpic = isEpic;
    }

    protected void setTask() {
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        System.out.println();

        Main.tasks.put(id, (new Task(name, description, false, false, id)));
    }

    @Override
    public String toString() {
        return "Задача " + id + " = '" + name + '\'' +
                ", Описание = '" + description + '\'' +
                ", Выполнена = '" + isCompleted + '\'';
    }

    Task(){
    }

    protected void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        System.out.println("Задача " + id + " " + (isCompleted ? "выполнена \n" : "не выполнена \n"));
    }
}

class Epic extends Task{
    protected HashMap<String,String> subTasks = new HashMap<>();

    Epic(String name, String description, boolean isCompleted, boolean isEpic, int id, HashMap<String,String> subTasks) {
        super(name, description, isCompleted, isEpic, id);
        this.subTasks = subTasks;

    }

    protected void setEpic() {
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        System.out.println("Введите подзадачу:");
        addSubTask();
        System.out.println("Вы можете изменить подзадачи в менеджере задач! \n");
        Main.tasks.put(id,(new Epic(name,description, false, true, id, subTasks)));
    }

    private void addSubTask() {
        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;

        while (cycle) {
            String nameOfSubTask = scanner.nextLine();
            if (!subTasks.isEmpty()) {
                for(String name : subTasks.keySet()) {
                    if (name.equals(nameOfSubTask)) {
                        System.out.println("Такая подзадача уже существует! \n");
                    } else {
                        subTasks.put(nameOfSubTask,"new");
                        break;
                    }
                }
            } else {
                subTasks.put(nameOfSubTask,"new");
            }
            System.out.println("Добавить еще одну подзадачу? y/n");
            switch (scanner.nextLine()) {
                case "y" -> System.out.println("Введите название");
                case "n" -> cycle = false;
                default -> System.out.println("Некорректный ввод. Нажмите Enter");
            }
        }
    }
    protected void setCompleted(){
        boolean cycle = true;
        boolean cycle2 = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вот подзадачи данной задачи: " + subTasks);
        if (subTasks.size() == 1) {
            while (cycle) {
                System.out.println("Введите новый статус для подзадачи. done/in progress");
                String status = scanner.nextLine();
                if (status.equals("done")) {
                    for (String name: subTasks.keySet()) {
                        subTasks.replace(name, "Done");
                        cycle = false;
                        System.out.println("Терерь позадача " + name + " имеет статус Done \n");
                    }
                } else if (status.equals("in progress")) {
                    for (String name: subTasks.keySet()) {
                        subTasks.replace(name, "In_Progress");
                        cycle = false;
                        System.out.println("Терерь позадача " + name + " имеет статус In_Progress \n");
                    }
                } else {
                    System.out.println("Неизвестная команда. Одидаю done/in progress \n");
                }
            }
        } else {
            while (cycle2){
                System.out.println("Введите название подзадачи, которую нужно изменить");
                String subTask = scanner.nextLine();
                if (subTasks.containsKey(subTask)) {
                    System.out.println("Введите новый статус для подзадачи. done/in progress");
                    String status = scanner.nextLine();
                    if (status.equals("done")) {
                        subTasks.replace(subTask, "Done");
                    } else if (status.equals("in progress")) {
                        subTasks.replace(subTask, "In_Progress");
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
        for (String name: subTasks.keySet()) {
            if (subTasks.get(name).equals("Done")) {
                numberOfCompletedSubTasks ++;
            }
        }
        isCompleted = numberOfCompletedSubTasks == subTasks.size();
    }

    Epic() {
    }

    @Override
    public String toString() {
        return "Задача " + id + " = '" + name + '\'' +
                ", Описание = '" + description + '\'' +
                ", Подзадачи = " + subTasks.toString() +
                ", Выполнена = '" + isCompleted + '\'';
    }
}

