package com.faircom.rest.jira;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.raysforge.rest.client.GenericRestClient;

public class JiraRestClient extends GenericRestClient {

	public static final SimpleDateFormat jiraJsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

	// undocumented fields: worklogDate workLogComment

	public JiraRestClient(String baseURL, String user, String pass) {
		super(baseURL, user, pass, Auth.Basic);
	}

	public Map<String, Object> getIssue(String issueName) throws IOException {
		return super.getDataAsMap("issue/" + issueName);
	}

	private Map<String, Object> getWorkLog(String key) throws IOException {
		return super.getDataAsMap("issue/" + key + "/worklog");
	}

	@SuppressWarnings("unchecked")
	public List<JiraIssue> search(String assignee, String startDate, String endDate) throws IOException {
		String fields = "id,key,summary,updated";
		String dateField = "updated";
		String jql = (dateField + ">=\"" + startDate + "\" AND " + dateField + "<=\"" + endDate + "\" and assignee=" + assignee).replace(" ", "%20");
		List<Map<String, Object>> issuesJsonMap = (List<Map<String, Object>>) getDataAsMap("search?maxResults=1000&fields=" + fields + "&jql=" + jql).get("issues");
		ArrayList<JiraIssue> issues = new ArrayList<JiraIssue>();
		for (Map<String, Object> issueJson : issuesJsonMap) {
			Map<String, Object> workLog = getWorkLog((String)issueJson.get("key"));
			issues.add(new JiraIssue(issueJson, workLog));
		}
		return issues;
	}

}
