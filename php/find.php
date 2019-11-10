
 <?php

 $server_name = "localhost";
 $mysql_user = "duksung12";
 $mysql_pass = "ejrwns12!@";
 $db_name = "duksung12";

header("Content-Type: application/json");

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


$user_email = $_GET[user_email];


$result;


if($user_email == ""){
	$result = array('result' => 'error', 
			'result_code' => '300',
			'description' => '빈 값이 있음'
		);
}else{

	$sql = "SELECT * FROM User WHERE user_email='$user_email'";


	if(!is_null(mysql_num_rows($sql))){



		if(!mysql_query($sql)){
			$result = array('result' => 'error', 
				'result_code' => '200',
				'description' => '쿼리 실패'
			);
		}else{
			$result = array('result' => 'ok', 'result_code' => '100','description' => '쿼리 성공');
		}

	}else{
		$result = array('result' => 'error', 
					'result_code' => '200',
					'description' => '등록된 email이 없음'
				);
	}

}


header("Content-Type: application/json");

echo json_encode($result);


mysql_close();

?>
