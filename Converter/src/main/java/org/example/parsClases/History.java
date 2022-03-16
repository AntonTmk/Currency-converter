package org.example.parsClases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class History extends Converter{


        public ArrayList<ft> rate=new ArrayList<>();
        public History(String fr, String t){
            URL=fr;
            API=t;
        }
        public History(){}
        public ft newFt(String f, String t){
            return new ft(f, t);
   }

   public void dateUpdate(){//add date to URL to get relevant history of changing
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDateTime now=LocalDateTime.now();
    String date=dtf.format(now);
    String[] dd=date.split("/");

    int EDD=Integer.parseInt(dd[2]), EMM=Integer.parseInt(dd[1]), EYY=Integer.parseInt(dd[0]);
    int SDD=EDD-7, SMM=EMM, SYY=EYY;
    if(SDD<1){
        SDD+=29;
        if(SMM==1){
            SMM=12;
            EYY--;
        }else{
            SMM--;
        }
    }
    URL = URL.replace("SYY-SMM-SDD", String.valueOf(SYY)+"-"+String.valueOf(SMM)+"-"+String.valueOf(SDD))
            .replace("EYY-EMM-EDD", String.valueOf(EYY)+"-"+String.valueOf(EMM)+"-"+String.valueOf(EDD));
}
    public void addrate(String from, String to){//add new date and curs to list
        ft simple=new ft(from, to);
        rate.add(simple);
    }
    public void output(){//for testing
        System.out.println("History"+from+" "+to);
        for(int i=0; i<rate.size(); i++) {
            System.out.println(rate.get(i).date+" "+rate.get(i).curs);
        }
    }
public String getDate(int i){//rturn date of list's element
            return rate.get(i).date;
}
    public String getCurs(int i){//rturn curs of list's element
        return rate.get(i).curs;
    }
}
