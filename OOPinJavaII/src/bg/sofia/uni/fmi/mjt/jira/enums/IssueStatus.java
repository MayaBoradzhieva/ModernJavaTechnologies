package bg.sofia.uni.fmi.mjt.jira.enums;

public enum IssueStatus 
{
	OPEN("open"),
	IN_PROGRESS("in progress"), 
	RESOLVED("resolved");
	
	private String status;
	 
	private IssueStatus(String status)
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}
}
