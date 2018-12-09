package com.company.Carlos.taskmanager;
import javax.xml.bind.annotation.*;

@XmlType(name = "task")
public class Task {
    @XmlElement(name = "title")
    public String title;
    @XmlElement(name = "description")
    public String description;
    @XmlElement(name = "date")
    public datetime date;
    @XmlElement(name = "contact")
    public String contact;
    @XmlAttribute(name = "id")
    public int id;

}

