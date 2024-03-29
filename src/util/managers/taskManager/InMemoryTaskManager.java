package util.managers.taskManager;

import main.*;
import util.TaskProgress;
import util.managers.historyManager.InMemoryHistoryManager;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager <Task> {

    private final InMemoryHistoryManager historyManager = Main.getDefaultHistoryManager();
    private final Main main = new Main();

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

    private <E extends Task> void getAllTasksForChoice(List<E> eList) { //
        List<String> namesOfAllSubtasks = new ArrayList<>();
        List<String> namesOfAllEpic = new ArrayList<>();

        boolean b = eList == main.getEpicList();
        if (b) {
            for (E e : eList) {
                Epic epic = (Epic) e;
                namesOfAllEpic.add(epic.getName() + " - " + epic.getId());
                namesOfAllSubtasks.addAll(epic.getNamesOfSubTasks());
            }

            if (namesOfAllSubtasks.isEmpty()) {
                System.out.println("Список эпиков пуст");
                System.out.println("Список подзадач пуст");
            } else {
                System.out.println("Список эпиков: " + namesOfAllEpic);
                System.out.println("Список подзадач: " + namesOfAllSubtasks);
            }

        } else {
            for (E e : eList) {
                Task task = (Task) e;
                namesOfAllEpic.add(task.getName() + " - " + task.getId());
            }
            if (namesOfAllEpic.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                System.out.println("Список задач: " + namesOfAllEpic);
            }
        }
    }

    @Override
    public <E extends Task> void getAllTasks(List<E> eList) {

        if (eList == main.getEpicList()) {
            for (E e : eList) {
                Epic epic = (Epic) e;
                System.out.println(epic.toString());
            }
        } else {
            for (E e : eList) {
                Task task = (Task) e;
                System.out.println(task.toString());
            }
        }
    }

    @Override
    public void deleteAllTask() {
        Scanner scanner = new Scanner(System.in);
        boolean cycle = true;
        System.out.println("Ты уверен, что хочешь удалить все задачи? y/n");
        while (cycle) {
            switch (scanner.nextLine()) {
                case "y" -> {
                    main.getEpicList().clear();
                    main.getTaskList().clear();
                    historyManager.clear();
                    cycle = false;
                    System.out.println("Все задачи удалены! \n");
                }
                case "n" -> cycle = false;
                default -> System.out.println("Ожидается y/n");
            }
        }
    }

    @Override
    public void deleteTaskById() {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);

        getAllTasksForChoice(main.getTaskList());
        getAllTasksForChoice(main.getEpicList());
        System.out.println("Введите id задачи: ");
        int id = scanner.nextInt();

        if(main.getAvailability(id) != null) {
            if (main.getAvailability(id) instanceof SubTask subTask) {
                for (Epic epic : main.getEpicList()) {
                    if (epic.getSubTasksList().size() <= 1 && epic.getSubTasksList().contains(subTask)) {
                        System.out.println("Эпик - " + epic.getName() + " имеет только одну подзадачу " +
                                epic.getNamesOfSubTasks() + "> она не может быть удалена");
                    } else if (epic.getSubTasksList().contains(subTask)) {
                        System.out.println("Подзадача - " + subTask.getName() + " - " + subTask.getId() + " удалена");
                        historyManager.remove(id);
                        epic.getSubTasksList().remove(main.getAvailability(id));
                    }
                }
            } else if (main.getAvailability(id) instanceof Epic epic) {
                System.out.println("Эпик - " + epic.getName() + " - " + epic.getId() + " удален");
                historyManager.remove(id);
                for (SubTask subTask : epic.getSubTasksList()) {
                    historyManager.remove(subTask.getId());
                }
                main.getEpicList().remove(epic);
            } else if (main.getAvailability(id) != null) {
                Task task = main.getAvailability(id);
                System.out.println("Задача - " + task.getName() + " - " + task.getId() + " удалена");
                historyManager.remove(id);
                main.getTaskList().remove(task);
            }
        } else {
            System.out.println("Задачи под таким id нет");
        }
    }

    @Override
    public void getTaskById() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи: ");
        int id = scanner.nextInt();
        if (main.getAvailability(id) != null) {
            historyManager.add(main.getAvailability(id));
            System.out.println(main.getAvailability(id).toString());
        } else {
            System.out.println("Под id = " + id + " нет ничего \n");
        }
    }

    @Override
    public void updateTaskStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи, которую нужно обновить: ");
        System.out.println("Выбрать эпик - попсть в пункт изменения подзадач этого эпика");
        System.out.println("Выбрать поздадачу - попасть в пункт изменения всех подзадач эпика(удалить, добавить)");
        getAllTasksForChoice(main.getTaskList());
        getAllTasksForChoice(main.getEpicList());

        int id = scanner.nextInt();

        if (main.getAvailability(id) != null) {

            switch (main.getAvailability(id).getType()) {
                case "Subtask" -> {
                    SubTask subTask = (SubTask) main.getAvailability(id);
                    Epic epic = null;
                    for (Epic e : main.getEpicList()) {
                        if (e.getSubTasksList().contains(subTask)) {
                            epic = e;
                        }
                    }
                    editSubTasks(epic);
                }
                case "Epic" -> {
                    Epic epic = (Epic) main.getAvailability(id);
                    setCompleted(epic);
                    historyManager.add(epic);
                }
                case "Task" -> {
                    Task task = main.getAvailability(id);
                    System.out.println("Текущий статус задачи: " + task.isCompleted());
                    System.out.println("Введите новый статус задачи (true/false): ");
                    boolean newStatus = scanner.nextBoolean();
                    task.setCompleted(newStatus);
                    historyManager.add(task);
                }
                default -> System.out.println("Этой ошибки быть не может кста, 21.03.2023 21:02 :)");
            }
        } else {
            System.out.println("Нет задачи под таким id");
        }
    }

    protected void setCompleted(Epic epic) {

        boolean cycle = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вот подзадачи данной задачи: " + epic.getNamesOfSubTasks().toString());
        while (cycle) {
            System.out.println("Введите id, которую нужно изменить");
            int idOfSubTask = scanner.nextInt();

            SubTask subTask= epic.getSubTask(idOfSubTask);

            scanner.nextLine();
            if (!(subTask == null)) {
                System.out.println("Введите новый статус для подзадачи. done/in progress");
                String status = scanner.nextLine();
                historyManager.add(subTask);
                if (status.equals("done")) {
                    subTask.setTaskProgress(TaskProgress.DONE);
                    System.out.println("Терерь позадача " + subTask.getName() + " имеет статус Done \n");
                } else if (status.equals("in progress")) {
                    subTask.setTaskProgress(TaskProgress.IN_PROGRESS);
                    System.out.println("Терерь позадача " + subTask.getName() + " имеет статус In_Progress \n");
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
                    case "y" -> cycleOfChoice = false;
                    case "n" -> {
                        cycle = false;
                        cycleOfChoice = false;
                    }
                    default -> System.out.println("Неизвестная команда. Ожидаю y/n \n");
                }
            }
        }
        int numberOfCompletedSubTasks = 0;
        for (SubTask subTask : epic.getSubTasksList()) {
            if (subTask.getTaskProgress().equals(TaskProgress.DONE)) {
                numberOfCompletedSubTasks++;
            }
        }
        boolean setCompleted = numberOfCompletedSubTasks == epic.getSubTasksList().size();
        epic.setCompleted(setCompleted);
    }

    public void editSubTasks(Epic epic) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберете действие");
        System.out.println("1. Добавить подзадачу");
        System.out.println("2. Удалить подзадачу");
        System.out.println("3. Изменить название");
        System.out.println("4. Выйти");

        int choice = scanner.nextInt();
        String returnStringScanner = scanner.nextLine();
        switch (choice) {
            case 1 -> epic.addSubTask();
            case 2 -> {
                boolean cycle = true;
                while (cycle) {
                    System.out.println("Введите id подзадачи, которую хотите удалить");
                    System.out.println(epic.getNamesOfSubTasks().toString());

                    int idOfSubTask = scanner.nextInt();
                    SubTask subTask = epic.getSubTask(idOfSubTask);

                    if (epic.getNamesOfSubTasks().size() == 1) {
                        System.out.println("Подзадача одна и удалить ее нельзя");
                    } else if (!(subTask == null)) {
                        System.out.println( subTask.getName() + " - удалена");
                        epic.getSubTasksList().remove(subTask);
                        historyManager.remove(idOfSubTask);
                    } else {
                        System.out.println("Позадача с таким именем не найдена");
                    }


                    System.out.println("Удалить еще одну позадачу? y/n");
                    String returnStringScanner1 = scanner.nextLine();
                    String choice1 = scanner.nextLine();

                    switch (choice1) {
                        case "y" -> editSubTasks(epic);
                        case "n" -> cycle = false;
                        default -> {
                            cycle = false;
                            System.out.println("Неверный ввод");
                        }
                    }
                }
            }
            case 3 -> {
                System.out.println("Введите id подзадачи, которую хотите изменить");
                System.out.println(epic.getNamesOfSubTasks().toString());

                boolean cycle = true;
                while (cycle) {
                    int idOfSubTask = scanner.nextInt();
                    scanner.nextLine();
                    SubTask subTask = epic.getSubTask(idOfSubTask);

                    if (subTask != null) {
                        System.out.println("Введите новое название");
                        String newName = scanner.nextLine();

                        subTask.setName(newName);
                        System.out.println("Новый список подзадач эпика " + epic.getName() + "\n" +
                                epic.getNamesOfSubTasks().toString());
                        historyManager.add(subTask);
                    } else {
                        System.out.println("Такой подзадачи нет. Возвращаемся в начало");
                    }

                    System.out.println("Изменить еще одну позадачу? y/n");
                    String choice1 = scanner.nextLine();

                    switch (choice1) {
                        case "y" -> editSubTasks(epic);
                        case "n" -> cycle = false;
                        default -> {
                            cycle = false;
                            System.out.println("Неверный ввод");
                        }
                    }
                }
            }
            case 4 -> System.out.print("");
            default -> System.out.println("Неверный ввод");
        }
    }
}
