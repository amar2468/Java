/***********************************
*Showing Files: This class shows the user the contents of the files they are using
*Author: Amar Plakalo
*Date:17/04/2021
***********************************/

package com.javaapp.test;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class ShowingFiles extends JFrame
{
	private ArrayList<String> fileNamesNeeded;
	private File[] seeFileContents;
	JTextArea[] showingContentsOfFiles;
	JScrollPane[] scrollBar;
	JPanel newPanel;
	
	
	ShowingFiles(String titleOfWindow,ArrayList<String> fileNamesNeeded)
	{
		// Inheriting the title and setting the size of the GUI window
		super(titleOfWindow);
		setSize(1500,1500);
		
		// Setting up the panel with a flow layout
		newPanel = new JPanel(new FlowLayout());
		
		// Calling the setter and passing the file names
		this.setFileNamesNeeded(fileNamesNeeded);
		
		// creating a file object and setting the size of it to be the size of the text file names array list
		seeFileContents = new File[fileNamesNeeded.size()];
		
		// text area object with the same size as above.
		showingContentsOfFiles = new JTextArea[fileNamesNeeded.size()];
		
		// Scroll bar with same size
		
		scrollBar = new JScrollPane[fileNamesNeeded.size()];
	
		// for loop runs for the amount of files chosen
		for(int s = 0; s < fileNamesNeeded.size();s++)
		{
			showingContentsOfFiles[s] = new JTextArea(30,35); // create a text area in that index
			newPanel.add(showingContentsOfFiles[s]); // put the text area on the panel
			scrollBar[s] = new JScrollPane(showingContentsOfFiles[s],JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			newPanel.add(scrollBar[s]);
			
		}
	

		add(newPanel); // add panel to screen
		

		setVisible(true); // makes everything visible on screen
		
		
		
	}
	
	public void openingFilesNeeded() // method which allows user to open the file/files
	{
		for(int k = 0; k < fileNamesNeeded.size(); k++) // loops for the amount of files chosen
		{
			// putting the filenames inside the file
		    // object array list in the specific index
			
			seeFileContents[k] = new File(getFileNamesNeeded().get(k)); 

		}
	}
	

	public void readFileContents() // allows the program to read the files and to present them on screen
	{
		// string variable that will hold the current line which will be appended
		// to the text area.
		String holdingCurrentLine = ""; 
		
		try // attempt this first
		{
			for(int p = 0; p < fileNamesNeeded.size(); p++) // iterates for the amount of files chosen
			{
				Scanner s1 = new Scanner(seeFileContents[p],"UTF-8"); // scanner object scanning file in index p
				
		
				while (s1.hasNextLine()) // scanner scans the next line in the file
				{
					holdingCurrentLine = s1.nextLine(); // put the line inside the string variable
					
					holdingCurrentLine = holdingCurrentLine.replaceAll("[\\p{Punct}&&[^.,]]+", "");
					
					
					// put the current line inside the text area so that the full file is read inside the text area
					showingContentsOfFiles[p].append(holdingCurrentLine + "\n"); 

				
				}
				s1.close(); // close scanner so the program can move on to the next file, if there are more files


			}
		}
		catch (FileNotFoundException e) // if an exception occurred
		{
			System.out.println("File not found: "); // print message
			e.printStackTrace();
		}
	}
	
	// getter for the file names

	private ArrayList<String> getFileNamesNeeded() 
	{
		return fileNamesNeeded;
	}
	
	// setter for the file names

	private void setFileNamesNeeded(ArrayList<String> fileNamesNeeded) 
	{
		this.fileNamesNeeded = fileNamesNeeded;
	}
	

}
