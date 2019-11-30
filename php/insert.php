
 <?php

 $server_name = "localhost";
 $mysql_user = "duksung12";
 $mysql_pass = "ejrwns12!@";
 $db_name = "duksung12";



$link = mysql_connect($server_name,$mysql_user,$mysql_pass);
if (!$link) {
    die('Not connected : ' . mysql_error());
}

// make foo the current db
$db_selected = mysql_select_db($db_name, $link);
if (!$db_selected) {
    die ('Can\'t use foo : ' . mysql_error());
}


mysql_query ( "SET NAMES UTF8" ); 



$user_nickname = $_GET[user_nickname];
$user_email = $_GET[user_email];
$user_pass = $_GET[user_pass];

$result;


if($user_nickname == "" || $user_email == "" || $user_pass == ""){
	$result = array('result' => 'error', 
			'result_code' => '300',
			'description' => '빈 값이 있음'
		);
}else{

	$sql = "INSERT INTO User (user_nickname,user_email,user_pass) VALUES ('$user_nickname','$user_email', '$user_pass')";

	if(!mysql_query($sql)){
		$result = array('result' => 'error', 
			'result_code' => '200',
			'description' => '쿼리 실패'
		);
	}else{
		$result = array('result' => 'ok', 'result_code' => '100','description' => '쿼리 성공');
	}
}


header("Content-Type: application/json");

echo json_encode($result);


mysql_close();

?>
