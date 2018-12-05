package taskmanager.Interface;

import taskmanager.elements.Task;
import taskmanager.elements.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsolInterface {


    public void show(Task task){
        System.out.println("\nTask " + task.getId());
        System.out.println("Title: " + task.getTitle());
        System.out.println("Decription: " + task.getDescription());
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd, kk:mm:ss");
        System.out.println("Date: " + fd.format(task.getDate()));
        System.out.println("Contact: " + task.getContact());
        System.out.println("=========>");

    }
    public void showAll(ArrayList<Task> taskList){
        for (Task task: taskList){
            show(task);
        }
    }
    public int typeOfRegister(){
        System.out.println("chose type of register");
        System.out.println("enter 1 to work with the Serialize file TaskManager.out");
        System.out.println("enter 2 to work with the Xml file TaskManager.xml");
        System.out.println("enter another digit to exit ");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        return input;
    }
    public void showAll(Tasks tasks){

        ArrayList arr = tasks.getTaskList();
        for (Object task: arr){
            show((Task) task);
        }
    }

    public int request(){
        Scanner in = new Scanner(System.in);
        System.out.println("enter request add(1), all(2), del(3), exit(0) ");
        int input;
        try {
            input = in.nextInt();
        } catch (InputMismatchException e){
            System.out.println("uncorrect input");
            return 10;
        }

        System.out.println("your input is: " + input);
        return input;
    }

    public Task newTask() {
        Scanner in = new Scanner(System.in);
        System.out.println("enter id ");
        String strId = in.nextLine();
        Integer id = null;
        while(id == null) {
            try {
                id = Integer.parseInt(strId);
            } catch (NumberFormatException e) {
                System.out.println("uncorrected id ");
                strId = in.nextLine();
            }
        }
        System.out.println("enter title ");
        String title = in.nextLine();

        System.out.println("enter date ");
        System.out.println("date format  \"yyyy-MM-dd, kk:mm\" ");
        Date date = getDate(in.nextLine());
        while (date == null){
            date = getDate(in.nextLine());
        }

        System.out.println("enter description ");
        String desc = in.nextLine();
        System.out.println("enter contact ");
        String contact = in.nextLine();

        Task task = new Task(title, desc, date, contact, id);
        return task;
    }

    public String getString(){
        Scanner in = new Scanner(System.in);
        System.out.println("enter task title");
        return in.nextLine();
    }
    public String getId(){
        Scanner in = new Scanner(System.in);
        System.out.println("enter task id");
        return in.nextLine();
    }


    public Date getDate(String date){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd, kk:mm");
        SimpleDateFormat formatForDateNowShort = new SimpleDateFormat("yyyy-MM-dd, kk:mm");
        SimpleDateFormat formatDate = new SimpleDateFormat ("yyyy-MM-dd");

        Date parsingDate = null;
        try {

            switch (date.length()){
                case 5:
                    Date today = new Date();
                    String s = formatDate.format(today) + ", " + date;
                    parsingDate = formatForDateNowShort.parse(s);
                    break;
                case 8:
                    Date today1 = new Date();
                    String s1 = formatDate.format(today1) + ", " + date;
                    parsingDate = formatForDateNow.parse(s1);
                break;
                case 10:
                    parsingDate = formatDate.parse(date);
                    break;
                case 17:
                    parsingDate = formatForDateNowShort.parse(date);
                    break;
                default:
                    System.out.println("Uncorrected date ");
                    System.out.println("date format  \"yyyy-MM-dd, kk:mm\" ");
                    return null;
            }

            System.out.println(parsingDate);

        }catch (ParseException e) {
            System.out.println("Uncorrected date " + formatForDateNow);
        }
        return parsingDate;
    }

    public void noticeMessege(Task task){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd, kk:mm");
        System.out.println(task.getTitle() + " it's time " + formatForDateNow.format( new Date()) );

    }
}
