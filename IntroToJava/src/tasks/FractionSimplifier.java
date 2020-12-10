package tasks;

public class FractionSimplifier
{
	public static String simplify(String fraction)
	{
		String[] tokens = fraction.split("/");
		
		int firstNumber = Integer.parseInt(tokens[0]);
		int secondNumber = Integer.parseInt(tokens[1]);
		
		if (firstNumber%secondNumber == 0)
		{
			return firstNumber/secondNumber + "";
		}
	
		int temp1 = firstNumber;
		int temp2 = secondNumber;
		
		// now find the greatest common divisor of num1 and num2 
		while (temp1 != temp2)
		{
			 if(temp1 > temp2)
	       	{
				 temp1 = temp1 - temp2;
	        }
	        else
	        {
	        	 temp2 = temp2 - temp1;
            }
	    }
		int gcd = temp2;
		
		int firstNumSimplified = firstNumber/gcd;
		int secondNumberSimplified = secondNumber/gcd;
		
		return firstNumSimplified + "/" + secondNumberSimplified;
	}
	
}
