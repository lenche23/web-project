$(document).ready(function(){
	$('#errorDiv').hide();
	$('#blockDiv').hide();
	
	$('#submit').click(function(){
		let username = $('#username').val();
		let password = $('#password').val();
		var userFound = false;
		
		$('#errorDiv').hide();
		$('#blockDiv').hide();

		$.get({
			url: "../rest/buyers/login?username=" + username + "&password=" + password,
			success: function(buyer){
				if(buyer) {
					if(!buyer.blocked){
						userFound = true;
						window.location.replace("../html/userProfile.html");
					} else {
						$('#blockDiv').show();
					}
				}
				else {
					$.get({
						url: "../rest/managers/login?username=" + username + "&password=" + password,
						success: function(manager){
							if(manager) {
								if(!manager.blocked){
									userFound = true;
									if(manager.restaurant.name.length > 0){
										window.location.replace("../html/manager.html");
									}
									else{
										window.location.replace("../html/index.html");
									}
								} else {
									$('#blockDiv').show();
								}
							}
							else {
								$.get({
									url: "../rest/deliverers/login?username=" + username + "&password=" + password,
									success: function(deliverer){
										if(deliverer) {
											if(!deliverer.blocked) {
												userFound = true;
												window.location.replace("../html/userProfile.html");
											} else {
												$('#blockDiv').show();
											}	
										}
										else {
											$.get({
												url: "../rest/administrators/login?username=" + username + "&password=" + password,
												success: function(administrator){
													if(administrator) {
														if(!administrator.blocked) {
															userFound = true;
															window.location.replace("../html/administrator.html");	
														} else {
															$('#blockDiv').show();
														}
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