package bg.sofia.uni.fmi.mjt.jira.issues;

public class Component 
{
	private String name;
	private String shortName;
	
	public Component(String name, String shortName)
	{
		this.name = name;
		this.shortName = shortName;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName) 
	{
		this.shortName = shortName;
	}
	
	@Override
	public boolean equals(Object other)
	{
		Component comp = (Component)other;
		if (this.getName().equals(comp.getName()) && this.getShortName().equals(comp.getShortName()))
		{
			return true;
		}
		
		return false;
	}
}
