<?php
  //connect to sql
  $link = mysql_connect('localhost', 'root', '');
  if (!$link) {
    die('Could not connect to sql: ' . mysql_error());
  }


  //connect to db
  $sublink = mysql_select_db('tuitions');
  if(!$sublink) {
    die('Could not connect to db: '. mysql_error());
  }

  $email_logged_in = $_GET['email'];
  $new_name = $_GET['name'];
  $new_contact = $_GET['cnt'];
  $new_address = $_GET['add'];
  $new_latitude = $_GET['lat'];
  $new_longitude = $_GET['lon'];

  $query = "UPDATE `teachers` SET `NAME` = '$new_name', `CONTACT_NO` = '$new_contact', `ADDRESS` = '$new_address', `LATITUDE` = '$new_latitude', `LONGITUDE` = '$new_longitude' WHERE `EMAIL` = '$email_logged_in'";
	$result = mysql_query($query);

  echo "{\"status\": \"1\"}";
?>
