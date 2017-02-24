import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class apl7 {
	//Max recursion limit is 8491
	public static int RECURSION_LIMIT = 8000;
	public static void main(String[] args) throws IOException {
		//Reads the stop words into the string
		String stopwords = (new Scanner(new FileReader("stop_words.txt")).next());
		Scanner sc = new Scanner(new FileReader("pride-and-prejudice.txt"));
		sc.useDelimiter("\\Z");
		//This creates a list of words by replacing no alphanumeric characters by a space and splitting them by space
		List <String> words = new LinkedList<String>(Arrays.asList((sc.next()).replaceAll("[^A-Za-z0-9]", " ").split(" ")));
		//Hashmap for storing words and frequencies
		HashMap<String, Integer> hm = new HashMap<>();
		//Function to count the words and frequencies
		count(words,stopwords,hm);
		//Sorting of hash map according to the frequencies 
		List<Map.Entry<String, Integer>> word_freq_sorted = new ArrayList<Map.Entry<String, Integer>>(hm.entrySet());
        Collections.sort(word_freq_sorted, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        //Print 25 most frequent words
        print(word_freq_sorted,25);
		
	}
	static void count_words(List<String> words, String sw, HashMap<String, Integer> hm,int cnt) {
		//End of recursion 
		if(cnt==0)
			return;
		//If its a stop word or length is less than 2
		if(sw.contains(","+words.get(0).trim().toLowerCase()) || words.get(0).trim().toLowerCase().length()<2) {
			words.remove(0);
		}
		// Word already present in the hashmap
		else if(hm.containsKey(words.get(0).trim().toLowerCase()) ) {
			hm.put(words.get(0).trim().toLowerCase(), hm.get(words.get(0).trim().toLowerCase())+1);
			words.remove(0);
		}
		//New word enter in hashmap
		else {
			hm.put(words.get(0).trim().toLowerCase(),1);
			words.remove(0);
		}
		cnt=cnt-1; 
		//Recursive call to count_words
		count_words(words,sw,hm,cnt);
	}
	static void count(List<String> words, String sw, HashMap<String, Integer> hm){
		//If size is greater than recursion limit , only process words upto recursion limit
		if(words.size()>RECURSION_LIMIT){
			count_words(words,sw,hm,RECURSION_LIMIT);
			count(words,sw,hm);
		}
		//else process remaining words
		else {
			count_words(words,sw,hm,words.size());
		}
	}
	static void print(List<Map.Entry<String, Integer>> wf_sorted,int cnt) {
		if(cnt==0)
			return;
		Map.Entry<String, Integer> entry = wf_sorted.get(0);
		System.out.println(entry.getKey() + " - " + entry.getValue());
		wf_sorted.remove(0);
		cnt= cnt-1;
		//Recursive call to print 
		print(wf_sorted,cnt);
	}
}
