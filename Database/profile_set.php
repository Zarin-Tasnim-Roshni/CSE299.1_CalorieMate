<?php
    $name = $_POST['name'];
    $height = $_POST['height'];
    $target_weight = $_POST['target_weight'];
    $gender = $_POST['gender'];
    $dateOfBirth = $_POST['dateOfBirth'];

    $conn = new mysqli('localhost','root','','calmate');
    if($conn->connection_error){
        die('Connection Failed : '.$conn->connect_error);
    }
    else{
        $stmt = $conn->prepare("insert into profile_set(name, height, weight, gender, dateOfBirth)
            values(?,?,?,?,?)");
        $stmt->bind_param("siisi",$name, $height, $weight, $gender, $dateOfBirth);
        $stmt->execute();
        echo "Successfully Saved"
        $stmt->close();
        $conn->close();    
    }
?>