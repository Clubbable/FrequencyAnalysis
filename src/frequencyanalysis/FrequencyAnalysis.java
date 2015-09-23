/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frequencyanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Martin
 */
public class FrequencyAnalysis {
    
    public static List<Item> sortByValue(Map map) 
    {
        List sortedList = new ArrayList<Item>();
        
        while(!map.isEmpty())
        {
            int Highest = 0;
            Object HighestKey = null;
            Iterator it = map.entrySet().iterator();
            
            while(it.hasNext())
            {
                Entry entry = (Entry) it.next();
                
                if((Integer)entry.getValue() > Highest)
                {
                    Highest = (Integer)entry.getValue();
                    HighestKey = entry.getKey();
                }
            }
            
            if(HighestKey != null)
            {
                sortedList.add(new Item(HighestKey, Highest));
                map.remove(HighestKey);
            }
        }
        
        return sortedList;
    } 
    
    public static List<Item> findAlphabetFreq(String input)
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
        
        return sortByValue(alphabetCount);
    }
    
    public static List<Item> findDigraphFreq(String input)
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

        return sortByValue(digraphCount);
        
    }
    
    public static List<Item> findRepeatedFreq(String input)
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
        
        return sortByValue(repeatedCount);
    }
    
    /** 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String testString = "HMAEQKQDQKMKZELLXMRZHGAAHQTGTLEJKMRZCJVSXYLELRMEMJTQMRZIGEBJQTTAGREBQGKBMRZAJQYMRELZMRSQEBQAWKGRYTQKGRYMWKQEECZMRSQSLHHMJVEKMEBQKDGYLKLVAZLEJVEJGTJLBMZRLEPGRGABQZZLEAQGOGRYMBLKRPKLHMCLVRYAEQKRQMKJCSLHHMBQJTQIEBKQQTLVZBLLEAZLEEBQRLGAQAVJAGZQZZLEGABMTTRLEXQQWCLVTLRYSLHHMBQSKGQZZLESBQQKAPKLHMTTEBQMAAQHJTCZLEGBMDQSMTTQZCLVMTTELYQEBQKPLKMWVKWLAQZLEALHQEBGRYGREBQIMCEBMEBQAMGZEBGAHMZQMRGHWKQAAGLRZLEEBQKQIMAMTHLAEAGTQRSQSLHHMMRZLRQLKEILLPEBQELLXAWKGSXQZVWEBQGKQMKA";
        
        List<Item> sortedAlphabetCount = findAlphabetFreq(testString);
        List<Item> sortedDigraphCount = findDigraphFreq(testString);
        List<Item> sortedRepeatedCount = findRepeatedFreq(testString);
        
        System.out.println("Alphabet Analysis \n");
        
        for(Item i : sortedAlphabetCount)
        {
            System.out.println(i.itemKey + "            " + i.itemValue);
        }
        
        System.out.println("Digraph Analysis \n");
        
        for(Item i : sortedDigraphCount)
        {
            System.out.println(i.itemKey + "            " + i.itemValue);
        }
        
        System.out.println("Repeated Letters Analysis \n");
        
        for(Item i : sortedRepeatedCount)
        {
            System.out.println(i.itemKey + "            " + i.itemValue);
        }
        
    }
}