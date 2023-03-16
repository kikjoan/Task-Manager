package main;

import java.util.Scanner;

public class Task {
    protected String name;
    protected String description;
    protected boolean isCompleted;
    protected int id;
    protected boolean isEpic;

    Task(String name, String description, boolean isCompleted, boolean isEpic, int id) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.id = id;
        this.isEpic = isEpic;
    }

    public void setTask() {
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание: ");
        description = scanner.nextLine();
        System.out.println();

        Main.tasks.put(id, (new Task(name, description, false, false, id)));
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        System.out.println("Задача " + id + " " + (isCompleted ? "выполнена \n" : "не выполнена \n"));
    }

    public Task() {}

    public boolean isEpic() {
        return isEpic;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Название - '" + name + "'; " +
                "ID - '" + id + "'; " +
                "Описание - '" + description + "'; " +
                "Выполнена - '" + isCompleted + "'";
    }
}
