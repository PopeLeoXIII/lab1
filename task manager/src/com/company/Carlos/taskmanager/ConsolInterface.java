package com.company.taskmanager;
import java.util.Scanner;

public class ConsolInterface {
    static private Scanner in = new Scanner(System.in);

    void show(String[] task){
        System.out.println();
        for(String Prop: task){
            System.out.println(Prop);
        }
        System.out.println("=========>");

    }
    String request(){
        System.out.println("enter request add, all, del, get, exit ");
        String input = in.nextLine();
        System.out.println("Your input is: " + input);
        return input;
    }
    String[] newTask(){
        String[] task = new String[4];
        String[] props = {"Title", "Description", "Date", "Contact"};

        for(int i = 0; i < props.length; i++) {
            System.out.println("enter " + props[i]);
            task[i] = in.nextLine();
        }
        return task;
    }

    String getString(){
        System.out.println("enter task title");
        return (in.nextLine());
    }
    void notFound(){
        System.out.println("could not find task\n");
    }
    void success(){
        System.out.println("success request\n");
    }
}
