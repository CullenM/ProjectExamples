<?php

class HomePage extends Page{
	function __construct(){
		parent:: __construct();
	}
	function content()
	{
		echo"<div id=content>";
			echo "This is the Home page.";
		echo"</div>";
	}

}
