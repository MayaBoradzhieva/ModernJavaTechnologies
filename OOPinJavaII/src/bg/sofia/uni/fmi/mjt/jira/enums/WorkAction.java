package bg.sofia.uni.fmi.mjt.jira.enums;

public enum WorkAction 
{
	RESEARCH("research"), 
	DESIGN("design"), 
	IMPLEMENTATION("implementation"), 
	TESTS("tests"), 
	FIX("fix");
	
	private String action;
	 
	private WorkAction(String action)
	{
		this.action = action;
	}

	public String getAction() 
	{
		return action;
	}
}
