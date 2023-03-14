package util.taskManager;

public class Managers <T extends TaskManager> {

    public InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
