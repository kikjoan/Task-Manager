import java.util.HashMap;
import java.util.Scanner;
// выполнить вывод подзадач + более красивый вывод
// новая реализация меню + сохранение задач
// НОВАЯ СТРОЧКА ДЛЯ GITHUB
// ЗАКРЕПЛЯЕМ МАТЕРИАЛ
public class Main {
    static int id = 1;
    static boolean programStatus = true;
    static HashMap<Integer, Object> tasks = new HashMap<>();

    public static void main(String[] args) {
        getMainMenu();
    }

    private static void getMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (programStatus) {
            System.out.println("1.Добавить новую задачу.");
            System.out.println("2.Получить список задач");
            System.out.println("3.Открыть менеджер задач");
            System.out.println("4.Завершить программу");
            int input = scanner.nextInt();

            switch (input) {
                case 1 -> new Main().getTypeOfTask();   // Добавить новую задачу
                case 2 -> new Main().getAllTasks();     //получить список задач
                case 3 -> new Main().getTaskMenu();     // открыть менеджер задач
                case 4 -> programStatus = false;        // закрыть прогу
            }
        }
        scanner.close();
    }

     private void getTaskMenu() {
        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
         System.out.println("1.Удалить все задачи");
         System.out.println("2.Получить задачу по id");
         System.out.println("3.Обновить задачу");
         System.out.println("4.Удалить задачу по идентификатору");
         System.out.println("5.Вернуться в основоное меню");

        while (cycle) {
            switch (scanner.nextInt()) {
                //case 1 -> ;
            }
        }
    }

    static Integer setId() {
        return id++;
    }

    private void getTypeOfTask() {
        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Задача имеет подзадачи? y/n, для выхода exit");

        while (cycle) {
            switch (scanner.nextLine()) {
                case "y":
                    new Epic().setEpic();
                    cycle = false;
                    break;
                case "n":
                    new Task().setTask();
                    cycle = false;
                    break;
                case "exit":
                    cycle = false;
                default:
                    System.out.println("Некорректный ввод. Ожидаю ввод вида y/n");
            }
        }
    }

    private void getAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Нет задач! \n");
        }
        tasks.forEach((id, task) -> {
            System.out.println(task);
        });
    }
}

class Task {
    private String name;
    private String description;
    private boolean isCompleted;
    private int id;
    private boolean isEpic;

    Task(String name, String description, boolean isCompleted, boolean isEpic, int id) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.id = id;
        this.isEpic = isEpic;
    }

    protected void setTask() {
        String name;
        String description;
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();

        Main.tasks.put(id, (new Task(name, description, false, false, id)));
    }

    @Override
    public String toString() {
        return "Название задачи = '" + name + '\'' +
                ", Описание задачи = '" + description + '\'' +
                ", Выполнена = '" + isCompleted + '\'' +
                ", id = '" + id + '\'' +
                ", Задача с подзадачами = '" + isEpic + '\'';
    }

    Task(){
    }
}

class Epic extends Task{
    private HashMap<String,String> subTasks = new HashMap<>();
    private boolean isEpic;

    Epic(String name, String description, boolean isCompleted, boolean isEpic, int id, HashMap<String,String> subTasks) {
        super(name, description, isCompleted, isEpic, id);
        this.subTasks = subTasks;
    }

    protected void setEpic() {
        String name;
        String description;
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);


        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        System.out.println("Введите подзадачу:");
        addSubTask();
        System.out.println("Вы можете изменить подзадачу в меню задач!");
        Main.tasks.put(id,(new Epic(name,description, false, true, id, subTasks)));
    }

    private void addSubTask() {
        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;

        System.out.println("Введите название");
        while (cycle) {
            String name = scanner.nextLine();
            subTasks.put(name,"new");
            System.out.println("Добавить еще одну подзадачу? y/n");
            switch (scanner.nextLine()) {
                case "y" -> System.out.println("Введите название");
                case "n" -> cycle = false;
                default -> System.out.println("Некорректный ввод. Ожидаю y/n ");
            }
        }
    }

    Epic() {
    }
}

class Menu {
    public static void getEpicMenu() {
        System.out.println("1.Получение списка всех подзадач эпика");
        System.out.println("2.Вернуться в меню задач");
    }
}

