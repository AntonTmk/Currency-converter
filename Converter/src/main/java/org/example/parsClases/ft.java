package org.example.parsClases;

public class ft{//auxiliary class for history.class
    String date;
    String curs;
    public ft(String f, String t){
        date=f;
        curs=t;
    }

    public void getFt(){
        System.out.println("Date_curs"+date+"_"+curs);
    }
}
