import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class ShadeDesigner extends JFrame
{
  private JPanel optionsPanel;          // To hold components
  private JPanel numberPanel;           // To hold components
  private JPanel tallyPanel;            // To hold components
  private JPanel calculatePanel;        // To hold components
  private JPanel resultsPanel;          // To hold components
  private JComboBox styleBox;           // A list of styles
  private JComboBox sizeBox;            // A list of sizes
  private JComboBox colorBox;           // A list of colors
  private JSlider numberSlider;         // A slider for the number of shades
  private JButton calculateButton;      // User interface to calculate
  private JLabel styleMessage;          // Message for style
  private JLabel sizeMessage;           // Message for size
  private JLabel colorMessage;          // Message for color
  private JLabel totalMessage;          // Message for total
  private JTextField numberField;       // Field to hold number of shades
  private JTextField totalField;        // Field to hold total
  final int basePrice = 50;             // Base price of shade
  
  // Create arrays for combo boxes and list
  private String[] style = { "Regular", "Folding",
                            "Roman"};
  private String[] size = { "25 inches", "27 inches", "32 inches", "40 inches"};
  private String[] color = { "Natural", "Blue", "Teal", "Red", "Green"};
  
  /**
   * Constructor
   */
  
  public ShadeDesigner()
  {
    // Set title
    setTitle("Shade Designer");
    
    // Specify action for the close button
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Create a BorderLayout manager
    setLayout(new GridLayout(5,1));
    
    // Build the panels
    buildOptionsPanel();
    buildNumberPanel();
    buildTallyPanel();
    buildCalculatePanel();
    buildResultsPanel();
    
    // Add the panels to the content pane
    add(optionsPanel);
    add(numberPanel);
    add(tallyPanel);
    add(calculatePanel);
    add(resultsPanel);
    
    // Pack and display the window
    pack();
    setVisible(true);
  }
  
  /**
   * The buildOptionsPanel method adds three combo boxes with the
   * shades types of styles, sizes, and colors.
   */
  
  private void buildOptionsPanel()
  {
    // Create a panel to hold the combo boxes
    optionsPanel = new JPanel(new GridLayout(2,3));
    
    // Create messages
    styleMessage = new JLabel("Styles");
    sizeMessage = new JLabel("Sizes");
    colorMessage = new JLabel("Colors");
    
    // Create the combo boxes
    styleBox = new JComboBox(style);
    sizeBox = new JComboBox(size);
    colorBox = new JComboBox(color);
    
    // Add the combo boxes to the panel
    optionsPanel.add(styleMessage);
    optionsPanel.add(sizeMessage);
    optionsPanel.add(colorMessage);
    optionsPanel.add(styleBox);
    optionsPanel.add(sizeBox);
    optionsPanel.add(colorBox);
  }
  
  /**
   * The buildNumberPanel method add a slider to indicate number of shades.
   */
  
  private void buildNumberPanel()
  {
    // Create a panel to hold the list
    numberPanel = new JPanel(new GridLayout(2,1));
    
    // Create the number slider
    numberSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 0);
    numberSlider.setMajorTickSpacing(40);
    numberSlider.setMinorTickSpacing(20);
    numberSlider.setPaintTicks(true);
    numberSlider.setPaintLabels(true);
    numberSlider.addChangeListener(new SliderListener());
    
    // Add the components to the panel
    numberPanel.add(numberSlider);
  }
  
  /**
   * The buildTallyPanel method adds a field to tally the number of shades
   */
  
  private void buildTallyPanel()
  {
    // Create a panel to hold the tally
    tallyPanel = new JPanel();
   
    // Create field to hold number
    numberField = new JTextField("0", 3);
    numberField.setEditable(false);
    
    // Add the field to the panel
    tallyPanel.add(numberField);
  }
  
  /**
   * The buildCalculatePanel method adds a calculate button
   */
  
  private void buildCalculatePanel()
  {
    // Create a panel to hold the button
    calculatePanel = new JPanel();
   
    // Create the button
    calculateButton = new JButton("Calculate");
        
    // Register action listener
    calculateButton.addActionListener(new ButtonListener());
    
    // Add the button to the panel
    calculatePanel.add(calculateButton);
  }
  
  /**
   * The buildResultsPanel method adds a list of results
   */
  
  private void buildResultsPanel()
  {
    // Create a panel to hold the totals
    resultsPanel = new JPanel();
    
    // Create the message
    totalMessage = new JLabel("Total:");
    
    // Create the field
    totalField = new JTextField(10);
    
    // Make field uneditable
    totalField.setEditable(false);
    
    // Add JTextFields
    resultsPanel.add(totalMessage);
    resultsPanel.add(totalField);
  }
  
  /**
   * The stylePrice method determines the cost of the style chosen
   * @param style The style chosen
   * @return price of style
   */
  
  private int stylePrice(String style)
  {
    int price = 0;
    if (style.equals("Folding"))
    {
      price = 10;
    }
    else if (style.equals("Roman"))
    {
      price = 15;
    }
    return price;
  }
  
  /**
   * The sizePrice method determines the cost of the size chosen
   * @param size The size chosen
   * @return price of size
   */
  
  private int sizePrice(String size)
  {
    int price = 0;
    if (size.equals("27 inches"))
    {
      price = 2;
    }
    else if (size.equals("32 inches"))
    {
      price = 4;
    }
    else if (size.equals("40 inches"))
    {
      price = 6;
    }
    return price;
  }
  
  /**
   * The colorPrice method determines the cost of the color chosen
   * @param color The color chosen
   * @return price of color
   */
  
  private int colorPrice(String color)
  {
    int price = 0;
    if (color.equals("Natural"))
    {
      price = 5;
    }
    return price;
  }
  
  /**
   * The totalPrice method determines the cost of the items
   * @param st price of style
   * @param si price of size
   * @param c price of color
   * @return price of all items
   */
  
  private int totalPrice(int st, int si, int c)
  {
    int price = basePrice + st + si + c;
    int number = numberSlider.getValue();
    int total = price * number;
    return total;
  }
  
  private class SliderListener implements ChangeListener
  {
    public void stateChanged(ChangeEvent e)
    {
      int number;
      
      // Get slider value
      number = numberSlider.getValue();
      
      // Set numberField
      numberField.setText(Integer.toString(number));
    }
  }
      
  /**
   * Private inner class that handles the event when the user selects the
   * calculate button
   */
  
  private class ButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    { 
      // Get the selected style, size, and color
      String selectedStyle = (String) styleBox.getSelectedItem();
      String selectedSize = (String) sizeBox.getSelectedItem();
      String selectedColor = (String) colorBox.getSelectedItem();
      
      // Get Prices
      int styleP = stylePrice(selectedStyle);
      int sizeP = sizePrice(selectedSize);
      int colorP = colorPrice(selectedColor);
      
      // Calculate total
      int total = totalPrice(styleP, sizeP, colorP);
      
      // Make total a string
      String t = "" + total;
      
      // Set results
      totalField.setText(t);
    }
  }
  
  /**
   * The main method creates and instance of the ShadeDesigner class
   */
      
  public static void main(String[] args)
  {
    new ShadeDesigner();
  }
}
  
  