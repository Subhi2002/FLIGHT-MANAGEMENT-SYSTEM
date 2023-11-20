/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package airticketmanagingsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Date;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author subhi
 */

class Admin{
    
    static Scanner sc=new Scanner(System.in);

    void addflight() throws SQLException{
        Date depDate = null;
            System.out.println();
         System.out.println("Enter flightname");
     
        String flightname = sc.next();
                 System.out.println("ENter source ");

        String src =sc.next();
                 System.out.println("Enter destination");

        String dest =  sc.next();
                 System.out.println("Enter departure date");

        String dateInputdept=  sc.next();
      /*  SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
            try{
             depDate = dateformat.parse(dateInputdept);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }*/
         System.out.println("Enter dept time");
        String deptime = sc.next();
         System.out.println("Enter arr time");
        String arrtime=  sc.next();
         System.out.println("Enter duration");
        int  duration = sc.nextInt();
         System.out.println("Enter totalseats");
        int totalseats = sc.nextInt();
         System.out.println("Enter Acailable seats");
        int availableseats= sc.nextInt() ;
          System.out.println("Enter price");
       int price = sc.nextInt();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
             PreparedStatement st = con.prepareStatement("insert into flighs ( flightname,src,dest,depdate,deptime,arrtime, duration, totalseats, availableseats,price) values(?,?,?,?,?,?,?,?,?,?);");
             st.setString(1,flightname);
             st.setString(2, src);
             st.setString(3, dest);
             st.setString(4,dateInputdept);
             st.setString(5, deptime);
             st.setString(6, arrtime);
             st.setInt(7, duration);
             st.setInt(8, totalseats);
             st.setInt(9, availableseats);
             st.setInt(10, price);
             int a =  st.executeUpdate();
             System.out.println("No of rows affected" + a);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
       }
    void showbookings() throws SQLException, ClassNotFoundException{
        
                 System.out.println();
         System.out.println("enter flightname");

           String flightNameFilter = sc.nextLine();
                    System.out.println("enter date");

            String dateString = sc.nextLine();
            viewBookings(flightNameFilter, dateString);
    }
     private static void viewBookings( String flightNameFilter, String dateFilter) throws SQLException, ClassNotFoundException {
          Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
        String query = "SELECT b.bookid, b.cust_id, b.flight_id, b.seats, b.total_price, f.flightname, f.depdate, f.deptime, f.arrtime "
                + "FROM bookings b "
                + "JOIN flighs f ON b.flight_id = f.id "
                + "WHERE f.flightname = ?AND f.depdate = ?";

        PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, flightNameFilter);
            pst.setString(2, dateFilter);

            ResultSet resultSet = pst.executeQuery();

            System.out.println("BookID | CustID | FlightID | Seats | TotalPrice | FlightName | DepDate | DepTime | ArrTime");
            System.out.println("---------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-6s | %-6s | %-8s | %-5s | %-10s | %-10s | %-11s | %-7s | %-7s%n",
                        resultSet.getString("bookid"),
                        resultSet.getString("cust_id"),
                        resultSet.getString("flight_id"),
                        resultSet.getString("seats"),
                        resultSet.getString("total_price"),
                        resultSet.getString("flightname"),
                        resultSet.getString("depdate"),
                        resultSet.getString("deptime"),
                        resultSet.getString("arrtime"));
            }
        }
}

class User{
   static Scanner sc = new Scanner(System.in);
   void searchflights() throws ClassNotFoundException, SQLException
   {
        Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
                      System.out.println();
         System.out.println("Enter source");
         String src = sc.next();
         System.out.println("enter destination");
         String dest = sc.next();
         System.out.println("enter date");
         String date = sc.next();
        // Create a connection to the database
        try  {
            // Construct the SQL query
            String sql = "SELECT * FROM flighs WHERE  src = ? AND dest = ?  AND depdate = ?";
            // Create a prepared statement
            PreparedStatement pst = con.prepareStatement(sql);
                                pst.setString(1, src);
                                pst.setString(2, dest);
                                pst.setString(3, date);




                // Execute the query
                ResultSet resultSet = pst.executeQuery(); 
                 System.out.println("ID |     NAME    |    SRC    |   DEST    | DEPT DATE | DEPT TIME | ARR TIME | DURATION | TOTALSEATS |   AVASEATS   |     PRICE      ");

                    while (resultSet.next()) {
                        // Retrieve flight information from the result set
                        int id = resultSet.getInt("id");
                        String flightName= resultSet.getString("flightname");
                        src = resultSet.getString("src");
                        dest = resultSet.getString("dest");
                        String depDate= resultSet.getString("depdate");
                        String depTime= resultSet.getString("deptime");
                        String arrTime = resultSet.getString("arrtime");
                        String duration = resultSet.getString("duration");
                        int totalSeats = resultSet.getInt("totalseats");
                        int availableSeats = resultSet.getInt("availableseats");
                        int price = resultSet.getInt("price");

                        // Display the flight information
                       System.out.printf(" %-2d|  %-10s | %-8s | %-8s | %-11s | %-8s | %-8s | %-8s | %-10d | %-14d | %-6d |%n" ,id,flightName ,src,dest,depDate,depTime,arrTime,duration,totalSeats,availableSeats,price);

                    
                }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
   }
   
   
   
   void bookflight(int a) throws ClassNotFoundException, SQLException
   {
      Class.forName("com.mysql.cj.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
       System.out.print("Enter the flight number: ");
            int flno = sc.nextInt();

            System.out.print("Enter the number of seats to book: ");
            int seatToBook = sc.nextInt();

            // Get flight information
            String flightQuery = "SELECT * FROM flighs WHERE id= ?";
            PreparedStatement st = con.prepareStatement(flightQuery);
                st.setInt(1, flno);

                ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        int flightId = rs.getInt("id");
                        int price = rs.getInt("price");
                        int availableSeats = rs.getInt("availableseats");

                        // Check if there are enough available seats
                        if (seatToBook <= availableSeats) {
                            // Book the flight
                            bookFlight(con, flightId, seatToBook, price,a);

                            // Display the total price to the user
                            int totalPrice = seatToBook * price;
                            System.out.println("Booking successful!");
                            System.out.println("Total Price: $" + totalPrice);

                        } else {
                            System.out.println("Not enough seats available. eelect smaller number of seats. The number of seats available "+availableSeats);
                        }
                    } else {
                        System.out.println("Not found :flight number.");
                    }
                
            

       
    }

    private static void bookFlight(Connection connection, int flightId, int seats, int price,int a) throws SQLException {
        // Insert booking information into bookings table
        String bookingQuery = "INSERT INTO bookings (cust_id, flight_id, seats, total_price) VALUES (?, ?, ?, ?)";
        PreparedStatement st = connection.prepareStatement(bookingQuery);
            // Assuming cust_id is a placeholder for the customer ID; replace with the actual customer ID
            price = price *seats;
            st.setInt(1, a); // Replace with the actual customer ID
            st.setInt(2, flightId);
            st.setInt(3, seats);
            st.setInt(4, price);
            st.executeUpdate();
            // Update available seats in flights table
            String Query = "UPDATE flighs SET availableseats = availableseats - ? WHERE id = ?";
            st = connection.prepareStatement(Query);
            st.setInt(1, seats);
            st.setInt(2, flightId);
            st.executeUpdate();
            
        
    }
    
    static void viewchanges(int k) throws ClassNotFoundException
    {
         try  {
             Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
                            String sql = "SELECT * FROM flighs WHERE  id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);
                            pst.setInt(1, k);
                            ResultSet resultSet = pst.executeQuery(); 
                            System.out.println("ID |     NAME    |    SRC    |   DEST    | DEPT DATE | DEPT TIME | ARR TIME | DURATION | TOTALSEATS |   AVASEATS   |     PRICE      ");

                    while (resultSet.next()) {
                        // Retrieve flight information from the result set
                        int id = resultSet.getInt("id");
                        String flightName= resultSet.getString("flightname");
                        String  src = resultSet.getString("src");
                        String    dest = resultSet.getString("dest");
                        String depDate= resultSet.getString("depdate");
                        String depTime= resultSet.getString("deptime");
                        String arrTime = resultSet.getString("arrtime");
                        String duration = resultSet.getString("duration");
                        int totalSeats = resultSet.getInt("totalseats");
                        int availableSeats = resultSet.getInt("availableseats");
                        int  price = resultSet.getInt("price");

                        // Display the flight information
                       System.out.printf(" %-2d|  %-10s | %-8s | %-8s | %-11s | %-8s | %-8s | %-8s | %-10d | %-14d | %-6d |%n" ,id,flightName ,src,dest,depDate,depTime,arrTime,duration,totalSeats,availableSeats,price);

                    
                }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      void cancelticket(int k) throws ClassNotFoundException, SQLException{
         
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
                System.out.println("Enter flight id which you want to cancel");
                int fid =sc.nextInt();
                String flightQuery = "SELECT * FROM bookings WHERE flight_id= ? and cust_id = ?";
                PreparedStatement st = con.prepareStatement(flightQuery);
                st.setInt(1, fid);
                st.setInt(2, k);
                ResultSet rs = st.executeQuery();
                rs.next();
                int seats= rs.getInt("seats");
                int price = rs.getInt("total_price");
                flightQuery = "DELETE FROM BOOKINGs WHERE CUST_ID = ? AND FLIGHT_ID = ?";
                st = con.prepareStatement(flightQuery);
                st.setInt(2, fid);
                st.setInt(1, k);
               int r = st.executeUpdate();
               if(k>0){
                   System.out.println("SUCESSFULLY CANCELLED AND THE REFUND AMOUNT IS "+price);
                   viewchanges(fid);
                     String Query = "UPDATE flighs SET availableseats = availableseats + ? WHERE id = ?";
               st = con.prepareStatement(Query);
               st.setInt(1, seats);
               st.setInt(2, fid);
               st.executeUpdate();
                System.out.println("AFTER CANCELLING");
                viewchanges(fid);
              
               }
               else System.out.println("Try Again");
               
               
             
               
           
    }

   
}
public class AirTicketManagingSystem {
 static Scanner sc=new Scanner(System.in);
    
       static String  adminlogin= "admin" ;
       static String  adminpwd="1234";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int option;
        do{
            System.out.println("");
            System.out.println("-----Welcome to Flight Booking Management System-----");
            System.out.println("1.Login as admin");
            System.out.println("2.Login as user");
            System.out.println("3.Exit");
            option=sc.nextInt();
            handleresponse(option);
           }
        while(option!=3);
        }
    
    
    static void handleresponse(int option) throws SQLException, ClassNotFoundException{
    switch(option){
       case 1: adminLogin();
       break;
       case 2: userLogin();
       break;
   }
}

    private static void adminLogin() throws SQLException, ClassNotFoundException {
         System.out.println();
         System.out.println();

        System.out.println("Enter your username and password");
        System.out.println("Username");
        String username = sc.next();
        System.out.println("Password");
        String password = sc.next();
        if (username == adminlogin && password == adminpwd)
            System.out.println("YOU ARE LOGGED IN AS AN ADMIN");
            System.out.println("");
            System.out.println("");
            int a;
            do{
            System.out.println("1.To add flights");
            System.out.println("2.To check for booked tickets");
            System.out.println("3.Back");
            
            System.out.println();

            a= sc.nextInt();
            Admin admin = new Admin();
            switch(a){
             case 1: admin.addflight();break;
             case 2:  admin.showbookings();
            } 
            }while(a!=3);
            
    }
    

    private static void userLogin() throws SQLException, ClassNotFoundException {
         Class.forName("com.mysql.cj.jdbc.Driver");
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
 
        int a,y=0;
        String name;
        String pwd;
        String gender;
        String email;
        do{
          System.out.println();
          System.out.println();

         System.out.println("1.Log in ");
         System.out.println("2.Sign up");
         System.out.println("3.EXIT");
         System.out.println();

         a= sc.nextInt();
         switch(a){
             case 1:
                  System.out.println("Name:");

                 name = sc.next();
                          System.out.println("Password:");

                 pwd  = sc.next();
                 String sql = "SELECT * FROM signup WHERE name = ? AND pwd = ?";
                 PreparedStatement stmt = con.prepareStatement(sql);
                 stmt.setString(1, name);
                 stmt.setString(2, pwd);
                 ResultSet rs = stmt.executeQuery();

                 if (rs.next()) {
                              System.out.println();

                     System.out.println("Login successful!");
                     int n=0;
                      do{
                      System.out.println();
                      System.out.println();
                     System.out.println("1.Search for flights");
                     System.out.println("2.Book tickets");
                     System.out.println("3.Cancel a Ticket");
                     System.out.println("4.Back");
                     System.out.println();

                     n= sc.nextInt(); 
                     User user = new User();
                     switch(n){
                         case 1:user.searchflights();
                         break;
                         case 2:
                              String flightQuery = "SELECT * FROM signup WHERE name = ?";
                              PreparedStatement st = con.prepareStatement(flightQuery);
                              st.setString(1, name);
                              ResultSet ResultSet = st.executeQuery();
                              ResultSet.next();
                              int k = ResultSet.getInt("id");
                              System.out.println();

                              System.out.println(k +" is the id of the user");
                              user.bookflight(k);
                              break;
                         case 3:
                             flightQuery = "SELECT * FROM signup WHERE name = ?";
                              st = con.prepareStatement(flightQuery);
                              st.setString(1, name);
                              ResultSet = st.executeQuery();
                              ResultSet.next();
                               k = ResultSet.getInt("id");
                              System.out.println();
                             user.cancelticket(k);
                             
                     }
                      }while (n!=4);
                     y=1;
                     break;
                 } else {
                     System.out.println("Invalid username or password. Please try again.");
                 }
                 break;
             case 2:
                          System.out.println();
                          System.out.println("Enter name");
                          name = sc.next();
                          System.out.println("Enter password");
                          pwd  = sc.next();
                          System.out.println("Enter gender");
                          gender = sc.next();
                          System.out.println("Enter email");
                          email = sc.next();
                          PreparedStatement st = con.prepareStatement("insert into signup (name, pwd,gender, emailid) values(?,?,?,?);");
                 
                 st.setString(1,name);
                 st.setString(2, pwd);
                 st.setString(3, gender);
                 st.setString(4,email);
                 int b =  st.executeUpdate();
                          System.out.println();

                 System.out.println("No of rows affected" + b);
                 break;
                 
            
         }
          if (y==1)break;
        }while(a!=3);
        
    }
    
}
