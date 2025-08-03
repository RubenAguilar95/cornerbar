(function() {
	'use strict';

	angular
		.module('cornerbarApp.controllers', [])
		.controller('globalCtrl', ['$rootScope', '$scope', '$window', '$location', 'globalFactory', 'globalService', globalCtrl])
		.controller('loginCtrl', ['$rootScope', '$scope', '$http', '$location', '$window', 'globalFactory', 'loginService', loginCtrl])
		.controller('registerCtrl', ['$scope', '$location', 'registerService', registerCtrl])
		.controller('homeCtrl', ['$scope', '$timeout', '$location', '$route', 'amazonConstants', 'userService', 'publicationService', 'globalFactory', homeCtrl])
		.controller('profileCtrl', ['$scope', '$timeout', '$location', '$routeParams', 'userService', 'publicationService', 'globalFactory',  profileCtrl])
		.controller('publicationCtrl', ['$http', '$scope', '$routeParams', '$anchorScroll', '$location', '$timeout', 'publicationService', 'globalFactory', publicationCtrl])
		.controller('simplePublicationCtrl', ['$scope', '$route', '$location', '$timeout', '$anchorScroll', 'publicationService', 'userService', 'commentService', 'globalFactory', simplePublicationCtrl])
		.controller('simpleCommentCtrl', ['commentService', 'userService', 'globalFactory', simpleCommentCtrl])


	function globalCtrl($rootScope, $scope, $window, $location, globalFactory, globalService) {
		console.log("GLOBAL CTRL");
		
		$scope.userLogged = globalFactory.getUserLogged();

		$scope.getGlobalUser = function(){
			globalFactory.getGlobalUser().then(
					function(response) {
						$scope.globalUser = response.data;
						globalFactory.setActualGlobalUser($scope.globalUser);
					},
					function(errResponse) {
						console.log(errResponse.status);
					}
			)
		}
//		if($scope.userLogged) {
//			$scope.getGlobalUser();
//		}
		console.log("GlobalCtrl -->" + $scope.userLogged);
		
		if($scope.userLogged) {
			$scope.getGlobalUser();
		}
		
		$scope.logout = function(){
			globalService.logout()
				.success(function(response) {
							if(response) {
								globalFactory.logoutUser();
								globalFactory.setActualGlobalUser(null);
//								$rootScope.loggedUser = false;
//								globalFactory.getUserLogged.setValue(false);
				            	$location.path("#/login");
				            	$window.location.reload();
				            }
				            else {
				            	alert("Se ha producido un error en logout");
				            }
				})
				.error(function(errRes) {
					console.log(errRes.status);
				})
		}
	}
	
	function loginCtrl($rootScope, $scope, $http, $location, $window, globalFactory, loginService) {
		$scope.username;
		$scope.password;
		$scope.rememberMe = false;
		$scope.loginError = false;
		
		$scope.login = function() {
			
			$http.post('rest/login', 
					{
						username : $scope.username,
						password : $scope.password,
						rememberMe : $scope.rememberMe
					}
			).success(function(response) {
				if(response) {
	            	console.log("Se ha confirmado el login");
	            	globalFactory.loginUser();
//	            	$rootScope.loggedUser = true;
//	            	globalFactory.getUserLogged.setValue(true);
	            	$location.path('home');
	            	$window.location.reload();
	            }
	            else {
	            	$scope.loginError = true;
	            }
			}).error(function(response) {
				console.log(response.status);
			})
		}
	}
	
	function registerCtrl($scope, $location, registerService) {
		console.log("REGISTER CTRL");
		$scope.userRegister = {};
		
		$scope.register = function() {
			registerService.register($scope.userRegister)
			.success(function(response) {
				if(response == "OK") {
					alert("Se ha registrado con éxito!");
					$location.path('login');
				} else {
					$scope.registerErrors = response;
				}
			})
			.error(function(respError) {
				console.log(respError.status);	
			}) 
		}
	}
	
	function homeCtrl($scope, $timeout, $location, $route, amazonConstants, userService, publicationService, globalFactory) {
		var ctrl = this;
		console.log("HOME CTRL");
		
		$scope.publications;
		
		userService.getAllUsers()
			.success(function(response) {
				console.log(response);
				$scope.allUsers = response;
			})
			.error(function(response) {
				console.log(response);
			})
		
		$timeout(function() {
			$scope.globalUser = globalFactory.getActualGlobalUser();
			$scope.profileImg = userService.parseProfileImg($scope.globalUser.nameProfileImage);
		});
		
		$scope.profileImgFn = function(profImg) {
			return userService.parseProfileImg(profImg);
		}
		
		$timeout(function() {
			publicationService.getHomePublications($scope.globalUser).then(function(response) {
				console.log(response);
				$scope.publications = response.data;
			});
		}, 100);
		
		$scope.file;
		
		$scope.addProfileImage = false;
		
		$scope.showInput = function() {
			$scope.addProfileImage = true;
		}
		
		$scope.hideInput = function() {
			$scope.addProfileImage = false;
		}
		
		$scope.upload = function() {
			  // Configure The S3 Object
			  AWS.config.update({ accessKeyId: amazonConstants.access_key, secretAccessKey: amazonConstants.secret_key });
			  AWS.config.region = amazonConstants.region;
			  var bucket = new AWS.S3({ params: { Bucket: amazonConstants.bucket } });
			 
			  if($scope.file) {
			    var params = { Key: parseToNameImage($scope.globalUser.userCredentials.username, $scope.file.name), ContentType: $scope.file.type, Body: $scope.file};
			 
			    bucket.putObject(params, function(err, data) {
			      if(err) {
			        // There Was An Error With Your S3 Config
			        alert(err.message);
			        return false;
			      }
			      else {
			    	  console.log(data);
			    	  $scope.uploaded = true;
			    	  $scope.saveImage();
			      }
			    })
			    .on('httpUploadProgress',function(progress) {
			          // Log Progress Information
			          console.log(Math.round(progress.loaded / progress.total * 100) + '% done');
			        });
			  }
			  else {
			    // No File Selected
			    alert('No se ha seleccionado ningún archivo');
			  }
		}
		
		
		$scope.saveImage = function() {
			console.log("SAVE IMAGEEE");
//			$scope.upload();
//			$timeout(function() {
				if($scope.file && $scope.uploaded) {
					$scope.globalUser.nameProfileImage = parseToNameImage($scope.globalUser.userCredentials.username, $scope.file.name);
					
					userService.updateGlobalUser($scope.globalUser)
					.success(function(response) {
						console.log(response);
						if(response) {
							$scope.globalUser = response;
							globalFactory.setActualGlobalUser($scope.globalUser);
							alert("Foto de perfil cambiada");
							$('#pickImageModal').modal('hide');
							$timeout(function() {
								$route.reload();
							}, 500);
						} else {
							alert("Usuario nulo!");
						}
					})
					.error(function(response) {
						console.log(response);
					})
				} else {
					alert("No se ha podido subir el fichero");
				}
//			}, 5000);
			
			
		}
		
		$scope.fileModel;
		
		$scope.previewImage = function() {
			console.log("CHANGE IMAGE");
			console.log($scope.file);
			console.log($scope.fileModel);
			var reader = new FileReader();
			
			reader.onloadend = function() {
				$scope.$apply(function(){
					$scope.pickedPhoto = reader.result;
					console.log($scope.pickedPhoto);
				});
				
			}
			if ($scope.file) {
			    reader.readAsDataURL($scope.file);
			} else {
				$scope.pickedPhoto = "";
			}
			
			$scope.uploaded = false;
		}
		

		$scope.getImage = function() {
			console.log("GET IMAGE");
			AWS.config.update({
				accessKeyId : amazonConstants.access_key,
				secretAccessKey : amazonConstants.secret_key
			});
			AWS.config.region = amazonConstants.region;

			var bucket = new AWS.S3({
				params : {
					Bucket : amazonConstants.bucket
				}
			});

			bucket.getObject({
				Key : 'fondo.jpg'
			}, function(err, file) {
				if(err) {
					alert("Error al obtener imagen");
				} else {
					$timeout(function() {
						var reader = new FileReader();
						var fileBlob = "data:image/jpeg;base64,"
							+ encode(file.Body);
						reader.onloadend = function () {
						    $scope.$s3url = reader.result;
						  }
						reader.readAsDataURL(fileBlob);
						console.log($scope.s3url);
					}, 1);
				}
			});
		}
		
		$scope.newPublication;
		
		$scope.publish = function() {
			console.log("ENTRANDO EN PUBLISH");
			if($scope.newPublication != null) {
			
				
				var publicationContext = {
						"publication" : $scope.newPublication,
						"publisher" : $scope.globalUser,
						"idUserWall" : $scope.globalUser.id
				};
				
				publicationService.publish(publicationContext)
					.success(function(response) {
						if(response != null) {
							$scope.publications = response;
							$scope.newPublication = {};
						} else {
							alert("No se ha podido realizar la publicación");
						}
					})
					.error(function(errResponse) {
						console.log(errResponse);
					})
			}
		}
		
		$scope.closeWithLinkContactModal = function(href) {
			$('#allContactsModal').modal('hide');
			$timeout(function() {
				$location.path(href);
			}, 500);
		}
		
		$scope.closeWithLinkUsersModal = function(href) {
			$('#allUsersModal').modal('hide');
			$timeout(function() {
				$location.path(href);
			}, 500);
		}
	}
	
	function encode(data) {
	    var str = data.reduce(function(a,b){ return a+String.fromCharCode(b) },'');
	    return btoa(str).replace(/.{76}(?=.)/g,'$&\n');
	}
	
	function parseToNameImage(username, image) {
		var divider = image.lastIndexOf(".jpg");
		var fullName = username + "profile" + image.slice(divider);
		
		if(divider === -1) {
			divider = image.lastIndexOf(".jpeg");
			fullName = username + "profile" + image.slice(divider);
		} if(divider === -1){
			divider = image.lastIndexOf(".png");
			fullName = username + "profile" + image.slice(divider);
		}
		
		return fullName;
		
		
	}
	
	function profileCtrl($scope, $timeout, $location, $routeParams, userService, publicationService, globalFactory) {
		var ctrl = this;
		console.log("PROFILE CTRL")
		
		$scope.profileUser;
		
		userService.getUserByUsername($routeParams.username)
			.success(function(response) {
				$scope.profileUser = response;
				$scope.profileImg = userService.parseProfileImg($scope.profileUser.nameProfileImage);
			})
			.error(function(response) {
				console.log(response);
			})
		
		publicationService.getPublicationsFromUser($routeParams.username)
		.success(function(response) {
			console.log("PUUUUUUUBLICATIONS FROM UUUUUSER");
			console.log(response);
			if(response != null && response != "") {
				$scope.publications = response;
			}
		})
		.error(function(response) {
			console.log(response);
		})
		
		$scope.profileImgFn = function(profImg) {
			return userService.parseProfileImg(profImg);
		}
		
		$scope.closeWithLinkContactModal = function(href) {
			$('#profileContactsModal').modal('hide');
			$timeout(function() {
				$location.path(href);
			}, 500);
		}
		
		$scope.follow = function() {
			var contactWrapper = {
					"user1" : $scope.globalUser,
					"user2" : $scope.profileUser
			}
			console.log(contactWrapper);
			
			userService.follow(contactWrapper)
				.success(function(response) {
					$scope.globalUser.contacts = response;
				})
				.error(function(response) {
					console.log(response);
				})
		}
		
	}

	function publicationCtrl($http, $scope, $routeParams, $anchorScroll, $location, $timeout, publicationService, globalFactory) {
		var ctrl = this;
		console.log("PUBLICATION CTRL");
		
		ctrl.pubsToShow = 15;
		
		ctrl.showMorePubs = function() {
			ctrl.pubsToShow += 8;
			console.log("MORE PUBS");
		}
		
		ctrl.endScroll = function(length) {
			return length < ctrl.pubsToShow;
		}
	}
	
	function simplePublicationCtrl($scope, $route, $location, $timeout, $anchorScroll, publicationService, userService, commentService, globalFactory) {
		var ctrl = this;
		
		ctrl.publication;

		ctrl.globalUser = globalFactory.getActualGlobalUser();
		
		var refUser;
		
		if(ctrl.publication.sharer != null) {
			refUser = ctrl.publication.sharer;
		} else {
			refUser = ctrl.publication.owner;
		}
		
		ctrl.profileImg = userService.parseProfileImg(refUser.nameProfileImage);
		
		ctrl.sharePub = function() {
			console.log("Entrando en SHAREPUB");
			
			var publicationContext = {
					"publication" : ctrl.publication,
					"publisher" : ctrl.globalUser,
					"idUserWall" : ctrl.globalUser.id
			};
			console.log(publicationContext);
			
			publicationService.publish(publicationContext)
			.success(function(response) {
				if(response != null) {
					$scope.$parent.publications = response;
//					$route.reload();
					alert("Se ha compartido con éxito!");
//					$location.hash("pub" + ctrl.indice)
				} else {
					alert("No se ha podido compartir la publicación");
				}
			})
			.error(function(errResponse) {
				console.log(errResponse);
			})
			
		}
		
		ctrl.follow = function() {
			var toFollow;
			if(ctrl.publication.sharer != null) {
				toFollow = ctrl.publication.sharer;
			} else {
				toFollow = ctrl.publication.owner;
			}
			var contactWrapper = {
					"user1" : ctrl.globalUser,
					"user2" : toFollow
			};
			
			if(ctrl.globalUser.userCredentials.username != toFollow) {
				userService.follow(contactWrapper)
					.success(function(response) {
						if(response) {
							ctrl.globalUser.contacts = response;
						} else {
							alert("NO SE HA PODIDO SEGUIR A " + toFollow.name + " " + toFollow.surname1)
						}
					})
					.error(function(response) {
						console.log(response);
					})
			}
		}
		
		
		ctrl.newComment;
		
		ctrl.comment = function() {
			var commentWrapper = {
					"comment" : {
						"text": ctrl.newComment,
						"owner": ctrl.globalUser
					},
					"idPublication" : ctrl.publication.id
			};
			console.log(commentWrapper);
			if(ctrl.newComment != null && ctrl.newComment != "") { 
				commentService.comment(commentWrapper)
					.success(function(response) {
						console.log(response);
						if(response != null) {
							ctrl.publication.comments = response;
							ctrl.newComment = null;
						}
					})
					.error(function(response) {
						console.log(response);
					})
			}
		}
		
		ctrl.like = function() {
			var likeWrapper = {
					"idPublication" : ctrl.publication.id,
					"username" : ctrl.globalUser.userCredentials.username
			};
			
			publicationService.like(likeWrapper)
				.success(function(response) {
					if(response != null) {
						ctrl.publication.likes = response;
					} else {
						console.log("DATOS INCORRECTOS");
					}
				})
				.error(function(response) {
					console.log(response);
				})
		}
		
		ctrl.showLikes = false;
		
		var timer; 
		var cancel = false;
		
		ctrl.seeLikes = function(num) {
			ctrl.showLikes = true;
//			if(num) {
//				ctrl.showLikes = true;
//			} else {
//				timer = $timeout(function() {
//					console.log("SEELIKES -->" + cancel);
//					if(!cancel) {
//						ctrl.showLikes = true;
//					} else {
//						cancel = false;
//					}
//				}, 500);
//			}
		}
		
		ctrl.dontSeeLikes = function() {
//			cancel = true;
//			$timeout.cancel(timer);
			ctrl.showLikes = false;
		}
	}
	
	function simpleCommentCtrl(commentService, userService, globalFactory) {
		var ctrl = this;
		
		ctrl.globalUser = globalFactory.getActualGlobalUser();
		ctrl.comment;
		ctrl.pubid;
		
		ctrl.profileImg = userService.parseProfileImg(ctrl.comment.owner.nameProfileImage);
		
		ctrl.deleted = false;
		
		ctrl.deleteComment = function() {
			var commentWrapper = {
					"comment" : ctrl.comment,
					"idPublication" : ctrl.pubid
			};
			console.log(commentWrapper);
			
			commentService.deleteComment(commentWrapper)
				.success(function(response) {
					console.log(response);
					if(response != null) {
						ctrl.deleted = true;
					}
				})
				.error(function(response) {
					
				})
		}
	}
	
})();