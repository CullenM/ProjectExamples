<?php

class Page extends General
{
	function __construct(){
		parent:: __construct();
		$this->at="";
		$this->menu=array();
		$this->title="";
		$this->style="page.css";

		$vars=new Vars();
		$this->pages=$vars->pages;
		$this->email=$vars->email;
		$this->gMapsKey=$vars->gMapsKey;
	}
	function generate(){
		$this->header();
		$this->doctype();
		$this->html();
	}
	function header(){
		echo "<!--Code made by Cullen McDevitt -->";
	}
	function doctype(){
		echo "<!DOCTYPE html>";
	}
	function html(){
		echo"<html>";
		$this->head();
		$this->body();
		echo"</html>";
	}
	function head(){
		echo"<head>";
		$this->style();
		$this->title();
		$this->script();
		echo"</head>";
	}
	function style(){
		echo"<link type='text/css' rel='stylesheet' href='Pages/style1.css'/>\n";
	}
	function title(){
		echo"<title>".$this->pages[$this->at]."</title>";
	}
	function script(){
		echo"<script src='Scripts/jQuery.js'></script>\n";
		echo"<script type='text/javascript' src='Scripts/menuScript.js'></script>\n";
		$this->anotherScript();
	}
	function anotherScript(){
    
  	}
	function body(){
		echo"<body>";
		$this->banner();
		$this->news();
		$this->login();
		$this->menu();
		$this->content();
		echo"</body>";
	}

	function banner(){
		echo"<div id=banBckgrnd>";
			echo"<div id=header>";
				echo"<h1>";
					echo"Banner </br>";
				echo"</h1>";
			echo"</div>";
		echo"</div>";
	}
	function news(){
		echo"<div id=newsBanner>";
		$news=file_get_contents('news.txt');
			echo "NEWS: ".$news;
		echo"</div>";
	}
	function login(){

	}
	function menu(){
		echo"<div id=menu>";
			echo"<ul>";
				foreach ($this->pages as $key=>$val) {
					echo "<a href='index.php?at=$key'><li>$val</li></a>";
				}
			echo"</ul>";
		echo"</div>";
	}
	function content(){
		echo"<div id=content>";
			echo $this->at."!!!</br>";
		echo"</div>";
	}
	function footer(){
		echo"<footer>";
			$this->fCont();
		echo"</footer>";
	}
	function fCont(){

	}
}