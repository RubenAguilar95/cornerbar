(function() {
	'use strict';

	angular
		.module('cornerbarApp.directives', [])
		.directive('publications', publications)
		.directive('simplePublication', simplePublication)
		.directive('simpleComment', simpleComment)
//		.directive('profileImage', ['$compile', profileImage])
		.directive('autoResize', autoResize)
		.directive('sendOnEnter', sendOnEnter)
		.directive('file', file)
		.directive('customOnChange', customOnChange)

	
	function publications() {
		return {
			restrict: 'E',
			scope: { } ,
			bindToController : {
				publications: '='
			},
			controller: 'publicationCtrl',
			controllerAs: 'pubCtrl',
			templateUrl: 'views/templates/publications.html'
		}
	}
		
		
	function simplePublication() {
		return {
			restrict: 'E',
			scope: { } ,
			bindToController : {
				publication: '=',
				indice: '@'
			},
			controller: 'simplePublicationCtrl',
			controllerAs: 'sPubCtrl',
			templateUrl: 'views/templates/simple-publication.html'
		}
	}
	
	function simpleComment() {
		return {
			restrict: 'E',
			scope: { },
			bindToController : {
				comment: '=',
				pubid: '@'
			},
			controller: 'simpleCommentCtrl',
			controllerAs: 'sCommCtrl',
			templateUrl: 'views/templates/simple-comment.html'
		}
	}
	
//	function profileImage($compile) {
//		return {
//			restrict: 'AE',
//			scope: {
//				userImage: '@'
////				piwidth: '=',
////				piheight: '='
//			},
////			templateUrl: 'views/templates/profile-image.html'
//			link: function(scope, element, attrs) {
//				console.log("HEEEEEEEEEY");
//				var url = 'resources/images/no-photo-perfil.jpg';
//				if(scope.userImage != null && scope.userImage != '') {
//					url = "https://s3.amazonaws.com/cornerbarproyectodam/" + userImage;
//				}
//				console.log(scope.userImage);
//				console.log(url);
////				
//				element.attr('ng-src', url);
////				attrs.$set('ng-src', url);
//			}
//		}
//	}
	
	// ADICIONALES
	
	function file() {
		return {
		    restrict: 'AE',
		    scope: {
		      file: '@'
		    },
		    link: function(scope, el, attrs){
		      el.bind('change', function(event){
		        var files = event.target.files;
		        var file = files[0];
		        scope.file = file;
		        scope.$parent.file = file;
		        scope.$apply();
		      });
		    }
		  };
	}
	
	
	function autoResize() {
		return function(scope, element, attr){
		    var minHeight = element[0].offsetHeight,
		      paddingLeft = element.css('paddingLeft'),
		      paddingRight = element.css('paddingRight');

		    var $shadow = angular.element('<div></div>').css({
		      position: 'absolute',
		      top: -10000,
		      left: -10000,
		      width: element[0].offsetWidth - parseInt(paddingLeft || 0) - parseInt(paddingRight || 0),
		      fontSize: element.css('fontSize'),
		      fontFamily: element.css('fontFamily'),
		      lineHeight: element.css('lineHeight'),
		      resize:     'none'
		    });
		    angular.element(document.body).append($shadow);

		    var update = function() {
		      var times = function(string, number) {
		        for (var i = 0, r = ''; i < number; i++) {
		          r += string;
		        }
		        return r;
		      }

		      var val = element.val().replace(/</g, '&lt;')
		        .replace(/>/g, '&gt;')
		        .replace(/&/g, '&amp;')
		        .replace(/\n$/, '<br/>&nbsp;')
		        .replace(/\n/g, '<br/>')
		        .replace(/\s{2,}/g, function(space) { return times('&nbsp;', space.length - 1) + ' ' });
		      $shadow.html(val);

		      element.css('height', Math.max($shadow[0].offsetHeight + 10 /* the "threshold" */, minHeight) + 'px');
		    }

		    element.bind('keyup keydown keypress change', update);
		    update();
		  }
	}
	
	function sendOnEnter() {
		return function (scope, element, attrs) {
	        element.bind("keydown keypress", function (event) {
	            if(event.which === 13 && !event.shiftKey) {
	                scope.$apply(function (){
	                    scope.$eval(attrs.sendOnEnter);
	                });

	                event.preventDefault();
	            }
	        });
	    };
	}
	
	function customOnChange() {
		 return {
			    restrict: 'A',
			    link: function (scope, element, attrs) {
			      var onChangeHandler = scope.$eval(attrs.customOnChange);
			      element.on('change', onChangeHandler);
			    }
		  };
	}

})();