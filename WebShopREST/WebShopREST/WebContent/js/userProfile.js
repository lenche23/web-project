$(document).ready(function(){
	loadPageForAdministrator();
	loadPageForManager();
});

function loadPageForAdministrator() {
	$.get({
			url: '../rest/administrators/loggedInAdministrator',
			success: function(administrator){
				if(administrator.username !== "") {
					$('#promoText').html("&nbsp" + administrator.firstName + " " + administrator.lastName);
					$('#username').val(administrator.username);
					$('#email').val(administrator.email);
					
					let date = new Date(administrator.dateOfBirth);
					var day = date.getDate();
				    var month = date.getMonth() + 1;
				    var year = date.getFullYear();
					$('#dateOfBirth').val([day, month, year].join('.'));
					
					if(administrator.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
					$('#mojePorudzbineTitleDiv').hide();
					$('#filterDiv').hide();
					$('#articlesTable').hide();
				}
			}
	})
}

function loadPageForManager() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				if(manager.username !== "") {
					$('#promoText').html("&nbsp" + manager.firstName + " " + manager.lastName);
					$('#username').val(manager.username);
					$('#email').val(manager.email);
					
					let date = new Date(manager.dateOfBirth);
					var day = date.getDate();
				    var month = date.getMonth() + 1;
				    var year = date.getFullYear();
					$('#dateOfBirth').val([day, month, year].join('.'));
					
					if(manager.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
					$('#mojePorudzbineTitleDiv').hide();
					$('#filterDiv').hide();
					$('#articlesTable').hide();
				}
			}
	})
}