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

public class XmlRegister implements Register{
        final private File file;
        private static final Logger log = Logger.getLogger(XmlRegister.class);

        public XmlRegister(String fileName) {
            this.file = new File(fileName);

        }
        public Tasks getTasks(){
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
        public void saveTasks(Tasks tasks){
            try {
                JAXBContext context = JAXBContext.newInstance(Tasks.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(tasks, file);
            } catch (JAXBException e){
                System.err.println("in saveTasks " + e);
                log.fatal("ошибка парсинга xml");
            }
        }
        public void addTask(Task task){
            Tasks tasks = getTasks();
            tasks.addTask(task);
            saveTasks(tasks);
            log.info("добавлена задача " + task.getId());
        }

        public void removeTask(String id){
            Tasks tasks = getTasks();
            tasks.removeTask(id);
            saveTasks(tasks);
            log.info("удалена задача " + id);
        }

        private Comparator<Task> taskToDateComp = (Task a, Task b) -> {
            Date dateA = a.getDate(), dateB = b.getDate();
            return dateA.compareTo(dateB);
        };

        public Task getFirstDateTask(){
            Tasks tasks = getTasks();
            ArrayList<Task> taskList = tasks.getTaskList();
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
            return getTasks().getTaskList();
        }

    }