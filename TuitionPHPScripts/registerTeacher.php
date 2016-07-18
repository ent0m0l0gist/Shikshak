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

	$name = $_GET['name'];
	$pass = $_GET['pass'];
	$email = $_GET['email'];
	$contact = $_GET['ct'];
	$address = $_GET['add'];
	$lon = $_GET['ln'];
	$lat = $_GET['lt'];

	$query = "SELECT `EMAIL` FROM `teachers` WHERE `EMAIL` = '$email'";
	$result = mysql_query($query);

	if(mysql_num_rows($result) > 0){
		echo "{\"status\": \"0\"}";
	}

	else{
		$query = "INSERT INTO `teachers` (`NAME`, `EMAIL`, `PASSWORD`, `CONTACT_NO`, `ADDRESS`, `LONGITUDE`, `LATITUDE`) VALUES ('$name', '$email', '$pass', '$contact', '$address', '$lon', '$lat');";
		mysql_query($query);

		echo "{\"status\": \"1\"}";
	}

	mysql_close($link);
?>