<?php
    $date = $_POST['date'];
    $weight = $_POST['weight'];
    $bmi = $_POST['bmi'];

    $conn = new mysqli('localhost','root','','calmate');
    if($conn->connection_error){
        die('Connection Failed : '.$conn->connect_error);
    }
    else{
        $stmt = $conn->prepare("insert into bmi(date, weight, bmi)
            values(?,?,?)");
        $stmt->bind_param("iii",$date, $weight, $bmi);
        $stmt->execute();
        echo "Successfully Saved"
        $stmt->close();
        $conn->close();    
    }
?>