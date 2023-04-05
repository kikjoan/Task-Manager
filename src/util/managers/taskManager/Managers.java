package util.managers.taskManager;
import util.managers.taskManager.InMemoryTaskManager;

public class Managers <T> {

    public <T> InMemoryTaskManager defaultManager() {
        return new InMemoryTaskManager();
    }
}

