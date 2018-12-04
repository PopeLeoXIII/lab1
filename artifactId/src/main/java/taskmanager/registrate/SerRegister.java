package taskmanager.registrate;

import org.apache.log4j.Logger;
import taskmanager.elements.Task;
import taskmanager.elements.Tasks;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class SerRegister implements Register{
    public SerRegister(String fileName){
        this.fileName = fileName;
        taskList = getTasks().getTaskList();
    }

    private ArrayList<Task> taskList;
    final private String fileName;
    private static final Logger log = Logger.getLogger(XmlRegister.class);

    @Override
    public Tasks getTasks() {
        Tasks tasks = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream oin = new ObjectInputStream(fis);
            tasks =  (Tasks) oin.readObject();
            oin.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        if(tasks == null){
            log.error("пустой файл ");
        }

        return  tasks;
    }

    @Override
    public void saveTasks(Tasks tasks) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            System.err.println("in saveTasks " + e);
            log.fatal("файл не найден ");
        } catch (IOException e) {
            System.err.println("in saveTasks " + e);
            log.fatal("ошибка сериализации ");
        }

    }

    @Override
    public void addTask(Task task) {
        taskList.add(task);
        log.info("в список задач добавлена " + task.getId());
        Tasks tasks = new Tasks().setList(taskList);
        saveTasks(tasks);
    }

    @Override
    public void removeTask(String id) {
        taskList.removeIf((Task t) -> t.getId().equals(id));
        log.info("из списка задач удалена " + id);
        Tasks tasks = new Tasks().setList(taskList);
        saveTasks(tasks);
    }

    private Comparator<Task> taskToDateComp = (Task a, Task b) -> {
        Date dateA = a.getDate(), dateB = b.getDate();
        return dateA.compareTo(dateB);
    };

    @Override
    public Task getFirstDateTask(){
        taskList.sort(taskToDateComp);
        log.info("список задач отсортирован");

        for (Task task : taskList){
            if((task.getDate()).after(new Date())){
                log.info("найдена подходящая задача");
                return task;
            }
        }
        log.info("нет подходящей для NoticeThread задачи");
        return null;
    }

    public ArrayList<Task> getTaskList(){
        return taskList;
    }
}
