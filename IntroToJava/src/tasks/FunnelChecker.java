package tasks;

public class FunnelChecker
{

	public static boolean isFunnel(String str1, String str2)
	{
		if (str1.length() != str2.length() + 1)
		{
			return false;
		}
		if (str2.length() == 0)
		{
			return true;
		}
			
		StringBuilder newString = new StringBuilder("");
		
		int j = 0;
		for (int i = 0; i < str1.length(); i++)
		{
			if (str1.charAt(i) == str2.charAt(j))
			{
				newString.append(str1.charAt(i));
				j++;
			}
			
			if (j == str2.length())
			{
			    break;
			}
		}

		
		if (newString.toString().equals(str2))
		{
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args)
	{
		
	}
	
	
}