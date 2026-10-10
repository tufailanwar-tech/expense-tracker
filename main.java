import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class main {
  public static void monthlySummary(ArrayList<Expense> list, Scanner sc) {
    System.out.print("Month (yyyy-mm): ");
    String input=sc.nextLine();
    String[] parts = input.split("-");
    int year = Integer.parseInt(parts[0]);
    int month = Integer.parseInt(parts[1]);
    double total = 0;
    for(Expense l:list){
      if (l.getDate().getMonthValue() == month && l.getDate().getYear() == year) {
        total = total + l.getAmount();
      }
    }
    System.out.println(total);
    
  }

  public static void categorySummary(ArrayList<Expense> list){
    HashMap<String,Double> map=new HashMap<>();
    for(Expense l:list){
      String cat=l.getCategory();
      double amount=l.getAmount();
      map.put(cat,map.getOrDefault(cat, 0.0) + amount);
    }
    System.out.println(map);
  }

  public static ArrayList<Expense> loadExpenses() {
    ArrayList<Expense> list = new ArrayList<>();
    try {
      File file = new File("expenses.txt");
      if (!file.exists())
        return list;
      Scanner fileScanner = new Scanner(file);
      while (fileScanner.hasNextLine()) {
        String[] parts = fileScanner.nextLine().split(",");
        list.add(new Expense(parts[2], Double.parseDouble(parts[3]), parts[1], LocalDate.parse(parts[0])));
      }
      fileScanner.close();
    } catch (Exception ex) {
      System.out.println("Could not load: " + ex.getMessage());
    }
    return list;
  }

  public static void saveExpenses(ArrayList<Expense> list) {
    try {
      PrintWriter writer = new PrintWriter("expenses.txt");
      for (Expense e : list) {
        writer.println(e.getDate() + "," + e.getCategory() + "," + e.getTitle() + "," + e.getAmount());
      }
      writer.close();
    } catch (Exception ex) {
      System.out.println("Could not save: " + ex.getMessage());
    }
  }

  public static void main(String[] args) {
    ArrayList<Expense> list = loadExpenses();
    Scanner scanner = new Scanner(System.in);

    System.out.print("Title: ");
    String title = scanner.nextLine();

    System.out.print("Amount: ");
    double amount = Double.parseDouble(scanner.nextLine());

    System.out.print("Category: ");
    String category = scanner.nextLine();

    System.out.print("Date (yyyy-mm-dd): ");
    LocalDate date = LocalDate.parse(scanner.nextLine());

    Expense ex = new Expense(title, amount, category, date);
    list.add(ex);
    for (Expense e : list) {
      System.out.println(e);
    }
    saveExpenses(list);
    monthlySummary(list,scanner);
    categorySummary(list);
  }
}
