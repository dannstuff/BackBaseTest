//Author: Daniel Morgan <daniel96morgan@hotmail.com>
import java.util.*;

//generates a transaction to be processed

public class Transaction {
    private int accountID;
    private String accountType;
    private String initiatorType;
    private String dateTime;
    private Float transactionValue;

    Transaction (String accountID, String accountType, String initiatorType, String dateTime, String transactionValue) {
      this.accountID = Integer.parseInt(accountID);
      this.accountType = accountType;
      this.initiatorType = initiatorType;
      this.dateTime = dateTime;
      this.transactionValue = Float.parseFloat(transactionValue);
    }



    public int getAccountID() {
      return accountID;
    }

    public String getAccountType() {
      return accountType;
    }

    public String getInitiatorType(){
      return initiatorType;
    }

    public String getDateTime(){
      return dateTime;
    }

    public Float getTransactionValue() {
      return transactionValue;
    }

    public String toString() {
      return accountID + "," + accountType + "," + initiatorType + "," + dateTime + "," + transactionValue;
    }

}
