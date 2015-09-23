/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frequencyanalysis;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martin
 */
public class FrequencyAnalysis {

    // SortByValue is not written by hand, but found off internet
    // Source: http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
    
    public static Map sortByValue(Map map) 
    {
        List list = new LinkedList(map.entrySet());
        
        Collections.sort(list, new Comparator() {
             public int compare(Object o1, Object o2) {
                 // -1 allows us to order from big to small
                  return -1 * ((Comparable) ((Map.Entry) (o1)).getValue())
                 .compareTo(((Map.Entry) (o2)).getValue());
             }
        });

       Map result = new LinkedHashMap();
       
       for (Iterator it = list.iterator(); it.hasNext();) 
       {
           Map.Entry entry = (Map.Entry)it.next();
           result.put(entry.getKey(), entry.getValue());
       }
       return result;
    } 
    
    public static HashMap findAlphabetFreq(String input)
    {
        HashMap alphabetCount = new HashMap<Character, Integer>();
        
        for(char c : input.toCharArray())
        {
            if(!alphabetCount.containsKey(c))
            {
                alphabetCount.put(c, 1);
            }
            else
            {
                int tempCount = (int)alphabetCount.get(c);
                tempCount++;
                alphabetCount.put(c, tempCount);
            }
        }
        
        return (HashMap) sortByValue(alphabetCount);
    }
    
    public static HashMap findDigraphFreq(String input)
    {
        HashMap digraphCount = new HashMap<String, Integer>();
        
        for(int i = 0; i < input.length(); i++)
        {
            if( i+1 < input.length())
            {
                String key = String.valueOf(input.charAt(i)) + String.valueOf(input.charAt(i+1));

                if(!digraphCount.containsKey(key))
                {
                    digraphCount.put(key, 1);
                }
                else
                {
                    int tempCount = (int)digraphCount.get(key);
                    tempCount++;
                    digraphCount.put(key, tempCount);
                }
            }
        }

        return (HashMap) sortByValue(digraphCount);
        
    }
    
    public static HashMap findRepeatedFreq(String input)
    {
        HashMap repeatedCount = new HashMap<String, Integer>();
        
        for(int i = 0; i < input.length(); i++)
        {
            if( i+1 < input.length() && input.charAt(i) == input.charAt(i+1))
            {
                String key = String.valueOf(input.charAt(i)) + String.valueOf(input.charAt(i+1));

                if(!repeatedCount.containsKey(key))
                {
                    repeatedCount.put(key, 1);
                }
                else
                {
                    int tempCount = (int)repeatedCount.get(key);
                    tempCount++;
                    repeatedCount.put(key, tempCount);
                }
            }
        }
        
        return (HashMap) sortByValue(repeatedCount);
    }
    
    /** 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String testString = "HMAEQKQDQKMKZELLXMRZHGAAHQTGTLEJKMRZCJVSXYLELRMEMJTQMRZIGEBJQTTAGREBQGKBMRZAJQYMRELZMRSQEBQAWKGRYTQKGRYMWKQEECZMRSQSLHHMJVEKMEBQKDGYLKLVAZLEJVEJGTJLBMZRLEPGRGABQZZLEAQGOGRYMBLKRPKLHMCLVRYAEQKRQMKJCSLHHMBQJTQIEBKQQTLVZBLLEAZLEEBQRLGAQAVJAGZQZZLEGABMTTRLEXQQWCLVTLRYSLHHMBQSKGQZZLESBQQKAPKLHMTTEBQMAAQHJTCZLEGBMDQSMTTQZCLVMTTELYQEBQKPLKMWVKWLAQZLEALHQEBGRYGREBQIMCEBMEBQAMGZEBGAHMZQMRGHWKQAAGLRZLEEBQKQIMAMTHLAEAGTQRSQSLHHMMRZLRQLKEILLPEBQELLXAWKGSXQZVWEBQGKQMKA";
        
        HashMap sortedAlphabetCount = findAlphabetFreq(testString);
        HashMap sortedDigraphCount = findDigraphFreq(testString);
        HashMap sortedRepeatedCount = findRepeatedFreq(testString);
        
        System.out.println("Put breakpoint here, read variable values and start hacking!");
    }
}