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

	$subject=$_GET['subject']; 
	$class=$_GET['class'];
	$lat=$_GET['lat'];
	//$long=$_GET['longitude'];
	 
	
	$query1="SELECT EMAIL FROM `subjects` WHERE Subject='$subject' AND FromClass<='$class' AND ToClass>='class';";
	$result1=mysql_query($query1);

	$count=0;
	$detailArray="[";

	while($row1=mysql_fetch_assoc($result1))
	{
		$email=$row1['EMAIL'];
		$query2="SELECT NAME, CONTACT_NO,LONGITUDE,LATITUDE FROM `teachers` WHERE EMAIL='$email' AND LATITUDE >  '$lat'-0.2 AND LATITUDE < '$lat'+0.2 ;";
		$result2=mysql_query($query2);

		$row2=mysql_fetch_assoc($result2);
		$name=$row2['NAME'];
		$contact=$row2['CONTACT_NO'];
		$longitude=$row2['LONGITUDE'];
		$latitude=$row2['LATITUDE'];

		$jsonObject = '{"Name":"'.$name.'", "Contact":"'.$contact.'", "email":"'.$email.'","Latitude":"'.$latitude.'","Longitude":"'.$longitude.'"},';
		$detailArray=$detailArray.$jsonObject;

		$count++;

	}

	$detailArray=trim($detailArray,",");
	$detailArray = $detailArray."]";

	$json_object_final = '{ "count":"'.$count.'", "array":'.$detailArray.'}';

	echo $json_object_final;

	mysql_close($link);

	?>
