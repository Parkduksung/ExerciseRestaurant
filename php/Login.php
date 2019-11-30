
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



$user_email = $_GET['user_email'];
$user_pass = $_GET['user_pass'];




$result = mysql_query("SELECT * FROM User WHERE user_email='$user_email'AND user_pass='$user_pass'");
$num_rows = mysql_num_rows($result);


$result; 
if($num_rows == 1){

  $nickname = mysql_query("SELECT user_nickname FROM User WHERE user_email='$user_email'AND user_pass='$user_pass'");

  while($row= mysql_fetch_array($nickname)){
  // 아래 코드 id 와 title 는 위 쿼리의 필드명입니다.
  $nickname = $row[user_nickname];

}


  $result = array('result' =>'ok',
                  'resultNickname' => $nickname
                );
}else{
  $result = array('result' =>'error');
}


header("Content-Type: application/json");

echo json_encode($result);


mysql_close();

?>
