import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

public class ExchangeRateback2 {

  static float holder1;
  static float change;
  static float end;
  static float holder;
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    System.out.println("Input 2 currencies on their own line with their identifier. EX. \nCAD \nUSD");
    String inp1 = scan.nextLine().toLowerCase();
    String inp2 = scan.nextLine().toLowerCase();
    try {
      // Set up the API request URL

      URL url = new URL("	https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + inp1 + '/' + inp2 + ".json");
      
      // Open a connection to the API endpoint
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      
      // Set the request method and headers
      conn.setRequestMethod("GET");
      conn.setRequestProperty("User-Agent", "Java/1.8");
      
      // Read the API response
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      // Get just the exchange rate
      String result = response.toString();
      int firstInd = result.indexOf(",");
      String output = result.substring((firstInd+12), (result.length()-1));
      holder1 = Float.parseFloat(output);

      // Precentage change
      System.out.println("Do you want to examine a year back month or week");
      String choice = scan.nextLine();
      if (choice.equals("year")){
        end = ChangeYear(inp1, inp2);
      }
      else if (choice.equals("month")){
        end = ChangeMonth(inp1, inp2);
      }
      else{
        end = Change(inp1, inp2);
      }
      System.out.println(end);
      // Print the API response
      int multipliedValue = (int) (change * 1000);
      int newChange = multipliedValue / 10;

      if (change>=1){
        System.out.println("The exchange rate changed by %" + (newChange-100) + " over the " + choice + " in favor of " + inp1);
        System.out.println("The old value was " + holder + " now it is " + holder1);
      }
      else {
      System.out.println("The exchange rate changed by %-" + ((newChange-100)*-1) + " over the " + choice + " against the favor of " + inp1);
      System.out.println("The old value was " + holder + " now it is " + holder1);
      }
    } catch (Exception e) {
      System.out.println("API request failed: " + e.getMessage());
    }
  }


  public static float Change(String a, String b ){
    LocalDate currentDate = LocalDate.now();
    LocalDate date = currentDate.minusDays(7);
    try {
      // Set up the API request URL

      URL url = new URL("	https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"+ date + "/currencies/" + a + '/' + b + ".json");
      
      // Open a connection to the API endpoint
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      
      // Set the request method and headers
      conn.setRequestMethod("GET");
      conn.setRequestProperty("User-Agent", "Java/1.8");
      
      // Read the API response
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      // Get just the exchange rate
      String result = response.toString();
      int firstInd = result.indexOf(",");
      String output = result.substring((firstInd+11), (result.length()-1));
      holder = Float.parseFloat(output);

      // Precentage change
      if ((holder1/holder) > 0){
        change = holder1/holder;
      }
      else {
        float temp = holder/holder1;
        change = holder/holder1-temp-temp;
      }
      // Print the API response
      return(change);
    } catch (Exception e) {
      System.out.println("API request failed: " + e.getMessage());
      return(change);
    }
  }

  public static float ChangeMonth(String a, String b ){
    LocalDate currentDate = LocalDate.now();
    LocalDate date = currentDate.minusMonths(1);
    try {
      // Set up the API request URL

      URL url = new URL("	https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"+ date + "/currencies/" + a + '/' + b + ".json");
      
      // Open a connection to the API endpoint
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      
      // Set the request method and headers
      conn.setRequestMethod("GET");
      conn.setRequestProperty("User-Agent", "Java/1.8");
      
      // Read the API response
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      // Get just the exchange rate
      String result = response.toString();
      int firstInd = result.indexOf(",");
      String output = result.substring((firstInd+11), (result.length()-1));
      holder = Float.parseFloat(output);

      // Precentage change
      if ((holder1/holder) > 0){
        change = holder1/holder;
      }
      else {
        float temp = holder/holder1;
        change = holder/holder1-temp-temp;
      }
      // Print the API response
      return(change);
    } catch (Exception e) {
      System.out.println("API request failed: " + e.getMessage());
      return(change);
    }
  }

  public static float ChangeYear(String a, String b ){
    LocalDate currentDate = LocalDate.now();
    LocalDate date = currentDate.minusYears(1);
    try {
      // Set up the API request URL

      URL url = new URL("	https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"+ date + "/currencies/" + a + '/' + b + ".json");
      
      // Open a connection to the API endpoint
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      
      // Set the request method and headers
      conn.setRequestMethod("GET");
      conn.setRequestProperty("User-Agent", "Java/1.8");
      
      // Read the API response
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      // Get just the exchange rate
      String result = response.toString();
      int firstInd = result.indexOf(",");
      String output = result.substring((firstInd+11), (result.length()-1));
      holder = Float.parseFloat(output);

      // Precentage change
      if ((holder1/holder) > 0){
        change = holder1/holder;
      }
      else {
        float temp = holder/holder1;
        change = holder/holder1-temp-temp;
      }
      // Print the API response
      return(change);
    } catch (Exception e) {
      System.out.println("API request failed: " + e.getMessage());
      return(change);
    }
  }
}
