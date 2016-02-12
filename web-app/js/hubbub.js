angular.module('Hubbub', ['restangular']).config(
    function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/hubbub/api');
    }
);

function PostCtrl($scope, Restangular) {
    var postsApi = Restangular.all("posts");

    $scope.allPosts = []
    $scope.refreshPosts = function() {
        postsApi.getList().then(function(newPostList) {
            $scope.allPosts = newPostList;
        }, function(errorResponse) {
            alert("Error on refreshing posts: " + errorResponse.status);
        });
    }

    $scope.$on('newPost', function() {
        $scope.refreshPosts();
    });

    $scope.refreshPosts();
}

function NewPostCtrl($scope, $rootScope, Restangular) {
    $scope.postContent = "";
    $scope.charsRemaining = function() {
        return 140 - $scope.postContent.length;
    }

    $scope.newPost = function() {
        var postApi = Restangular.one("posts");
        var newPost = { message: $scope.postContent };
        postApi.post(null, newPost).then(function(response) {
            $rootScope.$broadcast("newPost", newPost);
            $scope.postContent = "";
        }, function(errorResponse) {
            alert("Error on creating post: " + errorResponse.status);
        });
    }

    $scope.postInvalidLength = function() {
        return $scope.postContent.length == 0 || $scope.postContent.length > 140
    }
}

function EditPostCtrl($scope, $rootScope, Restangular) {
    $scope.isEditState = false;

    $scope.editedContent = $scope.post.message; // tenia content en lugar de message

    $scope.activate = function() {
        $scope.isEditState = true;
    }

    $scope.desactivate = function() {
        $scope.isEditState = false;
    }

    $scope.originalContent = $scope.post.message;
    $scope.editedContent = $scope.post.message;

    $scope.updatePost = function() {
        isEditState = false;

        $scope.post.message = $scope.editedContent;
        $scope.post.put().then(
            function() {
                $scope.isEditState = false;
            }, function(errorResponse) {
                $scope.post.message = $scope.originalContent;
                alert("Error saving object:" + errorResponse.status);
            }
        );
    }

    $scope.deletePost = function() {
        isEditState = false;

        $scope.post.message = $scope.editedContent;
        $scope.post.remove().then(
            function() {
                $rootScope.$broadcast("deletePost", $scope.post);
                $scope.refreshPosts(); // agregué yo
            }, function(errorResponse) {
                alert("Error saving object:" + errorResponse.status);
            }
        );
    }

    $scope.$on('deletePost', function(event, postToDelete) {
        $scope.allPosts = _.filter($scope.allPosts, function(nextPost) {
            return nextPost.id != postToDelete.id
        });
    });
}
