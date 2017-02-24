import java.util.List;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
public class apl4 {
	static List<String> words = new ArrayList<>();
	static Map<String, Integer> word_freq = new HashMap<>();
	static Map<String, Integer> word_freq_sorted = new LinkedHashMap<>();
	public static void main(String[] args) throws IOException {
		
		readfile("pride-and-prejudice.txt");
		removestopwords();
		frequencies();
		sortByValue();
		print();
	}
	static void readfile(String path) throws IOException{
		String word = "";
		FileInputStream f = new FileInputStream(path);
		//formation of words and putting into a words list
		while(f.available() > 0) {
			
			char temp = (char) f.read();
	        if(Character.isLetterOrDigit(temp)) {
	        	word+=temp;
	        	
	        }
	        else if(word.length()>=2) {
	        	words.add(word.toLowerCase());
	        	word ="";
	        }
	        else {
	        	word="";
	        }
		}	
	}
	static void removestopwords() throws IOException {
		Scanner sc = new Scanner(new FileReader("stop_words.txt")); 
		String stopwords = sc.nextLine();
		//scans eachword and removes it if its a stop word
		for (int i=0;i<words.size();) {
			if(stopwords.contains(","+ (CharSequence)words.get(i)+",")) {
				words.remove(i);
			}
			else {
				i+=1;
			}
		}
	}
	static void frequencies() {
		// Checks if word is present in Hashmap; if yes- updates count, else adds it to hashmap
		for(int i = 0;i<words.size();i++) {
			if(word_freq.containsKey(words.get(i)) ) {
				word_freq.put(words.get(i), word_freq.get(words.get(i))+1);
			}
			else {
				word_freq.put(words.get(i),1);
			}
		}
	}
	static void sortByValue() {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(word_freq.entrySet());

        // 2. Sort list with Collections.sort() using Comparator
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        for (Map.Entry<String, Integer> entry : list) {
            word_freq_sorted.put(entry.getKey(), entry.getValue());
        } 
    }
	static void print() {
		int i=0;
		for (String key : word_freq_sorted.keySet()) {
			if(i>=25)
				break;
		    System.out.println(key + " " + word_freq_sorted.get(key));
		    i+=1;
		}

	}
}
