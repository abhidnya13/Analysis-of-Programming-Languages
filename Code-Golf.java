import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
public class apl6 {
	public static void main(String[] args) throws IOException {
		//Loading of stopwords as a string
		String stopwords = (new Scanner(new FileReader("stop_words.txt")).next());
		//This line reads the data from a file, replaces all the special characters by space, splits it into a list of words , converts them to Lowercase , filters by stopwords and length , counts the frequencies, sorts and prints the top 25 most frequent words
		Arrays.asList((new Scanner(new FileReader("pride-and-prejudice.txt")).useDelimiter("\\Z").next()).replaceAll("[^A-Za-z0-9]", " ").split(" ")).stream().map(w->w.trim().toLowerCase()).collect(Collectors.toList()).stream().filter(w-> (w.length()>= 2 && !stopwords.contains(","+w+","))).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(w->w, Collectors.counting())).entrySet().stream().sorted((x,y) -> y.getValue().compareTo(x.getValue())).limit(25).forEach(w-> System.out.println(w.getKey() + "-" + w.getValue()));
	}
}