<!DOCTYPE html>
<html>
    <head>
        <title>Patients</title>
        <style>
        table
            {
                border-collapse: collapse;
                width: 100%;
                color: crimson;
                font-family: monospace;
                font-size: 25px;
                text-align: left;
            }
            th
            {
                background-color: crimson;
                color: white;
            }
            tr: nth-child(even) {background-color: #f2f2f2}
        </style>
    </head>
    
    <body>
        <table>
            <tr>
                <th>CPR</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Age</th>
                <th>Type</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Email</th>
            </tr>
            <?php           
                        
            
            $conn = mysqli_connect("localhost", "id3343620_bachelor", "12345", "id3343620_bachelor");
            if($conn-> connect_error)
            {
                die("Connection failed:". $conn-> connect_error);
            }
            $sql = "SELECT CPR, Name, Surname, Age, Type, Phone, Address, Email from Patient";
            $result = $conn-> query($sql);
            
            if($result-> num_rows > 0)
            {
                while($row = $result-> fetch_assoc())
                {
                    echo "<tr><td>". $row["CPR"] ."</td><td>".
                                     $row["Name"] ."</td><td>".
                                     $row["Surname"] ."</td><td>".
                                     $row["Age"] ."</td><td>".
                                     $row["Type"] ."</td><td>".
                                     $row["Phone"] ."</td><td>".
                                     $row["Address"] ."</td><td>".
                                     $row["Email"] ."</td><tr>";
                }
                echo "</table>";
            }
            else
            {
                echo "";
            }
            
            $conn-> close();
            ?>
            </table>
   
    </body>
</html>