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
	
	$query = "SELECT  FromClass, ToClass, Subject FROM `subjects` WHERE EMAIL= '$entered_email';";
	$result = mysql_query($query);


	$count = 0;
	$subj_arr = "[";

	while($row = mysql_fetch_assoc($result)){
		
		$from = $row['FromClass'];
		$to = $row['ToClass'];
		$subject = $row['Subject'];

		$jsonObject = '{"fromClass":"'.$from.'", "toClass":"'.$to.'", "subjectName":"'.$subject.'"},';

		$subj_arr = $subj_arr.$jsonObject;

		$count++;
	}

	$subj_arr = trim($subj_arr, ",");

	$subj_arr = $subj_arr."]";

	$json_object_final = '{ "count":"'.$count.'", "array":'.$subj_arr.'}';

	echo $json_object_final;

	mysql_close($link);
?>