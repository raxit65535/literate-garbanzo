package com.drfloob.insight.pulse.venturi;

import com.drfloob.insight.pulse.schema.gh.main.Root;
import com.drfloob.insight.pulse.schema.gh.main.root.Actor;
import com.drfloob.insight.pulse.schema.gh.main.root.Org;
import com.drfloob.insight.pulse.schema.gh.main.root.Repo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by aj on 9/20/16.
 */
public class PayloadParser {

    private PayloadParser() {
    }

    private static ObjectMapper mapper = new ObjectMapper();


    /*----------------------------------------------------------------
     Parse To-User
     ----------------------------------------------------------------*/

    public static String getToUser(Root r) {
        String type = r.getType().toString();

        // Todo: figure out how to parse these
//        ArrayList<String> createSet = new ArrayList<String>();
//        createSet.add("CreateEvent");
//        createSet.add("DeleteEvent");

        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(r.getPayload().toString());
        } catch(IOException e) {
            System.err.println("Oops: json mapper failed to parse the payload");
            // TODO: better error handling
            return null;
        }

        ArrayList<String> issuesSet = new ArrayList<String>();
        issuesSet.add("IssueCommentEvent");
        issuesSet.add("IssuesEvent");

        ArrayList<String> repoExtractSet = new ArrayList<String>();
        repoExtractSet.add("PushEvent");
        repoExtractSet.add("WatchEvent");

        if (type.equals("CommitCommentEvent")) // todo?
            return extractToUserFromComment(rootNode);
        else if (issuesSet.contains(type))
            return extractToUserFromIssueComment(rootNode);
        else if (type.equals("ForkEvent")) // todo?
            return extractToUserFromFork(rootNode, r);
        else if (type.equals("MemberEvent"))
            return extractToUserFromMember(rootNode);
        else if (type.equals("PullRequestEvent"))
            return extractToUserFromPullRequest(rootNode);
        else if (type.equals("PullRequestReviewCommentEvent"))
            return extractToUserFromPullRequestReviewComment(rootNode);
        else if (type.equals("PushEvent"))
            return extractToUserFromPush(rootNode, r);
        else if (repoExtractSet.contains(type))
            return extractToUserFromPayloadRepo(rootNode);

        return null;
    }

    private static String extractToUserFromComment(JsonNode rootNode) {
        return rootNode.path("comment").path("repository").path("owner").path("login").asText();
    }
    private static String extractToUserFromFork(JsonNode rootNode, Root r) {
	String ret = rootNode.path("forkee").path("owner").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	// 2013 org
	CharSequence retcl = r.getOrg().getLogin();
	if (retcl != null) {
	    ret = retcl.toString();
	    if (ret != null && ! ret.equals(""))
		return ret;
	}
	// 2013 from repo url
	ret = getUserFromURL(r);
	if (ret != null && ! ret.equals(""))
	    return ret;
	return null;
	
    }
    private static String extractToUserFromIssueComment(JsonNode rootNode) {
        String ret = rootNode.path("issue").path("user").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	ret = rootNode.path("repo").asText();
	int idx = ret.indexOf("/");
	if (idx <= 0)
	    return null;
	ret = ret.subSequence(0, idx).toString();
	return ret;
    }
    private static String extractToUserFromMember(JsonNode rootNode) {
        String ret = rootNode.path("member").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	ret = rootNode.path("member").asText();
	return ret;
    }
    private static String extractToUserFromPullRequest(JsonNode rootNode) {
        String ret = rootNode.path("pull_request").path("base").path("user").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	ret = rootNode.path("repo").asText();
	int idx = ret.indexOf("/");
	if (idx <= 0)
	    return null;
	ret = ret.subSequence(0, idx).toString();
	return ret;
    }
    private static String extractToUserFromPullRequestReviewComment(JsonNode rootNode) {
        return rootNode.path("pull_request").path("user").path("login").asText();
    }
    private static String extractToUserFromPush(JsonNode rootNode, Root r) {
	// return the user who made the first commit
	String ret;
	try {
	    return rootNode.path("commits").get(0).path("author").path("name").asText();
	} catch(Exception e) {
	    // pure evil
	}
	
	// 2013 org
	CharSequence retcl = r.getOrg().getLogin();
	if (retcl != null) {
	    ret = retcl.toString();
	    if (ret != null && ! ret.equals(""))
		return ret;
	}
	// 2013 from repo url
	ret = getUserFromURL(r);
	if (ret != null && ! ret.equals(""))
	    return ret;
	
	return extractToUserFromPayloadRepo(rootNode);
    }
    private static String extractToUserFromPayloadRepo(JsonNode rootNode) {
	String repo = rootNode.path("repo").asText();
	int idx = repo.indexOf("/");
	if (idx <= 0)
	    return null;
	return repo.subSequence(0, idx).toString();
    }
    // private static String extractToUserFromRepoName(Root r) {
    //     String name = r.getRepo().getName().toString();
    //     return name.subSequence(0, name.indexOf("/")).toString();
    // }


    /*----------------------------------------------------------------
     Parse From-User
     ----------------------------------------------------------------*/

    public static String getFromUser(Root r) {
        String type = r.getType().toString();

        // Todo: figure out how to parse these
//        ArrayList<String> createSet = new ArrayList<String>();
//        createSet.add("CreateEvent");
//        createSet.add("DeleteEvent");

        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(r.getPayload().toString());
        } catch(IOException e) {
            System.err.println("Oops: json mapper failed to parse the payload");
            // TODO: better error handling
            return null;
        }

        ArrayList<String> issuesSet = new ArrayList<String>();
        issuesSet.add("IssueCommentEvent");
        issuesSet.add("IssuesEvent");

        ArrayList<String> repoExtractSet = new ArrayList<String>();
        repoExtractSet.add("PushEvent");
        repoExtractSet.add("WatchEvent");

        if (type.equals("CommitCommentEvent"))
            return extractFromUserFromComment(rootNode);
        else if (type.equals("IssueCommentEvent"))
            return extractFromUserFromIssueComment(rootNode, r);
        else if (type.equals("IssuesEvent"))
            return extractFromUserFromIssues(rootNode);
        else if (type.equals("ForkEvent"))
            return extractFromUserFromFork(rootNode, r);
        else if (type.equals("MemberEvent"))
            return extractFromUserFromMember(rootNode);
        else if (type.equals("PullRequestEvent"))
            return extractFromUserFromPullRequest(rootNode);
        else if (type.equals("PullRequestReviewCommentEvent"))
            return extractFromUserFromPullRequestReviewComment(rootNode);
        else if (repoExtractSet.contains(type))
            return extractFromUserFromPayloadRepo(rootNode, r);

        return null;
    }

    private static String from_parseOrg(Root r) {
	Org o = r.getOrg();
	if (o == null)
	    return null;
	CharSequence cl = o.getLogin();
	if (cl == null)
	    return null;
	return cl.toString();
    }

    private static String from_parseRepoName(Root r) {
	Repo rep = r.getRepo();
	if (rep == null)
	    return null;
	CharSequence cl = rep.getName();
	if (cl == null)
	    return null;
	String ret = cl.toString();
	int idx = ret.indexOf("/");
	if (idx <= 0)
	    return null;
	ret = ret.substring(0, idx);
	return ret;
    }

    private static String extractFromUserFromComment(JsonNode rootNode) {
        return rootNode.path("comment").path("user").path("login").asText();
    }
    private static String extractFromUserFromFork(JsonNode rootNode, Root r) {
        String ret = rootNode.path("sender").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;

	// try org
	ret = from_parseOrg(r);
	if (ret != null && ! ret.equals(""))
	    return ret;

	// try from repo name
	ret = from_parseRepoName(r);
	if (ret != null && ! ret.equals(""))
	    return ret;

	// try actor
	CharSequence retcl = r.getActor().getLogin();
     	if (retcl != null) {
	    ret = retcl.toString();
	    if (ret != null && ! ret.equals(""))
		return ret;
	}
	return null;
    }
    private static String extractFromUserFromIssueComment(JsonNode rootNode, Root r) {
        String ret = rootNode.path("comment").path("user").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	Actor a = r.getActor();
	if (a == null)
	    return null;
	CharSequence cl = a.getLogin();
	if (cl == null)
	    return null;
	ret = cl.toString();
	return ret;
    }
    private static String extractFromUserFromMember(JsonNode rootNode) {
        String ret = rootNode.path("sender").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	ret = rootNode.path("actor").asText();
	return ret;
    }
    private static String extractFromUserFromIssues(JsonNode rootNode) {
        String ret = rootNode.path("sender").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	ret = rootNode.path("actor").asText();
	return ret;
    }
    private static String extractFromUserFromPullRequest(JsonNode rootNode) {
        String ret = rootNode.path("pull_request").path("user").path("login").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	ret = rootNode.path("actor").asText();
	return ret;
    }
    private static String extractFromUserFromPullRequestReviewComment(JsonNode rootNode) {
        return rootNode.path("comment").path("user").path("login").asText();
    }
    private static String extractFromUserFromPayloadRepo(JsonNode rootNode, Root r) {
	String ret = rootNode.path("actor").asText();
	if (ret != null && ! ret.equals(""))
	    return ret;
	Actor a = r.getActor();
	if (a == null)
	    return null;
	CharSequence cl = a.getLogin();
	if (cl == null)
	    return null;
	ret = cl.toString();
	return ret;
    }


    /*----------------------------------------------------------------
     Parse URL
     ----------------------------------------------------------------*/


    public static String getUrl(Root r) {
        String type = r.getType().toString();

        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(r.getPayload().toString());
        } catch(IOException e) {
            System.err.println("Oops: json mapper failed to parse the payload");
            // TODO: better error handling
            return null;
        }

        ArrayList<String> issuesSet = new ArrayList<String>();
        issuesSet.add("IssueCommentEvent");
        issuesSet.add("IssuesEvent");

        ArrayList<String> repoExtractSet = new ArrayList<String>();
        repoExtractSet.add("PushEvent");
        repoExtractSet.add("ReleaseEvent");
        repoExtractSet.add("WatchEvent");

        if (type.equals("CommitCommentEvent"))
            return extractURLFromComment(rootNode);
        else if (issuesSet.contains(type))
            return extractURLFromIssueComment(rootNode);
        else if (type.equals("ForkEvent"))
            return extractURLFromFork(rootNode);
        else if (type.equals("MemberEvent"))
            return extractURLFromRoot(r);
        else if (type.equals("PullRequestEvent"))
            return extractURLFromPullRequest(r);
        else if (type.equals("PullRequestReviewCommentEvent"))
            return extractURLFromComment(rootNode);
        else if (repoExtractSet.contains(type))
            return extractURLFromRoot(r);

        return null;
    }

    private static String extractURLFromComment(JsonNode rootNode) {
        return rootNode.path("comment").path("url").asText();
    }
    private static String extractURLFromIssueComment(JsonNode rootNode) {
        return rootNode.path("issue").path("url").asText();
    }
    private static String extractURLFromFork(JsonNode rootNode) {
        return rootNode.path("forkee").path("url").asText();
    }
    private static String extractURLFromPullRequest(Root r) {
        return extractURLFromRoot(r);
    }
    private static String extractURLFromRoot(Root r) {
	Repo repo = r.getRepo();
	if (repo == null)
	    return null;
	CharSequence url = repo.getUrl();
	if (url == null)
	    return null;
        return url.toString();
    }


    private static String getUserFromURL(Root r) {
	String ret = null;
	CharSequence retcl = r.getRepo().getUrl();
	if (retcl != null) {
	    ret = retcl.toString();
	    Pattern urlNameMatcher = Pattern.compile("^.*://[^/]*github.*/(.*)/.*$");
	    Matcher matcher = urlNameMatcher.matcher(ret);
	    if (matcher.matches()) {
		return matcher.group(1);
	    }
	}
	return ret;
    }
}
