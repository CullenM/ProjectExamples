<?php

class ServicesPage extends Page{
	function __construct(){
		parent:: __construct();
	}
	function content()
	{
		echo"<div id=content>";
			echo "This is the Services page.";
		echo"</div>";
	}
}