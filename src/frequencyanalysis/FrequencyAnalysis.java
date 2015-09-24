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
            int randInt = ThreadLocalRandom.current().nextInt(0, 25 - rand.length());
            rand = rand + alphabets.charAt(randInt);
            alphabets = alphabets.replace(String.valueOf(alphabets.charAt(randInt)), "");
        }
        
        return rand;
    }
    
    public static String modifyString(String input)
    {
        int randInt1 = ThreadLocalRandom.current().nextInt(0, 25);
        int randInt2 = ThreadLocalRandom.current().nextInt(0, 25);
        
        return input.replace(String.valueOf(input.charAt(randInt1)), "*")
                    .replace(String.valueOf(input.charAt(randInt2)), String.valueOf(input.charAt(randInt1)))
                    .replace("*", String.valueOf(input.charAt(randInt2)));
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
                char firstLetter = cipherText.charAt(i);
                char secondLetter = cipherText.charAt(i+1);
                
                Pair firstLetterLoc = (Pair) keyMap.get(firstLetter);
                Pair secondLetterLoc = (Pair) keyMap.get(secondLetter);
                
                // This null check ensure that the missing letter is taken care of. If J is missing, use K or Q, if D is, use E or B
                if(firstLetterLoc == null)
                {
                    firstLetter++;
                    firstLetterLoc = (Pair) keyMap.get(firstLetter);
                    
                    if(firstLetterLoc == null)
                    {
                        firstLetter = (char) (firstLetter - 2);
                        firstLetterLoc = (Pair) keyMap.get(firstLetter);
                    }
                }
                if(secondLetterLoc == null)
                {
                    secondLetter++;
                    secondLetterLoc = (Pair) keyMap.get(secondLetter);
                    
                    if(secondLetterLoc == null)
                    {
                        secondLetter = (char) (secondLetter - 2);
                        secondLetterLoc = (Pair) keyMap.get(secondLetter);
                    }
                }
                
                // Check rectangle
                if(firstLetterLoc.x != secondLetterLoc.x && firstLetterLoc.y != secondLetterLoc.y)
                {
                    plainText = plainText + keyArray[secondLetterLoc.x][firstLetterLoc.y] + keyArray[firstLetterLoc.x][secondLetterLoc.y];
                }
                // Check right shift
                else if(firstLetterLoc.y == secondLetterLoc.y)
                {
                    plainText = plainText + keyArray[firstLetterLoc.x - 1 < 0 ? 4 : firstLetterLoc.x - 1][firstLetterLoc.y] 
                                          + keyArray[secondLetterLoc.x - 1 < 0 ? 4 : secondLetterLoc.x - 1][secondLetterLoc.y];
                }
                // Check down shift
                else if(firstLetterLoc.x == secondLetterLoc.x)
                {
                    plainText = plainText + keyArray[firstLetterLoc.x][firstLetterLoc.y - 1 < 0 ? 4 : firstLetterLoc.y - 1] 
                                          + keyArray[secondLetterLoc.x][secondLetterLoc.y - 1 < 0 ? 4 : secondLetterLoc.y - 1];
                }
            }
        }
        
        return plainText;
    }
    
    public static float findFitnessOfString(Map quadGramData, String plainText)
    {
        float ans = 0.0f;
        
        for(int i = 0; i < plainText.length(); i++)
        {
            if( i+3 < plainText.length())
            {
                String quadGram = String.valueOf(plainText.charAt(i)) +
                                  String.valueOf(plainText.charAt(i+1)) +
                                  String.valueOf(plainText.charAt(i+2)) +
                                  String.valueOf(plainText.charAt(i+3));
                                  
                if(quadGramData.containsKey(quadGram))
                {
                    Integer occurances = (Integer)quadGramData.get(quadGram);
                    float logAns = (float) Math.log10(occurances / 2500000.0f);
                    ans += logAns;
                }
                // We approxmiate no occurances with -9,4, since 1 occurance is about -6.3, just a random guess
                else
                {
                    ans += -9.4f;
                }
            }
        }
        
        return ans;
    }
    
    public static String runPlayFairDecryptor(String cipherText) throws Exception
    {
        String warAndPeaceString = getCleansedStringFromWarAndPeace();
        HashMap quadGramData = (HashMap) getQuadgramDataFromWarAndPeace(warAndPeaceString);
        
        String parentKey = generateRandom25LetterString();
        float parentFitness = findFitnessOfString(quadGramData, decipherPlayFairWithKey(parentKey, cipherText));
        
        for(int temp = 50; temp >= 0; temp-- )
        {
            for(int count = 50000; count > 0; count-- )
            {
                String childKey = modifyString(parentKey);
                float childFitness = findFitnessOfString(quadGramData, decipherPlayFairWithKey(childKey, cipherText));
                System.out.println(temp + " - " + count + ": " + parentFitness + "     " + childFitness);
                float dF = childFitness - parentFitness;
                
                if(dF > 0)
                {
                    parentKey = childKey;
                    parentFitness = childFitness;
                }
                else
                {
                     float randFloat = ThreadLocalRandom.current().nextFloat();
                     
                     if(randFloat < Math.exp(dF/temp))
                     {
                         parentKey = childKey;
                         parentFitness = childFitness;
                     }
                }
            }
        }
        
        // Generally a fitness of more than -300 is good as an end point
        return parentKey;
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
        String number2Answer = "";
        
        try{
            number2Answer = runPlayFairDecryptor(number2String);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        System.out.println(number2Answer);
        
        // TESTS
        /*
        String warAndPeaceString = getCleansedStringFromWarAndPeace();
        HashMap quadGramData = new HashMap();
        
        if(warAndPeaceString != null)
        {
            quadGramData = (HashMap) getQuadgramDataFromWarAndPeace(warAndPeaceString);
            
            //System.out.println(quadGramData.get("TION"));
            //System.out.println(warAndPeaceString);
            for(int i = 0; i < 100; i++)
            {
                String testRand = generateRandom25LetterString();
                System.out.println(testRand);
            }
            String testKey = generateRandom25LetterString();
            String plainText = decipherPlayFairWithKey(testKey, "ZM");
            System.out.println(plainText);
            System.out.println(testKey);
            System.out.println(modifyString(testKey));
            
            //float fitness = findFitnessOfString(quadGramData, "ATTACKTHEEASTWALLOFTHECASTLEATDAWN");
            //float fitness = findFitnessOfString(quadGramData, "FYYFHPYMJJFXYBFQQTKYMJHFXYQJFYIFBS");
            //System.out.println(fitness);
        }
        */
        
    }
}