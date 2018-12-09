package com.company.taskmanager;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.FileOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;

public class Log {
    final private String fileName;
    private int length;
    public Log(String fileName){
        this.fileName = fileName;
        lengthCheck();
    }
    private void lengthCheck() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Node root = document.getDocumentElement();
            NodeList tasks = root.getChildNodes();
            int newlength = 0;
            for (int i = 0; i < tasks.getLength(); i++) {
                Node task = tasks.item(i);
                if (task.getNodeType() != Node.TEXT_NODE) {
                    newlength++;
                }
            }
            this.length = newlength;
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    boolean remove(String taskName){
        boolean result = false;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(new File(fileName));

            Element root = document.getDocumentElement();

            for (Node task = root.getFirstChild(); task != null; task = task.getNextSibling()){

                if (task.getNodeName().equals("Task")){
                    boolean deleted = false;
                    for (Node prop = task.getFirstChild(); prop != null; prop = prop.getNextSibling()){
                        if ("Title" == prop.getNodeName() && taskName.equals(prop.getTextContent())){
                            deleted = true;
                            break;
                        }
                    }
                    if (deleted) {
                        result = true;
                        task.getParentNode().removeChild(task);
                    }
                }
            }
            if (result)
                writeDocument(document);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            //ex.printStackTrace(System.out);
            System.err.println("remove err " + ex);
        }

        return result;
    }

    String[] getTask(String taskName){
        String[] result = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(new File(fileName));

            Element tasks = document.getDocumentElement();

            for (Node task = tasks.getFirstChild(); task != null; task = task.getNextSibling()){

                if (task.getNodeName().equals("Task")){
                    boolean itResult = false;
                    for (Node prop = task.getFirstChild(); prop != null; prop = prop.getNextSibling()){
                        if ("Title" == prop.getNodeName() && taskName.equals(prop.getTextContent())){
                            itResult = true;
                            break;
                        }
                    }
                    if(itResult) {
                        result = new String[4];
                        NodeList taskProps = task.getChildNodes();
                        for (int i = 0, propNum = 0; i < taskProps.getLength(); i++) {
                            Node taskProp = taskProps.item(i);
                            if (taskProp.getNodeType() != Node.TEXT_NODE) {
                                result[propNum] = taskProp.getNodeName() + ": " + taskProp.getChildNodes().item(0).getTextContent();
                                propNum++;
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            //ex.printStackTrace(System.out);
            System.err.println("getTask err " + ex);
        }
        return result;
    }
     String[][] getAllTask(){
        String[][] allTasks = new String[length][4];
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Node root = document.getDocumentElement();

            NodeList tasks = root.getChildNodes();
            for (int i = 0, taskNum = 0; i < tasks.getLength(); i++) {
                Node task = tasks.item(i);
                if (task.getNodeType() != Node.TEXT_NODE) {
                    NodeList taskProps = task.getChildNodes();
                    for(int j = 0, propNum = 0; j < taskProps.getLength(); j++) {
                        Node taskProp = taskProps.item(j);
                        if (taskProp.getNodeType() != Node.TEXT_NODE) {
                            allTasks[taskNum][propNum] = taskProp.getNodeName() + ": " + taskProp.getChildNodes().item(0).getTextContent();
                            propNum++;
                        }
                    }
                    taskNum++;
                }
            }

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return allTasks;
    }

     void addTask(String[] newTask) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Node root = document.getDocumentElement();

            Element task = document.createElement("Task");
            Element title = document.createElement("Title");
            title.setTextContent(newTask[0]);
            Element description = document.createElement("Description");
            description.setTextContent(newTask[1]);
            Element date = document.createElement("Date");
            date.setTextContent(newTask[2]);
            Element contact = document.createElement("Contact");
            contact.setTextContent(newTask[3]);

            task.appendChild(title);
            task.appendChild(description);
            task.appendChild(date);
            task.appendChild(contact);
            root.appendChild(task);

            writeDocument(document);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    // Функция для сохранения DOM в файл
    private void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(fileName);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            lengthCheck();
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

}