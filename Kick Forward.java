import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class apl8 {
	static List<String> words = new ArrayList<>();
	static Map<String, Integer> word_freq = new HashMap<>();
	static List<Map.Entry<String, Integer>> word_freq_sorted = new ArrayList<Map.Entry<String, Integer>>();
	//Funtion to read file. It passes the callable interface as an argument which implements the next method in pipeline
	static void readfile(String path, String sw, Callable<Void> c) throws Exception {
		
		String word = "";
		FileInputStream f = new FileInputStream(path);
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
		Scanner sc = new Scanner(new FileReader(sw)); 
		String stopwords = sc.nextLine();
		words.add("#stop_words#");
		String []stop_words = stopwords.split(",");
		for(int i =0;i<stop_words.length;i++) {
			words.add(stop_words[i]);
		}
		//Next function call, i.e Removes stopwords
		c.call();			
	}
	
	public static void removestopwords(Callable<Void> c) throws Exception {
		Boolean flag = false;
		List<String> sw = new ArrayList<>();
		for (int i=0;i<words.size();i++) {
			if(words.get(i)=="#stop_words#") {
				flag= true;
			}
			if(flag==true) {
				sw.add(words.get(i));
			}
		}
		words.removeAll(sw);
		c.call();
	}
	public static void frequencies(Callable<Void> c) throws Exception {		
		// Checks if word is present in Hashmap; if yes- updates count, else adds it to hashmap
		for(int i = 0;i<words.size();i++) {
			if(word_freq.containsKey(words.get(i)) ) {
				word_freq.put(words.get(i), word_freq.get(words.get(i))+1);
			}
			else {
				word_freq.put(words.get(i),1);
			}
		}
		c.call();
	}
	public static void sort(Callable<Void> c) throws Exception {
        // 1. Convert Map to List of Map
		
		word_freq_sorted =  new ArrayList<Map.Entry<String, Integer>>(word_freq.entrySet());
        // 2. Sort list with Collections.sort() using Comparator
        Collections.sort(word_freq_sorted, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        	
        c.call();
    }
	public static void print() {
		//printing of 25 highest frequency words
		for (int i = 0 ; i < 25; i++) {
			//System.out.println(i);
			Map.Entry<String, Integer> entry = word_freq_sorted.get(i);
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}	
	}
	static Callable<Void> removestopwords = new Callable<Void>() {
		public Void call() throws Exception {
			removestopwords(frequencies);
			return null;
		}
	};
	static Callable<Void> frequencies = new Callable<Void>() {
		public Void call() throws Exception {
			frequencies(sort);
			return null;
		}
	};
	static Callable<Void> sort = new Callable<Void>() {
		public Void call() throws Exception {
			sort(print);
			return null;
		}
	};
	static Callable<Void> print = new Callable<Void>() {
		
		public Void call()  {
			print();
			return null;
		}
	};
	
	public static void main(String[] args) throws Exception {		
		readfile(args[0],args[1], removestopwords);		
	}
}
