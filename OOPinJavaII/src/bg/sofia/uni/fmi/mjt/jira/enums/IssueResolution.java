package bg.sofia.uni.fmi.mjt.jira.enums;

public enum IssueResolution
{
	FIXED("fixed"), 
	WONT_FIX("wont fix"),
	DUPLICATE("duplicate"),
	CANNOT_REPRODUCE("cannot reproduce"), 
	UNRESOLVED("unresolved");
	
	private String resolution;
	 
	private IssueResolution(String resolution)
	{
		this.resolution=resolution;
	}

	public String getResolution() 
	{
		return resolution;
	}
}
