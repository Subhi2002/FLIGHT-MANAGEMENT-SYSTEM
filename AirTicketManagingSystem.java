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
        
        String flightname = sc.next();
        String src =sc.next();
        String dest =  sc.next();
        String dateInputdept=  sc.next();
      /*  SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
            try{
             depDate = dateformat.parse(dateInputdept);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }*/

        
        String deptime = sc.next();
        String arrtime=  sc.next();
        int  duration = sc.nextInt();
        int totalseats = sc.nextInt();
        int availableseats= sc.nextInt() ;
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
           String flightNameFilter = sc.nextLine();
            String dateString = sc.nextLine();
            viewBookings(flightNameFilter, dateString);
    }
     private static void viewBookings( String flightNameFilter, String dateFilter) throws SQLException, ClassNotFoundException {
          Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
        String query = "SELECT b.bookid, b.cust_id, b.flight_id, b.seats, b.total_price, f.flightname, f.depdate, f.deptime, f.arrtime "
                + "FROM bookings b "
                + "JOIN flighs f ON b.flight_id = f.id "
                + "WHERE (? IS NULL OR f.flightname = ?) AND (? IS NULL OR f.depdate = ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, flightNameFilter);
            preparedStatement.setString(2, flightNameFilter);
            preparedStatement.setString(3, dateFilter);
            preparedStatement.setString(4, dateFilter);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("BookID | CustID | FlightID | Seats | TotalPrice | FlightName | DepDate | DepTime | ArrTime");
            System.out.println("---------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-6s | %-6s | %-8s | %-5s | %-10s | %-10s | %-7s | %-7s | %-7s%n",
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
        }}
}

class User{
   static Scanner sc = new Scanner(System.in);
   void searchflights() throws ClassNotFoundException, SQLException
   {
        Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightmanagement","subhi","subhi");
        String src = sc.next();
        String dest = sc.next();
          String date = sc.next();
        // Create a connection to the database
        try  {
            // Construct the SQL query
            String sql = "SELECT * FROM flighs WHERE  src = ? AND dest = ?  AND depdate = ?";
            
            // Create a prepared statement
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                                preparedStatement.setString(1, src);
                                preparedStatement.setString(2, dest);
                                preparedStatement.setString(3, date);




                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                 System.out.println("ID   |   NAME     |   SRC  |   DEST   |     DEPT DATE   |    DEPT TIME   |    ARR TIME    |    DURATION    |    TOTALSEATS   |   AVASEATS   |     PRICE      ");

                    while (resultSet.next()) {
                        // Retrieve flight information from the result set
                        int id = resultSet.getInt("id");
                        String flightNameResult = resultSet.getString("flightname");
                        String srcResult = resultSet.getString("src");
                        String destResult = resultSet.getString("dest");
                        String depDateResult = resultSet.getString("depdate");
                        String depTimeResult = resultSet.getString("deptime");
                        String arrTimeResult = resultSet.getString("arrtime");
                        String durationResult = resultSet.getString("duration");
                        int totalSeatsResult = resultSet.getInt("totalseats");
                        int availableSeatsResult = resultSet.getInt("availableseats");
                        double priceResult = resultSet.getDouble("price");

                        // Display the flight information
                        System.out.println(id+"  |   "+flightNameResult +"  |   "+srcResult+"  |   "+destResult+"  |   "+depDateResult+"  |   "+depTimeResult+"  |   "+arrTimeResult+"  |   "+durationResult+"  |   "+totalSeatsResult+"  |   "+availableSeatsResult+"  |   "+priceResult);
                 
                    }
                }
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
            String flightNumber = sc.nextLine();

            System.out.print("Enter the number of seats to book: ");
            int seatsToBook = sc.nextInt();

            // Get flight information
            String flightQuery = "SELECT * FROM flighs WHERE id= ?";
            try (PreparedStatement flightStatement = con.prepareStatement(flightQuery)) {
                flightStatement.setString(1, flightNumber);

                try (ResultSet flightResultSet = flightStatement.executeQuery()) {
                    if (flightResultSet.next()) {
                        int flightId = flightResultSet.getInt("id");
                        int price = flightResultSet.getInt("price");
                        int availableSeats = flightResultSet.getInt("availableseats");

                        // Check if there are enough available seats
                        if (seatsToBook <= availableSeats) {
                            // Book the flight
                            bookFlight(con, flightId, seatsToBook, price,a);

                            // Display the total price to the user
                            int totalPrice = seatsToBook * price;
                            System.out.println("Booking successful!");
                            System.out.println("Total Price: $" + totalPrice);

                        } else {
                            System.out.println("Not enough available seats. Please choose a smaller number of seats.");
                        }
                    } else {
                        System.out.println("Flight not found.");
                    }
                }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void bookFlight(Connection connection, int flightId, int seats, int price,int a) throws SQLException {
        // Insert booking information into bookings table
        String bookingQuery = "INSERT INTO bookings (cust_id, flight_id, seats, total_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement bookingStatement = connection.prepareStatement(bookingQuery)) {
            // Assuming cust_id is a placeholder for the customer ID; replace with the actual customer ID
            bookingStatement.setInt(1, a); // Replace with the actual customer ID

            bookingStatement.setInt(2, flightId);
            bookingStatement.setInt(3, seats);
            bookingStatement.setInt(4, price);

            bookingStatement.executeUpdate();
        }

        // Update available seats in flights table
        String updateSeatsQuery = "UPDATE flighs SET availableseats = availableseats - ? WHERE id = ?";
        try (PreparedStatement updateSeatsStatement = connection.prepareStatement(updateSeatsQuery)) {
            updateSeatsStatement.setInt(1, seats);
            updateSeatsStatement.setInt(2, flightId);

            updateSeatsStatement.executeUpdate();
        
    }}

   
}
public class AirTicketManagingSystem {
 static Scanner sc=new Scanner(System.in);
       static String  adminlogin= "admin" ;
       static String  adminpwd="1234";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int option;
        do{
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
         a= sc.nextInt();
         switch(a){
             case 1:
                 name = sc.next();
                 pwd  = sc.next();
                 String sql = "SELECT * FROM signup WHERE name = ? AND pwd = ?";
                 PreparedStatement stmt = con.prepareStatement(sql);
                 stmt.setString(1, name);
                 stmt.setString(2, pwd);
                 ResultSet rs = stmt.executeQuery();

                 if (rs.next()) {
                     System.out.println("Login successful!");
                     int n=0;
                      do{
                      System.out.println();
                      System.out.println();
                     System.out.println("1.Search for flights");
                     System.out.println("2.Book tickets");
                     System.out.println("3.Back");
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
                              System.out.println(k +" is the id of the user");
                              user.bookflight(k);
                             
                     }
                      }while (n!=3);
                     y=1;
                     break;
                 } else {
                     System.out.println("Invalid username or password. Please try again.");
                 }
                 break;
             case 2:
                 name = sc.next();
                 pwd  = sc.next();
                 gender = sc.next();
                 email = sc.next();
                 PreparedStatement st = con.prepareStatement("insert into signup (name, pwd,gender, emailid) values(?,?,?,?);");
                 
                 st.setString(1,name);
                 st.setString(2, pwd);
                 st.setString(3, gender);
                 st.setString(4,email);
                 int b =  st.executeUpdate();
                 System.out.println("No of rows affected" + b);
                 break;
            
         }
          if (y==1)break;
        }while(a!=3);
        
    }
    
}
