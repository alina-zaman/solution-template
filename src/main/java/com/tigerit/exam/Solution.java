package com.tigerit.exam;


import static com.tigerit.exam.IO.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Alina Zaman
 */
class Table{
    String tableName;
    int numberOfColumn;
    int numberOfRecord;
    String columnName[];
    int columnValue[][]; // 2d array contains: ([n][m]) mth value of nth record where m is the columNnumber
}
class ShowTable{
    int printAllColumn;
    String printSelectedColumnList[];
    String selectedColumnListFromFirstTable[],selectedColumnListFromSecondTable[];
    int selectedColumnListFromFirstTableNumber,selectedColumnListFromSecondTableNumber;
    int printSelectedColumnNumber;
    int firstTableJoinEntry[],secondTableJoinEntry[];
    int numberOfJoinEntry;
    ShowTable(int s){ 
        printSelectedColumnList=new String[s];
        selectedColumnListFromFirstTable=new String[s];
        selectedColumnListFromSecondTable=new String[s];
    }
    void printdJoinTable(Table FirstTable,Table SecondTable){
        int c1=0,c2=0;
        for(int i=0;i<printSelectedColumnNumber;i++){
            String parts[]=printSelectedColumnList[i].split("\\,");
            printSelectedColumnList[i]=parts[0];
               
            String columnName[]=printSelectedColumnList[i].split("\\.");  //takine the table name part in columnName[0] and column name in columnName[1]
            if(columnName[0].equals(FirstTable.tableName)){
               selectedColumnListFromFirstTable[c1++]=columnName[1];
            }
            else
               selectedColumnListFromSecondTable[c2++]=columnName[1];
        }
        selectedColumnListFromFirstTableNumber=c1;selectedColumnListFromSecondTableNumber=c2;
                    
        if(printAllColumn==1){
            for(int i=1;i<=FirstTable.numberOfColumn;i++){
                System.out.print(FirstTable.columnName[i]);        // print all the column from first table when select *
                System.out.print(" ");
            }
            for(int i=1;i<=SecondTable.numberOfColumn;i++){
                System.out.print(SecondTable.columnName[i]);       // print all the column name from first table when select *
                if(i!=SecondTable.numberOfColumn){
                    System.out.print(" ");
                }
            }
        }
        else{
       
            for(int i=0;i<selectedColumnListFromFirstTableNumber;i++){
                System.out.print(selectedColumnListFromFirstTable[i]+" ");  //print only selected column name from first table
            }
            for(int i=0;i<selectedColumnListFromSecondTableNumber;i++){
                if(i==0)
                   System.out.print(selectedColumnListFromSecondTable[i]);   //print only selected column name from second table
                else
                   System.out.print(" "+selectedColumnListFromSecondTable[i]); 
              }
            
        }
        System.out.println();
        // printing the value of join entry
        for(int i=0;i<numberOfJoinEntry;i++){
            if(printAllColumn==1){ // if select * then print the value from all column
                for(int j=1;j<=FirstTable.numberOfColumn;j++){  
                    if(j==1)
                        System.out.print(FirstTable.columnValue[firstTableJoinEntry[i]][j]);  //printing the matched row from first table
                    else
                        System.out.print(" "+FirstTable.columnValue[firstTableJoinEntry[i]][j]);
                }
                for(int j=1;j<=SecondTable.numberOfColumn;j++){  
                    System.out.print(" "+SecondTable.columnValue[secondTableJoinEntry[i]][j]);  //printing the matched row from second table          
                }
            }
            else{
                int c=0;
                for(int j=1;j<=FirstTable.numberOfColumn;j++){
                    if(FirstTable.columnName[j].equals(selectedColumnListFromFirstTable[c])){
                         System.out.print(FirstTable.columnValue[firstTableJoinEntry[i]][j]+" "); //printing the selected column value from matched row from first table
                         c++;
                    }
                }
                    c=0;
                for(int j=1;j<=SecondTable.numberOfColumn;j++){    
                    if(SecondTable.columnName[j].equals(selectedColumnListFromSecondTable[c])){
                        if(c==0)
                         System.out.print(SecondTable.columnValue[secondTableJoinEntry[i]][j]);  //printing the selected column value from matched row from second table 
                        else
                            System.out.print(" "+SecondTable.columnValue[secondTableJoinEntry[i]][j]);
                         c++;
                    }
                    
                }
                   
            }
         System.out.println();
        }
    }
}
class JoinTable{
    int firstColumnNumber,secondColumnNumber,c;
    void joinTable(Table FirstTable,Table SecondTable,String joinColumn1,String joinColumn2,ShowTable queryShowTable){
        for(int m=1;m<=FirstTable.numberOfColumn;m++){
            if(FirstTable.columnName[m].equals(joinColumn1)){ // mth column has been choosen for join from Frist table
                    firstColumnNumber=m;                    // m is saved as first column number for join  from first table

            }
        }
        for(int n=1;n<=SecondTable.numberOfColumn;n++){
            if(SecondTable.columnName[n].equals(joinColumn2)){ // nth column has been choosen for join from Second table
                    secondColumnNumber=n;                      // n is saved as second column number for join from second table

            }
        }
        c=0;
        queryShowTable.firstTableJoinEntry=new int[FirstTable.numberOfRecord];   // save in which entry the first table match with second
        queryShowTable.secondTableJoinEntry=new int[SecondTable.numberOfRecord];  // save in which entry the second table match with first
        for(int i=1;i<=FirstTable.numberOfRecord;i++){
            
            for(int j=1;j<=SecondTable.numberOfRecord;j++){
                if(FirstTable.columnValue[i][firstColumnNumber]==SecondTable.columnValue[j][secondColumnNumber]){  // the selected column value from the ith entry from first table matched with the selected column value of jth entry from second table
                    queryShowTable.firstTableJoinEntry[c]=i;                    //ith entry saved as matched entry from first table
                    queryShowTable.secondTableJoinEntry[c++]=j;                 //jth entry saved as the matched column from second table
                }
            }
        }
        queryShowTable.numberOfJoinEntry=c;
        queryShowTable.printdJoinTable(FirstTable,SecondTable);
    }
}
class TigerITDatabaseDilemma {

    /**
     * @param args the command line arguments
     */
    public TigerITDatabaseDilemma(){
        Table[] DatabaseTable=new Table[11];
        //Scanner sc=new Scanner(System.in);
        IOHelper sc = new IOHelper();
        int testCase,numberOfTable,i,j,k,c,numberOfColumn,numberOfRecord,queryNumber,q,count,firstTable=0,secondTable=0;
        String query,shortTableName="",joinColumn1,joinColumn2;
        
        testCase=sc.nextInt();
        
        
        for(i=1;i<=testCase;i++){
            System.out.println("Test: "+i);
            numberOfTable=sc.nextInt();
            for(j=1;j<=numberOfTable;j++){
                DatabaseTable[j]=new Table();
                DatabaseTable[j].tableName=sc.next();
                DatabaseTable[j].numberOfColumn=sc.nextInt(); 
                DatabaseTable[j].numberOfRecord=sc.nextInt();
                DatabaseTable[j].columnValue=new int[DatabaseTable[j].numberOfRecord+1][DatabaseTable[j].numberOfColumn+1]; // declaring array where number of row is equal to the number of entry of table and number of column in each row is equal to number of column of table
                DatabaseTable[j].columnName=new String[DatabaseTable[j].numberOfColumn+1];
                for(c=1;c<=DatabaseTable[j].numberOfColumn;c++){
                  //  DatabaseTable[j].columnName[c]=new String();
                    DatabaseTable[j].columnName[c]=sc.next();
                   
                }
                for(k=1;k<=DatabaseTable[j].numberOfRecord;k++){
                    for(c=1;c<=DatabaseTable[j].numberOfColumn;c++){
                        DatabaseTable[j].columnValue[k][c]=sc.nextInt();
                    }
                }
           
            }
            
                queryNumber=sc.nextInt();
                for(q=1;q<=queryNumber;q++){
                    ShowTable queryShowTable= new ShowTable(200);
                    query = sc.next();
                    c=0;     //number of column to be print
                    while(true){
                         query = sc.next();
                         if(query.equals("FROM")){
                             break;}
                        if(query.equals("*")){
                            queryShowTable.printAllColumn=1;
                            
                        }
                        else{
                            queryShowTable.printSelectedColumnList[c++]=query; // column that should be print are saved
                        }
                    }
                    queryShowTable.printSelectedColumnNumber=c;          //number of column to be print
                    while(true){
                        query = sc.next();
                        for(count=1;count<=numberOfTable;count++){
                             if(DatabaseTable[count].tableName.equals(query)){
                                 firstTable=count;
                             }
                        }
                        if(query.equals("JOIN"))
                           break;
                        shortTableName=query;  // save the table's short name if exists otherwise the real name will be saved
                    }
                    DatabaseTable[firstTable].tableName=shortTableName;
                    while(true){
                        query = sc.next();
                        for(count=1;count<=numberOfTable;count++){
                             if(DatabaseTable[count].tableName.equals(query)){
                                 secondTable=count;
                             }
                        }
                        if(query.equals("ON"))
                           break;
                        shortTableName=query;  // save the table's short name if exists otherwise the real name will be saved
                    }
                    DatabaseTable[secondTable].tableName=shortTableName;
                    
                    
                    query = sc.next();
                    String[] parts1=query.split("\\.");            
                    joinColumn1=parts1[1];
                    query = sc.next();                    //taking the equal sign
                    query = sc.next();
                    String[] parts2=query.split("\\.");
                    joinColumn2=parts2[1];
                    JoinTable queryJoinTable= new JoinTable();
                    queryJoinTable.joinTable(DatabaseTable[firstTable],DatabaseTable[secondTable],joinColumn1,joinColumn2,queryShowTable);
                    System.out.println();
                }
                
                
            
        }
        
    }

    
    private static void Table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
class IOHelper{
    String current;
    int pos;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    IOHelper(){
        current = "";
        pos = 0;
    }
    public String next(){
        String ret="";
        try{
            while(true){
                if(pos == current.length()){
                    current = reader.readLine();
                    pos = 0;
                }
                while(pos<current.length() && (current.charAt(pos)==' ' || current.charAt(pos)=='\n' || current.charAt(pos)=='\t')){
                    pos++;
                }
                if(pos!=current.length()) 
                    break;
            }
            while(pos<current.length() && current.charAt(pos)!=' ' && current.charAt(pos)!='\n' && current.charAt(pos)!='\t'){
                ret += current.charAt(pos);
                pos ++;
            }
        }catch(IOException e){
            ret = null;
        }
        return ret;
    }
    public int nextInt(){
        String ret="";
        try{
            while(true){
                if(pos == current.length()){
                    current = reader.readLine();
                    pos = 0;
                }
                while(pos<current.length() && (current.charAt(pos)==' ' || current.charAt(pos)=='\n' || current.charAt(pos)=='\t')){
                    pos++;
                }
                if(pos!=current.length()) 
                    break;
            }
            while(pos<current.length() && current.charAt(pos)!=' ' && current.charAt(pos)!='\n' && current.charAt(pos)!='\t'){
                ret += current.charAt(pos);
                pos ++;
            }
        }catch(IOException e){
            ret = null;
        }
        return Integer.parseInt(ret);
    }
}
public class Solution implements Runnable {
    @Override
    public void run() {
        new TigerITDatabaseDilemma();
    }
}
