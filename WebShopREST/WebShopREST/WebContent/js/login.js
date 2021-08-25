$(document).ready(function(){
	$('#errorDiv').hide();
	
	$('#submit').click(function(){
		let username = $('#username').val();
		let password = $('#password').val();
		var userFound = false;
		
		$('#errorDiv').hide();

		$.get({
			url: "../rest/buyers/login?username=" + username + "&password=" + password,
			success: function(buyer){
				if(buyer)
					userFound = true;
				else {
					$.get({
						url: "../rest/managers/login?username=" + username + "&password=" + password,
						success: function(manager){
							if(manager) {
								userFound = true;
								window.location.replace("../html/manager.html");
							}
							else {
								$.get({
									url: "../rest/deliverers/login?username=" + username + "&password=" + password,
									success: function(deliverer){
										if(deliverer)
											userFound = true;
										else {
											$.get({
												url: "../rest/administrators/login?username=" + username + "&password=" + password,
												success: function(administrator){
													if(administrator) {
														userFound = true;
														window.location.replace("../html/administrator.html");	
													}
													else {
														$('#errorDiv').show();
													}
												}
											});
										}
									}
								});
							}
						}
					});
				}
			}
		});
	});
});