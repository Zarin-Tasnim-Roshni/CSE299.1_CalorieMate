<?php
    $name = $_POST['name'];
    $proteins = $_POST['proteins'];
    $carbohydrates = $_POST['carbohydrates'];
    $calories = $_POST['calories'];
    $fats = $_POST['fats'];
    $minerals = $_POST['minerals'];

    $conn = new mysqli('localhost','root','','calmate');
    if($conn->connection_error){
        die('Connection Failed : '.$conn->connect_error);
    }
    else{
        $stmt = $conn->prepare("insert into items(name, proteins, carbohydrates, calories, fats, minerals)
            values(?,?,?,?,?)");
        $stmt->bind_param("sddddd",$name, $proteins, $carbohydrates, $calories, $fats, $minerals);
        $stmt->execute();
        echo "Successfully Saved"
        $stmt->close();
        $conn->close();    
    }
?>