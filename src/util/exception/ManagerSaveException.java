package util.exception;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    public ManagerSaveException() throws IOException {
        super();
        throw new IOException();
    }

    public ManagerSaveException(String message) {
        super(message);
    }

    public ManagerSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerSaveException(Throwable cause) {
        super(cause);
    }
}
