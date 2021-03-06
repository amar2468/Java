/***********************************
*File Processing: This is the file processing class. This class allows the user to find which documents contain the word they typed
*		  by opening the file and reading it. 
*Author: Amar Plakalo
*Date:17/04/2021
***********************************/


package com.javaapp.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Scanner;



public class FileProcessing 
{
	private ArrayList <String> files;
	private File[] documentsNeeded;
	private Map<File, Integer> numberOfOccurences = new HashMap<File, Integer>();
	private int onlyOnce;
	
	public FileProcessing(ArrayList<String> files)
	{
		this.setFiles(files);
		this.setNumberOfOccurences(numberOfOccurences);
		getFiles();
		getNumberOfOccurences();
	}
	
	public void openFile()
	{
		// The files.length calculates the length of the array so the program nows how many files are being used
		documentsNeeded = new File[files.size()];
		
		// Loops around for the length of the file and creates new file objects in the array
		for(int i = 0; i < files.size(); i++)
		{
			documentsNeeded[i] = new File(files.get(i));
		}
	}
	
	public Map<File, Integer> readFile(String [] wordsThatWereEntered)
	{
		// wordInFile string is used to store the line in the file so it can be checked against the search term/terms
		String wordInFile = "";
	
		
		// Counter counts the amount of occurrences of a word in a file
		int counter = 0;
		
		int countMultipleWords = 0;
		
		onlyOnce = 0;

		int checkfor = 0;
		
		try
		{
			for(int m = 0; m < files.size(); m++)
			{
				// Scanner is created and it scans the file in the m index position of the files
				
				Scanner searchForWord = new Scanner(documentsNeeded[m],"UTF-8");

				
				checkfor = 0;
			
				// If the length of the word is greater than 1
				if (wordsThatWereEntered.length > 1)
				{
						
					// while the file has another word i.e. while the file is still not fully read
						
					while(searchForWord.hasNext())
					{
						wordInFile = searchForWord.next(); // store the next word inside the variable
						
						
						wordInFile = wordInFile.replaceAll("\\p{Punct}", ""); // remove punctuation from that word
						
						countMultipleWords = 0;
						
						// For loop runs for the amount of words that were entered
						
						for(int i = 0; i < wordsThatWereEntered.length; i++)
						{
								
							wordsThatWereEntered[i] = wordsThatWereEntered[i].replaceAll("\\p{Punct}", ""); // remove punctuation from the word entered
							
							if(wordsThatWereEntered.length == countMultipleWords)
							{
								checkfor++;
								countMultipleWords = 0;
							}
							
							
							// if it is equal to the current word in the file
							
							
							else if (wordInFile.equals(wordsThatWereEntered[i]))
							{
	
								countMultipleWords ++; // increment the counter
								
								
								if(wordsThatWereEntered.length == countMultipleWords)
								{
									checkfor++;
									countMultipleWords = 0;
								}
								
								else if(searchForWord.hasNext())
								{
								
									wordInFile = searchForWord.next(); // store the next word inside the variable
								
								
									wordInFile = wordInFile.replaceAll("\\p{Punct}", ""); // remove punctuation from that word
									
								}
							
					
				
							}
							else
							{
								countMultipleWords = 0;
								
							}
							
						}
						
						
							
						
					}
					if(checkfor > 0)
					{
						numberOfOccurences.put(documentsNeeded[m], checkfor);
					}
					else
					{
						numberOfOccurences.put(documentsNeeded[m], 0);
					}
					searchForWord.close(); // close scanner so a new file can be read (if there are new files)
				
				}
					
				// if only one word was entered into the text field
				else if(wordsThatWereEntered.length == 1)
				{
					while(searchForWord.hasNext())
					{
						wordInFile = searchForWord.next(); // store the next word inside the variable
							
						
						wordInFile = wordInFile.replaceAll("\\p{Punct}", ""); // remove punctuation from that word
					

						wordsThatWereEntered[0] = wordsThatWereEntered[0].replaceAll("\\p{Punct}", "");
						if (wordInFile.equals(wordsThatWereEntered[0])) 
						// if it is equal to the current word 
						// in the file
						{	
							counter ++; // Increment the counter
							onlyOnce = 1;
							
							
						}

					}
					searchForWord.close();
					numberOfOccurences.put(documentsNeeded[m], counter);
				}
	
					
	
				countMultipleWords = 0;
				counter = 0; // set counter to 0 so it can count new files
	

			}
		}
		catch(FileNotFoundException e) // if the file is not found
		{
			System.out.println("File not found: "); // print message
			e.printStackTrace(); // used to handle exceptions in java
		}
		return numberOfOccurences;
		
	}
	
	public String calculatePercentage(Entry<File, Integer> entry, String[] userEntersWords)
	{
		float percentage = 0;
		String formatPercentage = "";
		int amountOfWords = 0;
		
		try
		{
			Scanner s2 = new Scanner(entry.getKey(),"UTF-8");
			
			while(s2.hasNext())
			{
				s2.next();
				amountOfWords++;
			}
			s2.close();
		}
		catch(FileNotFoundException e) // if the file is not found
		{
			System.out.println("File not found: "); // print message
			e.printStackTrace(); // used to handle exceptions in java
		}
		
		if(onlyOnce == 0)
		{
			if(entry.getValue() > 0)
			{
				percentage = ((float)userEntersWords.length) / amountOfWords * 100;
				formatPercentage = String.format("%.2f", percentage);
			}
			else
			{
				percentage = 0 / amountOfWords * 100;
				formatPercentage = String.format("%.2f", percentage);
			}
			
		}
		else if(onlyOnce == 1)
		{
			percentage = ((float)entry.getValue()) / amountOfWords * 100;
			formatPercentage = String.format("%.2f", percentage);
		}

		return formatPercentage;
	}


	// getter for files
	private ArrayList<String> getFiles() 
	{
		return files;
	}

	// setter for files
	private void setFiles(ArrayList<String> files) 
	{
		this.files = files;
	}
	
	// Getter for number of occurrences

	Map<File, Integer> getNumberOfOccurences() 
	{
		return numberOfOccurences;
	}
	
	// setter for number of occurrences

	void setNumberOfOccurences(Map<File, Integer> numberOfOccurences) 
	{
		this.numberOfOccurences = numberOfOccurences;
	}
}
