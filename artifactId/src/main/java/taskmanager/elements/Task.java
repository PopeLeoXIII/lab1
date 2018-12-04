package taskmanager.elements;


import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( name = "task" , propOrder = {
        "title",
        "description",
        "date",
        "contact"
})
public class Task implements Serializable {
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date.toString() + '\'' +
                ", contact='" + contact + '\'' +
                ", id=" + id +
                '}';
    }
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private Date date;
    @XmlElement(required = true)
    private String contact;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    private String id;

    public String getId(){
        return id;
    }

    public Date getDate(){
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContact() {
        return contact;
    }

    public Task(String title, String description, Date date, String contact, String id) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.contact = contact;
        this.id = id;
    }

    public Task(String title, String description, Date date, String contact, int id) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.contact = contact;
        this.id = Integer.toString(id);
    }
    public Task(){}
}
