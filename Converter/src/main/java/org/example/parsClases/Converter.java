package org.example.parsClases;

public class Converter extends Content {
    public String from;
    public String to;
    public Converter(){}
    public void setFromTo(String from, String to){
        this.from=from;
        this.to = to;
    }
    public void addURL(){//edit URL( add countries IDs  to URL)
        //System.out.println("now: "+from+" "+to+": URL"+URL);
        URL=URL.replace("from_to", from+"_"+to);
    }
}
