(function() {
	'use strict';
	
	angular
		.module('cornerbarApp.factories', [])
		.factory('UserFactory', ['$http', userFactory])
		.factory('globalFactory', ['$http', '$cookies', globalFactory])
		
		
	function userFactory($http) {
		return {

			getAllUsers : function() {
				return $http.get('http://localhost/miredsocial/user/')
						.then(
								function(response) {
									return response.data;
								},
								function(err) {
									console.log('Error en: userFactory.getAllUsers'+ err)
									return err;
								}
							);
			},

			createUser : function(user) {
				return $http.post('http://localhost/miredsocial/user/', user)
						.then(
								function(response) {
									return response.data;
								},
								function(err) {
									console.log('Error en: userFactory.createUser')
									return err;
								}
							);
			}/*,

			updateUser: function(user, id) {
				return $http.put('http://localhost/miredsocial/user/' + id, user)
						.then(
								function(response) {
									return response.data;
								},
								function(err) {
									console.log('Error en: userFactory.updateUser')
									return err;
								}
							);
			}/*,

			deleteUser: function(id) {
				return $http.delete('http://localhost/miredsocial/user/' + id)
						.then(
								function(response) {
									return response.data;
								},
								function(err) {
									console.log('Error en: userFactory.deleteUser')
									return err;
								}
							);
			}*/

		}
	};
	
	function globalFactory($http, $cookies) {
		console.log("GLOBAL FACTORY");
		var fact = this;
		
//		var userLogged = {
//				value : false
//		};
//		
//		userLogged.getValue = function() {
//			return this.value;
//		}
//		
//		userLogged.setValue = function(newVal){
//			this.value = newVal;
//		}
		
		fact.userLogged;
		fact.actualGlobalUser;
		
		return {
			getGlobalUser : function() {
				var url;
				if($cookies.get("rememberMe")) {
					url = 'http://localhost:8080/proyectoDAM/rest/userRememberMe';
				}else {
					url = 'http://localhost:8080/proyectoDAM/rest/user';
				}
				
				return $http.get(url);
			},
			getActualGlobalUser : function() {
				return fact.actualGlobalUser;
			},
			setActualGlobalUser : function(actualUser) {
				fact.actualGlobalUser = actualUser;
			},
			getUserLogged : function() {
				fact.userLogged = $cookies.get("token") != null || $cookies.get("rememberMe") != null;
				return fact.userLogged;
			},
			loginUser : function() {
				console.log("User logging in");
				fact.userLogged = true;
			},
			logoutUser : function() {
				fact.userLogged = false;
			}
		}
	}
	
		
	
	
})();