package com.java.dictionary;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
  
/** 
 * �ж���������״��. 
 * @author nagsh 
 * 
 */  
public class NetStatus {  
    public boolean isConnect(){  
        boolean connect = false;  
        Runtime runtime = Runtime.getRuntime();  
        Process process;  
        try {  
            process = runtime.exec("ping " + "www.baidu.com");  
            InputStream is = process.getInputStream();   
            InputStreamReader isr = new InputStreamReader(is);   
            BufferedReader br = new BufferedReader(isr);   
            String line = null;   
            StringBuffer sb = new StringBuffer();   
            while ((line = br.readLine()) != null) {   
                sb.append(line);   
            }   
//            System.out.println("����ֵΪ:"+sb);    
            is.close();   
            isr.close();   
            br.close();   
  
            if (null != sb && !sb.toString().equals("")) {   
                if (sb.toString().indexOf("TTL") > 0) {   
                    // ���糩ͨ    
                    connect = true;  
                } else {   
                    // ���粻��ͨ    
                    connect = false;  
                }   
            }   
        } catch (IOException e) {  
            e.printStackTrace();  
        }   
        return connect;  
    } 
    
    
}
