package customer;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
public class Customer {
    public static void main(String[] args) {
               Scanner scan = new Scanner(System.in);
        Customers cust = new Customers();
        Supplier Supp = new Supplier();
        Product product = new Product();
        Invoice invoice = new Invoice();
        Pruchase purch = new Pruchase();
        Payable pay = new Payable();
        Recive recive = new Recive();
        Ledger ledger = new Ledger();
        Ledgers Supplier = new Ledgers();
        ReturnInvoice returnIn = new ReturnInvoice();
        System.out.println("\t----Welcome to Abbasi's Mart----");
        System.out.println("\t---Menu---");
            double option = 0;
while(option != 11) {
                    System.out.println("\n1. Customer Registration");
            System.out.println("2. Supplier Registration");
            System.out.println("3. Product Registration");
            System.out.println("4. Invoice");
            System.out.println("5. Return Invoice");
            System.out.println("6. Purchase");
            System.out.println("7. Payable");
            System.out.println("8. Receivable");
            System.out.println("9. Search Invoice");
            System.out.println("10. Check ledger");
            System.out.println("11. Exit\n");
    String num;
    do {
        System.out.print("Enter Option: ");
        num = scan.next();
        if (!num.matches("^[1-9][0-9]*$")) {
            System.out.println(" ! enter  Positive number only.");
        }
    } while (!num.matches("^[1-9][0-9]*$")); 

    option = Double.parseDouble(num);
if(option == 1){
    scan.nextLine(); 
    boolean completed = cust.information(scan); 
    
    if(completed){
        cust.File(); 
        cust.InsertData();
    } else {
        System.out.println("Customer registration cancelled. Returning to main menu...");
    }
}
else if(option == 2){ 
    scan.nextLine();
    boolean completed = Supp.information(scan);
    if(completed){
        Supp.File();
        Supp.InsertData();
    } else {
        System.out.println("Supplier registration cancelled. Returning to main menu...");
    }
}

else if(option == 3){
    scan.nextLine();
    boolean completed = product.inputData(scan);
    if(completed){
        product.insertData();
    } else {
        System.out.println("Product registration cancelled. Returning to main menu...");
    }
}

else if (option == 4) {
    scan.nextLine();
    boolean completed = invoice.Bill(scan);

    if (!completed) {
        System.out.println("Invoice generation cancelled. Returning to main menu...");
    }
}



else if(option == 5){
        scan.nextLine();
    boolean completed = returnIn.processReturn(scan);

    if (!completed) {
        System.out.println("Invoice generation cancelled. Returning to main menu...");
    }
}
    else if(option == 6){        
            scan.nextLine();
    boolean completed = purch.Bill(scan);
    if (!completed) {
        System.out.println("Invoice generation cancelled. Returning to main menu...");
    }
}
 else if (option == 7) {
       System.out.println("Type '00' to exit Payable.");
    scan.nextLine();
    boolean completed = false;

    String supplierId;
    boolean found = false;
    double total =0.0;

    while (true) {
        System.out.print("Enter Supplier ID: ");
        supplierId = scan.next().trim();

        if (supplierId.equals("00")) break;

        if (!supplierId.matches("^[0-9]+$")) {
            System.out.println("! Only positive numbers\n");
            continue;
        }

        found = pay.fetchSupplierById(supplierId);

        if (!found) {
            System.out.println("Supplier not found! Please enter again.\n");
            continue;
        }

        total = Double.parseDouble(pay.totalAmount);

        if (total == 0.0) {
            System.out.println("This supplier’s bill is already paid\n");
            continue;
        }

        break;
    }

    if (supplierId.equals("00")) {
        System.out.println("Payment cancelled. Returning to main menu...");
    } else {

        double amount = 0.0;

        while (true) {
            System.out.print("Enter payment amount: ");
            String inputt = scan.next().trim();

            if (inputt.equals("00")) break;

            if (inputt.matches("^[0-9]+(\\.[0-9]+)?$")) {
                amount = Double.parseDouble(inputt);

                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero!");
                    continue;
                }
                if (amount > total) {
                    double extra = amount - total;
                    System.out.println("You overpaid by " + extra + ", please re-enter.");
                    continue;
                }
                break;
            } else {
                System.out.println("Enter valid positive number!");
            }
        }

        if (amount == 0 || String.valueOf(amount).equals("00")) {
            System.out.println("Payment cancelled. Returning to main menu...");
        } else {

            double confirm = 0.0;

            while (true) {
                System.out.print("Enter confirm amount: ");
                String inputt = scan.next().trim();

                if (inputt.equals("00")) break;

                if (inputt.matches("^[0-9]+(\\.[0-9]+)?$")) {
                    confirm = Double.parseDouble(inputt);

                    if (confirm != amount) {
                        System.out.println("Confirm amount doesn’t match payment amount!");
                        System.out.print("Do you want to continue with this amount? (Yes/No): ");
                        String choices = scan.next().trim().toUpperCase();

                        if (choices.equalsIgnoreCase("Yes")) {
                            System.out.println("Payment confirmed manually.");
                            break;
                        } else {
                            System.out.println("Please re-enter confirm amount.\n");
                            continue;
                        }
                    }
                    break;

                } else {
                    System.out.println("Enter valid positive number!");
                }
            }

            if (confirm == 0 || String.valueOf(confirm).equals("00")) {
                System.out.println("Payment cancelled. Returning to main menu...");
            } else {

                String date = "";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

                while (true) {
                    System.out.print("Enter payment date (e.g. 1/12/2025): ");
                    date = scan.next().trim();

                    if (date.equals("00")) break;

                    if (!date.matches("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}$")) {
                        System.out.println("Invalid format!\n");
                        continue;
                    }

                    try {
                        LocalDate parsedDate = LocalDate.parse(date, formatter);

                        if (parsedDate.getYear() < 2025) {
                            System.out.println("Year must be 2025 or greater!\n");
                            continue;
                        }

                        break;

                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date!\n");
                    }
                }

                if (date.equals("00")) {
                    System.out.println("Payment cancelled. Returning to main menu...");
                } else {

                    double remaining = total - confirm;

                    if (confirm == total) {
                        System.out.println("Payment complete!");
                        pay.updateSupplierRemainingAmount(supplierId, 0.0, date);
                        pay.savePaymentRecord(supplierId, pay.supplierName, total, confirm, 0.0, date);
                    } else {
                        System.out.println("Remaining amount: " + remaining);
                        pay.updateSupplierRemainingAmount(supplierId, remaining, date);
                        pay.savePaymentRecord(supplierId, pay.supplierName, total, confirm, remaining, date);
                    }completed = true;}}}}
}

    
    
    else if (option == 8) {
          System.out.println("Type '00' to exit Reciveable.");
    scan.nextLine();
    boolean completed = false;

    String CustomerId;
    boolean found = false;
    double total=0.0;

    while (true) {
        System.out.print("Enter Customer ID: ");
        CustomerId = scan.next().trim();

        if (CustomerId.equals("00")) break;

        if (!CustomerId.matches("^[0-9]+$")) {
            System.out.println("! Only positive numbers\n");
            continue;
        }

        found = recive.fetchCustomerById(CustomerId);

        if (!found) {
            System.out.println("Customer not found! Please enter again.\n");
            continue;
        }

        total = Double.parseDouble(recive.totalAmount);

        if (total == 0.0) {
            System.out.println("This customer’s bill is already paid\n");
            continue;
        }

        break;
    }

    if (CustomerId.equals("00")) {
        System.out.println("Payment cancelled. Returning to main menu...");
    } else {

        double amount = 0.0;

        while (true) {
            System.out.print("Enter payment amount: ");
            String input = scan.next().trim();

            if (input.equals("00")) break;

            if (input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                amount = Double.parseDouble(input);

                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero!");
                    continue;
                }

                if (amount > total) {
                    double extra = amount - total;
                    System.out.println("You overpaid by " + extra + ", please re-enter.");
                    continue;
                }

                break;

            } else {
                System.out.println("Enter valid positive number!");
            }
        }

        if (amount == 0 || String.valueOf(amount).equals("00")) {
            System.out.println("Payment cancelled. Returning to main menu...");
        } else {

            double confirm = 0.0;

            while (true) {
                System.out.print("Enter confirm amount: ");
                String input = scan.next().trim();

                if (input.equals("00")) break;

                if (input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                    confirm = Double.parseDouble(input);

                    if (confirm != amount) {
                        System.out.println("Confirm amount doesn’t match payment amount!");
                        System.out.print("Do you want to continue with this amount? (Yes/No): ");
                        String choice = scan.next().trim().toUpperCase();

                        if (choice.equalsIgnoreCase("Yes")) {
                            System.out.println("Payment confirmed manually.");
                            break;
                        } else {
                            System.out.println("Please re-enter confirm amount.\n");
                            continue;
                        }
                    }

                    break;

                } else {
                    System.out.println("Enter valid positive number!");
                }
            }

            if (confirm == 0 || String.valueOf(confirm).equals("00")) {
                System.out.println("Payment cancelled. Returning to main menu...");
            } else {

                String date = "";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

                while (true) {
                    System.out.print("Enter payment date (e.g. 1/12/2025): ");
                    date = scan.next().trim();

                    if (date.equals("00")) break;

                    if (!date.matches("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}$")) {
                        System.out.println("Invalid format!\n");
                        continue;
                    }

                    try {
                        LocalDate parsedDate = LocalDate.parse(date, formatter);

                        if (parsedDate.getYear() < 2025) {
                            System.out.println("Year must be 2025 or greater!\n");
                            continue;
                        }

                        break;

                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date!\n");
                    }
                }

                if (date.equals("00")) {
                    System.out.println("Payment cancelled. Returning to main menu...");
                } else {

                    double remaining = total - confirm;

                    if (confirm == total) {
                        System.out.println("Payment complete!");
                        recive.updateCustomerRemainingAmount(CustomerId, 0.0, date);
                        recive.savePaymentRecord(CustomerId, recive.CustomerName, total, confirm, 0.0, date);
                    } else {
                        System.out.println("Remaining amount: " + remaining);
                        recive.updateCustomerRemainingAmount(CustomerId, remaining, date);
                        recive.savePaymentRecord(CustomerId, recive.CustomerName, total, confirm, remaining, date);
                    }completed = true;}}}}
}

  else if (option == 9) {
    scan.nextLine();
    boolean completed = false;
    int choice = 0;
    while (choice != 3) {
        System.out.println("1. Customer Invoice");
        System.out.println("2. Supplier Invoice");
        System.out.println("3. Exit");
        String select;

        while (true) {
            System.out.print("Enter Choice: ");
            select = scan.next().trim();

            if (select.equals("00")) {
                System.out.println("Returning to main menu...");
                break;
            }

            if (select.matches("^[1-9][0-9]*$")) {
                choice = Integer.parseInt(select);
                break;
            } else {
                System.out.println("Invalid input! Numbers only.");
            }
        }

        if (select.equals("00")) break;

        if (choice == 1) {
            while (true) {
                System.out.print("Enter Invoice No (or 00 to cancel): ");
                String fetch = scan.next().trim();

                if (fetch.equals("00")) break;

                if (!fetch.matches("^[1-9]+$")) {
                    System.out.println("Invalid input! Positive numbers only.\n");
                    continue;
                }

                boolean found = invoice.FetchBill(fetch);
                if (found) break;
            }
            completed = true;
        }

        else if (choice == 2) {
            while (true) {
                System.out.print("Enter Invoice No (or 00 to cancel): ");
                String fetch = scan.next().trim();

                if (fetch.equals("00")) break;

                if (!fetch.matches("^[1-9]+$")) {
                    System.out.println("Invalid input! Positive numbers only.\n");
                    continue;
                }
                boolean found = purch.FetchBill(fetch);
                if (found) break;
            }
            completed = true;
        }

        else if (choice == 3) {
            System.out.println("Returning to main menu...");
            break;
        }
        else {System.out.println("Incorrect Option");}}
    if (!completed) {
        System.out.println("Operation cancelled. Returning to main menu....");
    } 
}

else if(option == 10){ 
    scan.nextLine();
                  double choice =0 ;
        while(choice != 3){
            System.out.println("1. Customer Ledger");
            System.out.println("2. Supplier Ledger");
            System.out.println("3. Exit");
            
           String select;
while (true) {
    System.out.print("Enter Choice: ");
    select = scan.next();
    if (select.matches("^[1-9][0-9]*$")) {
        choice = Integer.parseInt(select);
        if (choice > 0) {
            break; 
        } else {
            System.out.println("Please enter positive number!");
        }
    } else {
        System.out.println(" Invalid input! Numbers only");
    }
}
            if(choice == 1){
                scan.nextLine();
 ledger.CustomerLedger(scan);
            }
            else if(choice== 2){
                scan.nextLine();
      Supplier.SupplierLedger(scan);
            }
            else if( choice== 3){
                System.out.println("Welcome Back ");
            }
            else if(choice>= 3){
                System.out.println("! Incorrect Option");
            }}
}

    else if(option==11){
        System.out.println("Exit From Menu");
    }
    else if(option >= 11){
        System.out.println("Your Option is Incorrect");
    }
}
    }}
    
    

class Customers{
    String Cid;
    String Cname;
    String Add;
    String phone;
    double Amount;
     String nameLine;
    public boolean information(Scanner scan) {
    System.out.println("Type '00' to exit registration.");

    while (true) {
        System.out.print("Enter Customer ID: ");
        String input = scan.next().trim();
        if(input.equals("00")) return false;

        if (!input.matches("^[0-9]+$") || input.isEmpty()) {
            System.out.println("Invalid Input!");
            continue;
        }
        if (FetchData(input)) {
            System.out.println("Try again! This ID is already taken.");
            continue; 
        }
        this.Cid = input;
        break;
    }

    scan.nextLine();

    while (true) {
        System.out.print("Enter Customer Name: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;

        if (!input.matches("^[A-Za-z ]+$")) {
            System.out.println("Invalid Input! Only letters allowed.");
            continue; 
        }
        if (input.isEmpty()) {
            System.out.println("Name cannot be empty!");
            continue;
        }
        this.Cname = input;
        break;
    }

    while (true) {
        System.out.print("Enter Customer Address: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;

        if (!input.matches("^[A-Za-z0-9 ,.-]+$")) {
            System.out.println("Invalid Address!");
            continue;
        }
        if (input.isEmpty()) {
            System.out.println("Address cannot be empty!");
            continue;
        }
        this.Add = input;
        break; 
    }

    while (true) {
        System.out.print("Enter Phone: ");
        String input = scan.next().trim();
        if(input.equals("00")) return false;

        if (!input.matches("^[0-9]{11}$")) {
            System.out.println("Invalid Phone Number!");
            continue;
        }
        this.phone = input;
        break; 
    }

    while (true) {
        System.out.print("Opening Balance: ");
        String input = scan.next(); 
        if(input.equals("00")) return false;

        if (!input.matches("\\d+(\\.\\d+)?")) {
            System.out.println("Try again! Amount must be numeric.");
            continue;
        }
        if (input.replace(".", "").length() > 10) {
            System.out.println("Try again! Amount cannot exceed 10 digits.");
            continue;
        }
        this.Amount = Double.parseDouble(input);
        break;
    }

    return true; 
}

    
          public void File(){
    try {
            File file = new File("F:\\costumers.txt");
        } catch (Exception ee) {
            System.out.println("File could not be created.");
        }
    }
        public void InsertData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\costumers.txt", true))) {
            writer.write("Customer ID: "+this.Cid);
            writer.newLine();
            writer.write("Customer Name: "+this.Cname);
            writer.newLine();
            writer.write("Address: "+this.Add);
            writer.newLine();
            writer.write("Phone: "+this.phone);
            writer.newLine();
            writer.write(("Amount: "+this.Amount));
            writer.newLine();
            writer.write("-------------------------");
            writer.newLine();
            System.out.println("Your Data Saved Successfull");
        } 
            
        catch (IOException e) {
            System.out.println(" Error while saving data: ");
        }
    }
        

public boolean FetchData(String searchId) { 
    try (BufferedReader br = new BufferedReader(new FileReader("F:\\costumers.txt"))) {
String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().equalsIgnoreCase("Customer ID: " + searchId)) {
                this.nameLine = br.readLine();
                if (this.nameLine != null) {
                    System.out.println(this.nameLine);
                }
                return true;
            }
        }
        return false;
    } catch (Exception ex) {
        System.out.println(" Not Fetch from File of Customer");
        return false;
    }
}
}



class Supplier{
    String Sid;
    String Sname;
    String Add;
    String phone;
    double Amount;
     String nameLine;
    public boolean information(Scanner scan) {
    System.out.println("Type '00' to exit registration.");

    while (true) {
        System.out.print("Enter Supplier ID: ");
        String input = scan.next().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[0-9]+$") || input.isEmpty()){
            System.out.println("Enter Positive Number.");
            continue;
        }
           if (FetchData(input)) {
            System.out.println("Try again! This ID is already taken.");
            continue; 
        }
        this.Sid = input;
        break;
    }

    scan.nextLine();

    while (true) {
        System.out.print("Enter Supplier Name: ");
        String input = scan.nextLine().trim(); 
        if(input.equals("00")) return false;
        if (!input.matches("^[A-Za-z ]+$")){
            System.out.println("Enter Alphabets.");
            continue;
        } 
        if (input.isEmpty()) continue;
        this.Sname = input;
        break;
    }

    while (true) {
        System.out.print("Enter Address: ");
        String input = scan.nextLine().trim(); 
        if(input.equals("00")) return false;
        if (!input.matches("^[A-Za-z0-9 ,.-]+$")) continue;
        if (input.isEmpty()) continue;
        this.Add = input;
        break; 
    }

    while (true) {
        System.out.print("Enter Phone: ");
        String input = scan.next().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[0-9]{11}$")){
            System.out.println("Ente only 13 digits.");
            continue;
        }
        this.phone = input;
        break; 
    }

    while (true) {
        System.out.print("Opening Balance: ");
        String input = scan.next(); 
        if(input.equals("00")) return false;
        if (!input.matches("\\d+(\\.\\d+)?")){
            System.out.println("Enter positive no.");
            continue;
        }
        if (input.replace(".", "").length() > 10){
            System.out.println("Unlimited not allowed");
            continue;
        }
        this.Amount = Double.parseDouble(input);
        break;
    }

    return true;
}
    
          public void File(){
    try {
            File file = new File("F:\\Supplier.txt");
        } catch (Exception ee) {
            System.out.println("File could not be created.");
        }
    }
        public void InsertData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Supplier.txt", true))) {
            writer.write("Supplier ID: "+this.Sid);
            writer.newLine();
            writer.write("Supplier Name: "+this.Sname);
            writer.newLine();
            writer.write("Address: "+this.Add);
            writer.newLine();
            writer.write("Phone: "+this.phone);
            writer.newLine();
            writer.write(("Amount: "+this.Amount));
            writer.newLine();
            writer.write("-------------------------");
            writer.newLine();
            System.out.println("Your Data Saved Successfull");
        } 
            
        catch (IOException e) {
            System.out.println(" Error while saving data: ");
        }
    }
        

public boolean FetchData(String searchId) { 
    try (BufferedReader br = new BufferedReader(new FileReader("F:\\Supplier.txt"))) {
String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().equalsIgnoreCase("Supplier ID: " + searchId)) {
                this.nameLine = br.readLine();
                if (this.nameLine != null) {
                    System.out.println(this.nameLine);
                }
                return true;
            }
        }
        return false;
    } catch (Exception ex) {
        System.out.println(" Not Fetch from File of Supplier");
        return false;
    }
}
}



class Product {
    int productId;
    String productName;
    double price;
    int availableStock;
    double sellingRate;
    

  public boolean inputData(Scanner scan) {
        System.out.println("Type '00' to exit registration.");
    while (true) {
        System.out.print("Enter Product ID: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[1-9][0-9]*$")) continue;
        int id = Integer.parseInt(input);
        if (FetchData(String.valueOf(id))) continue;
        this.productId = id;
        break;
    }

    while (true) {
        System.out.print("Enter Product Name: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[A-Za-z ]+$")) continue;
        this.productName = input;
        break;
    }

    while (true) {
        System.out.print("Enter Product Price: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[0-9]+(\\.[0-9]+)?$")) continue;
        this.price = Double.parseDouble(input);
        break;
    }

    while (true) {
        System.out.print("Enter Available Stock: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[1-9][0-9]*$")) continue;
        this.availableStock = Integer.parseInt(input);
        break;
    }

    while (true) {
        System.out.print("Enter Selling Rate: ");
        String input = scan.nextLine().trim();
        if(input.equals("00")) return false;
        if (!input.matches("^[0-9]+(\\.[0-9]+)?$")) continue;
        this.sellingRate = Double.parseDouble(input);
        break;
    }

    return true;
}

    public void insertData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\products.txt", true))) {
            writer.write("Product ID: " + this.productId);
            writer.newLine();
            writer.write("Product Name: " + this.productName);
            writer.newLine();
            writer.write("Price: " + this.price);
            writer.newLine();
            writer.write("Available Stock: " + this.availableStock);
            writer.newLine();
            writer.write("Selling Rate: " + this.sellingRate);
            writer.newLine();
            writer.write("-------------------------");
            writer.newLine();
        } catch (IOException e) {
            System.out.println(" Not Store ");
        }
    }
    
public boolean FetchData(String searchId) {
    try (BufferedReader br = new BufferedReader(new FileReader("F:\\products.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().equalsIgnoreCase("Product ID: " + searchId)) {

                String name = br.readLine();
                String priceLine = br.readLine();
                String stock = br.readLine();
                String sellingLine = br.readLine();

                System.out.println(name);
                System.out.println(priceLine);
                System.out.println(stock);
                System.out.println(sellingLine);

                this.productId = Integer.parseInt(searchId);
                String[] priceParts = priceLine.split(":");
                if (priceParts.length == 2) {
                    this.price = Double.parseDouble(priceParts[1].trim());
                }
                String[] sellParts = sellingLine.split(":");
                if (sellParts.length == 2) {
                    this.sellingRate = Double.parseDouble(sellParts[1].trim());
                }
              String[] NameParts = name.split(":");
                if (NameParts.length == 2) {
                 this.productName = NameParts[1].trim();
               }
                return true;
            }
        }
    } catch (Exception ex) {
        System.out.println("Cannot fetch data from products.txt");
    }
    return false;
}}






class Invoice extends Product {
    String cusID;
    int invoiceNo;
    int productQuantity;
    String PId;

    public boolean Bill(Scanner scan) {
  System.out.println("Type '00' to exit Invoice.");
    Customers cust = new Customers();
    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = today.format(formatter);

    System.out.print("Enter Customer Id: ");
    boolean found;
    do {
        this.cusID = scan.next();
        found = cust.FetchData(this.cusID);

        if (!found) {
            System.out.print("Customer not found!\nEnter Customer Id again: ");
        }
        if (this.cusID.equals("00")) return false;

    } while (!found);

    System.out.println("Current Date & Time: " + formattedDate);

    while (true) {
        System.out.print("Enter Invoice No: ");
        String invo = scan.next().trim();

        if (!invo.matches("^[0-9]+$")) {
            System.out.println("Invalid! Enter positive numbers only.");
            continue;
        }

        if (DoubleID(invo)) {
            System.out.println("Try again! This ID is already taken.");
            continue;
        }

        this.invoiceNo = Integer.parseInt(invo);
        break;
    }

    int quantity = 0;
    boolean validInput = false;

    while (!validInput) {
        System.out.print("How many products do you want: ");
        String input = scan.next().trim();

        if (input.equals("00")) return false;

        if (input.matches("^[1-9][0-9]*$")) {
            quantity = Integer.parseInt(input);
            validInput = true;
        } else {
            System.out.println("! Please enter a positive number.");
        }
    }

    List<String> productDetails = new ArrayList<>();
    double Finalprice = 0.0;

    for (int i = 1; i <= quantity; i++) {

        scan.nextLine();

        while (true) {
            System.out.print("Enter Product Id " + i + " : ");
            this.PId = scan.nextLine().trim();

            if (this.PId.equals("00")) return false;

            if (!this.PId.matches("^[1-9]+$")) {
                System.out.println("Invalid input! Enter positive numbers.");
                continue;
            }

            if (!FetchData(this.PId)) {
                System.out.println("This Product Not Found. Try again.");
                continue;
            }

            break;
        }

        while (true) {
            System.out.print("Enter Product Quantity: ");

            if (!scan.hasNextInt()) {
                System.out.println("Invalid input! Enter positive numbers.");
                scan.next();
                continue;
            }

            this.productQuantity = scan.nextInt();

            if (this.productQuantity == 00) return false;

            if (this.productQuantity <= 0) {
                System.out.println("Invalid input! Enter positive numbers only.");
                continue;
            }

            break;
        }

        double total = this.productQuantity * (this.price + this.sellingRate);

        updateStock(this.PId, this.productQuantity);
        Finalprice += total;

        String detail = String.format(
                "Product Name: %s\nPrice: %.2f (Qty: %d)\nTotal: %.2f\n-----------------------",
                this.productName, (this.price + this.sellingRate), this.productQuantity, total
        );

        productDetails.add(detail);

        System.out.println("Your Product Amount: " + total);
    }

    System.out.println("--------------------------------");
    System.out.println("Your Total Amount is : " + Finalprice);
    System.out.println("--------------------------------");

    String Ask;

    while (true) {
        System.out.print("Would you like to save bill (Yes / No) : ");
        Ask = scan.next().trim();

        if (Ask.equals("00")) return false;

        if (!Ask.matches("^[A-Za-z]+$")) {
            System.out.println("Invalid input.");
            continue;
        }

        if (!Ask.equalsIgnoreCase("Yes") && !Ask.equalsIgnoreCase("No")) {
            System.out.println("Please type only 'Yes' or 'No'.");
            continue;
        }

        break;
    }

    if (Ask.equalsIgnoreCase("Yes")) {

        System.out.println("\n=========== CUSTOMER BILL ===========");
        System.out.println("Bill No: " + this.invoiceNo);
        System.out.println("Customer Id: " + this.cusID);
        System.out.println(cust.nameLine);
        System.out.println("Date & Time: " + formattedDate);
        System.out.println("Product Details:");

        for (String detail : productDetails) {
            System.out.println(detail);
        }

        System.out.println("TOTAL BILL AMOUNT: " + Finalprice);
        System.out.println("=====================================");

        BillSaved(cust.nameLine, formattedDate, productDetails, Finalprice);

    } else {
        System.out.println("Invoice not saved.");
    }
    return true;
}

    public void updateStock(String productId, int soldQty) {
    String filePath = "F:\\products.txt";
    List<String> lines = new ArrayList<>();
    boolean found = false;
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
            if (line.equalsIgnoreCase("Product ID: " + productId)) {
                found = true;
                String nameLine = br.readLine();
                String priceLine = br.readLine();
                String stockLine = br.readLine();
                String sellLine = br.readLine();
                String separator = br.readLine();
                int currentStock = Integer.parseInt(stockLine.split(":")[1].trim());
                int newStock = currentStock - soldQty;
                if (newStock < 0) {
                    System.out.println(" Not enough stock! Current stock: " + currentStock);
                    return;
                }
                lines.add(nameLine);
                lines.add(priceLine);
                lines.add("Available Stock: " + newStock);
                lines.add(sellLine);
                lines.add(separator);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading product file while updating stock.");
        return;
    }}
    
        private void BillSaved(String customerName, String date, List<String> details, double totalAmount) {
            String filePath = "F:\\CustomerBills.txt"; 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("=========== CUSTOMER BILL ===========");
            writer.newLine();
            writer.write("Bill No: " + this.invoiceNo);
            writer.newLine();
            writer.write("Customer Id: " + this.cusID);
            writer.newLine();
            writer.write(customerName);
            writer.newLine();
            writer.write("Date & Time: " + date);
            writer.newLine();
            for (String d : details) {
                writer.write(d);
                writer.newLine();
            }
            writer.write("------------------------------------");
            writer.newLine();
            writer.write("TOTAL BILL AMOUNT: " + totalAmount);
            writer.newLine();
            writer.write("=====================================");
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving bill to file!");
        }
    }
        public boolean FetchBill(String searchBillNo) {
    String filePath = "F:\\CustomerBills.txt";
    boolean found = false;
    String billContent = ""; 
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("Bill No: " + searchBillNo)) {
                found = true;
                billContent += "=========== CUSTOMER BILL ===========\n";
                billContent += line + "\n";
                while ((line = br.readLine()) != null && !line.contains("===========")) {
                    billContent += line + "\n";
                }
                break;
            }
        }
        if (found) {
            System.out.println("\n--- BILL FOUND ---");
            System.out.println(billContent);
        } else {
            System.out.println("Bill No " + searchBillNo + " not found!");
        }
    } catch (IOException e) {
        System.out.println("Error reading CustomerBills.txt file!");
    }
    return found;
}
        
        private boolean DoubleID(String searchBillNo) {
    String filePath = "F:\\CustomerBills.txt";
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("Bill No: " + searchBillNo)) {
                return true; 
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading file while checking invoice!");
    }
    return false;
}
}



class ReturnInvoice {
    
    public boolean processReturn(Scanner scan) {
          System.out.println("Type '00' to exit ReturnInvoice.");
        int invoiceNo = 0;
        List<String> billLines = new ArrayList<>();
        boolean found = false;
        String customerId = "";
        String customerName = "";
        int invoiceStartLine = -1;

        while (!found) {
            System.out.print("Enter Invoice no. to return: ");
            String input = scan.next().trim();
             if (input.equals("00")) return false;
            if (!input.matches("^[1-9][0-9]*$")) {
                System.out.println("Invalid! Enter positive numbers only.");
                continue;
            }
            invoiceNo = Integer.parseInt(input);
            try {
                List<String> allLines = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader("F:\\CustomerBills.txt"));
                String line;
                while ((line = br.readLine()) != null) allLines.add(line);
                br.close();

                found = false;
                for (int i = 0; i < allLines.size(); i++) {
                    if (allLines.get(i).contains("Bill No: " + invoiceNo)) {
                        invoiceStartLine = i;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Invoice No " + invoiceNo + " not found! Try again.");
                    continue;
                }

                billLines.clear();
                for (int i = invoiceStartLine; i < allLines.size(); i++) {
                    billLines.add(allLines.get(i));
                    if (allLines.get(i).contains("=====================================")) break;
                }

                for (String l : billLines) {
                    if (l.startsWith("Customer Id:")) customerId = l.replace("Customer Id:", "").trim();
                    if (l.startsWith("Customer Name:")) customerName = l.replace("Customer Name:", "").trim();
                }

            } catch (IOException e) {
                System.out.println("Error reading CustomerBills.txt!");
                return true;
            }
        }

        List<String> productNames = new ArrayList<>();
        List<Integer> soldQty = new ArrayList<>();
        List<Double> productPrice = new ArrayList<>();
        List<Integer> lineIndexes = new ArrayList<>();

        for (int i = 0; i < billLines.size(); i++) {
            String line = billLines.get(i);
            if (line.startsWith("Product Name:")) {
                String productName = line.replace("Product Name:", "").trim();

                if (i + 1 >= billLines.size()) continue;
                String priceLine = billLines.get(i + 1).trim();
                if (!priceLine.startsWith("Price:")) continue;

                int qtyStart = priceLine.indexOf("(Qty:");
                int qtyEnd = priceLine.indexOf(")", qtyStart);
                if (qtyStart == -1 || qtyEnd == -1) continue;

                double price = Double.parseDouble(priceLine.substring(6, qtyStart).trim());
                int qty = Integer.parseInt(priceLine.substring(qtyStart + 5, qtyEnd).trim());

                productNames.add(productName);
                productPrice.add(price);
                soldQty.add(qty);
                lineIndexes.add(i + 1);
            }
        }

        System.out.println("\n--- Invoice Details ---");
        System.out.println("Bill No: " + invoiceNo);
        System.out.println("Customer Id: " + customerId);
        System.out.println("Customer Name: " + customerName);
        for (int i = 0; i < productNames.size(); i++) {
            System.out.println("Product Name: " + productNames.get(i));
            System.out.println("Price: " + productPrice.get(i) + " (Qty: " + soldQty.get(i) + ")");
            System.out.println("-------------------");
        }

        List<Integer> returnQty = new ArrayList<>();
        for (int i = 0; i < productNames.size(); i++) {
            int qtySold = soldQty.get(i);
            int qtyReturn = 0;
            while (true) {
                System.out.print("Enter quantity to return for '" + productNames.get(i) + "' (Max " + qtySold + "): ");
                if (!scan.hasNextInt()) {                 
                    System.out.println("Invalid input!");
                    scan.next();
                    continue;
                }
                qtyReturn = scan.nextInt();
                if (qtyReturn < 0) System.out.println("Cannot return negative quantity!");
                else if (qtyReturn > qtySold) System.out.println("Cannot return more than sold quantity (" + qtySold + ")!");
                else break;
            }
            returnQty.add(qtyReturn);
        }

        Map<String, String> stockSummary = updateStockWithSummary(productNames, returnQty);

        double totalBill = 0;
        List<String> updatedInvoiceLines = new ArrayList<>();
        updatedInvoiceLines.add("Bill No: " + invoiceNo);
        updatedInvoiceLines.add("Customer Id: " + customerId);
        updatedInvoiceLines.add("Customer Name: " + customerName);

        for (int i = 0; i < productNames.size(); i++) {
            int remainingQty = soldQty.get(i) - returnQty.get(i);
            if (remainingQty <= 0) continue;
            double total = remainingQty * productPrice.get(i);
            totalBill += total;

            updatedInvoiceLines.add("Product Name: " + productNames.get(i));
            updatedInvoiceLines.add("Price: " + productPrice.get(i) + " (Qty: " + remainingQty + ")");
            updatedInvoiceLines.add("-------------------");
        }

        updatedInvoiceLines.add("------------------------------------");
        updatedInvoiceLines.add("TOTAL BILL AMOUNT: " + totalBill);
        updatedInvoiceLines.add("=====================================");

        
        try {
            List<String> allLines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("F:\\CustomerBills.txt"));
            String line;
            while ((line = br.readLine()) != null) allLines.add(line);
            br.close();

            allLines.subList(invoiceStartLine, invoiceStartLine + billLines.size()).clear();
            allLines.addAll(invoiceStartLine, updatedInvoiceLines);

            BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\CustomerBills.txt"));
            for (String l : allLines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error updating CustomerBills.txt!");
        }

        double refundTotal = 0;
        System.out.println("\n=========== RETURN SUMMARY ===========");
        System.out.println("Bill No: " + invoiceNo);
        System.out.println("Customer Id: " + customerId);
        System.out.println("Customer Name: " + customerName);

        for (int i = 0; i < productNames.size(); i++) {
            if (returnQty.get(i) > 0) {
                double refund = returnQty.get(i) * productPrice.get(i);
                refundTotal += refund;
                String stockInfo = stockSummary.get(productNames.get(i));
                String[] stocks = stockInfo.split(",");
                System.out.println("Product: " + productNames.get(i) +
                        " | Returned Qty: " + returnQty.get(i) +
                        " | Refund: " + refund +
                        " | Old Stock: " + stocks[0] +
                        " | New Stock: " + stocks[1]);
            }
        }

        System.out.println("TOTAL REFUND AMOUNT: " + refundTotal);
        System.out.println("UPDATED TOTAL BILL AMOUNT: " + totalBill);
        System.out.println("=====================================");
        return true;
    }

    
    private Map<String, String> updateStockWithSummary(List<String> productNames, List<Integer> returnQty) {
        String filePath = "F:\\products.txt";
        List<String> lines = new ArrayList<>();
        Map<String, String> stockSummary = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            System.out.println("Error reading products.txt!");
            return stockSummary;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                bw.write(line);
                bw.newLine();

                if (line.startsWith("Product Name:")) {
                    String prodName = line.replace("Product Name:", "").trim();
                    int idx = productNames.indexOf(prodName);

                    if (idx != -1 && returnQty.get(idx) > 0) {
                        for (int j = i + 1; j < lines.size(); j++) {
                            if (lines.get(j).startsWith("Available Stock:")) {
                                int currentStock = Integer.parseInt(lines.get(j).split(":")[1].trim());
                                int newStock = currentStock + returnQty.get(idx);
                                stockSummary.put(prodName, currentStock + "," + newStock);
                                i = j;
                                bw.write("Available Stock: " + newStock);
                                bw.newLine();
                                break;
                            } else {
                                bw.write(lines.get(j));
                                bw.newLine();
                            }}}}}
        } catch (IOException e) {System.out.println("Error writing products.txt!");}
        return stockSummary;
    }}

class Pruchase extends Product {
    String SID;
    int invoiceNo;
    int productQuantity;
    String PId;

    public boolean Bill(Scanner scan) {
          System.out.println("Type '00' to exit Purches.");
        Supplier Supp = new Supplier();
        LocalDateTime today = LocalDateTime.now(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = today.format(formatter);

        System.out.print("Enter Supplier Id: ");
        boolean found;
        do {
            this.SID = scan.next().trim();
             if (this.SID.equals("00")) return false;
            found = Supp.FetchData(this.SID); 
            if (!found) {
                System.out.print("Supplier not found!\nEnter Supplier Id again: ");
            }
        } while (!found);     
        System.out.println("Current Date & Time: " + formattedDate);
        while (true) {
            System.out.print("Enter Invoice No: ");
            String invo = scan.next().trim();
             if (invo.equals("00")) return false;
            if (!invo.matches("^[0-9]+$")) {
                System.out.println("Invalid! Enter positive numbers only.");
                continue;
            }
                if (DoubleID(invo)) {
        System.out.println("Try again! This ID is already taken.");
        continue; 
    }
            this.invoiceNo = Integer.parseInt(invo); 
            break; 
        }   

        int quantity = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("How many products do you want: ");
             if (quantity== 00) return false;
            try {
                quantity = Integer.parseInt(scan.next()); 
                if (quantity <= 0) {
                    System.out.println("Quantity must be positive. Try again.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
            }
        }
        List<String> productDetails = new ArrayList<>();
        double Finalprice = 0.0; 
        for (int i = 1; i <= quantity; i++) {
                scan.nextLine();
            while (true) {
                System.out.print("Enter Product Id " + i + " : ");
                this.PId = scan.nextLine().trim();
                 if (this.PId.equals("00")) return false;
                if (!this.PId.matches("^[1-9]+$")) {
                    System.out.println("Invalid input! Enter positive numbers.");
                    continue;
                }
                if (!FetchData(this.PId)) {
                    System.out.println("This Product Not Found. Try again.");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("Enter Product Quantity: ");
                if (!scan.hasNextInt()) {
                    System.out.println("Invalid input! Enter positive numbers.");
                    scan.next();
                    continue;
                }
                this.productQuantity = scan.nextInt();
                 if (this.productQuantity == 00) return false;
                if (this.productQuantity <= 0) {
                    System.out.println("Invalid input! Enter positive numbers only.");
                    continue;
                }
                break;
            }

            double total = this.productQuantity * (this.price + this.sellingRate);
                updateStock(this.PId, this.productQuantity);
            Finalprice += total;
             String detail = String.format(
                "Product Name: %s\nPrice: %.2f (Qty: %d)\nTotal: %.2f\n-----------------------",
                this.productName, (this.price + this.sellingRate), this.productQuantity, total
            );
            productDetails.add(detail);
            System.out.println("Your Product Amount: " + total );
        }

        System.out.println("--------------------------------");
        System.out.println("Your Total Amount is : " + Finalprice);
        System.out.println("--------------------------------");
        
        String Ask;
        while (true) {
            System.out.print("Would you like to save bill (Yes / No) : ");
            Ask = scan.next().trim();
             if (Ask.equals("00")) return false;
            if (!Ask.matches("^[A-Za-z]+$")) {
                System.out.println(" Invalid input.");
                continue;
            }
            if (!Ask.equalsIgnoreCase("Yes") && !Ask.equalsIgnoreCase("No")) {
                System.out.println("Please type only 'Yes' or 'No'.");
                continue;
            }
            break; 
        }
         if (Ask.equalsIgnoreCase("Yes")) {
            System.out.println("\n=========== Supplier BILL ===========");
            System.out.println("Bill No: " + this.invoiceNo);
            System.out.println("Supplier Id: " + this.SID);
            System.out.println(Supp.nameLine);
            System.out.println("Date & Time: " + formattedDate);
            System.out.println("Product Details:");
            for (String detail : productDetails) {System.out.println(detail);}
            System.out.println("TOTAL BILL AMOUNT: " + Finalprice);
            System.out.println("=====================================");
            BillSaved(Supp.nameLine, formattedDate, productDetails, Finalprice);
        } else {
            System.out.println("Invoice not saved.");
        }
         return true;
    }
    public void updateStock(String productId, int soldQty) {
    String filePath = "F:\\products.txt";
    List<String> lines = new ArrayList<>();
    boolean found = false;
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
            if (line.equalsIgnoreCase("Product ID: " + productId)) {
                found = true;
                String nameLine = br.readLine();
                String priceLine = br.readLine();
                String stockLine = br.readLine();
                String sellLine = br.readLine();
                String separator = br.readLine();
                int currentStock = Integer.parseInt(stockLine.split(":")[1].trim());
                int newStock = currentStock + soldQty;
                if (newStock < 0) {
                    System.out.println(" Not enough stock! Current stock: " + currentStock);
                    return;
                }
                lines.add(nameLine);
                lines.add(priceLine);
                lines.add("Available Stock: " + newStock);
                lines.add(sellLine);
                lines.add(separator);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading product file while updating stock.");
        return;
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        for (String l : lines) {
            bw.write(l);
            bw.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error writing updated stock to file.");
    }
}

    
        private void BillSaved(String customerName, String date, List<String> details, double totalAmount) {
            String filePath = "F:\\SupplierBills.txt"; 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("=========== Supplier BILL ===========");
            writer.newLine();
            writer.write("Bill No: " + this.invoiceNo);
            writer.newLine();
            writer.write("Supplier Id: " + this.SID);
            writer.newLine();
            writer.write(customerName);
            writer.newLine();
            writer.write("Date & Time: " + date);
            writer.newLine();
            for (String d : details) {
                writer.write(d);
                writer.newLine();
            }
            writer.write("------------------------------------");
            writer.newLine();
            writer.write("TOTAL BILL AMOUNT: " + totalAmount);
            writer.newLine();
            writer.write("=====================================");
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving bill to file!");
        }
    }
        public boolean FetchBill(String searchBillNo) {
    String filePath = "F:\\SupplierBills.txt";
    boolean found = false;
    String billContent = ""; 
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("Bill No: " + searchBillNo)) {
                found = true;
                billContent += "=========== Supplier BILL ===========\n";
                billContent += line + "\n";
                while ((line = br.readLine()) != null && !line.contains("===========")) {
                    billContent += line + "\n";
                }
                break;
            }
        }
        if (found) {
            System.out.println("\n--- BILL FOUND ---");
            System.out.println(billContent);
        } else {
            System.out.println("Bill No " + searchBillNo + " not found!");
        }
    } catch (IOException e) {
        System.out.println("Error reading CustomerBills.txt file!");
    }
    return found;
}
        
        private boolean DoubleID(String searchBillNo) {
    String filePath = "F:\\SupplierBills.txt";
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("Bill No: " + searchBillNo)) {
                return true; 
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading file while checking invoice!");
    }
    return false;
}
}
class Payable {
    String supplierName = "";
    String totalAmount = "";
    public boolean fetchSupplierById(String searchSupplierId) {
        String filePath = "F:\\SupplierBills.txt";
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Supplier Id:")) {
                    String id = line.split(":")[1].trim();
                    if (id.equals(searchSupplierId)) {
                        found = true;
                        while ((line = br.readLine()) != null && !line.contains("===========")) {
                            if (line.contains("Supplier Name:"))
                                supplierName = line.split(":")[1].trim();
                            else if (line.contains("TOTAL BILL AMOUNT:"))
                                totalAmount = line.split(":")[1].trim();
                        }

                        System.out.println("\n--- SUPPLIER BILL SUMMARY ---");
                        System.out.println("Supplier ID: " + searchSupplierId);
                        System.out.println("Supplier Name: " + supplierName);
                        System.out.println("Total Amount: " + totalAmount);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading SupplierBills.txt: " + e.getMessage());
        }
        return found;
    }
public void updateSupplierRemainingAmount(String supplierId, double newAmount, String date) {
    String filePath = "F:\\SupplierBills.txt";
    StringBuilder updatedFile = new StringBuilder();
    boolean insideTarget = false;
    boolean matched = false;
    boolean totalUpdated = false;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("Supplier Id:")) {
                String id = line.split(":")[1].trim();
                insideTarget = id.equals(supplierId);
                if (insideTarget) matched = true;
            }
            if (insideTarget) {
                if (line.startsWith("Last Payment Date:")) {
                    continue; 
                }
                if (line.startsWith("TOTAL BILL AMOUNT:")) {
                    line = "TOTAL BILL AMOUNT: " + newAmount;
                    updatedFile.append(line).append("\n");
                    updatedFile.append("Last Payment Date: ").append(date).append("\n");
                    totalUpdated = true;
                    continue;
                }
                if (line.startsWith("===========")) {
                    insideTarget = false;
                }
            }
            updatedFile.append(line).append("\n");
        }
    } catch (IOException e) {
        System.out.println(" Error reading file: " + e.getMessage());
        return;
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        bw.write(updatedFile.toString());
    } catch (IOException e) {
        System.out.println("Error writing file: " + e.getMessage());
        return;
    }
}
    public void savePaymentRecord(String supplierId, String supplierName,
                                  double totalAmount, double paymentAmount, double remainingAmount, String date) {
        String filePath = "F:\\SupplierPayments.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write("Supplier ID: " + supplierId);
            bw.newLine();
            bw.write("Supplier Name: " + supplierName);
            bw.newLine();
            bw.write("Total Amount: " + totalAmount);
            bw.newLine();
            bw.write("Payment: " + paymentAmount);
            bw.newLine();
            bw.write("Remaining: " + remainingAmount);
            bw.newLine();
            bw.write("Status: " + (remainingAmount == 0.0 ? "PAID" : "PENDING"));
            bw.newLine();
            bw.write("Payment Date: " + date);
            bw.newLine();
            bw.write("===============================================");
            bw.newLine();
            bw.newLine();
            System.out.println("Payment record saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing payment record: " + e.getMessage());
        }
    }}

class Recive {
    String CustomerName = "";
    String totalAmount = "";
    public boolean fetchCustomerById(String searchCustomerId) {
        String filePath = "F:\\CustomerBills.txt";
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Customer Id:")) {
                    String id = line.split(":")[1].trim();
                    if (id.equals(searchCustomerId)) {
                        found = true;
                        while ((line = br.readLine()) != null && !line.contains("===========")) {
                            if (line.contains("Customer Name:"))
                                this.CustomerName = line.split(":")[1].trim();
                            else if (line.contains("TOTAL BILL AMOUNT:"))
                               this. totalAmount = line.split(":")[1].trim();
                        }

                        System.out.println("\n--- SUPPLIER BILL SUMMARY ---");
                        System.out.println("Customer ID: " + searchCustomerId);
                        System.out.println("Customer Name: " + CustomerName);
                        System.out.println("Total Amount: " + totalAmount);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CustomerBills.txt: " + e.getMessage());
        }
        return found;
    }
    public void updateCustomerRemainingAmount(String CustomerId, double newAmount, String date) {
    String filePath = "F:\\CustomerBills.txt";
    StringBuilder updatedFile = new StringBuilder();
    boolean insideTarget = false;
    boolean matched = false;
    boolean totalUpdated = false;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("Customer Id:")) {
                String id = line.split(":")[1].trim();
                insideTarget = id.equals(CustomerId);
                if (insideTarget) matched = true;
            }
            if (insideTarget) {
                if (line.startsWith("Last Payment Date:")) {
                    continue; 
                }
                if (line.startsWith("TOTAL BILL AMOUNT:")) {
                    line = "TOTAL BILL AMOUNT: " + newAmount;
                    updatedFile.append(line).append("\n");
                    updatedFile.append("Last Payment Date: ").append(date).append("\n");
                    totalUpdated = true;
                    continue;
                }
                if (line.startsWith("===========")) {
                    insideTarget = false;
                }
            }
            updatedFile.append(line).append("\n");
        }
    } catch (IOException e) {
        System.out.println(" Error reading file: " + e.getMessage());
        return;
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        bw.write(updatedFile.toString());
    } catch (IOException e) {
        System.out.println("Error writing file: " + e.getMessage());
        return;
    }}
    
    public void savePaymentRecord(String CustomerId, String CusotmersName,
                                  double totalAmount, double paymentAmount, double remainingAmount, String date) {
        String filePath = "F:\\CustomerPayments.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write("Customer ID: " + CustomerId);
            bw.newLine();
            bw.write("Customer Name: " + CustomerName);
            bw.newLine();
            bw.write("Total Amount: " + totalAmount);
            bw.newLine();
            bw.write("Payment: " + paymentAmount);
            bw.newLine();
            bw.write("Remaining: " + remainingAmount);
            bw.newLine();
            bw.write("Status: " + (remainingAmount == 0.0 ? "PAID" : "PENDING"));
            bw.newLine();
            bw.write("Payment Date: " + date);
            bw.newLine();
            bw.write("===============================================");
            bw.newLine();
            bw.newLine();
            System.out.println("Payment record saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing payment record: " + e.getMessage());
        }
    }}


class Ledger {
    public void CustomerLedger(Scanner scan) {
        String filePath = "F:\\CustomerPayments.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Ledger file not found at: " + filePath);
            return;
        }

        while (true) {
            System.out.print("Enter Customer ID: ");
            String customerId = scan.nextLine().trim();

            if (!customerId.matches("\\d+")) {
                System.out.println("Please enter positive numbers!\n");
                continue;
            }

            boolean found = false;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean capture = false;

                while ((line = br.readLine()) != null) {
                    if (line.contains("Customer ID: " + customerId)) {
                        found = true;
                        capture = true;
                        System.out.println("\n=========== CUSTOMER Ledger ===========");
                    }
                    if (capture) {
                        System.out.println(line);
                        if (line.startsWith("===") || line.startsWith("---")) {
                            capture = false;
                        }} } }
            catch (IOException e) {
                System.out.println("Error reading ledger file: " + e.getMessage());
            }

            if (!found) {
                System.out.println("No bills found for Customer ID: " + customerId + "\n");
            } else {
                break;
            }}}}



class Ledgers {
    public void SupplierLedger(Scanner scan) {
        String filePath = "F:\\SupplierPayments.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Ledger file not found at: " + filePath);
            return;
        }

        while (true) {
            System.out.print("Enter Supplier ID: ");
            String customerId = scan.nextLine().trim();

            if (!customerId.matches("\\d+")) {
                System.out.println("Please enter positive numbers!\n");
                continue;
            }

            boolean found = false;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean capture = false;

                while ((line = br.readLine()) != null) {
                    if (line.contains("Supplier ID: " + customerId)) {
                        found = true;
                        capture = true;
                        System.out.println("\n=========== Supplier Ledger ===========");
                    }

                    if (capture) {
                        System.out.println(line);
                        if (line.startsWith("===") || line.startsWith("---")) {
                            capture = false;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading ledger file: " + e.getMessage());
            }

            if (!found) {
                System.out.println("No bills found for Supplier ID: " + customerId + "\n");
            } else {
                break;
            }}}}
