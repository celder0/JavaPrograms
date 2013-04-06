import javax.swing.*;       // Swing classes needed
import java.awt.*;          // awt classes needed
import java.io.*;           // io classes needed
import java.util.Scanner;   // Scanner class needed
import java.awt.event.*;    // event classes needed
import java.util.ArrayList; // ArrayList class needed

/**
 * The TaxGui creates a GUI for creating editing and saving tax information
 * for an employee
 */

public class TaxGui extends JFrame
{
  private JPanel namePanel;
  private JPanel ratePanel;
  private JPanel dependentPanel;
  private JPanel healthPanel;
  private JPanel maritalPanel;
  private JPanel hoursPanel;
  private JPanel calculatePanel;
  private JPanel infoPanel;
  private JPanel infoPanel2;
  private JPanel infoPanel3;
  private JPanel resultsPanel;
  private JPanel buttonPanel;
  private JLabel messageName;
  private JLabel messageRate;
  private JLabel messageDependent;
  private JLabel messageHealthBox;
  private JLabel messageHours;
  private JLabel messageGross;
  private JLabel messageHealth;
  private JLabel messageTax;
  private JLabel messageNet;
  private JTextField rate;
  private JTextField hours;
  private JTextField gross;
  private JTextField health;
  private JTextField tax;
  private JTextField net;
  private JTextField number;
  private JRadioButton married;
  private JRadioButton single;
  private ButtonGroup radioButtonGroup;
  private String[] names = {""};
  private JTextField name;
  private JSpinner dependent;
  private JCheckBox healthBox;
  private JButton calculate;
  private JButton first;
  private JButton previous;
  private JButton next;
  private JButton last;
  private JButton add;
  private ArrayList<Employee> company = new ArrayList<Employee>(100);
  private String fileName;
  private int location;
  private int employeeNumber = 1;
  private boolean healthBoxState = false;
  private boolean marriedState;
  private final int WINDOW_WIDTH = 400;  // Window width
  private final int WINDOW_HEIGHT = 600; // Window height
  
  public TaxGui()
  {
    // Set the title
    setTitle("Tax");
    
    // Specify the action for the close button
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new Closing());
    
    // Set size of the window
    setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    
    // Add a GridLayout manager to the content pane
    setLayout(new GridLayout(6,1));
    
    // Create labels
    messageName = new JLabel("Name:");
    messageRate = new JLabel("Pay Rate:");
    messageDependent = new JLabel("#Dependents:");
    messageHours = new JLabel("Hours Worked:");
    messageGross = new JLabel("Gross Pay:");
    messageHealth = new JLabel("Health Deduction:");
    messageTax = new JLabel("Tax:");
    messageNet = new JLabel("Net Pay:");
    
    // Create text fields
    name = new JTextField(10);
    rate = new JTextField(5);
    hours = new JTextField(2);
    gross = new JTextField(10);
    health = new JTextField(10);
    health.setEditable(false);
    tax = new JTextField(10);
    tax.setEditable(false);
    net = new JTextField(10);
    net.setEditable(false);
    number = new JTextField(4);
    number.setEditable(false);
    
    // Create buttons
    calculate = new JButton("Calculate");
    first = new JButton("1st");
    previous = new JButton("Prev");
    next = new JButton("Next");
    last = new JButton("Last");
    add = new JButton("Add");
    
    // Register the action listeners
    previous.addActionListener(new NavigationButtonListener());
    first.addActionListener(new NavigationButtonListener());
    next.addActionListener(new NavigationButtonListener());
    last.addActionListener(new NavigationButtonListener());
    add.addActionListener(new NavigationButtonListener());
    calculate.addActionListener(new CalcButtonListener());
    
    // Create spinner
    dependent = new JSpinner(new SpinnerNumberModel(1,0,10,1));
    
    // Create checkbox
    healthBox = new JCheckBox("Health Deductions?");
    
    // Create radio buttons and group them
    married = new JRadioButton("Married");
    single = new JRadioButton("Single");
    radioButtonGroup = new ButtonGroup();
    radioButtonGroup.add(married);
    radioButtonGroup.add(single);
    
    // Create Panels and add components to them
    namePanel = new JPanel();
    namePanel.add(messageName);
    namePanel.add(name);
    ratePanel = new JPanel();
    ratePanel.add(messageRate);
    ratePanel.add(rate);
    dependentPanel = new JPanel();
    dependentPanel.add(messageDependent);
    dependentPanel.add(dependent);
    healthPanel = new JPanel();
    healthPanel.add(healthBox);
    maritalPanel = new JPanel();
    maritalPanel.add(married);
    maritalPanel.add(single);
    maritalPanel.setBorder(BorderFactory.createTitledBorder("Marital Status"));
    hoursPanel = new JPanel();
    hoursPanel.add(messageHours);
    hoursPanel.add(hours);
    calculatePanel = new JPanel();
    calculatePanel.add(calculate);
    infoPanel = new JPanel( new GridLayout(2,1));
    infoPanel.add(namePanel);
    infoPanel.add(ratePanel);
    infoPanel2 = new JPanel( new GridLayout(2,1));
    infoPanel2.add(dependentPanel);
    infoPanel2.add(healthPanel);    
    infoPanel3 = new JPanel( new GridLayout(2,1));
    infoPanel3.add(hoursPanel);
    infoPanel3.add(calculatePanel);
    resultsPanel = new JPanel(new GridLayout(4,2));
    resultsPanel.add(messageGross);
    resultsPanel.add(gross);
    resultsPanel.add(messageHealth);
    resultsPanel.add(health);
    resultsPanel.add(messageTax);
    resultsPanel.add(tax);
    resultsPanel.add(messageNet);
    resultsPanel.add(net);
    resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));
    buttonPanel = new JPanel();
    buttonPanel.add(first);
    buttonPanel.add(previous);
    buttonPanel.add(number);
    buttonPanel.add(next);
    buttonPanel.add(last);
    buttonPanel.add(add);
    
    // Add panels to the frame
    add(infoPanel);
    add(infoPanel2);
    add(maritalPanel);
    add(infoPanel3);
    add(resultsPanel);
    add(buttonPanel);
    
    // Display Window
    setVisible(true);
  }
  
  public void open()
  { 
    try
    {
      fileName = JOptionPane.showInputDialog("Enter file name.");
        
      // Open file
      File file = new File(fileName);
      if (!file.exists())
      {
      }
      else
      {
        Scanner inputFile = new Scanner(file);
        employeeNumber = 1;
        location = employeeNumber - 1;

        // Read the file contents into the student object arraylist
        while (inputFile.hasNext())
        {
          company.add(new Employee(inputFile.next(), inputFile.next(),
                                   inputFile.next(), inputFile.next(), 
                                   inputFile.next(), inputFile.next()));
        }
          
        // Close the file
        inputFile.close();
        setInfo();
      }
    }
    
    catch (IOException x)
    {
    }
  }
  
  public void save()
  {
    try
    {
      //Open file
      File file = new File(fileName);
      PrintWriter outputFile = new PrintWriter(file);
      
      // Write arraylist to file
      for (int i = 0; i < company.size(); i++)
      {
        company.get(location).setName(name.getText());
        company.get(location).setHealth(healthBox.isSelected());
        company.get(location).setMarried(single.isSelected());
        company.get(location).setRate(rate.getText());
        company.get(location).setHours(hours.getText());
        company.get(location).setDependents((Integer) (dependent.getModel().getValue()));
        String married;
        String health;
        marriedState = single.isSelected();
        healthBoxState = healthBox.isSelected();
        if (company.get(location).getMarried())
        {
          married = "Single";
        }
        else
        {
          married = "Married";
        }
        if (company.get(location).getHealth())
        {
          health = "yes";
        }
        else
        {
          health = "no";
        }
        outputFile.println(company.get(i).getName() + " " + 
                           Double.toString(company.get(i).getRate()) + " " +
                           married + " " +
                           Integer.toString(company.get(i).getDependents()) + " " +
                           health + " " +
                           Integer.toString(company.get(i).getHours()));
      }
      outputFile.close();
    }
    catch (IOException x)
    {
    }
  }
  
  private void setInfo()
  {
    name.setText(company.get(location).getName());
    rate.setText(Double.toString(company.get(location).getRate()));
    dependent.getModel().setValue(new Integer(company.get(location).getDependents()));
    healthBox.setSelected(company.get(location).getHealth());
    if (company.get(location).getMarried())
    {
      single.setSelected(false);
      married.setSelected(true);
    }
    else
    {
      married.setSelected(false);
      single.setSelected(true);
    }
    hours.setText(Integer.toString(company.get(location).getHours()));
    number.setText(Integer.toString(employeeNumber));
  }
  
  private void clearInfo()
  {
    name.setText("");
    rate.setText("");
    dependent.getModel().setValue(new Integer(0));
    healthBox.setSelected(false);
    single.setSelected(false);
    married.setSelected(false);
    hours.setText("");
    number.setText("");
  }
  
  private void clearCalc()
  {
    gross.setText("");
    health.setText("");
    tax.setText("");
    net.setText("");
  }
  
  private class CalcButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      save();
      gross.setText(Double.toString(company.get(location).getGross()));
      health.setText(Integer.toString(company.get(location).getHealthDeduction()));
      tax.setText(Double.toString(company.get(location).getTax()));
      net.setText(Double.toString(company.get(location).getNet()));
    }
  }
  
  private class NavigationButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() == first)
      {
        save();
        employeeNumber = 1;
        location = employeeNumber - 1;
        setInfo();
        clearCalc();
      }
      else if (e.getSource() == previous) 
      {
        save();
        if (location == 0)
        {
          JOptionPane.showMessageDialog(null, "Already at first employee");
        }
        else
        {
          employeeNumber--;
          location = employeeNumber - 1;
          setInfo();
          clearCalc();
        }
      }
      else if (e.getSource() == next)
      {
        save();
        if (location == company.size() - 1)
        {
          JOptionPane.showMessageDialog(null, "No more employees");
        }
        else
        {
          employeeNumber++;
          location = employeeNumber - 1;
          setInfo();
          clearCalc();
        }
      }
      else if (e.getSource() == last)
      {
        save();
        employeeNumber = company.size();
        location = company.size() - 1;
        setInfo();
        clearCalc();
      }
      else if (e.getSource() == add)
      {
        save();
        employeeNumber = company.size() + 1;
        location = employeeNumber - 1;
        clearInfo();
        clearCalc();
        company.add(new Employee("Name","0","","0","","0"));
        number.setText(Integer.toString(employeeNumber));
      }
    }
  }
  
  private class Closing extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      save();
      System.exit(0);
    }
    
    public void windowOpened(WindowEvent e)
    {
      open();
    }
  }
  
  public static void main(String[] args)
  {
    new TaxGui();
  }  
}

class Employee
{
  private String name;
  private double rate;
  private boolean married;
  private int dependents;
  private boolean health;
  private int hours;
  private int overtime;
  private double gross;
  private int healthDeduc;
  private int deduction;
  private int dependentDeduc;
  private double tax;
  private double net;
  private double overtimeRate = 1.5;
  private double pay;
  private double taxablePay;
  private final int maxHours = 40;
  private final int medicalSingle = 60;
  private final int standardSingle = 75;
  private final int medicalMarried = 100;
  private final int standardMarried = 150;
  private final int dependentDeductionRate = 70;
  private final int secondSingleTax = 250;
  private final int secondMarriedTax = 500;
  private final int thirdSingleTax = 400;
  private final int thirdMarriedTax = 800;
  private final int fourthSingleTax = 400;
  private final int fourthMarriedTax = 800;
  private final double firstTax = .1;
  private final double secondTax = .15;
  private final double thirdTax = .25;
  private final double fourthTax = .35;
  
  public Employee()
  {
    name = "";
    rate = 0.0;
    married = true;
    dependents = 0;
    health = true;
    hours = 0;
  }
  
  public Employee(String n, double r, boolean m, int d, boolean he, int ho)
  {
    name = n;
    rate = r;
    married = m;
    dependents = d;
    health = he;
    hours = ho;
  }
  
  public Employee(String n, double r, String m, int d, String he, int ho)
  {
    name = n;
    rate = r;
    if (m.equalsIgnoreCase("Single"))
    {
      married = false;
    }
    else if (m.equalsIgnoreCase("Married"))
    {
      married = true;
    }
    dependents = d;
    if (he.equalsIgnoreCase("yes"))
    {
      health = true;
    }
    else if (he.equalsIgnoreCase("no"))
    {
      health = false;
    }
    hours = ho;
  }
  
  public Employee(String n, String r, String m, String d, String he, String ho)
  {
    if (r.equals(""))
    {
      r = "0";
    }
    if (d.equals(""))
    {
      d = "0";
    }
    if (ho.equals(""))
    {
      ho = "0";
    }
    name = n;
    rate = Double.parseDouble(r);
    if (m.equalsIgnoreCase("Single"))
    {
      married = false;
    }
    else if (m.equalsIgnoreCase("Married"))
    {
      married = true;
    }
    dependents = Integer.parseInt(d);
    if (he.equalsIgnoreCase("yes"))
    {
      health = true;
    }
    else if (he.equalsIgnoreCase("no"))
    {
      health = false;
    }
    hours = Integer.parseInt(ho);
  }
  
  public String getName()
  {
    return name;
  }
  
  public double getRate()
  {
    return rate;
  }
  
  public boolean getMarried()
  {
    return married;
  }
  
  public int getDependents()
  {
    return dependents;
  }
  
  public boolean getHealth()
  {
    return health;
  }
  
  public int getHours()
  {
    return hours;
  }
  
  public void setName(String n)
  {
    if (n.equals(""))
    {
      name = "Employee";
    }
    else
    {
      name = n;
    }
  }
  
  public void setRate(String r)
  {
    if (r.equals(""))
    {
    rate = 0;
    }
    else
    {
      rate = Double.parseDouble(r);
    }
  }
  
  public void setRate(double r)
  {
    rate = r;
  }
  
  public void setMarried(boolean m)
  {
    married = m;
  }
  
  public void setDependents(String d)
  {
    if (d.equals(""))
    {
    dependents = 0;
    }
    else
    {
      dependents = Integer.parseInt(d);
    }  
  }
  
  public void setDependents(int d)
  {
    dependents = d;
  }
  
  public void setHealth(boolean h)
  {
    health = h;
  }
  
  public void setHours(String h)
  {
    if (h.equals(""))
    {
      hours = 0;
    }
    else
    {
      hours = Integer.parseInt(h);
    }
  }
  
  public void setHours(int h)
  {
    hours = h;
  }
  
  public double getGross()
  {
    if (hours > maxHours)
    {
      overtime = hours - maxHours;
      overtimeRate = rate * overtimeRate;
      gross = overtimeRate * overtime;
      pay = rate * maxHours;
      gross = gross + pay;
    }
    else
    {
      gross = rate * hours;
    }
    return gross;
  }
  
  public int getDeduction()
  {
    if (married = true)
    {
      deduction = standardSingle;
      
      if (health = true)
      {
        healthDeduc = medicalMarried;
      }
    }
    else
    {
      deduction = standardSingle;
      
      if (health = true)
      {
        healthDeduc = medicalSingle;
      }
    }
    dependentDeduc = dependents * dependentDeductionRate;
    deduction = healthDeduc + dependentDeduc + deduction;
    return deduction;
  }
  
  public int getHealthDeduction()
  {
    if (married = true)
    {
      if (health = true)
      {
        healthDeduc = medicalMarried;
      }
    }
    else
    {
      if (health = true)
      {
        healthDeduc = medicalSingle;
      }
    }
    return healthDeduc;
  }
  
  public double getTax()
  {
    gross = getGross();
    healthDeduc = getHealthDeduction();
    taxablePay = gross - healthDeduc;
    
    if (married = true)
    {
      if (taxablePay > fourthMarriedTax)
      {
        tax = (taxablePay - fourthMarriedTax) * fourthTax + (fourthMarriedTax - thirdMarriedTax) * 
          thirdTax + (thirdMarriedTax - secondMarriedTax) * secondTax + secondMarriedTax * firstTax; 
      }
      else if (taxablePay > thirdMarriedTax)
      {
        tax = (taxablePay - thirdMarriedTax) * thirdTax + (thirdMarriedTax - secondMarriedTax) * 
          secondTax + secondMarriedTax * firstTax;
      }
      else if (taxablePay > secondMarriedTax)
      {
        tax = (taxablePay - secondMarriedTax) * secondTax + secondMarriedTax * firstTax;
      }
      else
      {
        tax = taxablePay * firstTax; 
      }
    }
    else
    {
      if (taxablePay > fourthSingleTax) 
      {
        tax = (taxablePay - fourthSingleTax) * fourthTax + (fourthSingleTax - thirdSingleTax) * 
          thirdTax + (thirdSingleTax - secondSingleTax) * secondTax + secondSingleTax * firstTax;
      }
      else if (taxablePay > thirdSingleTax)
      {
        tax = (taxablePay - thirdSingleTax) * thirdTax + (thirdSingleTax - secondSingleTax) * 
          secondTax + secondSingleTax * firstTax;
      }
      else if (taxablePay > secondSingleTax)
      {
        tax = (taxablePay - secondSingleTax) * secondTax + secondSingleTax * firstTax;
      }
      else
      {
        tax = taxablePay * firstTax;
      }
    }
    return tax;
  }
  
  public double getNet()
  {
    gross = getGross();
    healthDeduc = getHealthDeduction();
    tax = getTax();
    net = gross - healthDeduc - tax;
    return net;
  }
}