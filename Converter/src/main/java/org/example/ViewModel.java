package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.example.parsClases.History;



public class ViewModel {
    private Model model =new Model();
    public String cursfrom="USD";//save last currency (from)
    public String cursto="UAH";//save last currency (to)
    public float index=1;//save last value to convert
    public String curs(String fr, String t, String mnoj){//return curs
        try{
            float f=Float.parseFloat(mnoj);
            index=f;
        }catch (Exception e){}
//System.out.println("float: "+index);
        String from=fr.replace(" ", "").substring(0, 3);
        String to=t.replace(" ", "").substring(0, 3);
        cursfrom=from;
        cursto=to;
        //System.out.println(from+" "+to);
        String res= model.transfer(from, to);
        String zero="";
        boolean irrational=false;
        try{
String time[]=res.split("e");
double zeros= Double.parseDouble(time[1]);
//System.out.println("zseros"+zeros);
            zero="0,";
            while(zeros<0){
                zero+="0";
                zeros++;
            }
            res=res.replace(".", "").substring(0, 2);
            irrational=true;
        }catch (Exception e){}

res=String.valueOf(Float.parseFloat(res)*index);
        if(!irrational){
            int i=0;
            while(res.charAt(i)!='.' && i<res.length()){

                i++;
            }
            while(res.charAt(i)=='0' && i<res.length()){
                i++;
            }
            if(i+4<res.length()) {
                res = res.substring(0, i + 3);
            }else{if(i+1<res.length()){
                res=res.substring(0, i+1);}}
        }
if(irrational){
    res=res.replace(".", "");
}
        return index+"  "+from+"  to  "+to+":  "+zero+res;
    }
    public ObservableList<String> Countries(){//return countries list
        ObservableList<String> val= FXCollections.observableArrayList();
        for(int i = 0; i< model.countries.size(); i++){
            val.add(model.countries.get(i).id+" "+ model.countries.get(i).name);
        }
        return val;
    }

    private String lineratefrom="";
    private String linerateto="";
    public XYChart.Series Series(String fr, String t){//return series for line rate
        XYChart.Series<String, Double> result = new XYChart.Series();
        String from=fr.replace(" ", "").substring(0, 3);
        lineratefrom=from;
        String to=t.replace(" ", "").substring(0, 3);
        linerateto=to;
        History time = model.getHistory(from, to);
        result.setName(time.from+" to "+time.to);
        for (int i = 0; i < time.rate.size(); i++) {
            result.getData().add(new XYChart.Data(time.getDate(i), Double.parseDouble(time.getCurs(i))));
        }
        return result;
    }
    public ObservableList<String> list= FXCollections.observableArrayList();//list of comparising currencies
    public String listfrom="";
    public void addList(String fr, String t){//add element to list
        if(listfrom==""){
            listfrom=fr;
            list.add(fr+" 1");
        }
        if(listfrom==fr){
            String from=fr.replace(" ", "").substring(0, 3);
            String to=t.replace(" ", "").substring(0, 3);
            list.add(to+" "+t.replace(" ", "").substring(3)+"       "+ model.transfer(from, to));
        }else{
            list.clear();
            listfrom="";
            addList(fr, t);
        }
        //for testing
       /* System.out.println("listshow");
        for(int i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }*/
    }
    public void editlist(String inf){//delete element from list
        for(int i=0; i<list.size(); i++){
            //System.out.println(list.get(i).replace(" ", "").substring(0, 3));
            if(i!=0)
                if(list.get(i).replace(" ", "").substring(0, 3).equals(inf)){
                   // System.out.println(list.get(i));
                    list.remove(i);
                }
        }
    }
    public ViewModel(){}
    public void updateList(){//update all elements in list
        try {
            String from = listfrom.replace(" ", "").substring(0, 3);
            for (int i = 1; i < list.size(); i++) {
                String valname = "";
                for (int m = 0; m < list.get(i).length(); m++) {
                    if (list.get(i).charAt(m) > 47 && list.get(i).charAt(m) < 58 || list.get(i).charAt(m) == '.') {

                    } else {
                        valname += list.get(i).charAt(m);
                    }
                }
                String to = list.get(i).replace(" ", "").substring(0, 3);
                list.remove(i);
                list.add(valname.replace("\n", "") + model.transfer(from, to));
            }
        }catch(Exception e){}
    }
    public String updateCurs(){//update curs
        return curs(cursfrom, cursto, String.valueOf(index));
    }

    public XYChart.Series linerateUpdate(){//update series for linear rate
        if(lineratefrom!=""){
            return Series(lineratefrom, linerateto);}
        else{
            XYChart.Series b=new XYChart.Series();
            return b;}
    }
}
