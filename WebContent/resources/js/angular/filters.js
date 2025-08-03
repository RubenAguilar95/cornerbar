(function() {
	'use strict';

	angular
		.module('cornerbarApp.filters', [])
		.filter('followState', followStateFilter)
		.filter('shuffleArray', shuffleArrayFilter)
		
	
	function followStateFilter() {
		return function(input, list) {
			if(list != null && input != null) {
				var retorno = "SEGUIR";
				for(var contact in list) {
					if(list[contact].contact.id == input.id){
						retorno = "DEJAR DE SEGUIR";
					}
				}
				return retorno;
			} else {
				return "Esperando respuesta..";
			}
		}
	}
	
	function shuffleArrayFilter() {
		return function(input) {
			var j, x, i;
			var array = input.slice(0);
		    for (i = array.length; i; i--) {
		        j = Math.floor(Math.random() * i);
		        x = array[i - 1];
		        array[i - 1] = array[j];
		        array[j] = x;
		    }
		  return array;
		}
	}
	
		
		
})();