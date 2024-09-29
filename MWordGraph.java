import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;  
public class MWordGraph extends WordGraph
{
    private LinkedList<String> lastWords;
    private WeightedGraph<String> graph;
    private String lastWord;
    /**
     * Constructor for objects of class MWordGraph
     */
    public MWordGraph()
    {
        graph = new WeightedAdjacencyListGraph<String>();
        addWord("[START]");
        addWord("[END]");
        lastWords = new LinkedList<String>();
        lastWord = null;
    }
    public void addWord(String newWord, int order)
    {
        if(lastWords.isEmpty())
            lastWords.add("[START]");
        newWord = newWord.trim();
        graph.add(newWord);
        graph.addEdge(lastWord, newWord);
        graph.setWeight(lastWord,newWord,(graph.getWeight(lastWord, newWord) + 1));
        lastWord = newWord;
        if(isEndWord(newWord)){
            graph.addEdge(newWord, "[END]");
            lastWords = null;
        }
    }
    public void processString(String str, int order)
    {
        String[] processed = str.split(" ");
        for(String word: processed)
            addWord(word, order);
    }

    /**
     *    Process a file by reading each line from a file (using nextLine() method)
     *    and call the processString() method on it.
     */
    public void processFile(String filename, int order)
    {
        try
        {
            //open the specified file
            File file = new File(filename);
            Scanner in = new Scanner(file);

            //loop through each line of the file and process it
            while(in.hasNextLine())
                processString(in.nextLine());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
