import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class apl1 {
	public static void main(String[] args) throws IOException {
		String data[] = new String[9]; //Primary memory
		/* 
		 * data[0] - Stop words stored as a string
		 * data[1] - Each line that is read is stored in data[1]
		 * data[2] - Counter for for loop
		 * data[3] - Word
		 * data[4] - Word,Frequency
		 * data[5] - Word Count
		 * data[6] - Found or Not Found
		 * data[7] - Final String to be written in the file
		 * data[8] - File seek position
		 */
		Scanner readfile = new Scanner(new FileReader("stop_words.txt"));
	    data[0] =  "," + readfile.nextLine(); 
	    readfile.close();
		RandomAccessFile word_freq = new RandomAccessFile("words_freq.txt", "rw");
		readfile = new Scanner(new FileReader(args[0]));
		data[3] = "";
		while(readfile.hasNextLine()) {
			// reads the book line by line
			data[1] = "" + readfile.nextLine();
			// for loop traversing character by character 
			for (data[2] = "0"; Integer.parseInt(data[2]) < data[1].length() + 1; data[2] = ""
					+ (Integer.parseInt(data[2]) + 1)) {
				if ((Integer.parseInt(data[2]) < data[1].length()) 
						&& Character.isLetterOrDigit(data[1].charAt(Integer.parseInt(data[2])))) {
						data[3]+=data[1].charAt(Integer.parseInt(data[2]));
						//appending characters to a word
					}
				// Ignore words with length <2 and stop words
				else if(data[3].trim().length()>=2 && !(data[0].contains(","+data[3].toLowerCase()))){
					data[3] = data[3].toLowerCase().trim();
					word_freq.seek(0);
					data[6] = "0";
					data[8] = "0";
					while(true) {
						data[8] = "" + word_freq.getFilePointer();
						data[4] = word_freq.readLine();
						if(data[4]==null || data[4].equals(""))
							break;
						//if word exists, increment counter by 1
						if(data[4].split(",")[0].equals(data[3])) {
							data[6]="1";
							data[5] = data[4].split(",")[1];
							data[5] = ""+(Integer.parseInt(data[5]) + 1);
							data[5] = Integer.parseInt(data[5]) < 10 ? "00" + data[5]: (Integer.parseInt(data[5]) < 100) ? "0" + data[5] : data[5];
							data[7] = data[4].split(",")[0]+","+data[5];
							break;
						}
					}
					//if word is found write that word to the correct position in the file
					if (data[6].equals("1")) {
						word_freq.seek(Long.parseLong(data[8]));
						word_freq.writeBytes(data[7]);
					}
					// else add the new word at the end of the file
					else {
						word_freq.seek(word_freq.length());
						word_freq.writeBytes(data[3] + ",001\n");
					}
					//reset data[3] for new word
					data[3]="";
				}
				else {
					data[3]="";
				}
			}			
		}
		readfile.close();
		word_freq.close();
		data = null;
		String newd [][] = new String[4][];
		/*
		 * 2D array 
		 * newd[0][0] and newd[0][1] store the read line from word_freq into word and count separately
		 * newd[1][0] stores as a single string in the format pride,003 prejudice,088
		 * newd[2] stores top 25 words
		 * newd[3][0] - flag and newd[3][1] - counter
		 */
		readfile = new Scanner(new FileReader("words_freq.txt"));
		newd[1] = new String[1];
		newd[3] = new String[2];
		newd[1][0] = "";
		while(readfile.hasNextLine()) {
			
		newd[0] = readfile.nextLine().split(",");	
		newd[2] =  newd[1][0].split(" ",25);
		// if first word
		if(newd[1][0].length()==0) {
			newd[1][0] = newd[0][0] + "," + newd[0][1];
			continue;
		}
		// if 25 top words are present and frequency of new word is less than smallest
		if ((newd[2].length == 25)&& (Integer.parseInt(newd[2][24].split(",")[1]) > Integer.parseInt(newd[0][1])))
			continue;
		newd[3][0] = "NOT INSERTED";
		//Insert the word in the correct position
		for(newd[3][1] = "0"; Integer.parseInt(newd[3][1]) < newd[2].length;newd[3][1] = ""
		+ (Integer.parseInt(newd[3][1]) + 1)) {
			if ((Integer.parseInt(newd[2][Integer.parseInt(newd[3][1])].split(",")[1])) <= 
					Integer.parseInt(newd[0][1])) {
				newd[1][0] = newd[1][0].substring(0,
						newd[1][0].indexOf(newd[2][Integer.parseInt(newd[3][1])])) 
						+ newd[0][0] + ","+ newd[0][1] + " " + newd[1][0].
						substring(newd[1][0].indexOf(newd[2][Integer.parseInt(newd[3][1])]));
				newd[3][0] = "INSERTED";
				break;
			}
		}
		/* If the word wasn't inserted, but the total number of words
		 * that we have is less than 25, means its the least frequent so append it to the back
		 * */
		if (newd[3][0].equals("NOT INSERTED") && (newd[2].length < 25))
			newd[1][0] = newd[1][0] + " " + newd[0][0] + "," + newd[0][1];
		//if it was inserted the last element is 26th word and needs to be deleted
		else if (newd[2].length == 25 && newd[3][0].equals("INSERTED"))
			newd[1][0] = newd[1][0].substring(0, newd[1][0].lastIndexOf(" "));
		newd[2] = newd[1][0].split(" ", 25);
		
		}	
		for (newd[3][1] = "0"; Integer.parseInt(newd[3][1]) < newd[2].length; newd[3][1] = ""+ (Integer.parseInt(newd[3][1]) + 1)) 
			System.out.println(newd[2][(Integer.parseInt(newd[3][1]))]);
		readfile.close();
	}
}

