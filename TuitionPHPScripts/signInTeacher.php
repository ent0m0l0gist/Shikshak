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

	$entered_email = $_GET['email'];
	$entered_pass = $_GET['pass'];

	$query = "SELECT PASSWORD, NAME, ADDRESS, CONTACT_NO FROM `teachers` WHERE EMAIL= '$entered_email';";
	$result = mysql_query($query);

	if(mysql_num_rows($result) == 1 ){
		$fetched_row = mysql_fetch_assoc($result);

		$actual_password = $fetched_row['PASSWORD'];
		$name = $fetched_row['NAME'];
		$add = $fetched_row['ADDRESS'];
		$contact = $fetched_row['CONTACT_NO'];

		if($entered_pass == $actual_password){
			echo '{"status": "2", "name": "'.$name.'", "address": "'.$add.'", "contact": "'.$contact.'"}';
		}

		else{
			echo '{"status": "1"}';
		}
	}

	else{
		echo '{"status": "0"}';
	}

	mysql_close($link);
?>
