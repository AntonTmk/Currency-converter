package org.example.parsClases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import static javax.swing.JOptionPane.showMessageDialog;

public class Content {//get content from URL
    public String API;
    public String URL;
    public Content(String API, String URL){
        this.API=API;
        this.URL=URL;
    }

    public Content() {
    }

    public void setAPI(String API) {
        this.API = API;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    public StringBuilder getContent(){//return string with all test from URL
        StringBuilder content = new StringBuilder();
        try {
            URL=URL.replace("API", API);
           // System.out.println("URL: "+URL);
            java.net.URL url = new URL(URL);
            URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // reading from the urlconnection
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch(Exception e){
            showMessageDialog(null, "SERVER ERROR: " + e);
           // System.out.println(e);
        }
        return content;
    }
}
