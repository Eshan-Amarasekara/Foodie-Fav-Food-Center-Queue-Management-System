package com.foodieFav;
import java.util.*;
public class FoodQueue {
    //initialize arrays for each cashier queue
    public static Customer[] cashier1;
    public static Customer[] cashier2;
    public static Customer[] cashier3;
    public static int c1=0;                 //Cashier 1 count
    public static int c2=0;                 //Cashier 2 count
    public static int c3=0;                 //Cashier 3 count
    public static int min;
    public static Customer[][] cashiers = {cashier1, cashier2, cashier3};

    public FoodQueue(){
        cashier1 = new Customer[2];
        cashier2 = new Customer[3];
        cashier3 = new Customer[5];
        Customer[][] cashiers = {cashier1, cashier2, cashier3};
    }

    public static void getMinQueue(){

        c1=c2=c3=0;

        for(int i = 0; i<=cashier1.length-1; i++){
            if (cashier1[i]!=null){
                c1+=1;
            }
        }
        for(int i = 0; i<=cashier2.length-1; i++){
            if (cashier2[i]!=null){
                c2+=1;
            }
        }
        for(int i = 0; i<=cashier3.length-1; i++){
            if (cashier3[i]!=null){
                c3+=1;
            }
        }
        if(c1<2) {
            if (c1 < c2 && c1 < c3) {
                min = 1;
            }else if (c1 == c2 && c1 == c3) {
                    min = 1;
            }else if (c2 < c1 && c2 < c3 ) {
                min = 2;
            }else if(c2 == c3){
                min = 2;
            } else {
                min = 3;
            }
        } else if(c2<3) {
            if (c2 < c3 || c2 == c3) {
                min = 2;
            } else {
                min = 3;
            }
        }else{
            min=3;
        }
    }


}
