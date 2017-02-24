import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class apl3 {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new FileReader("stop_words.txt"));
		// Using Array List to store string and frequencies
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Integer> counts = new ArrayList<Integer>();
		// A few boolean variables to keep track of found variables 
		boolean found=false,foundst=false;
		//Temporary variables
		String temp1;
		Integer t1;
		//Reading stop words into an array of strings
		String stwrds = sc.nextLine();
		String []sw = stwrds.split(",");
		//Input file
		FileInputStream f = new FileInputStream(args[0]);
		String word = "";
		int index=0;
		while(f.available() > 0) {
			char temp = (char) f.read();
			//append the character if its an alphabet or digit
		    if(Character.isLetterOrDigit(temp)) {
		        word+=temp;
		    }
		    //Goes here if it finds a token
		    else if(word.length()>=2) {
		    	found = false;
		    	foundst = false;
		    	word = converttolowercase(word);
		        //Removal of stop words
		        for(int i=0;i<sw.length;i++) {
		        	if(sw[i].equals(word)) {
		        		foundst= true;
		        		break;
		        	}
		        }
		        //Perform further counting operations only if its not a stopword
		        if(!foundst) {
		        	index=0;
		        	for(int i=0;i<words.size();i++) {
		        		//if word already exists or not
				        if(words.get(i).equals(word)) {
				        	index=counts.get(i);
				        	counts.set(i, index+1);
				        	found = true;
				        	index = i;
				        	break;
				        }
				        index+=1;
				    }
		        	//if it doesnt exist add the word to the end of the array list
				    if(found==false) {
				        words.add(word);
				        counts.add(1);
				    }
				    //else insert it in the correct position
				    else if(words.size() >1){
				        for(int i=index;i>=0;i--) {
				        	if(counts.get(index)> counts.get(i)) {
				        		temp1= words.get(index);
				        		words.set(index, words.get(i));
				        		words.set(i,temp1);
				        		t1= counts.get(index);
				        		counts.set(index, counts.get(i));
				        		counts.set(i,t1);
				        		index=i;	
				        	}
				        }
				    }
				}
		        word="";
		    }
		    else {
		    	word="";
		    }
		}	
		//print the 25 most frequent words
		for(int i =0 ;i<25;i++) {
			System.out.println(words.get(i) + " - "+ counts.get(i));
		}
		
	}
	static String converttolowercase(String word) {
		String temp = "";
			for(int i=0;i<word.length();i++) {
				
			if ((word.charAt(i)) < 91) {
				temp+= (char)( (word.charAt(i) + 32) );
			}
			else {
				temp+= word.charAt(i);
			}
		}
			return temp;
		
	}

}
