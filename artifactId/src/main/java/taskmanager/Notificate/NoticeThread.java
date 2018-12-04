package taskmanager.Notificate;

import org.apache.log4j.Logger;
import taskmanager.Interface.ConsolInterface;
import taskmanager.elements.Task;
import taskmanager.registrate.Register;
import taskmanager.registrate.XmlRegister;

import java.util.Date;

public class NoticeThread extends Thread{
    private Date date;
    private Task task;
    private Register register;
    private ConsolInterface interfacee;
    private static final Logger log = Logger.getLogger(XmlRegister.class);

    public NoticeThread (Register register, Task task, ConsolInterface interfacee){
        super();
        this.interfacee = interfacee;
        this.register = register;
        setTask(task);
        log.info("создан NoticeThread");
    }

    private void setTask(Task task){
        if(task != null) {
            this.task = task;
            this.date = task.getDate();
            log.info("новая задача для уведомления установлена");
        }
    }

    @Override
    public void run() {
        if(task != null) {
            boolean result = m();
            if(result)
                log.info("уведомление сработало в штатном режиме");
            else
                log.info("уведомление завершено нештатно");

            while (result) {
                //setTask(register.getTasks().getFirstDateTask());
                result = m();
            }
        } else {
            //System.out.println("no notice");
            log.error("пустая ссылка");
        }
        log.info("поток завершается");

    }

    private boolean m(){
        Date now = new Date();
        try {
            if (date.after(now)) {
                long dif = date.getTime() - now.getTime();
                Thread.sleep(dif);
                interfacee.noticeMessege(task);
                return true;
            } else {
                //System.out.println("can't notice in past");
                log.error("дата уведомления уже прошла");
                return false;
            }
        } catch (InterruptedException e) {
            log.error("завершение потока во время его работы ");
            //System.out.println("date was change");
            return false;
        }
    }
}
