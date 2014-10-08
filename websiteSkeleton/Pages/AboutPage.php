<?php

class AboutPage extends Page{
	function __construct(){
		parent:: __construct();
	}
	function content()
	{
		echo"<div id=content>";
			echo "This is the About Us page.";
		echo"</div>";
	}
}