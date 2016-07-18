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

$query = "INSERT INTO `teachers` (`NAME`, `EMAIL`, `PASSWORD`, `PHONE_NO`, `ADDRESS`, `LONGITUDE`, `LATITUDE`) VALUES ('name', 'email', 'pass', '89998', 'SRS', '0', '0');";
	$result = mysql_query($query);

	mysql_close($link);
?>
