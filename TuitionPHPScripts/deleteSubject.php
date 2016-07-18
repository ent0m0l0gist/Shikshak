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

	$email=$_GET['emailId'];
	$Subject=$_GET['subject'];
	$fromClass=$_GET['from'];
	$toClass=$_GET['to'];

	$query="DELETE FROM `subjects` WHERE `EMAIL` = '$email' AND `SUBJECT`='$Subject' AND `FROMCLASS`='$fromClass' AND `TOCLASS` = '$toClass'";
	echo $query;
	$result=mysql_query($query);
    mysql_close($link);

?>