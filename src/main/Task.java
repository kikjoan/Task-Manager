package main;

import util.Saver;

import java.io.Serializable;
import java.util.Scanner;

public class Task implements Serializable{
    protected String name;
    protected String description;
    protected String type;
    protected boolean isCompleted;
    protected int id;
    protected boolean isEpic;

    Task(String name, String description, boolean isCompleted, boolean isEpic, int id, String type) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.id = id;
        this.isEpic = isEpic;
        this.type = type;
    }

    public void setTask() {
        int id = Main.setId();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();

        if (new Main().checkForDuplicate(name)) {
            System.out.println("Введите описание: ");
            description = scanner.nextLine();
            System.out.println("Записана новая задача - " + name + "\n");

            Main.getTaskList().add(new Task(name, description, false, false, id, "Task"));
        } else {
            System.out.println("Задача или эпик с названием - " + name + " уже существует \n");
        }

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

    protected Task(String name, int id, String type){
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
