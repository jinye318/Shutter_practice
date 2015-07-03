package com.example.administrator.myapplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Administrator on 2015-07-03.
 */
public class Dictionary {

    private HashMap<String, String> dic;

    Dictionary() {
        dic = new HashMap<String, String>();
    }

    Dictionary(String filename) {
        dic = new HashMap<String, String>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String data = "";

            while ((data = reader.readLine()) != null) {
				System.out.println(data);

                String[] temp = data.split(":");
                String key = temp[0].trim();
                String value = temp[1].trim();
                dic.put(key, value);
            }
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String find(String word) {

        String find = dic.get(word);
        return find;
    }


}
