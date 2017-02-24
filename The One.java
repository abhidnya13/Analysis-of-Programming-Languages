import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.Function;
public class apl9 {
	private Object value;
	private apl9(String value) {
		this.value = value;
	}
	private apl9 bind(Function<Object,Object> func) throws Exception {
		this.value = func.apply(this.value);
        return this;
    }
	private void printMe() 
	{
		//Printing the first 25 words
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();
		list = (List<Entry<String, Integer>>) this.value;
		for (int i = 0 ; i < 25; i++) {
			Map.Entry<String, Integer> entry = list.get(i);
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
    }
	public static Object readfile(Object path) {
		List<String> words = new ArrayList<>();
		String word = "";
		FileInputStream f;
		try {
			//read file charachter by character to extract words
			f = new FileInputStream((String) path);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner sc;
		try {
			//Adding stopwords
			sc = new Scanner(new FileReader("stop_words.txt"));
			String stopwords = sc.nextLine();
			words.add("#stop_words#");
			String []stop_words = stopwords.split(",");
			for(int i =0;i<stop_words.length;i++) {
				words.add(stop_words[i]);
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return words;			
	}
	public static Object removestopwords(Object words){
		Boolean flag = false;
		List<String> sw = new ArrayList<>();
		for (int i=0;i<((List<String>) words).size();i++) {
			if(((List<String>) words).get(i)=="#stop_words#") {
				flag= true;
			}
			if(flag==true) {
				sw.add(((List<String>) words).get(i));
			}
		}
		((List<String>) words).removeAll(sw);
		return words;
	}
	public static Object frequencies(Object words) {		
		// Checks if word is present in Hashmap; if yes- updates count, else adds it to hashmap
		HashMap<String, Integer> word_freq = new HashMap<String, Integer>();
		for(int i = 0;i<((List<String>) words).size();i++) {
			if(word_freq.containsKey(((List<String>) words).get(i)) ) {
				word_freq.put(((List<String>) words).get(i), word_freq.get(((List<String>) words).get(i))+1);
			}
			else {
				word_freq.put(((List<String>) words).get(i),1);
			}
		}
		return word_freq;
	}
	public static Object sort(Object word_freq)  {
		List<Map.Entry<String, Integer>> word_freq_sorted = new ArrayList<Map.Entry<String, Integer>>();
		word_freq_sorted =  new ArrayList<Map.Entry<String, Integer>>(((HashMap) word_freq).entrySet());
        // 2. Sort list with Collections.sort() using Comparator
        Collections.sort(word_freq_sorted, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        	
        return word_freq_sorted;
    }   
	public static void main(String[] args) throws Exception 
	{
		new apl9("pride-and-prejudice.txt")
			.bind(apl9::readfile)
			.bind(apl9::removestopwords)
			.bind(apl9::frequencies)
			.bind(apl9::sort)
			.printMe();
	}
}