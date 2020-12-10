package bg.sofia.uni.fmi.mjt.jira.enums;

public enum IssuePriority
{
	TRIVIAL("trivial"),
	MINOR("minor"), 
	MAJOR("major"), 
	CRITICAL("critical");
	
	private String priority;
	 
	private IssuePriority(String priority)
	{
		this.priority = priority;
	}

	public String getPriority() 
	{
		return priority;
	}

}