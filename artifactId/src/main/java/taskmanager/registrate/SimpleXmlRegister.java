package taskmanager.registrate;

import org.apache.log4j.Logger;
import taskmanager.elements.Task;
import taskmanager.elements.Tasks;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class SimpleXmlRegister extends XmlRegister implements Register{
    public SimpleXmlRegister(String fileName){
        super(fileName);
        taskList = this.getTasks().getTaskList();
    }

    private ArrayList<Task> taskList;
    private static final Logger log = Logger.getLogger(XmlRegister.class);


    @Override
    public void saveTasks(Tasks tasks){
        super.saveTasks(tasks);
    }
    @Override
    public Tasks getTasks() {
       return super.getTasks();
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
