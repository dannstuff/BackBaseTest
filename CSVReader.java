//Author: Daniel Morgan <daniel96morgan@hotmail.com>
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;


public class CSVReader{
//this class both reads and writes to CSV
  public static ArrayList<String> readFile(String csvFile, Boolean skipfirstline) {
    //function that reads in the input CSV file, gives the option to skipfirstline if the
    //first line is a header
    String filename = csvFile;
    BufferedReader reader = null;
    String line = "";
    String headerLine="";
    //the array in which we'll keep our transactions
    ArrayList<String> transactions = new ArrayList<String>();
    try {

       reader = new BufferedReader(new FileReader(filename));

       if(skipfirstline) {
         headerLine = reader.readLine();
       }else{
         line = reader.readLine();
       }

        while (line != null) {
          //because we initialise line as the empty string
          if(!line.equals("")){
            transactions.add(line);
            line = reader.readLine();
          }else {
            line = reader.readLine();
          }
       }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return transactions;
  }

  public static void writeFirstLine(File filename) {
    //write the header to the file
    String firstLine = "AccountID,AccountType,InitiatorType,DateTime,TransactionValue";
    try{
      FileWriter fw = new FileWriter(filename,true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter writer = new PrintWriter(bw);
      writer.println(firstLine);
      writer.close();


    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void writeTransaction(String transaction, File filename) {
    //write the transaction to the file using the toString function in transaction
    try {
        FileWriter fw = new FileWriter(filename,true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter writer = new PrintWriter(bw);
        writer.println(transaction);
        writer.close();

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
  }
  }
}
