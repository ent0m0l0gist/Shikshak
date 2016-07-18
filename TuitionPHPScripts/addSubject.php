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

	$email = $_GET['emailId'];
	$subject = $_GET['subject'];
	$fromClass = $_GET['from'];
	$toClass = $_GET['to'];

	$query = "INSERT INTO `subjects` (`eMail`, `Subject`, `FromClass`, `ToClass`) VALUES ('$email', '$subject', '$fromClass','$toClass');";
	mysql_query($query);

	echo "Subject successfully added.";

	mysql_close($link);
?>