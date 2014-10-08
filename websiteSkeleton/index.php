<?php
/*if(mail('acount4junkmail@gmail.com','Test Mail','This is a Test','From: cullenmcdevitt@gmail.com')){
	echo "It worked!";
}
else{
	echo"Something went horribly wrong";
}*/
require_once('include.php');

$vars=new Vars();

$at="Home";

if(!empty($_GET["at"])){
	$at=$_GET["at"];
}
if(!preg_match('/^[a-zA-Z0-9][a-zA-Z0-9]*$/',$at)||!$vars->checkPage($at)){
	$action="home";
}
else{
	$action=$at;
}

$PageClass = ucfirst($action)."Page";
$page=new $PageClass();
$page->at=$action;

$page->generate();