import util.ManagerMenu;

import java.util.*;
// реализовать выполнение подзадач + сохранение задач
// по возможности реализовать вывод подзадач без скобок

public class Main {
    static protected int id = 1;
    static private boolean programStatus = true;
    static protected HashMap<Integer, Object> tasks = new HashMap<>();

    public static void main(String[] args) {
        getMainMenu();
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
        Scanner scanner = new Scanner(System.in);;
        try {
            while (cycle) {
                System.out.println("1.Удалить все задачи");
                System.out.println("2.Найти задачу по id");
                System.out.println("3.Обновить задачу");
                System.out.println("4.Удалить задачу по id");
                System.out.println("5.Вернуться в основоное меню");
                switch (scanner.nextInt()) {
                    case 1 :
                        new ManagerMenu().checkToDelete(tasks);
                        break;
                    case 2 :
                        new ManagerMenu().getTaskById(tasks);
                        break;
                    case 3:
                        System.out.println("Введите id задачи, которую нужно обновить: ");
                        int taskId = scanner.nextInt();
                        Task taskToUpdate = (Task) tasks.get(taskId);
                        if (taskToUpdate != null) {
                            System.out.println("Текущий статус задачи: " + taskToUpdate.isCompleted);
                            System.out.println("Введите новый статус задачи (true/false): ");
                            boolean newStatus = scanner.nextBoolean();
                            taskToUpdate.setCompleted(newStatus);
                        } else {
                            System.out.println("Задачи с id " + taskId + " не найдено");
                        }
                        break;
                    case 4 :
                        new ManagerMenu().deleteTaskById(tasks);
                        break;
                    case 5 :
                        cycle = false;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Ожидаю выбор пункта \n");
            getTaskMenu();
        }

    }

    static Integer setId() {
        return id++;
    }

    private void getTypeOfTask() {
        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Задача имеет подзадачи? y/n, для выхода exit");
        try {
            while (cycle) {
                switch (scanner.nextLine()) {
                    case "y":
                        cycle = false;
                        new Epic().setEpic();
                        break;
                    case "n":
                        cycle = false;
                        new Task().setTask();
                        break;
                    case "exit":
                        cycle = false;
                    default:
                        System.out.println("Некорректный ввод. Ожидаю ввод вида y/n");
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
        System.out.println("Задача " + id + " теперь " + (isCompleted ? "выполнена" : "не выполнена"));
    }
}

class Epic extends Task{
    private HashMap<String,String> subTasks = new HashMap<>();

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
            subTasks.put(nameOfSubTask,"false");
            System.out.println("Добавить еще одну подзадачу? y/n");
            switch (scanner.nextLine()) {
                case "y" -> System.out.println("Введите название");
                case "n" -> cycle = false;
                default -> System.out.println("Некорректный ввод. Нажмите Enter");
            }
        }
    }

    Epic() {
    }

    @Override
    public String toString() {
        return "Задача " + id + " = '" + name + '\'' +
                ", Описание = '" + description + '\'' +
                ", Подзадачи : " + subTasks.toString() +
                ", Выполнена = '" + isCompleted + '\'';
    }
}

