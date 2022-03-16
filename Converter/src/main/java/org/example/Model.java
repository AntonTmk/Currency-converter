package org.example;

import org.example.parsClases.Content;
import org.example.parsClases.Converter;
import org.example.parsClases.History;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.example.parsClases.ft;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Model {

    private ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");

    public class currency{//class for countries list
        String id;
        String name;
        public currency(){
            id="";
            name="";
        }
        public currency(String[] inf){//get country's id and name from string array
            id=inf[0];
            name=inf[1];
        }
        public currency( String alpha31, String currencyId1, String currencyName1,  String currencySymbol1, String id1, String name1){
            id=id1;
            name=name1;
        }
        public void input( String alpha31, String currencyId1, String currencyName1,  String currencySymbol1, String id1, String name1){
            id=id1;
            name=name1;
        }
        public void output(){
            System.out.println(" id: "+id+" name: "+name);
        }

    }


    public Model(){
       // System.out.println("Parses object");
        getCountries();
    }

    public History getHistory(String from, String to){//return's history of currency changing
//System.out.println("\n getHistory");
    History history=context.getBean("historyBean", History.class);
       // System.out.println("\n from_to"+from+"_"+to);
        history.setFromTo(from, to);
        history.addURL();
        history.dateUpdate();
        String content=history.getContent().toString();
        String[] word = content.replace(":{" , ",").replace("}", "").replace("{", "").split(",");
       //for testing
        /* for(int i=1; i<word.length; i++) {
            System.out.println("add5=........."+word[i]);
        }*/
        for(int i=1; i<word.length; i++) {
            String[] add = word[i].replace("\"", "").split(":");
             //System.out.println("add1=......"+add[0] + " " + add[1]);
            ft pom=new ft(add[0], add[1]);
            history.rate.add(pom);
            //for testing
            /*pom.getFt();
            history.addrate(add[0], add[1]);
            history.rate.add(history.newFt(add[0], add[1]));
            history.output();*/
        }
        return history;
    }
    public String transfer(String from, String to){//convert currancies
        Converter converter=context.getBean("converterBean", Converter.class);
        converter.setFromTo(from, to);
        converter.addURL();
        String content=converter.getContent().toString();
        content=content.replace("{", "").replace("}", "");
        String con[]=content.split(":");
        content=con[1];
        con=content.split("e");
        try {
            long c = Long.parseLong(content);
            //System.out.print(" double: "+c);
        }catch (Exception e){}
        /*for(int i=0; i<content.length(); i++){
            if(content.charAt(i)>47 && content.charAt(i)<58 || content.charAt(i)=='.'){
                result+=content.charAt(i);
            }
        }*/

        return content;
    }
    public ArrayList<currency> countries= new ArrayList<>();//list with countries ids and names
    private void getCountries(){//add all countries to list
         //System.out.println("Get countries");
        Content contents=context.getBean("countriesBean", Content.class);
        String content=contents.getContent().toString();
        String[] word = content.replace("{", "").replace("}", "").replace("\n", "").split(",");
       //for testing
        /*for(int i=0; i<word.length; i++){

            System.out.println(word[i]);
        }*/
        for(int i=0; i<word.length; i++){
            String[] add=word[i].replace("\"","").split(":");
            currency val=new currency(add);
            countries.add(val);
        }
    }
}
