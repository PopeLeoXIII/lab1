package taskmanager.registrate;

import taskmanager.elements.Task;
import taskmanager.elements.Tasks;

import java.util.ArrayList;

public interface Register {
    public Tasks getTasks();
    public void saveTasks(Tasks tasks);
    public void addTask(Task task);
    public void removeTask(String id);
    public Task getFirstDateTask();
    public ArrayList<Task> getTaskList();
}
