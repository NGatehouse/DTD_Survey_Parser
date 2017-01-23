//Nicholas Scott Gatehouse /1/20/2017
//This program is to be used by the Director of Academic Affairs
//for the Fraternity Delta Tau Delta

import java.lang.*;
import java.util.*;

public class Brother{
	public String name;
	public float cumulative_gpa;
	public float quarterly_gpa;
	public String[] class_schedule = new String[6];
	public int my_tier; // better way to track? feels unsafe.. need defaults for all?

	public Brother(String name, float quarterly_gpa, float cumulative_gpa, String[] class_schedule){
		this.name = name;
		this.cumulative_gpa = cumulative_gpa;
		this.quarterly_gpa = quarterly_gpa;
		this.class_schedule = class_schedule;
		my_tier = 0;	
	}	
}
	