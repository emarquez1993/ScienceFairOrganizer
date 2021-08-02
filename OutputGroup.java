// Eric Marquez and Nick Keele
// COSC 3420.001
// Project #5
// Due date: 4/25/2018
/*
 * This class takes two .txt files from the user (a judge list and a project list)
 * and utilizes data structures to write to a third file. The third file will contain
 * a list of judges and the projects that they are assigned to judge in groups of three judges.
 */
import java.util.*;
import java.io.*;
public class OutputGroup {
	
	//LinkedLists to store the projects into categories.
	public static LinkedList<String> bsProjects = new LinkedList<String>();
	public static LinkedList<String> chemProjects = new LinkedList<String>();
	public static LinkedList<String> earthSpaceProjects = new LinkedList<String>();
	public static LinkedList<String> environmentalProjects = new LinkedList<String>();
	public static LinkedList<String> lifeProjects = new LinkedList<String>();
	public static LinkedList<String> mathProjects = new LinkedList<String>();
	
	//ArrayLists to store the judges into categories.
	public static ArrayList<String> allJudges = new ArrayList<String>();
	public static ArrayList<String> bsJudges = new ArrayList<String>();
	public static ArrayList<String> chemJudges = new ArrayList<String>();
	public static ArrayList<String> earthSpaceJudges = new ArrayList<String>();
	public static ArrayList<String> environmentalJudges = new ArrayList<String>();
	public static ArrayList<String> lifeJudges = new ArrayList<String>();
	public static ArrayList<String> mathJudges = new ArrayList<String>();
	
	//Int array to decide the order in which the judges are assigned.
	public static int[] projectSort = new int[6]; 
	
	//Array list to store the contents of the output file.
	public static ArrayList<String> groupAssignment = new ArrayList<String>(); 
	
	/*
	 * This method utilizes a BufferedReader object to write the users projects line by line
	 * into their respective category.
	 */
	public static void projectsFile(String fileName)
	{
		try {
			BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
			String line = inputStream.readLine();
			line = line.trim();
			while (line != null)
			{
				if(line.contains("Behavioral/Social"))
					bsProjects.add(line.substring(0, line.indexOf(' ')));
				else if(line.contains("Chemistry"))
					chemProjects.add(line.substring(0, line.indexOf(' ')));
				else if(line.contains("Earth"))
					earthSpaceProjects.add(line.substring(0, line.indexOf(' ')));
				else if(line.contains("Environmental"))
					environmentalProjects.add(line.substring(0, line.indexOf(' ')));
				else if(line.contains("Life") || line.contains("Biology"))
					lifeProjects.add(line.substring(0, line.indexOf(' ')));
				else if(line.contains("Mathematics/Physics"))
					mathProjects.add(line.substring(0, line.indexOf(' ')));
				line = inputStream.readLine();
				if(line != null)
					line = line.trim();
			}
			inputStream.close();
		}
		
		catch(FileNotFoundException e)
		{
			System.exit(0);
		}
		
		catch(IOException e)
		{
			System.exit(0);
		}
	}
	
	/*
	 * This method utilizes a BufferedReader object to write the judge file contents into a single ArrayList.
	 * After this, the method will sort the contents of that ArrayList into separate ArrayLists representing 
	 * the different academic subjects. When the judges are sorted, they will be concatenated with two integers.
	 * The number concatenated at the beginning of the judge's name represents the number of projects they have judged (default 0).
	 * The number concatenated at the end of the judge's name represents the number of subjects the judge is willing to judge.
	 */
	public static void judgesFile(String fileName)
	{
		try 
		{
			BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
			String line = inputStream.readLine();
			line = line.trim();
			while (line != null)
			{
				if(line.contains(":"))
					allJudges.add(line);
				else
					allJudges.set(allJudges.size() - 1, allJudges.get(allJudges.size() - 1) + " " + line);
				line = inputStream.readLine();
				if(line != null)
					line = line.trim();
			}
			inputStream.close();
			for(int k = 0; k < allJudges.size(); k++)
			{
				line = allJudges.get(k);
				int subjectCount = 1;
				char[] charArray = line.toCharArray();
				for(int n = 0; n < charArray.length; n++)
					if(charArray[n] == ',')
						subjectCount++;
				if(line.contains("Behavioral/Social"))
					bsJudges.add(0 + line.substring(0, line.indexOf(':')) + subjectCount);
				if(line.contains("Chemistry"))
					chemJudges.add(0 + line.substring(0, line.indexOf(':')) + subjectCount);
				if(line.contains("Earth"))
					earthSpaceJudges.add(0 + line.substring(0, line.indexOf(':')) + subjectCount);
				if(line.contains("Environmental"))
					environmentalJudges.add(0 + line.substring(0, line.indexOf(':')) + subjectCount);
				if(line.contains("Life") || line.contains("Biology"))
					lifeJudges.add(0 + line.substring(0, line.indexOf(':')) + subjectCount);
				if(line.contains("Mathematics/Physics"))
					mathJudges.add(0 + line.substring(0, line.indexOf(':')) + subjectCount);
			}
		}
		
		catch(FileNotFoundException e)
		{
			System.exit(0);
		}
		
		catch(IOException e)
		{
			System.exit(0);
		}
	}
	
	/*
	 * This method utilizes the other methods of this class to write to the output file's ArrayList 
	 * and essentially assign the judges to their respective projects. No judge can judge more than 
	 * six projects. The order of the subjects in which the judges are assigned is determined by an
	 * array that represents the amount of judges in each category. The category with the least amount
	 * judges will be assigned first.
	 */
	public static void assignJudges()
	{
		sortArray();
		String judges;
		String judge;
		String projects;
		int count;
		int groupSize;
		int bsJudge = bsJudges.size();
		int chemJudge = chemJudges.size();
		int earthSpaceJudge = earthSpaceJudges.size();
		int environmentalJudge = environmentalJudges.size();
		int lifeJudge = lifeJudges.size();
		int mathJudge = mathJudges.size();
		int numOfJudged;
		boolean bs = true;
		boolean chem = true;
		boolean earthSpace = true;
		boolean environmental = true;
		boolean life = true;
		boolean math = true;
		for(int i = 0; i < 6; i++)
		{
			if(bs && projectSort[i] == bsJudge)
			{
				count = 1;
				judge = null;
				judges = null;
				projects = null;
				while(!bsProjects.isEmpty())
				{
					if(bsProjects.size() <= 6)
						groupSize = bsProjects.size();
					else if(bsProjects.size() % 6 == 0)
						groupSize = 6;
					else if(bsProjects.size() % 5 == 0)
						groupSize = 5;
					else if(bsProjects.size() % 4 == 0)
						groupSize = 4;
					else
						groupSize = 6;
					for(int j = 0; j < 3; j++)
					{
						for(int x = 1; x < 7; x++)
						{
							for(int u = 0; u < bsJudges.size(); u++)
							{
								if(Character.getNumericValue(bsJudges.get(u).charAt(bsJudges.get(u).length() - 1)) == x)
								{
									numOfJudged = groupSize + Character.getNumericValue(bsJudges.get(u).charAt(0));
									if(numOfJudged <= 6)
									{
										x = 7;
										judge = bsJudges.get(u).substring(1, bsJudges.get(u).length() - 1);
										if(numOfJudged == 6)
											removeJudge(judge);
										else
										{
											setJudge(numOfJudged + bsJudges.get(u).substring(1), judge);
											removeBS(judge);
										}
										break;
									}
								}
							}
						}
						if(j == 0)
							judges = judge;
						else
							judges = judges + ", " + judge;
					}
					for(int m = 0; m < groupSize; m++)
					{
						if(m == 0)
						{
							projects = bsProjects.getFirst();
							bsProjects.removeFirst();
						}
						else
						{
							projects = projects + ", " + bsProjects.getFirst();
							bsProjects.removeFirst();
						}
					}
					groupAssignment.add("Behavioral/Social Science_" + count + " " + judges);
					groupAssignment.add("Projects: " + projects);
					groupAssignment.add(" ");
					count++;
				}
				bs = false;
			}
			
			if(chem && projectSort[i] == chemJudge)
			{
				count = 1;
				judge = null;
				judges = null;
				projects = null;
				while(!chemProjects.isEmpty())
				{
					if(chemProjects.size() <= 6)
						groupSize = chemProjects.size();
					else if(chemProjects.size() % 6 == 0)
						groupSize = 6;
					else if(chemProjects.size() % 5 == 0)
						groupSize = 5;
					else if(chemProjects.size() % 4 == 0)
						groupSize = 4;
					else
						groupSize = 6;
					for(int j = 0; j < 3; j++)
					{
						for(int x = 1; x < 7; x++)
						{
							for(int u = 0; u < chemJudges.size(); u++)
							{
								if(Character.getNumericValue(chemJudges.get(u).charAt(chemJudges.get(u).length() - 1)) == x)
								{
									numOfJudged = groupSize + Character.getNumericValue(chemJudges.get(u).charAt(0));
									if(numOfJudged <= 6)
									{
										x = 7;
										judge = chemJudges.get(u).substring(1, chemJudges.get(u).length() - 1);
										if(numOfJudged == 6)
											removeJudge(judge);
										else
										{
											setJudge(numOfJudged + chemJudges.get(u).substring(1), judge);
											removeChem(judge);
										}
										break;
									}
								}
							}
						}
						if(j == 0)
							judges = judge;
						else
							judges = judges + ", " + judge;
					}
					for(int m = 0; m < groupSize; m++)
					{
						if(m == 0)
						{
							projects = chemProjects.getFirst();
							chemProjects.removeFirst();
						}
						else
						{
							projects = projects + ", " + chemProjects.getFirst();
							chemProjects.removeFirst();
						}
					}
					groupAssignment.add("Chemistry_" + count + " " + judges);
					groupAssignment.add("Projects: " + projects);
					groupAssignment.add(" ");
					count++;
				}
				chem = false;
			}
			
			if(earthSpace && projectSort[i] == earthSpaceJudge)
			{
				count = 1;
				judge = null;
				judges = null;
				projects = null;
				while(!earthSpaceProjects.isEmpty())
				{
					if(earthSpaceProjects.size() <= 6)
						groupSize = earthSpaceProjects.size();
					else if(earthSpaceProjects.size() % 6 == 0)
						groupSize = 6;
					else if(earthSpaceProjects.size() % 5 == 0)
						groupSize = 5;
					else if(earthSpaceProjects.size() % 4 == 0)
						groupSize = 4;
					else
						groupSize = 6;
					for(int j = 0; j < 3; j++)
					{
						for(int x = 1; x < 7; x++)
						{
							for(int u = 0; u < earthSpaceJudges.size(); u++)
							{
								if(Character.getNumericValue(earthSpaceJudges.get(u).charAt(earthSpaceJudges.get(u).length() - 1)) == x)
								{
									numOfJudged = groupSize + Character.getNumericValue(earthSpaceJudges.get(u).charAt(0));
									if(numOfJudged <= 6)
									{
										x = 7;
										judge = earthSpaceJudges.get(u).substring(1, earthSpaceJudges.get(u).length() - 1);
										if(numOfJudged == 6)
											removeJudge(judge);
										else
										{
											setJudge(numOfJudged + earthSpaceJudges.get(u).substring(1), judge);
											removeEarthSpace(judge);
										}
										break;
									}
								}
							}
						}
						if(j == 0)
							judges = judge;
						else
							judges = judges + ", " + judge;
					}
					for(int m = 0; m < groupSize; m++)
					{
						if(m == 0)
						{
							projects = earthSpaceProjects.getFirst();
							earthSpaceProjects.removeFirst();
						}
						else
						{
							projects = projects + ", " + earthSpaceProjects.getFirst();
							earthSpaceProjects.removeFirst();
						}
					}
					groupAssignment.add("Earth/Space Science_" + count + " " + judges);
					groupAssignment.add("Projects: " + projects);
					groupAssignment.add(" ");
					count++;
				}
				earthSpace = false;
			}
			
			if(environmental && projectSort[i] == environmentalJudge)
			{
				count = 1;
				judge = null;
				judges = null;
				projects = null;
				while(!environmentalProjects.isEmpty())
				{
					if(environmentalProjects.size() <= 6)
						groupSize = environmentalProjects.size();
					else if(environmentalProjects.size() % 6 == 0)
						groupSize = 6;
					else if(environmentalProjects.size() % 5 == 0)
						groupSize = 5;
					else if(environmentalProjects.size() % 4 == 0)
						groupSize = 4;
					else
						groupSize = 6;
					for(int j = 0; j < 3; j++)
					{
						for(int x = 1; x < 7; x++)
						{
							for(int u = 0; u < environmentalJudges.size(); u++)
							{
								if(Character.getNumericValue(environmentalJudges.get(u).charAt(environmentalJudges.get(u).length() - 1)) == x)
								{
									numOfJudged = groupSize + Character.getNumericValue(environmentalJudges.get(u).charAt(0));
									if(numOfJudged <= 6)
									{
										x = 7;
										judge = environmentalJudges.get(u).substring(1, environmentalJudges.get(u).length() - 1);
										if(numOfJudged == 6)
											removeJudge(judge);
										else
										{
											setJudge(numOfJudged + environmentalJudges.get(u).substring(1), judge);
											removeEnvironmental(judge);
										}
										break;
									}
								}
							}
						}
						if(j == 0)
							judges = judge;
						else
							judges = judges + ", " + judge;
					}
					for(int m = 0; m < groupSize; m++)
					{
						if(m == 0)
						{
							projects = environmentalProjects.getFirst();
							environmentalProjects.removeFirst();
						}
						else
						{
							projects = projects + ", " + environmentalProjects.getFirst();
							environmentalProjects.removeFirst();
						}
					}
					groupAssignment.add("Environmental Science_" + count + " " + judges);
					groupAssignment.add("Projects: " + projects);
					groupAssignment.add(" ");
					count++;
				}
				environmental = false;
			}
			
			if(life && projectSort[i] == lifeJudge)
			{
				count = 1;
				judge = null;
				judges = null;
				projects = null;
				while(!lifeProjects.isEmpty())
				{
					if(lifeProjects.size() <= 6)
						groupSize = lifeProjects.size();
					else if(lifeProjects.size() % 6 == 0)
						groupSize = 6;
					else if(lifeProjects.size() % 5 == 0)
						groupSize = 5;
					else if(lifeProjects.size() % 4 == 0)
						groupSize = 4;
					else
						groupSize = 6;
					for(int j = 0; j < 3; j++)
					{
						for(int x = 1; x < 7; x++)
						{
							for(int u = 0; u < lifeJudges.size(); u++)
							{
								if(Character.getNumericValue(lifeJudges.get(u).charAt(lifeJudges.get(u).length() - 1)) == x)
								{
									numOfJudged = groupSize + Character.getNumericValue(lifeJudges.get(u).charAt(0));
									if(numOfJudged <= 6)
									{
										x = 7;
										judge = lifeJudges.get(u).substring(1, lifeJudges.get(u).length() - 1);
										if(numOfJudged == 6)
											removeJudge(judge);
										else
										{
											setJudge(numOfJudged + lifeJudges.get(u).substring(1), judge);
											removeLife(judge);
										}
										break;
									}
								}
							}
						}
						if(j == 0)
							judges = judge;
						else
							judges = judges + ", " + judge;
					}
					for(int m = 0; m < groupSize; m++)
					{
						if(m == 0)
						{
							projects = lifeProjects.getFirst();
							lifeProjects.removeFirst();
						}
						else
						{
							projects = projects + ", " + lifeProjects.getFirst();
							lifeProjects.removeFirst();
						}
					}
					groupAssignment.add("Life Science_" + count + " " + judges);
					groupAssignment.add("Projects: " + projects);
					groupAssignment.add(" ");
					count++;
				}
				life = false;
			}
			
			if(math && projectSort[i] == mathJudge)
			{
				count = 1;
				judge = null;
				judges = null;
				projects = null;
				while(!mathProjects.isEmpty())
				{
					if(mathProjects.size() <= 6)
						groupSize = mathProjects.size();
					else if(mathProjects.size() % 6 == 0)
						groupSize = 6;
					else if(mathProjects.size() % 5 == 0)
						groupSize = 5;
					else if(mathProjects.size() % 4 == 0)
						groupSize = 4;
					else
						groupSize = 6;
					for(int j = 0; j < 3; j++)
					{
						for(int x = 1; x < 7; x++)
						{
							for(int u = 0; u < mathJudges.size(); u++)
							{
								if(Character.getNumericValue(mathJudges.get(u).charAt(mathJudges.get(u).length() - 1)) == x)
								{
									numOfJudged = groupSize + Character.getNumericValue(mathJudges.get(u).charAt(0));
									if(numOfJudged <= 6)
									{
										x = 7;
										judge = mathJudges.get(u).substring(1, mathJudges.get(u).length() - 1);
										if(numOfJudged == 6)
											removeJudge(judge);
										else
										{
											setJudge(numOfJudged + mathJudges.get(u).substring(1), judge);
											removeMath(judge);
										}
										break;
									}
								}
							}
						}
						if(j == 0)
							judges = judge;
						else
							judges = judges + ", " + judge;
					}
					for(int m = 0; m < groupSize; m++)
					{
						if(m == 0)
						{
							projects = mathProjects.getFirst();
							mathProjects.removeFirst();
						}
						else
						{
							projects = projects + ", " + mathProjects.getFirst();
							mathProjects.removeFirst();
						}
					}
					groupAssignment.add("Mathematics/Physics_" + count + " " + judges);
					groupAssignment.add("Projects: " + projects);
					groupAssignment.add(" ");
					count++;
				}
				math = false;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element
	 * from the bsJudges ArrayList that contains that parameter.
	 */
	public static void removeBS(String judge)
	{
		for(int n = 0; n < bsJudges.size(); n++)
		{
			if(bsJudges.get(n).contains(judge))
			{
				bsJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element 
	 * from the chemJudges ArrayList that contains that parameter.
	 */
	public static void removeChem(String judge)
	{
		for(int n = 0; n < chemJudges.size(); n++)
		{
			if(chemJudges.get(n).contains(judge))
			{
				chemJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element
	 * from the earthSpaceJudges ArrayList that contains that parameter.
	 */
	public static void removeEarthSpace(String judge)
	{
		for(int n = 0; n < earthSpaceJudges.size(); n++)
		{
			if(earthSpaceJudges.get(n).contains(judge))
			{
				earthSpaceJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element
	 * from the environmentalJudges ArrayList that contains that parameter.
	 */
	public static void removeEnvironmental(String judge)
	{
		for(int n = 0; n < environmentalJudges.size(); n++)
		{
			if(environmentalJudges.get(n).contains(judge))
			{
				environmentalJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element
	 * from the lifeJudges ArrayList that contains that parameter.
	 */
	public static void removeLife(String judge)
	{
		for(int n = 0; n < lifeJudges.size(); n++)
		{
			if(lifeJudges.get(n).contains(judge))
			{
				lifeJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element
	 * from the mathJudges ArrayList that contains that parameter.
	 */
	public static void removeMath(String judge)
	{
		for(int n = 0; n < mathJudges.size(); n++)
		{
			if(mathJudges.get(n).contains(judge))
			{
				mathJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method takes one String parameter and removes any element
	 * from all judge ArrayLists that contain the passed parameter.  
	 */
	public static void removeJudge(String judge)
	{
		int n;
		for(n = 0; n < bsJudges.size(); n++)
		{
			if(bsJudges.get(n).contains(judge))
			{
				bsJudges.remove(n);
				n--;
			}
		}
		for(n = 0; n < chemJudges.size(); n++)
		{
			if(chemJudges.get(n).contains(judge))
			{
				chemJudges.remove(n);
				n--;
			}
		}
		for(n = 0; n < earthSpaceJudges.size(); n++)
		{
			if(earthSpaceJudges.get(n).contains(judge))
			{
				earthSpaceJudges.remove(n);
				n--;
			}
		}
		for(n = 0; n < environmentalJudges.size(); n++)
		{
			if(environmentalJudges.get(n).contains(judge))
			{
				environmentalJudges.remove(n);
				n--;
			}
		}
		for(n = 0; n < lifeJudges.size(); n++)
		{
			if(lifeJudges.get(n).contains(judge))
			{
				lifeJudges.remove(n);
				n--;
			}
		}
		for(n = 0; n < mathJudges.size(); n++)
		{
			if(mathJudges.get(n).contains(judge))
			{
				mathJudges.remove(n);
				n--;
			}
		}
	}
	
	/*
	 * This method goes through all judge collections and replaces the 
	 * elements that contain the specified judgeName with the replacement String.
	 */
	public static void setJudge(String replacement, String judgeName)
	{
		for(int n = 0; n < bsJudges.size(); n++)
			if(bsJudges.get(n).contains(judgeName))
				bsJudges.set(n, replacement);
		for(int n = 0; n < chemJudges.size(); n++)
			if(chemJudges.get(n).contains(judgeName))
				chemJudges.set(n, replacement);
		for(int n = 0; n < earthSpaceJudges.size(); n++)
			if(earthSpaceJudges.get(n).contains(judgeName))
				earthSpaceJudges.set(n, replacement);
		for(int n = 0; n < environmentalJudges.size(); n++)
			if(environmentalJudges.get(n).contains(judgeName))
				environmentalJudges.set(n, replacement);
		for(int n = 0; n < lifeJudges.size(); n++)
			if(lifeJudges.get(n).contains(judgeName))
				lifeJudges.set(n, replacement);
		for(int n = 0; n < mathJudges.size(); n++)
			if(mathJudges.get(n).contains(judgeName))
				mathJudges.set(n, replacement);
	}
	
	/*
	 * This method sets contents of the projectSort int array to the judge collection 
	 * sizes and utilizes an insertionSort to sort the array.
	 */
	public static void sortArray()
	{
		projectSort[0] = bsJudges.size();
		projectSort[1] = chemJudges.size();
		projectSort[2] = earthSpaceJudges.size();
		projectSort[3] = environmentalJudges.size();
		projectSort[4] = lifeJudges.size();
		projectSort[5] = mathJudges.size();
		insertionSort(projectSort);
	}
	
	/*
	 * This method utilizes a PrintWriter object to write the contents of the
	 * groupAssignment ArrayList to the users specified output file.
	 */
	public static void writeToFile(String outputFileName)
	{
		try {
			PrintWriter outputStream = new PrintWriter(new FileOutputStream(outputFileName));
			for(int i = 0; i < groupAssignment.size(); i++)
			{
				outputStream.println(groupAssignment.get(i));
			}
			outputStream.close();
		} catch (FileNotFoundException e) {
			System.exit(0);
		}
	}
	
	/*
	 * Simple sort. Best used on arrays with a size less than or equal to 
	 * seven. In this classes case the array size is six.
	 */
	public static void insertionSort(int[] x)
	{
		for(int i = 1; i < x.length; i++)
			for(int k = i; k > 0 && x[k - 1] > x[k]; k--)
				swap(x, k, k - 1);
	}
	
	/*
	 * This method swaps two specified indexes of an array. Utilized by the
	 * Insertion sort method.
	 */
	public static void swap(int[] x, int a, int b)
	{
		int t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
}
