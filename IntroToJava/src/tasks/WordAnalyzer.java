package tasks;

public class WordAnalyzer 
{

	public static String getSharedLetters(String word1, String word2)
	{
		
		StringBuilder letters = new StringBuilder();
		boolean[] commonLetters = new boolean[25];
		String lowerWord1 =  word1.toLowerCase();
		String lowerWord2 = word2.toLowerCase();
		
		for (int i = 0; i < word1.length(); i++)
		{
			for (int j = 0; j < word2.length(); j++)
			{
				if (lowerWord1.charAt(i) == lowerWord2.charAt(j))
				{
					if (lowerWord1.charAt(i) >= 97 && lowerWord1.charAt(i) <= 122
							&& lowerWord2.charAt(j) >= 97 && lowerWord2.charAt(j) <=122 )
					{
						commonLetters[lowerWord1.charAt(i) - 97] = true;
					}
				}
			}
		}
		
		for (int i = 0; i < commonLetters.length; i++)
		{
			if (commonLetters[i] == true)
			{
				letters.append((char)(97 + i));
			}
		}


		return letters.toString();
	}

}
