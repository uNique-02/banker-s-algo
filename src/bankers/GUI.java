
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
    
    
    int processCount=0;
    int[][] alloc;
    int[][] maxMatrix;
    int[] available;
    
    public GUI() {
        
        try {
            retrieveInput();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Process Count: " + processCount);
        
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
        Object[][] data2 = new Object[processCount][12];
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
                generateNeed(table2, available, maxMatrix, alloc, processCount, 3);
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
        
        
        
        setFileInputToTable();
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
    
    public void setFileInputToTable(){
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
    
    public void retrieveInput() throws FileNotFoundException{
  
            
        
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
        processCount = userInput[0][0];
        
        alloc = new int[processCount][];
        maxMatrix = new int[processCount][];
        available = new int[processCount];
        
        System.out.println("Proces Count: " + processCount);
        for(int count=0; count<processCount; count++){
            alloc[count] = userInput[count+1];
        }
        for(int count=0; count<processCount; count++){
            maxMatrix[count] = userInput[count+processCount+1];
        }
        available = userInput[userInput.length-1];
        
    }
    public void generateNeed(JTable table, int availableArray[], int maxArray[][], int allocationArray[][], int totalProcess, int totalResources){
        int value1;
        int value2;
        
        int allocation[][] = allocationArray;
        
        int[][] need = new int [processCount][3];
        for(int i=0; i<processCount; i++){
            for(int j=0; j<3; j++){
                
                value1 = Integer.parseInt(table.getModel().getValueAt(i, j+4).toString());
                value2 = Integer.parseInt(table.getModel().getValueAt(i, j+1).toString());
                table.getModel().setValueAt(value1-value2, i, j+10);
                need[i][j] = value1-value2;
            }
        }
        //safetyAlgo(table);
        int processes[] = {1, 2, 3};
        
        //checkSafeSystem(availableArray, maxArray, allocationArray, totalProcess, totalResources, need);
        
        safetyAlgo(availableArray, maxArray, allocationArray, totalProcess, totalResources, need);
        
    }
    
    public boolean checkifSafe(int need[], int work[]){
        int i=0;
        boolean less = true;
        
        while(less && i<work.length){
            if(need[i]>work[i]){
                less=false;
            }
            i++;
        }
        return less;
    }
    
    public int[] updateWork(int work[], int alloc[]){
            
            for(int i=0; i<alloc.length; i++){
                work[i] = work[i] + alloc[i];
            }
        
        return work;
    }

    public void safetyAlgo(int availableArray[], int maxArray[][], int allocationArray[][], int totalProcess, int totalResources, int needArray[][]){
        
        int work[] = availableArray;
        int loopCount=0;
        boolean finish[] = new boolean[totalProcess];
        int counter=0;
        String[] processes = new String[totalProcess];
        String[] sequence = new String[totalProcess];
        boolean exit=false;
        
        for(int i=0; i<totalProcess; i++){
            finish[i] = false;
            processes[i] = "P"+ i;
        }
        
        while(counter!=totalProcess && exit==false){
            for(int i=0; i<totalProcess; i++){
            
            System.out.println("Need: ");
            for(int j=0; j<needArray[i].length; j++){
                System.out.print(needArray[i][j] + " ");
            }
            
            System.out.println("\n\nWork: " );
            for(int j=0; j<work.length; j++){
                System.out.print(work[j] + " ");
            }
            
            boolean isSafe = checkifSafe(needArray[i], work);
            if(finish[i]==false && isSafe){
                finish[i] = true;
                updateWork(work, alloc[i]);
                System.out.println("Work > Need: " + isSafe);
                System.out.println("Process " + i + " is kept in safe sequence");
                sequence[counter] = processes[i];
                counter++;
                System.out.println("Process: " + processes[i]);
            }
            else{
                System.out.println("Work > Need: " + isSafe);
                System.out.println("Process " + i + " is not kept in safe sequence");
            }
            }
            loopCount++;
            
            if(loopCount>processCount){
                exit=true;
            }
        }
        exit=false;
        
        
        for(int i=0; i<sequence.length; i++){
            if(sequence[i]==null){
                exit=true;
                break;
            }  
        } 
        
        if(exit==true){
            System.out.println("SAFE SEQUENCE IS NOT FOUND");
        }else{
            System.out.println("SAFE SEQUENCE: ");
            for(int i=0; i<sequence.length; i++){
                System.out.println(sequence[i] + " ");

            }  
        }
            
        
         
        
    }
    
        
}
    
    /*
     // create checkSafeSystem() method to determine whether the system is in safe state or not  
    static boolean checkSafeSystem(int availableArray[], int maxArray[][], int allocationArray[][], int totalProcess, int totalResources, int needArray[][])  
    {  
        //int [][]needArray = new int[totalProcess][totalResources];  
  
        // call findNeedValue() method to calculate needArray  
        //findNeedValue(needArray, maxArray, allocationArray, totalProcess, totalResources);  
  
        // all the process should be infinished in starting  
        
        
        System.out.println("M: " + totalProcess + " N: "+ totalResources);
        
        boolean []finishProcesses = new boolean[totalProcess];  
  
        // initialize safeSequenceArray that store safe sequenced  
        int []safeSequenceArray = new int[totalProcess];  
  
        // initialize workArray as a copy of the available resources  
        int []workArray = new int[totalResources];  
          
        for (int i = 0; i < totalResources ; i++)    //use for loop to copy each available resource in the workArray  
            workArray[i] = availableArray[i];  
  
        // initialize counter variable whose value will be 0 when the system is not in the safe state or when all the processes are not finished.  
        int counter = 0;  
          
        // use loop to iterate the statements until all the processes are not finished  
        while (counter < totalProcess)  
        {  
            // find infinished process which needs can be satisfied with the current work resource.  
            boolean foundSafeSystem = false;  
            for (int m = 0; m < totalProcess; m++)  
            {  
                System.out.println("For i= " + m + "\n Need: ");
                for(int k=0; k<needArray[m].length; k++){
                    System.out.print(needArray[m][k] + " ");
                }
                
                System.out.println("\nWork: " );
                for(int k=0; k<needArray[m].length; k++){
                    System.out.print(workArray[k] + " ");
                }
                if (finishProcesses[m] == false)        // when process is not finished  
                {  
                    int j;  
                      
                    //use for loop to check whether the need of each process for all the resources is less than the work  
                    for (j = 0; j < totalResources; j++)  
                        if (needArray[m][j] > workArray[j])      //check need of current resource for current process with work  
                            break;  
  
                    // the value of J and totalResources will be equal when all the needs of current process are satisfied  
                    if (j == totalResources)  
                    {  
                        for (int k = 0 ; k < totalResources ; k++)  
                            workArray[k] += allocationArray[m][k];  
  
                        // add current process in the safeSequenceArray  
                        safeSequenceArray[counter++] = m;  
  
                        // make this process finished  
                        finishProcesses[m] = true;  
  
                        foundSafeSystem = true;  
                    }  
                }  
            }  
  
            // the system will not be in the safe state when the value of the foundSafeSystem is false  
            if (foundSafeSystem == false)  
            {  
                System.out.print("The system is not in the safe state because lack of resources");  
                return false;  
            }  
        }  
  
        // print the safe sequence  
        System.out.print("The system is in safe sequence and the sequence is as follows: ");  
        for (int i = 0; i < totalProcess ; i++)  
            System.out.print("P"+safeSequenceArray[i] + " ");  
  
        return true;  
    }  
}
*/