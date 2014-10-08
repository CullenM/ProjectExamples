<?php
class Vars{
	//private $pages;
	function __construct(){
		$this->pages=array("Home"=>"Home","Services"=>"Services",
			"About"=>"About Us","Contact"=>"Contact Us");
		$this->email="cullenmcdevitt@gmail.com";
		$this->gMapsKey="AIzaSyC68s9vZ7esLMzwcFdMAVdtbphnAQVz8aM";
	}	
	public function checkPage($check){
		$match=false;
		foreach ($this->pages as $key => $val) {
			if($key==$check){$match=true;}
		}
		return $match;
	}
	public function givePages(){
		return $this->pages;
	}
}
?>