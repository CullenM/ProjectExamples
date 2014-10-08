<?php

class ContactPage extends Page{
	function __construct(){
		parent:: __construct();
	}
	function anotherScript(){
		echo"<script 
      src='https://maps.googleapis.com/maps/api/js?key=$this->gMapsKey'>
    </script>\n";
    echo"<script src='Scripts/mapScript.js'></script>\n";
	}
	function content()
	{
		echo"<div id=content>";
			echo "This is the Contact page.";

			echo'<div id="mapCanvas"></div>';

			echo '<div id="emailForm">'; 
				echo"<h2>Feedback Form</h2>";
        $this->email();        
			echo'</div>';

		echo"</div>";
  }
  function email(){
    //if submitted check data and then send else display email form
    if (isset($_POST["submit"])) {
      $from=$_POST["from"];
      $subject=$_POST["subject"];
      $message = $_POST["message"];

      //check if email addr is valid
      if(filter_var($from, FILTER_VALIDATE_EMAIL)){
        //check length of email addr
        if($from>255){$from=substr($from,0,254);}
        //add 'From: ' for mail() 
        $from = 'From: '.$_POST["from"];

        //check if subject line is valid & sanitze
        if(!empty($subject)){
          if(strlen($subject)>78){
            $subject=substr($subject,0,77);
          }
          htmlspecialchars(strip_tags(trim($subject)));
        }
        else{$subject="No Subject.";}
                      
        //sanitize message 
        if(!empty($message)){
          htmlspecialchars(htmlentities(trim($message)));
          $message=wordwrap($message,70);
        }

        // send mail
        if(mail($this->email,$subject,$message,$from)){
          echo "Your message has been sent.";
          $this->emailBttn();
        }
        else{
          echo"Something went horribly wrong!";
          $this->emailBttn();
        }
        //TODO $this->emailBttn();
        }
      else{
        //TODO make this better
        echo"invalid email";
        $this->emailForm();
      }
    } 
    else{
      $this->emailForm();
    }           
  }
  function emailForm(){
            echo'<form method="post" action="index.php?at=Contact">
            From: <input type="text" name="from"><br>
            Subject: <input type="text" name="subject"><br>
            Message: <textarea rows="10" cols="40" name="message"></textarea><br>
            <input type="submit" name="submit" value="Send Email">
          </form>';
  }
  function emailBttn(){

    echo'<form method="post" action"index.php?at=Contact">
    <input type="submit" name="return" value="Return">
    </form>';
    if (isset($_POST["return"])) {
      echo"IT WORKED!";
    }
    //TODO make button to return to email form
    /*echo'<script type="text/javascript">
      function init(){  
        var returnBttn=document.getElementById("returnBttn");
        printButton.addEventListener("click",returnForm,false);
      }
      function returnForm(){

      }
      window.addEventListener("load",init,false);
    </script>
    <form action="#">
      <script type="text/javascript"></script>
      <input id="returnBttn" type="button" value="Return">
    </form>';*/
  }
}