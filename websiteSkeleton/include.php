<?php
	spl_autoload_register('AutoLoader::Loader');

	class Autoloader{
		static function Loader($class){
			if (preg_match('/.+Page.*/',$class)) {
  				$path='Pages/';
			}
			else{
				$path='Classes/';
			}
			//echo $path.$class.'.php';
    		include $path.$class.'.php';
		}
	}

	/*include 'Classes/General.class.php';
	include 'Classes/Page.class.php';
	include 'Pages/Home.Page.php';	
	include 'Pages/About.Page.php';
	include 'Pages/Contact.Page.php';
	include 'Pages/Services.Page.php';*/

?>