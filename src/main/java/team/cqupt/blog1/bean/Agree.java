package team.cqupt.blog1.bean;

public class Agree {
    private String username;
    private String textid;
    private String click;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClick() {
        return click;
    }
    public void setClick(String click){
        this.click=click;
    }

    public String getTextid() {
        return textid;
    }

    public void setTextid(String textid) {
        this.textid = textid;
    }
    public Agree(String username,String textid,String click){
        this.username=username;
        this.textid=textid;
        this.click=click;
    }
}
