package com.company.Carlos.taskmanager;

public class Handler {
    private com.company.taskmanager.ConsolInterface consolInterface;
    private Log log;
    public Handler(ConsolInterface ci, Log l){
        consolInterface = ci;
        log = l;
    }

    public boolean request(){
        String req = consolInterface.request();
        switch (req) {
            case "add":
                log.addTask(consolInterface.newTask());
                break;
            case "all":
                for (String[] task :
                        log.getAllTask()) {
                    consolInterface.show(task);
                }
                break;
            case "get":
                String[] findTask = log.getTask(consolInterface.getString());
                if (findTask == null) {
                    consolInterface.notFound();
                } else {
                    consolInterface.show(findTask);
                    consolInterface.success();
                }
                break;
            case "del":
                if (!log.remove(consolInterface.getString())){
                    consolInterface.notFound();
                } else {
                    consolInterface.success();
                }
                break;
            case "exit":
                return false;
        }
        return true;
    }
}
