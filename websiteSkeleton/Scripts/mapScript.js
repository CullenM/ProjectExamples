function init() {
  var myLatlng = new google.maps.LatLng(39.628856, -105.137724);
	var mapOptions = {
		center: myLatlng,
		zoom: 11
	};
  var map = new google.maps.Map(document.getElementById("mapCanvas"),
    mapOptions);
    var marker = new google.maps.Marker({
      position: myLatlng,
      map: map,
      title: "Hello World!"
    });
  }
google.maps.event.addDomListener(window, "load", init);