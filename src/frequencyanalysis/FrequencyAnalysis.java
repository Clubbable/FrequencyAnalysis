/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frequencyanalysis;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
    
    public static float findIOC(List<Item> list, int textLength)
    {
        float ioc = 0.0f;
        
        for( Item i : list)
        {
            ioc = ioc + i.itemValue * (i.itemValue - 1);
        }
        
        return ioc / (textLength * (textLength - 1));
    }
    
    public static String readFile(String path, Charset encoding) 
         throws IOException 
    {
         byte[] encoded = Files.readAllBytes(Paths.get(path));
         return new String(encoded, encoding);
    }
    
    public static String getCleansedStringFromWarAndPeace()
    {
        String sampleTextString;
        
        try
        {
            sampleTextString = readFile(Paths.get("WarAndPeace.txt").toAbsolutePath().toString(), Charset.defaultCharset());
            
            //Remove all punctuation
            sampleTextString = sampleTextString.replaceAll("\\W", "");
            
            //Remove all numbers
            sampleTextString = sampleTextString.replaceAll("\\d","");
            
            //All uppercase
            sampleTextString = sampleTextString.toUpperCase();
            
            return sampleTextString;
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
        
        return null;
    }
    
    public static Map getQuadgramDataFromWarAndPeace(String warAndPeaceString)
    {
        HashMap QuadGramCount = new HashMap<String, Integer>();
        
        for(int i = 0; i < warAndPeaceString.length(); i++)
        {
            if( i+3 < warAndPeaceString.length())
            {
                String quadGram = String.valueOf(warAndPeaceString.charAt(i)) + 
                                  String.valueOf(warAndPeaceString.charAt(i+1)) +
                                  String.valueOf(warAndPeaceString.charAt(i+2)) +
                                  String.valueOf(warAndPeaceString.charAt(i+3));
                
                if(!QuadGramCount.containsKey(quadGram))
                {
                    QuadGramCount.put(quadGram, 1);
                }
                else
                {
                    int tempCount = (int)QuadGramCount.get(quadGram);
                    tempCount++;
                    QuadGramCount.put(quadGram, tempCount);
                }
            }
        }
        
        return QuadGramCount;
    }
    
    public static String generateRandom25LetterString()
    {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String rand = "";
        
        while(rand.length() < 25)
        {
            int randInt = ThreadLocalRandom.current().nextInt(0, 26 - rand.length());
            rand = rand + alphabets.charAt(randInt);
            alphabets = alphabets.replace(String.valueOf(alphabets.charAt(randInt)), "");
        }
        
        return rand;
    }
    
    public static String decipherPlayFairWithKey( String key, String cipherText)
    {
        String plainText = "";
        char[][] keyArray = new char[5][5];
        Map keyMap = new HashMap<Character, Pair>();
        int yCoord = 0;
        
        for(int i = 0; i < key.length(); ++i)
        {
            if( i % 5 == 0 && i != 0) 
            {
                yCoord++;
            }
            keyArray[i - yCoord * 5][yCoord] = key.charAt(i);
        }
        
        for(int x = 0; x < 5; x++)
        {
            for(int y = 0; y < 5; y++)
            {
                keyMap.put(keyArray[x][y], new Pair(x,y));
            }
        }
        
        for(int i = 0; i < cipherText.length(); i = i+2 )
        {
            if( i+1 < cipherText.length())
            {
                Pair firstLetterLoc = (Pair) keyMap.get(cipherText.charAt(i));
                Pair secondLetterLoc = (Pair) keyMap.get(cipherText.charAt(i+1));
                
                // Check rectangle
                if(firstLetterLoc.x != secondLetterLoc.x && firstLetterLoc.y != secondLetterLoc.y)
                {
                    plainText = plainText + keyArray[secondLetterLoc.x][firstLetterLoc.y] + keyArray[firstLetterLoc.x][secondLetterLoc.y];
                }
            }
        }
        
        return plainText;
    }
   
    public static void main(String[] args) {
        
        // Question 1 statistics
        String number1String = "HMAEQKQDQKMKZELLXMRZHGAAHQTGTLEJKMRZCJVSXYLELRMEMJTQMRZIGEBJQTTAGREBQGKBMRZAJQYMRELZMRSQEBQAWKGRYTQKGRYMWKQEECZMRSQSLHHMJVEKMEBQKDGYLKLVAZLEJVEJGTJLBMZRLEPGRGABQZZLEAQGOGRYMBLKRPKLHMCLVRYAEQKRQMKJCSLHHMBQJTQIEBKQQTLVZBLLEAZLEEBQRLGAQAVJAGZQZZLEGABMTTRLEXQQWCLVTLRYSLHHMBQSKGQZZLESBQQKAPKLHMTTEBQMAAQHJTCZLEGBMDQSMTTQZCLVMTTELYQEBQKPLKMWVKWLAQZLEALHQEBGRYGREBQIMCEBMEBQAMGZEBGAHMZQMRGHWKQAAGLRZLEEBQKQIMAMTHLAEAGTQRSQSLHHMMRZLRQLKEILLPEBQELLXAWKGSXQZVWEBQGKQMKA";
        
        List<Item> sortedAlphabetCount = findAlphabetFreq(number1String);
        List<Item> sortedDigraphCount = findDigraphFreq(number1String);
        List<Item> sortedRepeatedCount = findRepeatedFreq(number1String);
        
        System.out.println("\nAlphabet Analysis \n");
        
        for(Item i : sortedAlphabetCount)
        {
            System.out.println(i.itemKey + "            " + i.itemValue);
        }
        
        System.out.println("\nDigraph Analysis \n");
        
        for(Item i : sortedDigraphCount)
        {
            System.out.println(i.itemKey + "            " + i.itemValue);
        }
        
        System.out.println("\nRepeated Letters Analysis \n");
        
        for(Item i : sortedRepeatedCount)
        {
            System.out.println(i.itemKey + "            " + i.itemValue);
        }
        
        System.out.println("\nIndex of Coincidence \n");
        System.out.println(findIOC(sortedAlphabetCount, number1String.length()));
        
        
        //Question 2 string
        String number2String = "IFXATNVCHSLBEODEIOPDHSRNLZPCSEMHOLEFKVEICVHUGVCFIUMHPLEFKVCFVWOEPIEFKVUGGVBSCDUWIMNCVLCPWLMISGFMNQEFHNNUINTNUCTVFBOEHUGVUGFIWNCSWMLEUGMEWLHCMBSDNWYSEOLVPCHDUCPERLNWHULZVEDNNUNPOUGETHGUSYGEDILBMHTUUWKMEFOUUEOBLPGSWMZBTNRECVHSKLTNCGPNGXDNLSVICOAXBNEWOPFAKVOEKEVEMVYCFHOWOPFAKVLMMHWSOBENOZNPOHPNHUSYAXLWOPFAKVMVLUPNFBOMKDETYCLZPCBSCDUWMVFMOPFAKVHWIAETNLHOZGCPHNTLEFKVHUSYWOWNUBNWESEONUPCCUPEDILRUTPCFGMTNWYCFHOWCPFEQBPNRUPCOEOPFAKVXHWNNUPNRLPCXGWDVGCDWLRCMFDWUNBDCZUWDBLUOHPNEFMKPCUWKNCHGUWTVGHLCPNZVKWOPNODUWHWZBULOBTNUCYBCBOUUGNICHGUTNVGRLUWICSIWMTUUWUNIDESMBCPPNOLMFNPMHIANWESEONIMNZGUGNWFGEFTNUOCHGUTNZGUGWHNUULOBUGWNMEICPOPMODUWNEYBCNHEOBXMUOBRFGEONEDEUBBKQLOPFAKVPNOLEFKVNQNUBMTBMEPBMIVGRLLETPVUYSUNUGOHWNGVHUCUMFBKWZFHWEODUWNPIGBKTLEFKVHKHSUCOBUGMHQBWLSVCNOZUWHWZBULOBHTMIVCTNUGBKQLAXTUVAMOBSBKHOTNIMENGPCLEISIOWPCCEMBRLKHLMEPUGNUETDNMEMXWNUBMHUNVNTHBCNHFCNQEFUGNIPOBSUSYBSBHOOPFAKVPCUOQLCVTNCOGVIGGPINOPFAKVNQEFHUEFNQNUAXVLCPINEBHSKLVILEHXIVFAFEELOBIGBKNVPCUGOWSBERVMCPLEDPNTTIWUBKQLVMOPVILEGXBRSXRLDPVIZGCPUGOHWNGVEDNANWENHWVMINLQNIGVSUMICPSGFEZESBHOUENVPCBSYNETPNOZUWEKNWOIIDCOUEOBIMGVLETGSBMLOPFAKVCMUGGVUGFIOPDIUBNIKCGEKWMERCOUPCUWWLHAVCMHVCTNVGGECVENHUCUNWNGNUKHCUNWIABVBKWLUMVBEBETWNBKCSNWOPFAKVBSEOHPCUAXTPNLMOSHSPOUNUGXRLOPFAKVBMVBYBCNOZUWIMHEVXBLIKNWOIDWWNODCLYSZGUCOBVMGEPDULCVBMIOHKXSDEUNKBDPFHNUPCAXQBQLOPDIVLFHMHDNIWAXUFENWCWOMKMPWEYAIVLEXOPNOVUWCKPCTNDGWULEUBBKQLOPFAKVUGFHMODPBMIOCPUGMFBKWZFHNWMUMHINLBEPMVRLPNKIENNQXHSCOHNCUNOLAXLWYAIMCPOMCUUWKLLUMEMXCNOBWTPWINSGPWOUTNZGUGMITWGPPDLETGNCMIHWWNMOUPLENUHEUGHUCUMHRLTPNLNYEPNWLVPCNQNUBMTBMEPBWUBVMXCVMONUBMMONPFEVIHDOLGEOXNCUNRDBSCNEOCMUMPDUWFHGSCGECODHUNWNHSCSBRLNYWIPKETNLIANPDCLXOIEFWUVIGXRLBMURYENUPCETVCLUOIEFKVHDZGUOQNCSMIBMLDFHMIHWWNGEUMWLOBSBUYCLMTNWUGOHKBYAMVRLUWTBNWEPTNUGBKQLKHLKGVUWWDOLEFNEPDXGINTNZGUGMFTHBCNHFCHUMCMHQGWOBKBMIOUWKHMVQZNMOCLEHPVMYCCHGUCLMXZGUOPDAXEBSEBMUBSYLBMILKNGMINMCDGEVGRLUCHAUCODUWEZYBCBSBWNENVMVEFMKLVBLZPCHUMOCHOUKVQBWLNUOPFAKVMVLUNQNUOPFAKVUVHBHCOBGVUGOINCSBPNEXSYEPRBNGMIOPFAKVEHEODGNHPCUWWLOBHEUGBMCFOZUELMEOIMMLNUPCCFTIBMBSIRMHPKAXZCETCSWMOPFAKVBMULYBCRVUXMDINDNTLXLETLDWIMEOEVEVODSCCUMVYBCNUMBLPCIAWHVMODCPNGBVUGSYFEBRLETOEFKVOPMLLUNWLETOEFKVMVLUHONPHOTNCOWLNUXGCUXGETCGNWILOEDGWEOBUWWNOPFAKVHDUPGEKHODIASETNCPAGCSFMLIEFKVDBGSFIETKLNIUMYIEFKVUWWNUGFIOPFMOPFAKVCNMPTNDGWLHADCLINCNPNUEYHTWNIKNWMHQGWOBKLETOEFKVEONHCFOMETCPNOEPUWWNGVUGNUSYVGODGNDEVBQLENUGFIBKZBTNUPCUUEOFLEVUMIGKPNIOYAYSEOAXZNNPFMTLEFKVHUSIAGOHBMCPLMVAWEVCKHIZGCBWETHAUGOHBKUNODEPBKTUUWKVPRIWNUPCFHPCSINYEPNWMVRLHNHECOUWLDLWVLNYVUUOWNTNODUWHECOUWIAGVYIEFKVHUSCOWWNISETCAMFCACSZGUOWNZNPNFAKVFLENUGWHYUBKVLLPVSGVMVUIFENMGYCUNUIGGVUOPBWNRIETUNKBRLCNVTNWOPFAKVLBEPELOBUGMIETRLENUGNHFHMIVMUGOIPNRUGKWPMGIGNUUGOUMOZG";
        String warAndPeaceString = getCleansedStringFromWarAndPeace();
        HashMap quadGramData = new HashMap();
        
        if(warAndPeaceString != null)
        {
            quadGramData = (HashMap) getQuadgramDataFromWarAndPeace(warAndPeaceString);
            
            // TESTS
            
            //System.out.println(quadGramData.get("TION"));
            //System.out.println(warAndPeaceString);
            /*for(int i = 0; i < 100; i++)
            {
                String testRand = generateRandom25LetterString();
                System.out.println(testRand);
            }*/
            
            String testRand = generateRandom25LetterString();
            String plainText = decipherPlayFairWithKey(testRand, "AZCG");
            System.out.println(plainText);
        }
    }
}