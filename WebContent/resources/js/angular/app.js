(function() {
	angular
		.module('cornerbarApp', ['ngRoute', 'ngCookies', 'ngSanitize', 'infinite-scroll', 'cornerbarApp.services', 'cornerbarApp.factories', 'cornerbarApp.controllers', 'cornerbarApp.directives', 'cornerbarApp.filters', 'cornerbarApp.constants'])
		.run(runApp)
		.config(configApp)

	// Configuramos el comportamiento de la página para cada URL
	function configApp($routeProvider, $locationProvider, $cookiesProvider) {
		console.log("GLOBAL CTRL");

		//Utilizamos locationProvider para eliminar el hashtag "/#" de las URL, logrando asi mantener una "Prety URL" (URL amigable)
//		$locationProvider.html5Mode({ enabled: true});
		$cookiesProvider.defaults.path = '/' ;
		
		console.log("ANTES DE ROUTEPROVIDER");
		$routeProvider
			.when('/home/', {
				templateUrl: 'views/home.html',
				controller: 'homeCtrl'
			})
			.when('/login', {
				templateUrl: 'login.html',
				controller: 'loginCtrl'
			})
			.when('/register', {
				templateUrl: 'views/register.html',
				controller: 'registerCtrl'
			})
			.when('/profile/:username/', {
				templateUrl: 'views/profile.html',
				controller: 'profileCtrl'
			})
			.otherwise({
				template: '<h1>ERROR 404: Página no encontrada</h1>'
			})
			
		console.log("DESPUES DE ROUTEPROVIDER");

	}

	// Controlamos la aplicación mientras funciona
	function runApp($rootScope, $cookies, $location, $window) {
		// Cada vez que cambie de URL, llamamos a la función "checkStatus" de la 
		// factoría "loginFactory" para saber qué página mostrar.
		console.log("EN RUNAPP");
		console.log("LOCATION PATH: " + $location.path());
		console.log('$location',$location.$$absUrl);
		if( $location.path() == '/login' && $cookies.get("token") != null ) {
			$location.path('/home');
		}
		if($location.path() == '' && $cookies.get("rememberMe") != null) {
			$location.path('/home');
		}
		$rootScope.$on('$routeChangeStart', function() {
			if($location.path() == '/login' || $location.path() == '/register') {
				if($cookies.get("token") || $cookies.get("rememberMe")){
					$location.path('/home');
				}
			}else {
				if(!($cookies.get("token") || $cookies.get("rememberMe"))){
					$location.path('/login');
				}
			}
		})

	}
})();