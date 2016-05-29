package com.faircom.rest.jira;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class WorkLog {

	public final long id;
	public final long issueId;

	public final String url;
	public String author;
	public String updateAuthor;

	public final String comment;

	public final Date created;
	public final Date updated;
	public final Date started;

	public final String timeSpent;
	public final long timeSpentSeconds;

	

	public WorkLog(Map<String, Object> worklogJsonMap) {
		id = Long.parseLong((String) worklogJsonMap.get("id"));
		issueId = Long.parseLong((String) worklogJsonMap.get("issueId"));
		url = (String) worklogJsonMap.get("self");
		// TODO: author
		comment = (String) worklogJsonMap.get("comment");

		try {
			created = JiraRestClient.jiraJsonDateFormat.parse((String) worklogJsonMap.get("created"));
			updated = JiraRestClient.jiraJsonDateFormat.parse((String) worklogJsonMap.get("updated"));
			started = JiraRestClient.jiraJsonDateFormat.parse((String) worklogJsonMap.get("started"));
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}

		timeSpent = (String) worklogJsonMap.get("timeSpent");
		timeSpentSeconds = ((Double) worklogJsonMap.get("timeSpentSeconds")).longValue();

	}

	public String getCreatedStr() {
		return JiraRestClient.jiraJsonDateFormat.format(created);
	}

}
