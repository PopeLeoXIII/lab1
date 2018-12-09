package taskmanager;

import taskmanager.Interface.ConsoleInterface;
import taskmanager.handler.Handler;
import taskmanager.registrate.*;

public class Main {
    public static void main(String[] args){

        Register register;
        switch (ConsoleInterface.typeOfRegister()){
            case 1:
                register = new SerRegister("src\\main\\resources\\TaskManager.out");
                break;
            case 2:
                register = new SimpleXmlRegister("src\\main\\resources\\TaskManager.xml");
                break;
            default:
                return;

        }
        Handler handler = new Handler(register);
        while (handler.request()){

        }

    }
}