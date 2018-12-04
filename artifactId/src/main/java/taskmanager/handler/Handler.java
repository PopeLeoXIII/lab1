package taskmanager.handler;

import org.apache.log4j.Logger;
import taskmanager.Interface.ConsolInterface;
import taskmanager.Notificate.NoticeThread;
import taskmanager.elements.Task;
import taskmanager.elements.Tasks;
import taskmanager.registrate.Register;
import taskmanager.registrate.XmlRegister;

import java.util.Date;

public class Handler {

    private NoticeThread noticeThread;
    private Register register;
    private Task taskNotice;
    private ConsolInterface interfacee;
    private static final Logger log = Logger.getLogger(XmlRegister.class);

    public Handler(Register r, ConsolInterface interfacee) {
        log.info("Создан handler");
        register = r;
        this.interfacee = interfacee;
        setNotice(register.getFirstDateTask());
    }

    private void setNotice(Task task){
        taskNotice = task;
        noticeThread = new NoticeThread(register, taskNotice, interfacee);
        noticeThread.setDaemon(true);

        if (taskNotice != null) {
            log.info("NoticeThread стартует ");
            noticeThread.start();
        } else {
            log.error("Пустая ссылка вместо актуальной задачи");
            log.info("NoticeThread не стартует");
        }

    }

    public boolean request(){
        int req = interfacee.request();
        log.info("Запрос с кодом " + req);
        switch (req) {
            case 1:
                Task task = interfacee.newTask();
                log.info("Получена задача " + task.getId() + " " + task.getTitle());

                Date newDate = task.getDate();
                if(newDate.after(new Date())) {
                    if (null == taskNotice || newDate.before(taskNotice.getDate())) {
                        log.info("Дата уведомления изменена " );
                        setNotice(task);
                    }
                }
                register.addTask(task);
                break;
            case 2:
                Tasks tasks = register.getTasks();

                interfacee.showAll(tasks.getTaskList());

                break;
            case 3:
                String id = interfacee.getId();
                log.info("Запрос на удаление задачи " + id);

                register.removeTask(id);

                if(taskNotice != null && taskNotice.getId().equals(id)){
                    log.error("Удаляем дату из уведомления ");
                    noticeThread.interrupt();
                    setNotice(register.getFirstDateTask());
                }
                break;
            case 10:
                log.error("Неверный формат ввода ");
                break;
            case 0:
                log.error("Завершение работы приложения");
                return false;
        }
        return true;
    }
}
