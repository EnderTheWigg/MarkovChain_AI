import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class MarkovChain 
{
    //INSTANCE VARIABLES
    /**
     *    This holds all of the word relationships
     */
    private WordGraph wg;
    
    /**
     *    This remembers the lastWord that this Markov Chain generated
     */
    private String lastWord;

    //CONSTRUCTOR
    public MarkovChain() 
    {
        //initialize instance variables
        wg = new WordGraph();
        lastWord = null;
    }
    
    //METHODS
    /**
     *    Add word relationships from the specified file
     */
    public void train(String filename)
    {
        wg.processFile(filename);
    }
    
    /**
     *    Get a list of words that follow lastWord.
     *    words with more *weight* will appear more times in the list.
     *  if lastWord is null, then return the words that are neighbors of [START]
     */
    public List<String> getNextWords()
    {
        //TODO: return List<String> of words that are neighbors of lastWord, weighted appropriatly
        
        Map<String, Integer> x;
        WeightedGraph<String> n = wg.getGraph();
        if(lastWord == null || lastWord.equals("[END]"))
            x = n.getNeighborWeights("[START]");
        else
            x = n.getNeighborWeights(lastWord);
        List<String> nextWords = new ArrayList<String>();
        for(Map.Entry<String, Integer> entry: x.entrySet()){
            for(int i = 0; i < entry.getValue(); i++){
                if(!(entry.getKey().equals("")))
                    nextWords.add(entry.getKey());
            }
        }
        return nextWords;
    }
    
    /**
     *    Get a word that follows lastWord
     *    Use a weighted random number to pick the next word from the list of all of lastWord's neighbors in wordGraph
     *    If lastWord is null or [END], this should generate a word that *starts* a sentence
     *    Remember the word that is about to be returned in the appropriate instance variable
     */
    public String getNextWord()
    {
        //TODO: return random word with an edge from lastWord
            
        List<String> n = getNextWords();
        if(n.size() == 0)
        {
            return null;
        }
        int r = (int) (Math.random() * n.size());
        String nextWord = n.get(r);
        lastWord = nextWord;
        return nextWord;
    }
    
    /**
     *    Generate a sentence using the data in the wordGraph. 
     */
    public String generateSentence()
    {
        String sentence = "";
        String nextWord = getNextWord();
        while(!(nextWord == null || nextWord.equals("[END]"))){
            sentence += nextWord + " ";
            nextWord = getNextWord();
        }
        lastWord = null;        
        return sentence;
    }
    
    
}