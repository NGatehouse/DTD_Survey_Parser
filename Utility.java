//Nicholas Scott Gatehouse /1/20/2017
//This program is to be used by the Director of Academic Affairs
//for the Fraternity Delta Tau Delta

import java.util.Scanner;
import java.lang.*;
import java.io.*;
import java.util.*;

public class Utility{ 

	private int size = 65; // input the number of people who filled out the survey
	public ArrayList<Brother> brotherList = new ArrayList<Brother>(size);
    public ArrayList<Brother> probationList = new ArrayList<Brother>(size); 
    public ArrayList<Brother> deansList = new ArrayList<Brother>(size);
    public ArrayList<String> distinct_classList = new ArrayList<String>(size*3); 
    HashMap<String,ArrayList<Brother>> matchList = new HashMap<>(size*2);

    // compares the names within the survey to the names in the offical roster of the fraternity
    // this is to check for survey compliance.
    public void roster_check(Scanner roster){
        System.out.println("List of Brothers who failed to meet the deadline:\n");
        boolean submitted_grades = false;
        while(roster.hasNextLine()){
            String lastName = roster.nextLine().toLowerCase();           
            for(int i = 0; i<size; i++){
                String[] fullName =brotherList.get(i).name.split("\\s");
                if(fullName[1].toLowerCase().equals(lastName)){
                    submitted_grades = true;
                }
            }
            if(submitted_grades == false){
                System.out.println("\t" + lastName);
            }
            submitted_grades = false;
        }
        System.out.println();
    }

    // creates a distinct list of all the classes the brothers in the fraternity are taking
    // this is used for initializing the hashmap 
    // brother_info --- one line from the survey.txt contains(first name, last name, quarterly gpa, cumulative gpa, classes)
    // classes --- place holder to concatenate name of class and class number             
    public void find_distinct_classes(String[] brother_info, String[] classes){
        for (int i = 4; i<brother_info.length; i+=2){
                    classes[i-4] = brother_info[i].toLowerCase() + brother_info[i+1];
                    if( !(distinct_classList.contains(classes[i-4])) ){  
                        distinct_classList.add(classes[i-4]);                       
                    }
                }
    }

    //initializes hashmap with keys of classes that point to arrayList of brothers who take those classes
    public void initMap(ArrayList<String> distinctList){
        for(int i = 0; i<distinctList.size(); i++){
            matchList.put(distinctList.get(i), new ArrayList<Brother>(10));
        }
    }

    //maps the brothers to the classes they are taking
    public void find_matching_classes(ArrayList<String> distinctList, ArrayList<Brother> brotherList){ // O(n^2)
        String[] classes;
        for(int i = 0; i < brotherList.size(); i++ ){
            classes = brotherList.get(i).class_schedule;
            for(int j = 0; j<classes.length; j++){
                if(matchList.containsKey(classes[j])){
                    matchList.get(classes[j]).add(brotherList.get(i));
                }                 
            }
        }
    }

    // parses the survey line by line creating a list of every brother and calls the 3 functions above 
	public void create_brother_list(Scanner in){ 
    	String name = "default";
    	Float quarterly = new Float(0.0);
    	Float cumulative = new Float(0.0);
    	String[] classes = new String[12];
    	Brother bro;
    	while (in.hasNextLine()){
            	String[] brother_info = in.nextLine().split("\\s"); 
                name = brother_info[0] + " " + brother_info[1];
                try {
                    quarterly = Float.parseFloat(brother_info[2]); 
                    cumulative = Float.parseFloat(brother_info[3]); 
                } catch (NumberFormatException e) {
                    System.err.println("NumberFormatException: String did not contain Float value");
                }            	
                find_distinct_classes(brother_info,classes);                
            	bro = new Brother(name,quarterly,cumulative,classes);
                classes = new String[12];
                brotherList.add(bro); 
                if(cumulative < 2.7 || quarterly < 2.5){
                    probationList.add(bro);
                }
                else if(quarterly >= 3.5){
                    deansList.add(bro);
                }            	
        }
        initMap(distinct_classList);
        find_matching_classes(distinct_classList,brotherList);
        deansList.trimToSize();
        probationList.trimToSize();
    }

    //prints brothers who are on academic probation
    public void print_ap(){
        if(probationList.isEmpty()){
            return ;
        }else{
            System.out.println("Brothers who are now on Academic Probation: \n");
            for(int i = 0; i < probationList.size(); i++){
                System.out.println(probationList.get(i).name + " Quarterly: " + probationList.get(i).quarterly_gpa + " Cumulative: " + probationList.get(i).cumulative_gpa);
            }
            System.out.println();
        }
    }

    //prints brothers on the deans list --- want a way to test gpa improvements aswell
    public void print_dl(){
        if(deansList.isEmpty()){
            return ;
        }else{
            System.out.println("Brothers who recieved above a 3.5 GPA last quarter: \n");
            for(int i = 0; i < deansList.size(); i++){
                System.out.println(deansList.get(i).name);
            }
            System.out.println();
        }
    }

    // prints out brothers who are in the same class, focuses on 2 or more.
    public void print_classmatches(){
        if( !(matchList.isEmpty()) ){

           String[] keyArray = matchList.keySet().toArray(new String[0]);
           int len = keyArray.length;
           ArrayList<Brother> matchedBros;

           for(int i =0; i<len;i++){
               matchedBros=matchList.get(keyArray[i]);

               if(matchedBros.size()>1){
                    System.out.println(keyArray[i] + ":");
                    for(int j = 0;j<matchedBros.size();j++){
                        System.out.println("\t" + matchedBros.get(j).name);
                    }
               }               
           }
        }
    }

    //calculates and prints the average GPA of the fraternity.
    public void average_gpa(){
    	double cumulative_sum = 0.0;
    	double quarterly_sum = 0.0;
    	for(int i = 0; i<size; i++){
    		cumulative_sum += brotherList.get(i).cumulative_gpa;
    		quarterly_sum += brotherList.get(i).quarterly_gpa;
    	}
    	double averageC = cumulative_sum/size;
    	double averageQ = quarterly_sum/size;
    	System.out.println("Average Cumulative GPA: " + averageC + "\n" + "Average Quarterly GPA: " + averageQ);
    }
}
