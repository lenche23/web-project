$(document).ready(function(){
	loadPageForAdministrator();
});

function loadPageForAdministrator() {
	$.get({
			url: '../rest/administrators/loggedInAdministrator',
			success: function(administrator){
				if(administrator.username !== "") {
					$('#promoText').html("&nbsp" + administrator.firstName + " " + administrator.lastName);
					$('#firstName').val(administrator.firstName);
					$('#lastName').val(administrator.lastName);
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