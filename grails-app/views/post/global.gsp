<%--
  Created by IntelliJ IDEA.
  User: Brian
  Date: 15/01/2016
  Time: 10:19 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Global Timeline</title>
    <meta name="layout" content="main"/>
</head>

<body>
<sec:ifLoggedIn>
    <g:form name="logoutForm" controller="logout" action="index">
        <g:submitButton name="signOut" value="sign out"/>
    </g:form>
</sec:ifLoggedIn>

<div id="allPosts">
    <g:each in="${posts}" var="post">
        <div class="postEntry">
            <div class="postText">
                ${post.content}
            </div>
            <div class="postDate">
                <!--${post.dateCreated}-->
                <hub:dateFromNow date="${post.dateCreated}"/>
            </div>
        </div>
    </g:each>
</div>
<g:paginate action="global" total="${postCount}" max="3"/>
</body>
</html>