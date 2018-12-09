package taskmanager.elements;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.*;


@XmlRootElement

public class Tasks implements Serializable
{
    @XmlElement(name = "List")
    private ArrayList<Task> taskList = new ArrayList<>();
    public Tasks(){
        super();
    }


    public Tasks setList(ArrayList<Task> arrayList){
        taskList = arrayList;
        return this;
    }

    public ArrayList getTaskList(){
        return taskList;
    }
    public boolean addTask(Task task){
        return taskList.add(task);
    }

    public void removeTask(String id){
        this.taskList.removeIf((Task t) -> t.getId().equals(id));
    }

//    Comparator<Task> taskToDateComp = (Task a, Task b) -> {
//        Date dateA = a.getDate(), dateB = b.getDate();
//        return dateA.compareTo(dateB);
//    };
//
//    public Task getFirstDateTask(){
//        taskList.sort(taskToDateComp);
//
//        for (Task task : taskList){
//
//            if((task.getDate()).after(new Date())){
//                return task;
//            }
//        }
//        return null;
//    }
}