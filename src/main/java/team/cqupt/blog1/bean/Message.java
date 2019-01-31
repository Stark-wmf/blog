package team.cqupt.blog1.bean;

import lombok.Data;

import java.util.List;


@Data
public class Message {
    private String username;
    private String text;
    private String title;
    private int id;
    private int type;
    private int pid;
    private List<Message> childContent;

    public Message() {

    }


    public List<Message> getChildContent() {
        return childContent;
    }
    public void setChildContent(List<Message> childList) {
        this.childContent=childList;
    }
    public Message(String username,String title,String text,int pid,int type){
        this.username=username;
        this.title=title;
        this.text=text;
        this.pid=pid;
        this.type=type;
    }
}
