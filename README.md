# JiraRestClient
A client to the Jira REST API written in Java using rhulha/GenericRestClient

  
# Usage example:

```Java
public class TestJiraRestClient {
	public static void main(String[] args) throws IOException {
		long totalSecs=0;

		String startDate = "2016/05/01";
		String endDate = "2016/05/31";

		JiraRestClient jira = new JiraRestClient("https://jira.atlassian.net/rest/api/latest/", "username", "pass");
		List<JiraIssue> issues = jira.search("currentUser()", startDate, endDate);
		for (JiraIssue issue : issues) {
			System.out.println(issue.toShortString());
			for (WorkLog worklog : issue.worklogs) {
				if(worklog.getCreatedStr().startsWith("2016-05")) {
					System.out.print("- " + worklog.getCreatedStr().substring(0, 10));
					System.out.print("- " + worklog.timeSpent);
					System.out.println(" - " + worklog.comment.substring(0, Math.min(20, worklog.comment.length())));
					totalSecs +=worklog.timeSpentSeconds;
				}
			}
		}
		
		System.out.println(totalSecs / 3600);
		System.out.println((totalSecs % 3600) / 60);

	}
}

````
