package taskmanager.registrate;

import org.apache.log4j.Logger;
import taskmanager.elements.Task;
import taskmanager.elements.Tasks;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class SimpleXmlRegister implements Register{
    public SimpleXmlRegister(String fileName){
        this.file = new File(fileName);
        taskList = this.getTasks().getTaskList();
    }
    final private File file;
    private ArrayList<Task> taskList;
    private static final Logger log = Logger.getLogger(XmlRegister.class);


    public void saveTasks(){
        try {
            JAXBContext context = JAXBContext.newInstance(Tasks.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new Tasks().setList(taskList), file);
        } catch (JAXBException e){
            System.err.println("in saveTasks " + e);
            log.fatal("ошибка парсинга xml");
        }
    }

    public Tasks getTasks() {
        Tasks tasks = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Tasks.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            tasks = (Tasks) unmarshaller.unmarshal(file);
        } catch (JAXBException e){
            System.err.println("in getTask " + e);
            log.fatal("ошибка парсинга xml");
        }
        if(tasks == null){
            log.error("пустой файл ");
        }

        return tasks;
    }
    @Override
    public void addTask(Task task) {
        taskList.add(task);
        log.info("в список задач добавлена " + task.getId());
        //Tasks tasks = new Tasks().setList(taskList);
        //saveTasks(tasks);
    }

    @Override
    public void removeTask(String id) {
        taskList.removeIf((Task t) -> t.getId().equals(id));
        log.info("из списка задач удалена " + id);
        //Tasks tasks = new Tasks().setList(taskList);
        //saveTasks(tasks);
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
