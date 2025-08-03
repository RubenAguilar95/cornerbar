(function() {
	'use strict';

	angular
		.module('cornerbarApp.services', [])
		.service('loginService', ['$http', loginService])
		.service('registerService', ['$http', registerService])
		.service('globalService', ['$http', '$cookies', globalService])
		.service('publicationService', ['$http', publicationService])
		.service('userService', ['$http', userService])
		.service('commentService', ['$http', commentService])

	function loginService($http) {

	}
	
	function registerService($http) {
		var ctrl = this;
		
		ctrl.register = function(userRegister) {
			return $http.post('rest/register', userRegister);
		}
	}

	function globalService($http, $cookies) {
		var ctrl = this;
		console.log("GLOBAL SERVICE");

		ctrl.logout = function() {
			return $http.post('rest/logout');
//			return $http({
//				method : 'POST',
//				url : 'rest/logout',
//				dataType : 'json',
//				data : {
//					token : $cookies.get("token")
//				},
//				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//			})
		}
	}
	
	function publicationService($http) {
		var ctrl = this;
		console.log("PUBLICATION SERVICE");
		
		ctrl.getAllPublications = function() {
			
			return $http.get('rest/getAllPublications');
		}
		
		ctrl.getHomePublications = function(user) {
			return $http.post("rest/getHomePublications", JSON.stringify(user));
		}
		
		ctrl.getPublicationsFromUser = function(username) {
			return $http.get("rest/getPublicationsFromUser/" + username);
		}
		
		
		ctrl.publish = function(publicationContext) {
			return $http.post('rest/publish', JSON.stringify(publicationContext));
		}
		
		ctrl.like = function(likeWrapper) {
			return $http.post('rest/like', likeWrapper);
		}
	}
	
	function userService($http) {
		var ctrl = this;
		console.log("USER SERVICE");
		
		ctrl.getUserByUsername = function(username) {
			return $http.post('rest/getUserByUsername', JSON.stringify(username));
		}
		
		ctrl.updateGlobalUser = function(user) {
			return $http.post('rest/updateUser', JSON.stringify(user));
		}
		
		ctrl.getAllUsers = function() {
			return $http.get('rest/listUsers');
		}
		
		ctrl.follow = function(followWrapper) {
			return $http.post('rest/followUser', followWrapper);
		}
		
		ctrl.parseProfileImg = function(profileImg) {
			var url = 'resources/images/no-photo-perfil.jpg';
			if(profileImg != null && profileImg != '') {
				url = "https://s3.amazonaws.com/cornerbarproyectodam/" + profileImg;
			}
			return url;
		}
	}
	
	function commentService($http) {
		var ctrl = this;
		console.log("COMMENT SERVICE");
		
		ctrl.deleteComment = function(commentWrapper) {
			return $http.post('rest/deleteComment', commentWrapper);
		}
		
		ctrl.comment = function(commentWrapper) {
			return $http.post('rest/comment', commentWrapper);
		}
	}

})();