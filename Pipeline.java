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
public class apl5 {
	public static void main(String[] args) throws IOException {
		print(sortByValue(frequencies(removestopwords(readfile(args[0],args[1])))));
	}
	static List<String> readfile(String path, String sw) throws IOException{
		List<String> words = new ArrayList<>();
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
		Scanner sc = new Scanner(new FileReader("stop_words.txt")); 
		String stopwords = sc.nextLine();
		words.add("#stop_words#");
		String []stop_words = stopwords.split(",");
		for(int i =0;i<stop_words.length;i++) {
			words.add(stop_words[i]);
		}
		return words;
	}
	static List<String> removestopwords(List<String> words) {
		Boolean flag = false;
		List<String> sw = new ArrayList<>();
		List<String> finalwordlist = new ArrayList<>();
		//Takes stopwords and puts in a different list of stopwords only
		for (int i=0;i<words.size();i++) {
			if(words.get(i)=="#stop_words#") {
				flag= true;
			}
			if(flag==true) {
				sw.add(words.get(i));
			}
		}
		//Removal of stopwords
		for(int i=0;i<words.size();i++)
		{
			if (words.get(i)=="#stop_words#")
				break;
			if(!sw.contains(words.get(i))) {
				finalwordlist.add(words.get(i));
			}
		}
		return finalwordlist;
	}
	static Map<String, Integer> frequencies(List<String> words) {
		Map<String, Integer> word_freq = new HashMap<>();
		// Checks if word is present in Hashmap; if yes- updates count, else adds it to hashmap
		for(int i = 0;i<words.size();i++) {
			if(word_freq.containsKey(words.get(i)) ) {
				word_freq.put(words.get(i), word_freq.get(words.get(i))+1);
			}
			else {
				word_freq.put(words.get(i),1);
			}
		}
		return word_freq;
	}
	static List<Map.Entry<String, Integer>> sortByValue(Map<String,Integer> word_freq) {
        // 1. Convert Map to List of Map
		List<Map.Entry<String, Integer>> word_freq_sorted = new ArrayList<Map.Entry<String, Integer>>(word_freq.entrySet());
        // 2. Sort list with Collections.sort() using Comparator
        Collections.sort(word_freq_sorted, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return word_freq_sorted;
    }
	static void print(List<Map.Entry<String, Integer>> word_freq_sorted) {
		//printing of 25 highest frequency words
		for (int i = 0 ; i < 25; i++) {
			Map.Entry<String, Integer> entry = word_freq_sorted.get(i);
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}	

	}
}
