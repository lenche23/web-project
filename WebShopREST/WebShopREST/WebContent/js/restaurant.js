$(document).ready(function(){
	loadRestaurantPage();
});

function loadRestaurantPage() {
	$.get({
			url: '../rest/restaurants/getViewedRestaurant',
			success: function(restaurant){
				$('#promoText').html("&nbsp" + restaurant.name);
				$("#logo").attr('src', "../images/" + restaurant.logo );
				$('#address').val(restaurant.location.address);
				$('#type').val(restaurant.type);
				$('#grade').val('-');
				if(restaurant.status === "OPEN")
					$('#status').val("Otvoren");
				else if(restaurant.status === "CLOSED")
					$('#status').val("Zatvoren");
				$('#articlesTable').hide();
				$('#commentsTable').hide();
			}
	})
}