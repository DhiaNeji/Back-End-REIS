package Reis.PFE.NameMatching;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import info.debatty.java.stringsimilarity.Levenshtein;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class NameMatching {
private ArrayList<String> to_remove=new ArrayList<String>();
private RandomDemo random=new RandomDemo();
public NameMatching()
{
	to_remove.add("hej");
	to_remove.add("haj");
	to_remove.add("hed");
	to_remove.add("had");
	to_remove.add("bel");
	to_remove.add("bil");
	to_remove.add("byl");
	to_remove.add("Mr");
	to_remove.add("mr");
	to_remove.add("md");
}
public ArrayList<String> getTo_remove() {
	return to_remove;
}

public void setTo_remove(ArrayList<String> to_remove) {
	this.to_remove = to_remove;
}
public int match_names(String name_1,String name_2)
{
	int res=0;
	for(int i=0;i<to_remove.size();i++)
	{
		name_1.replace(to_remove.get(i),"");
		name_2.replace(to_remove.get(i),"");
	}
	res=FuzzySearch.tokenSortPartialRatio(name_1,name_2);
	if(res>75)
	return res;
	else
	{
		name_1.replace("e","a");
		name_1.replace("y","i");
		name_1.replace("ss","c");
		name_1.replace("ll","l");
		name_2.replace("e","a"); //mariam
		name_2.replace("y","i");
		name_2.replace("ss","c");
		name_2.replace("ll","l");
		return FuzzySearch.tokenSortPartialRatio(name_1,name_2);
	}
}
public List<String> ngrams(int n, String str) {
    List<String> ngrams = new ArrayList<String>();
    for (int i = 0; i < str.length() - n + 1; i++)
        ngrams.add(str.substring(i, i + n));
    return ngrams;
  }
public double calculateJaccardSimilarity(String name_1,String name_2)
{
	
	List<String> left=ngrams(2,name_1);
	List<String> right=ngrams(2,name_2);
	Set<String> intersectionSet = new HashSet<String>();
    Set<String> unionSet = new HashSet<String>();
    boolean unionFilled = false;
    int leftLength = left.size();
    int rightLength = right.size();
    if (leftLength == 0 || rightLength == 0) {
        return 0d;
    }
for(int leftIndex=0;leftIndex<leftLength;leftIndex++)
{
	unionSet.add(String.valueOf(left.get(leftIndex)));
	for(int rightIndex=0;rightIndex<rightLength;rightIndex++)
	{
		if(!unionFilled)
			unionSet.add(String.valueOf(right.get(rightIndex)));
		if(left.get(leftIndex).equals(right.get(rightIndex)))
			intersectionSet.add(String.valueOf(left.get(leftIndex)));
	}
	unionFilled=true;
}
return Double.valueOf(intersectionSet.size())/Double.valueOf(unionSet.size());
}
public double calculate_matches(String name_1,String name_2)
{
	double resultat_1=0;
	double resultat_2=0;
	for(int i=0;i<to_remove.size();i++)
	{
		name_1.replace(to_remove.get(i),"");
		name_2.replace(to_remove.get(i),"");
	}
	resultat_1=this.calculateJaccardSimilarity(name_1, name_2);
	if(resultat_1<0.75)
	{
		name_1=name_1.replace("e","a");
		name_1=name_1.replace("y","i");
		name_1=name_1.replace("ss","c");
		name_1=name_1.replace("ll","l");
		name_1=name_1.replace("j","g");
		name_2=name_2.replace("e","a"); 
		name_2=name_2.replace("y","i");
		name_2=name_2.replace("ss","c");
		name_2=name_2.replace("ll","l");
		name_2=name_2.replace("j","g");
		resultat_2=this.calculateJaccardSimilarity(name_1, name_2);
	}
	System.out.println(resultat_2+name_1+name_2);
	if(resultat_2>0.75)
	{
		return resultat_2;
	}
	else
	{
		return 4/1000;
	}
}
}
