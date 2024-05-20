package com.foodieFav;
import java.io.FileNotFoundException;
import java.io.FileWriter;  //Import FileWriter class
import java.util.*; // Import all utilities
import java.lang.*; // Import Java Language Package
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

import static com.foodieFav.FoodQueue.*;


public class Main {
    static int stock = 50; //initialize stock variable
    //initialize arrays for each cashier queue
    static boolean element1 = true;
    static double price = 650.00;
    static double queue1_Income=0.00;
    static double queue2_Income=0.00;
    static double queue3_Income=0.00;
    private static FoodQueue foodQueue = new FoodQueue();       //Creating new instance of a class
    private static CircularQueue circularQueue = new CircularQueue();
    private static int burgersReq = 0;  //Required Burger count

    //Creating Methods for each option
    public static void viewAllQueues() {
        System.out.print("""
                ********************
                *     Cashiers     *
                ********************
                """);
        for (int i = 0; i < foodQueue.cashier1.length || i < cashier2.length || i < foodQueue.cashier3.length; i++) {   //using for loop to print queues vertically
            if (i < foodQueue.cashier1.length) {
                if (foodQueue.cashier1[i] == null) {  //printing X if element is null
                    System.out.print(" ");
                    System.out.print("   X");
                } else {
                    System.out.print("    O");
                }
            } else {
                System.out.print("     ");
            }
            if (i < cashier2.length) {
                if (cashier2[i] == null) {
                    System.out.print("    X");
                } else {
                    System.out.print("    O");
                }
            } else {
                System.out.print("     ");
            }
            if (i < foodQueue.cashier3.length) {
                if (foodQueue.cashier3[i] == null) {
                    System.out.print("    X");
                } else {
                    System.out.print("    O");
                }
            }
            System.out.println("  ");
        }
        System.out.println("X – Occupied O – Not Occupied");
    }


    public static void allEmptyQueues() {        //https://stackoverflow.com/questions/21592835/how-to-check-a-list-whether-it-contains-all-the-elements-as-null-string
        if (foodQueue.cashier1[0] == null && foodQueue.cashier1[1] == null) {     //Checking if each element is null to find empty queues
            System.out.println("Queue 1 is empty");
        }
        if (cashier2[0] == null && cashier2[1] == null && cashier2[2] == null) {
            System.out.println("Queue 2 is empty");
        }
        if (foodQueue.cashier3[0] == null && foodQueue.cashier3[1] == null && foodQueue.cashier3[2] == null && foodQueue.cashier3[3] == null && foodQueue.cashier3[4] == null) {
            System.out.println("Queue 3 is empty");
        }
    }

    public static void addCustomer() {      //https://stackoverflow.com/questions/2622725/how-to-take-user-input-in-array-using-java
        if (stock > 0) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter first name of customer : ");
                String fName = input.next();     //creating temporary variable to store customer name before adding it to the array
                System.out.print("Enter second name of customer : ");
                String sName = input.next();
                System.out.print("Enter No. of burgers required : ");
                int bCount = input.nextInt();
                if (bCount>stock){
                    System.out.println("Insufficient Burger Stock, Add customer after updating stock");
                    menu();
                }

                Customer customerDetails = new Customer(fName,sName,bCount);  //Creating new instance
                FoodQueue.getMinQueue();                                      //Calling method to get queue with minimum customers from FoodQueue class
                String queued="n";// Variable to identify whether customer is queued or not
                if(foodQueue.cashier3[4] != null){
                    System.out.println("No free queues available, customer added to waiting queue");
                    CircularQueue.enqueue(fName,sName,bCount);
                }
                switch(min){
                    case 1:
                        for (int i = 0; i < foodQueue.cashier1.length; i++) {

                            if (foodQueue.cashier1[i] == null && queued=="n") {
                                foodQueue.cashier1[i] = customerDetails;        //Adding customerDetails to cashier array accordingly if slots are available
                                queued="y";
                                System.out.println(Arrays.toString(cashier1));
                                System.out.println(Arrays.toString(cashier2));
                                System.out.println(Arrays.toString(cashier3));
                                System.out.println(fName + "added to the Queue 1 successfully");

                                break;
                            }
                        }break;
                    case 2:
                        for (int i = 0; i < cashier2.length; i++) {
                            if (cashier2[i] == null && queued=="n") {   //Adding customerDetails to cashier array accordingly if slots are available
                                cashier2[i] = customerDetails;
                                queued="y";
                                System.out.println(Arrays.toString(cashier1));
                                System.out.println(Arrays.toString(cashier2));
                                System.out.println(Arrays.toString(cashier3));
                                System.out.println(fName + "added to the Queue successfully");
                                break;
                            }
                        }break;
                    case 3:
                        for (int i = 0; i < foodQueue.cashier3.length; i++) {
                            if (foodQueue.cashier3[i] == null && queued=="n") {
                                foodQueue.cashier3[i] = customerDetails;            //Adding customerDetails to cashier array accordingly if slots are available
                                queued="y";
                                System.out.println(Arrays.toString(cashier1));
                                System.out.println(Arrays.toString(cashier2));
                                System.out.println(Arrays.toString(cashier3));
                                System.out.println(fName + "added to the Queue 3 successfully");
                                break;
                            }
                        }break;
                }

            } catch (
                    InputMismatchException e) {    //checking if the input is valid, if not, calls the same method again
                System.out.println("Invalid data\n");
                addCustomer();
            }
            if (stock <= 10) {
                System.out.println("Warning: Burger stock is at " + stock);    //Shows warning when burger stock hits 10
            }
        } else {
            System.out.println("Burger stock insufficient, please add more stock");
        }
    }

    public static void removeServedCustomer() {      //https://stackoverflow.com/questions/7970857/java-shifting-elements-in-an-array
        try {
            System.out.print("Enter which queue includes the served customer (1,2 or 3) : ");
            Scanner input = new Scanner(System.in);
            int queue = input.nextInt();
            if (queue == 1) {

                burgersReq = foodQueue.cashier1[0].getB_count();    // Calling getB_count method from Customer class to get requested burger count of appropriate customer
                stock-=burgersReq;
                queue1_Income+=(burgersReq*price);                  // Calculating queue income using burger count and price of each burger
                for (int i = 0; i < foodQueue.cashier1.length; i++) {
                    if (i == foodQueue.cashier1.length - 1) {
                        foodQueue.cashier1[i] = null;                       //making last element null
                        break;
                    }
                    foodQueue.cashier1[i] = foodQueue.cashier1[i + 1];      //shifting each element forward by one index
                }

                cashier1[cashier1.length-1]=CircularQueue.dequeue();
                System.out.println("Customer served and removed successfully");
            }
            if (queue == 2) {
                burgersReq = cashier2[0].getB_count();
                stock-=burgersReq;
                queue1_Income+=(burgersReq*price);
                for (int i = 0; i < cashier2.length; i++) {
                    if (i == cashier2.length - 1) {
                        cashier2[i] = null;
                        break;
                    }
                    cashier2[i] = cashier2[i + 1];

                }
                cashier2[cashier2.length-1]=CircularQueue.dequeue();
                System.out.println("Customer served and removed successfully");
            }
            if (queue == 3) {
                burgersReq = foodQueue.cashier3[0].getB_count();
                stock-=burgersReq;
                queue1_Income+=(burgersReq*price);
                for (int i = 0; i < foodQueue.cashier3.length; i++) {
                    if (i == foodQueue.cashier3.length - 1) {
                        foodQueue.cashier3[i] = null;
                        break;
                    }
                    foodQueue.cashier3[i] = foodQueue.cashier3[i + 1];
                }
                cashier3[cashier3.length-1]=CircularQueue.dequeue();
                System.out.println("Customer served and removed successfully");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid data\n");
            removeServedCustomer();
        }
    }

    public static void removeCustomer() {
        try {
            System.out.print("Enter which queue includes the customer to be removed (1,2 or 3) : ");
            Scanner input = new Scanner(System.in);
            int queue = input.nextInt();
            System.out.print("Enter position number : ");
            int position = input.nextInt();  //declare position variable to get customer's row

            switch (queue) {
                case 1:
                    if (foodQueue.cashier1[position - 1] != null) {    //checking whether the row before the element is empty or not
                        if (foodQueue.cashier1[position] == null) {
                            foodQueue.cashier1[position - 1] = null;
                        } else {
                            foodQueue.cashier1[position] = null;   //loops weren't used because there are only 2 elements in this array
                        }
                        //stock+=foodQueue.cashier1[position-1].getB_count();     //Re-adding stock after removing customer
                        System.out.println("Customer successfully removed");
                    } else {
                        System.out.println("That row is already empty");
                    }
                    break;
                case 2:
                    if (cashier2[position - 1] != null) {
                        for (int i = position - 1; i <= cashier2.length; i++) {
                            if (position == cashier2.length) {
                                cashier2[position - 1] = null;   //checking if the customer is the last customer, if so remove directly

                            } else {
                                cashier2[i] = cashier2[position];
                                position++;  //shifting customers forward
                            }
                        }
                        //stock+=foodQueue.cashier2[position-1].getB_count();
                        System.out.println("Customer successfully removed");

                    } else {
                        System.out.println("That row is already empty");
                    }
                    break;
                case 3:
                    if (foodQueue.cashier3[position - 1] != null) {
                        for (int i = position - 1; i <= foodQueue.cashier3.length; i++) {
                            if (position == foodQueue.cashier3.length) {
                                foodQueue.cashier3[position - 1] = null;
                            } else {
                                foodQueue.cashier3[i] = foodQueue.cashier3[position];
                                position++;
                            }
                        }
                        //stock+=foodQueue.cashier3[position-1].getB_count();
                        System.out.println("Customer successfully removed");

                    } else {
                        System.out.println("That row is already empty");
                        break;
                    }

            }
        } catch (InputMismatchException e) {
            System.out.println("invalid data");
            removeCustomer();       //Recalling same method if user inputs invalid data
        }
    }

    public static void sortedNames() {  //https://www.youtube.com/watch?v=j1ATGus8a2I
        Scanner input = new Scanner(System.in);
        System.out.print("Enter queue to be sorted :");
        int cashier_num = input.nextInt();  //cashier queue number to be sorted
        switch (cashier_num) {
            case 1:
                if (foodQueue.cashier1 != null && foodQueue.cashier1.length > 0) {  //Checking if queue has customers
                    for (int i = 0; i < foodQueue.cashier1.length - 1; i++) {
                        for (int j = 0; j < (foodQueue.cashier1.length - 1) - i; j++) {  //Comparing each name through inner loops
                            if (foodQueue.cashier1[j] != null && foodQueue.cashier1[j + 1] != null && foodQueue.cashier1[j].getF_name().compareTo(foodQueue.cashier1[j + 1].getF_name()) > 0) {
                                //Calling getF_name method from Customer class to get first names of customers and comparing first array element with the next element

                                Customer k = foodQueue.cashier1[j];                     //Switching the names according to alphabetical order
                                foodQueue.cashier1[j] = foodQueue.cashier1[j + 1];
                                foodQueue.cashier1[j + 1] = k;
                            }
                        }
                    }
                    System.out.print("Cashier 1 : ");
                    for (Customer element : foodQueue.cashier1) {  //Printing sorted queue array
                        if (element != null) {
                            if (!element1) {
                                System.out.print(", ");
                            }
                            System.out.print(element);
                            element1 = false;
                        }
                    }
                    System.out.println();
                }
            case 2:
                if (cashier2 != null && cashier2.length > 0) {
                    for (int i = 0; i < cashier2.length - 1; i++) {
                        for (int j = 0; j < (cashier2.length - 1) - i; j++) {
                            if (cashier2[j] != null && cashier2[j + 1] != null && cashier2[j].getF_name().compareTo(cashier2[j + 1].getF_name()) > 0) {
                                Customer k = cashier2[j];
                                cashier2[j] = cashier2[j + 1];
                                cashier2[j + 1] = k;
                            }
                        }
                    }
                    System.out.print("Cashier 2 : ");
                    for (Customer element : foodQueue.cashier1) {
                        if (element != null) {
                            if (!element1) {
                                System.out.print(", ");
                            }
                            System.out.print(element);
                            element1 = false;
                        }
                    }
                    System.out.println();
                }
            case 3:
                if (foodQueue.cashier3 != null && foodQueue.cashier3.length > 0) {
                    for (int i = 0; i < foodQueue.cashier3.length - 1; i++) {
                        for (int j = 0; j < (foodQueue.cashier3.length - 1) - i; j++) {
                            if (foodQueue.cashier3[j] != null && foodQueue.cashier3[j + 1] != null && foodQueue.cashier3[j].getF_name().compareTo(foodQueue.cashier3[j + 1].getF_name()) > 0) {
                                Customer k = foodQueue.cashier3[j];
                                foodQueue.cashier3[j] = foodQueue.cashier3[j + 1];
                                foodQueue.cashier3[j + 1] = k;
                            }
                        }
                    }
                    System.out.print("Cashier 3 : ");
                    for (Customer element : foodQueue.cashier3) {
                        if (element != null) {
                            if (!element1) {
                                System.out.print(", ");
                            }
                            System.out.print(element);
                            element1 = false;
                        }
                    }
                    System.out.println();
                }
        }
    }

    public static void storeData() {     //https://www.w3schools.com/java/java_files_create.asp\
        Customer [][] queues = {FoodQueue.cashier1, FoodQueue.cashier2, FoodQueue.cashier3};    //Creating 2D array (queue) from Customer class
        try {
            FileWriter myObj = new FileWriter("Foodies Fave Food Center.txt", true);  //creating file
            for (Customer[] cashier : queues) {   //array initialization (Getting each inner array from 2D array)
                for (int i = 0; i < cashier.length-1; i++) {
                    if (cashier[i] != null) {
                        myObj.write(cashier[i] + ", "); //writing outputs if array is not empty
                    }
                }
                myObj.write("\n----------------\n");
            }
            myObj.write(stock + " Burgers in stock\n"); //Writing burger stock to file
            myObj.write("\n----------------\n");
            myObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void loadDataFromTxtFile() {       //https://www.w3schools.com/java/java_files_read.asp
        try {
            File myFile = new File("Foodies Fave Food Center.txt");
            Scanner myReader = new Scanner(myFile); //using scanner class to read data from file
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();  //reading each line
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(">>>> An error occurred.");
        }
    }

    public static void viewStock() {
        System.out.println(stock + " Burgers remain in stock"); //printing remaining stock
    }

    public static void addStock() {
        System.out.print("Add number of burgers to be added to stock :");
        Scanner input = new Scanner(System.in);
        int n_stock = input.nextInt();   //adding stock
        stock += n_stock;
        if (stock > 50) {
            stock -= n_stock;     //checking if stock exceeds 50, if so gives error message and return stock
            System.out.println("Burger stock cannot exceed 50, stock remain at :" + stock);
        }
    }
    public static void viewIncome(){
        System.out.println("***** Income *****");
        System.out.println("Queue 1 : " +queue1_Income+"0/-");
        System.out.println("Queue 2 : " +queue2_Income+"0/-");
        System.out.println("Queue 3 : " +queue3_Income+"0/-");

    }
    public static void menu(){

        Scanner input = new Scanner(System.in);
        boolean shouldContinue = true;
        while (shouldContinue) {
            System.out.println("\n--- Foodies Fave Food Center ---");
            System.out.println("100 or VFQ : View all Queues.");
            System.out.println("101 or VEQ : View all empty Queues.");
            System.out.println("102 or ACQ : Add customer to a Queue.");
            System.out.println("103 or RCQ : Remove a customer from a Queue.");
            System.out.println("104 or PCQ : Remove a served customer.");
            System.out.println("105 or VCS : View Customers Sorted in alphabetical order.");
            System.out.println("106 or SPD : Store Program Data into file.");
            System.out.println("107 or LPD : Load Program Data from file.");
            System.out.println("108 or STK : View Remaining burgers Stock");
            System.out.println("109 or AFS : Add burgers to Stock");
            System.out.println("110 or IFQ : View income of each queue");
            System.out.println("999 or EXT : Exit the Program");
            System.out.print("Enter option: ");
            String option = input.next();
            if (option.equals("999") || (option.equals("EXT"))) {  //https://stackoverflow.com/questions/10208052/string-equals-with-multiple-conditions-and-one-action-on-result
                System.out.println("Exiting..");
                shouldContinue = false;
            } else {
                switch (option) {   //https://stackoverflow.com/questions/16706716/using-two-values-for-one-switch-case-statement
                    case "100":
                    case "VFQ":
                        viewAllQueues();
                        break;
                    case "101":
                    case "VEQ":
                        allEmptyQueues();
                        break;
                    case "102":
                    case "ACQ":
                        addCustomer();

                        break;
                    case "103":
                    case "RCQ":
                        removeCustomer();
                        break;
                    case "104":
                    case "PCQ":
                        removeServedCustomer();
                        break;
                    case "105":
                    case "VCS":
                        sortedNames();
                        break;
                    case "106":
                    case "SPD":
                        storeData();
                        break;
                    case "107":
                    case "LPD":
                        loadDataFromTxtFile();
                        break;
                    case "108":
                    case "STK":
                        viewStock();
                        break;
                    case "109":
                    case "AFS":
                        addStock();
                        break;
                    case "110":
                    case "IFQ":
                        viewIncome();
                        break;
                    case "112":
                    case "GUI":
                        System.out.println("GUI not implemented");
                        menu();
                    default:
                        System.out.println("Invalid data");
                        break;
                }
            }
        }
    }
    public static void main(String[] args) {
        viewAllQueues();
        menu();
    }
}