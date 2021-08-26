$(document).ready(function(){
	loadPage();
});

function loadPage() {
	$.get({
			url: '../rest/managers/loggedInManager',
			success: function(manager){
				$('#buyersText').html("&nbsp&nbsp" + manager.firstName + " " + manager.lastName);
				$("#logo").attr('src', "../images/" + manager.restaurant.logo );
				$('#name').val(manager.restaurant.name);
				$('#address').val(manager.restaurant.location.address);
				$('#type').val(manager.restaurant.type);
				$('#grade').val('-');
				if(manager.restaurant.status === "OPEN")
					$('#status').val("Otvoren");
				else if(manager.restaurant.status === "CLOSED")
					$('#status').val("Zatvoren");
			}
	})
}