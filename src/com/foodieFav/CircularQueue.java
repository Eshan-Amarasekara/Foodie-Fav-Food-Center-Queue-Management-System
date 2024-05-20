package com.foodieFav;

import java.util.NoSuchElementException;
//https://www.youtube.com/watch?v=8sjFA-IX-Ww
public class CircularQueue {
    private static int front;   //initializing start and end of circular array
    private static int end;
    private static Customer[] waitingQueue;     //creating waiting queue array
    private static int length;

    public CircularQueue(){     //constructor
        this.length=5;
        this.front=this.end=-1;
        waitingQueue = new Customer[5];

    }
    public static boolean isEmpty(){        //method to check if array is empty
        return front == -1;
    }
    private static boolean isFull(){        //method to check if array is full
        return (end+1) % waitingQueue.length == front;
    }

    private static void resize(){           //method to resize array
        Customer [] temp = new Customer[waitingQueue.length*2];
        int i = 0;
        int j = front;

        do{
            temp[i++] = waitingQueue[j];
            j=(j+1) % waitingQueue.length;
        }while(j != front);
        front = 0;
        end = waitingQueue.length-1;
        waitingQueue=temp;
    }
    public static void enqueue(String fName, String sName, int bCount) {
        if(isFull()){
            resize();
        }else if(isEmpty()) {
            ++front;
            end = (end+1) % waitingQueue.length;
            Customer customerDetails = new Customer(fName, sName, bCount);
            waitingQueue[end]= customerDetails;     // Add customer details to array
        }
    }

    public static Customer dequeue(){
        if(isEmpty()){
            System.out.println("Waiting queue is empty");
            Main.menu();
        }
        Customer temp = waitingQueue[front];
        if(front == end){
            front = end =-1;
        }else{
            front = (front+1) % waitingQueue.length;
        }
        return temp;
    }
}
