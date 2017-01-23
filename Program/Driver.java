//Nicholas Scott Gatehouse /1/20/2017
//This program is to be used by the Director of Academic Affairs
//for the Fraternity Delta Tau Delta

import java.io.*;
import java.util.Scanner;

public class Driver{

    public static void main(String[] args) {
    	Utility runner = new Utility();
        try {
            System.out.print("Enter the file name with extension : ");

            Scanner input = new Scanner(System.in);

            File file = new File(input.nextLine());
            
            input = new Scanner(file);            
            
            runner.create_brother_list(input);

            input.close();

        } catch (FileNotFoundException exception) {
            System.out.println("File was not found, make sure you add the extension\nQuitting");
            System.exit(0);
        }
        System.out.println("Choose one of the following operations: )");
		System.out.println("-	print probation list (Enter the letter 'p')");
		System.out.println("-	print deans list (enter the letter 'd')");
		System.out.println("-	print class matches (enter the letter 'c')");
		System.out.println("-	check roster (enter the letter 'r')");
		System.out.println("-	average gpa (enter the letter 'a')");
		System.out.println("-	quit (enter the letter 'q')");
		
		
		Scanner input = new Scanner(System.in);
		String answer;
		boolean cont = true;

		while(cont) {
			System.out.println("Enter a Menu Choice: (p,d,c,r,a,q)");
			answer = input.nextLine();
			switch (answer){
				case "p": 
					runner.print_ap();
					break;

				case "d":
					runner.print_dl();
					break;

				case "c":
					runner.print_classmatches();
					break;

				case "r":
					try {
			            System.out.print("Enter the Roster file.txt: ");

			            Scanner input2 = new Scanner(System.in);

			            File roster = new File(input2.nextLine());

			            input2 = new Scanner(roster);
			            
			            runner.roster_check(input2);

			            input2.close();

			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
					break;

				case "a":
					runner.average_gpa();
					break;

				case "q":
					cont = false;
					System.out.println("Quitting.");
					break;
					
				default:
					System.out.println("Invalid Choice.");
					break;
			}
		}
    }


}
