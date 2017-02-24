import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
public class apl2 {
	// Stack 1 for normal operations and Stack 2 for sorting
	public static Stack st = new Stack();
	public static Stack st1 = new Stack();
	// Hash Map for storing words and frequencies and heap for storing variables
	public static HashMap<String, Object> heap = new HashMap<>();
	public static HashMap<String, Integer> hm = new HashMap<>();
	public static void main(String[] args) throws IOException {
		st.add(args[0]);
		readfile();
		removestopwords();
		frequencies();
		sort();
		print();
	}
	//This function reads the file line by line and forms tokens and adds them on the stack
	static void readfile() throws IOException {
		FileInputStream f = new FileInputStream(st.pop().toString());
		heap.put("word", "");
		while(f.available() > 0) {
			st.add((char) f.read());
	        if(Character.isLetterOrDigit((char)st.peek())) {
	        	heap.put("word",heap.get("word").toString()+(char)(st.pop()));
	        }
	        else if(heap.get("word").toString().length()>=2) {
	        	st.pop();
	        	st.add(heap.get("word").toString().toLowerCase());
	        	heap.put("word", "");
	        }
	        else {
	        	st.pop();
	        	heap.put("word", "");
	        }
		}
	}
	// This function removes stop words
	static void removestopwords() throws IOException {
		Scanner sc = new Scanner(new FileReader("stop_words.txt"));
		heap.put("sw", "");
		st.add(sc.nextLine());
		st1.add(st.pop());
		while(!(st.isEmpty())) {
			if((st1.peek().toString().contains(","+(CharSequence) st.peek()+","))) {
				st.pop();
				continue;
			}
			else{
				heap.put("sw", (String)st1.pop()) ;
				st1.add(st.pop().toString());
				st1.add(heap.get("sw"));
			}
		}
		st1.pop();
		while(!(st1.isEmpty())){
			st.add(st1.pop());
		}
		st1.clear();
	}
	//This function counts the frequency of words
	static void frequencies() {
		heap.put("a", 0);
		while(!(st.isEmpty())) {
			if(hm.containsKey(st.peek())) {
				st.add(hm.get(st.peek()));
				st.add(1);
				st.add((Integer)st.pop()+(Integer)st.pop());
				heap.put("a", (Integer) st.pop()); 
				hm.put((String) st.pop(), (Integer) heap.get("a"));
			}
			else
				hm.put((String) st.pop(), 1);
		}
		for (String key : hm.keySet()) {
		    st.add(key);
		    st.add(hm.get(key));
		}
		hm.clear();
	}
	//This function sorts according to frequencies 
	static void sort() {
        while(!st.isEmpty()) {
        	heap.put("t1",(int)st.pop());
        	heap.put("temp1", (String) st.pop());
            heap.put("t2",0);
            while(!st1.isEmpty() && (int)(st1.peek()) > (int)heap.get("t1")) {
            	heap.put("t2", (int)st1.pop());
                st.push(st1.pop());
                st.push(heap.get("t2"));
            }
            st1.push(heap.get("temp1"));
            st1.push(heap.get("t1"));
        }			
	}
	//This function prints 25 most frequent words
	static void print() {
		st1.add(0);
		heap.put("temp", 0);
		while(((Integer)st1.peek() < 25) && st1.size() >1) {
			hm.put("i", (Integer)st1.pop());
			heap.put("temp", (Integer)st1.pop());
			System.out.println(st1.pop()+" - "+heap.get("temp"));
			st1.add(hm.get("i"));
			st1.add(1);
			st1.add((Integer)st1.pop() + (Integer)st1.pop());
		}
	}	
}
