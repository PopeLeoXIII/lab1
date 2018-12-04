package taskmanager;

import taskmanager.Interface.ConsolInterface;
import taskmanager.handler.Handler;
import taskmanager.registrate.Register;
import taskmanager.registrate.SerRegister;
import taskmanager.registrate.SimpleXmlRegister;



public class Main {
    public static void main(String[] args){
        ConsolInterface inter = new ConsolInterface();
        int typeOfRegister = inter.typeOfRegister();

        Register register;
        switch (typeOfRegister){
            case 1:
                register = new SerRegister("src\\main\\resources\\TaskManager.out");
                break;
            case 2:
                register = new SimpleXmlRegister("src\\main\\resources\\TaskManager.xml");
                break;
            default:
                return;

        }
        Handler handler = new Handler(register, inter);
        while (handler.request()){

        }


    }
}