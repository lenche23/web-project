$(document).ready(function(){
	loadPageForAdministrator();
	
	
});

function loadPageForAdministrator() {
	$.get({
			url: '../rest/administrators/loggedInAdministrator',
			success: function(administrator){
				if(administrator.username !== "") {
					$('#firstName').val(administrator.firstName);
					$('#lastName').val(administrator.lastName);
					$('#username').val(administrator.username);
					$('#email').val(administrator.email);
					
					let date = new Date(administrator.dateOfBirth);
					var day = ("0" + date.getDate()).slice(-2);
					var month = ("0" + (date.getMonth() + 1)).slice(-2);
					var dateFormat = date.getFullYear()+"-"+(month)+"-"+(day) ;
					$('#dateOfBirth').val(dateFormat);
					
					$('#password').val(administrator.password);
					if(administrator.gender === "MALE")
						$('#gender').val("Muško");
					else
						$('#gender').val("Žensko");
				}
			}
	})
}