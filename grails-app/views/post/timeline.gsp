<%--
  Created by IntelliJ IDEA.
  User: Brian
  Date: 12/01/2016
  Time: 11:04 AM
--%>

<html>
<head>
    <title>
        Timeline for ${user.profile ? user.profile.fullName : user.loginId}
    </title>
    <meta name="layout" content="main"/>
    <g:javascript library="jquery"/>
    <g:if test="${user.profile?.skin}">
        <g:external dir="css" file="${user.profile.skin}.css"/>
    </g:if>
</head>
<body>
<sec:ifLoggedIn>
    <g:form name="logoutForm" controller="logout" action="index">
        <g:submitButton name="signOut" value="sign out"/>
    </g:form>
</sec:ifLoggedIn>

<g:javascript>
    function clearPost(e) {
        $('#postContent').val('');
    }
    function showSpinner(visible) {
        if (visible) $('#spinner').show();
        else $('#spinner').hide();
    }
    function addTinyUrl(data) {
        var tinyUrl = data.urls.small;
        var postBox = $("#postContent")
        postBox.val(postBox.val() + tinyUrl);
        toggleTinyUrl();
        $("#tinyUrl input[name='fullUrl']").val('');
    }
</g:javascript>

<h1>Timeline for ${user.profile ? user.profile.fullName : user.loginId}</h1>
<g:if test="${flash.message}">
    <div class="flash">
        ${flash.message}
    </div>
</g:if>

<div id="newPost">
    <h3>
        What is ${user.profile.fullName} hacking on right now?
    </h3>
    <p>
        <g:form>
            <g:textArea id="postContent" name="content" rows="3" cols="50"/><br/>
            <g:submitToRemote value="Post"
                              url="[controller: 'post', action: 'addPostAjax']"
                              update="allPosts"
                              onSuccess="clearPost(data)"
                              onLoading="showSpinner(true)"
                              onComplete="showSpinner(false)"/>
            <br/>
            <a href="#" id="showHideUrl" onclick="toggleTinyUrl().slideToggle(300); return false;">
                TinyURL Bar
            </a>
            <g:img id="spinner" style="display: none"
                   uri="/images/spinner.gif"/>
        </g:form>
    <div id="tinyUrl" style="display:none;">
        <g:formRemote name="tinyUrlForm" url="[action: 'tinyUrl']" onSuccess="addTinyUrl(data);">
            TinyUrl: <g:textField name="fullUrl"/>
            <g:submitButton name="submit" value="Make Tiny"/>
        </g:formRemote>
    </div>
    <r:script disposition="head">
        function toggleTinyUrl() {
            var toggleText = $('#showHideUrl');
            if ($('#tinyUrl').is(':visible')) {
                $('#tinyUrl').slideUp(300);
                toggleText.innerText = 'Hide TinyURL';
            } else {
                $('#tinyUrl').slideDown(300);
                toggleText.innerText = 'Show TinyURL';
            }
        }
    </r:script>
</p>
</div>

<div id="allPosts">
    <!--<g:each in="${user.posts}" var="post">
        <div class="postEntry">
            <div class="postText">
        ${post.content}
        </div>
        <div class="postDate">
        <hub:dateFromNow date="${post.dateCreated}"/>
        </div>
    </div>
    </g:each>-->
    <g:render template="postEntry" collection="${user.posts}" var="post"/>
</div>
</body>
</html>