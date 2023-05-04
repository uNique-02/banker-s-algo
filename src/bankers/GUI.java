
package bankers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


public class GUI extends JFrame {
    
    final int WIDTH = 600, HEIGHT = 450;
    final Dimension gameDimension = new Dimension(WIDTH, HEIGHT);
    JTable table2;
    
    public GUI() {
        
        JPanel panel = new JPanel();
        panel.setSize(gameDimension);        
        
        // Set up the frame
        setTitle("Two Fixed JTables Example");
        setSize(gameDimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create Table headers       
        String[] columns0 = {"Allocation", "Maximum", "New Available", "Need"};
        Object[][] data0 = new Object[4][0];
        JTable table0 = new JTable(new DefaultTableModel(data0, columns0));
        table0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table0.setBorder(null);
        
        // Create the first table
        String[] columns1 = {"Processes"};
        Object[][] data1 = new Object[4][1];
        JTable table1 = new JTable(new DefaultTableModel(data1, columns1));
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setBorder(null);
        

        // Create the second table
        String[] columns2 = {"Processes", "A", "B", "C", "A", "B", "C", "A", "B", "C", "A", "B", "C"};
        Object[][] data2 = new Object[4][12];
        table2 = new JTable(new DefaultTableModel(data2, columns2));
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.setBorder(null);
        
        for(int i=0; i<columns0.length; i++){
            table0.getColumnModel().getColumn(i).setPreferredWidth(90);
           
        }
        
        table1.setRowHeight(30);
        table2.setRowHeight(30);
        
        Dimension headerSize = table0.getTableHeader().getPreferredSize();
      headerSize.height = 25;
      table0.getTableHeader().setPreferredSize(headerSize);
      
      
      // Create a custom cell renderer for the table header
        
      DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.CYAN);
        headerRenderer.setForeground(Color.BLACK);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, Color.BLACK));       
        // Set the custom cell renderer for the table header
        JTableHeader tableHeader = table0.getTableHeader();
        tableHeader.setDefaultRenderer(headerRenderer);
        
        table0.getTableHeader().setReorderingAllowed(false);
        table0.getTableHeader().setResizingAllowed(false);
      
        for(int i=0; i<columns2.length; i++){
            table2.getColumnModel().getColumn(i).setPreferredWidth(30);
        }
        
        table2.getColumnModel().getColumn(0).setPreferredWidth(80);
        
        table2.getTableHeader().setReorderingAllowed(false);
        table2.getTableHeader().setResizingAllowed(false);
        
       /* DefaultTableCellRenderer headerRenderer2 = new DefaultTableCellRenderer();
        
        headerRenderer2.setHorizontalAlignment(JLabel.CENTER); // Optional: Set the text alignment to center
        headerRenderer2.setFont(headerRenderer2.getFont().deriveFont(Font.BOLD));
        headerRenderer2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JTableHeader tableHeader2 = table2.getTableHeader();
        tableHeader2.setDefaultRenderer(headerRenderer2);
        */
  

        // Add the tables to the frame with fixed sizes
        JScrollPane scrollPane0 = new JScrollPane(table0);
        scrollPane0.setPreferredSize(new Dimension(180, 30));
        
        JScrollPane scrollPane1 = new JScrollPane(table1);
        scrollPane1.setPreferredSize(new Dimension(78, 200));
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setPreferredSize(new Dimension(180, 200));
      
        
        scrollPane0.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        ImageIcon splashImage = new ImageIcon(this.getClass().getResource("resources/menuBGF.png"));
        Image splashCopy = splashImage.getImage().getScaledInstance(600, 450, Image.SCALE_SMOOTH);
        ImageIcon splash = new ImageIcon(splashCopy);
		JLabel splashImg= new JLabel(splash);
		splashImg.setBounds(0, 0, 600,450);
                
                panel.setLayout(null);
                
        scrollPane0.setBounds(140, 10, 360, 30);
        //scrollPane1.setBounds(80, 40, 78, 110);
        scrollPane2.setBounds(60, 40, 460, 140);
        panel.add(scrollPane0);
        //panel.add(scrollPane1);
        panel.add(scrollPane2);
        
        JButton generateNeed = new JButton("Generate Need");
        generateNeed.setBounds(300, 350, 80, 20);

        generateNeed.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNeed(table2);
            }
            
        });
        panel.add(generateNeed);
        panel.add(splashImg, BorderLayout.CENTER);
        
        // Set the layout to display tables horizontally
        //setLayout(new FlowLayout());

        // Show the frame
        setResizable(false);
        setContentPane(panel);
     
        setVisible(true);
        setLocationRelativeTo(null);
        
        try {
            retrieveInput();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        new GUI();
        
    }
    
    public int[][] toInt(String[][] arr, int i, int[][] userInput){
        int[] input = new int[3];
        for(int j=0; j<arr[i].length; j++){
            input[j] = Integer.parseInt(arr[i][j]);
            userInput[i] = input;
        }
        
        return userInput;
    }
    
    public void retrieveInput() throws FileNotFoundException{
        int[][] alloc = new int[3][];
        int[][] maxMatrix = new int[3][];
        int[] available = new int[3];
        
  
            File file = new File("C:\\Users\\admin\\Desktop\\Third Year Second semester\\CMSC 125 - Operating Systems - Lab\\Bankers\\src\\bankers\\resources\\input.txt");
            Scanner scanner = new Scanner(file);
            int i = 0;           
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                i++;
            }
            
            String[][] lines = new String[i][]; // create an array to store the lines of the file
            int[][] userInput = new int[i][];
            
            Scanner scanner2 = new Scanner(file);
            i = 0;
            while (scanner2.hasNextLine()) {
                System.out.println("KIM: " + i);
                String line = scanner2.nextLine();
                lines[i] = line.split(","); // store the line in the array
                userInput = toInt(lines, i, userInput);
                i++;
            }
            scanner.close(); // close the scanner object to free up system resources
            // do something with the lines array here
        
        
        System.out.println("Length: " + lines.length);
        for(int count=0; count<3; count++){
            alloc[count] = userInput[count+1];
        }
        for(int count=0; count<3; count++){
            maxMatrix[count] = userInput[count+4];
        }
        available = userInput[userInput.length-1];
        
        for(int j=0; j<alloc.length; j++){
            for(int k=0; k<3; k++){
                table2.getModel().setValueAt(alloc[j][k], j, k+1);
                System.out.println(alloc[j][k]);
            }  
        }
        
        for(int j=0; j<maxMatrix.length; j++){
            for(int k=0; k<3; k++){
                table2.getModel().setValueAt(maxMatrix[j][k], j, k+4);
                System.out.println(maxMatrix[j][k]);
            }  
        }
        
        for(int k=0; k<3; k++){
                table2.getModel().setValueAt(available[k], 0, k+7);
                System.out.println(available[k]);
            } 
        
        
        
    }
    public void generateNeed(JTable table){
        int value1;
        int value2;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                /*value1 = Integer.parseInt(table.getModel().getValueAt(i, j+4).toString());
                value2 = Integer.parseInt(table.getModel().getValueAt(i, j+1).toString());
                table.getModel().setValueAt(value1-value2, i, j+10);*/
                
                value1 = Integer.parseInt(table.getModel().getValueAt(i, j+4).toString());
                value2 = Integer.parseInt(table.getModel().getValueAt(i, j+1).toString());
                table.getModel().setValueAt(value1-value2, i, j+10);
            }
        }
        safetyAlgo(table);
        
    }

    public void safetyAlgo(JTable table){
        int[] work = new int[3];
        int[] need = new int[3];
        int[] finish = {0, 0, 0};
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.println("Index: " + j);
                try{
                    work[j] = Integer.parseInt(table.getModel().getValueAt(i, j+7).toString());
                }catch(Exception e){
                    System.out.println("WORK");
                }
                
                try{
                    need[j] = Integer.parseInt(table.getModel().getValueAt(i, j+10).toString());
                }catch(Exception e){
                    System.out.println("NEED");
                }
                
                
            }
            System.out.println("Need: \n { ");
            for(int j=0; j<3; j++){
                System.out.print(need[j]);
            }
            System.out.print("}");
            
            System.out.println("Work: \n { ");
            for(int j=0; j<3; j++){
                System.out.print(work[j]);
            }
            System.out.print("}");
            
        }
    }
}
