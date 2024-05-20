package com.foodieFav;
import java.util.*;
public class Customer {
    private String f_name;   // First name
    private String s_name;   // Second name
    private int b_count;  // Burgers required

    public Customer(String f_name, String s_name, int b_count) {
        this.f_name = f_name;
        this.s_name = s_name;
        this.b_count = b_count;
    }

    public String getF_name(){
        return  f_name;
    }

    public String getS_name(){
        return  s_name;
    }

    public int getB_count(){

        return  b_count;
    }

    @Override
    public String toString(){
        return "Customer:" +
                "First name='" + f_name + '\'' +
                ", Second name='" + s_name + '\'' +
                ", No. of burgers required=" + b_count;
    }
}
