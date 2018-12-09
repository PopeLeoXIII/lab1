package com.company.Carlos;
import com.company.Carlos.taskmanager;
public class Main {
    static String []task1  = {"d", "sdf", "sdf", "-"};
    public static void main(String[] args) {
        com.company.taskmanager.Log log = new Log("TaskManager.xml");
        ConsolInterface consolInterface = new ConsolInterface();
        Handler handler = new Handler(consolInterface, log);


        do {

        } while (handler.request());

    }
}
