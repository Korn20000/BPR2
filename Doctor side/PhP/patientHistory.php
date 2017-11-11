<!DOCTYPE html>
<html>
    <head>
        <title>Patients History</title>
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
                <th>Type</th>
                <th>Level</th>
                <th>Date</th>
                <th>Time</th>
            </tr>
            <?php           
            
            $conn = mysqli_connect("localhost", "id3343620_bachelor", "12345", "id3343620_bachelor");
            if($conn-> connect_error)
            {
                die("Connection failed:". $conn-> connect_error);
            }
            $sql = "SELECT CPR, Type, Level, Date, Time from PatientHistory";
            $result = $conn-> query($sql);
            
            if($result-> num_rows > 0)
            {
                while($row = $result-> fetch_assoc())
                {
                    echo "<tr><td>". $row["CPR"] ."</td><td>".
                                     $row["Type"] ."</td><td>".
                                     $row["Level"] ."</td><td>".
                                     $row["Date"] ."</td><td>".
                                     $row["Time"] ."</td></tr>";
                }
                echo "</table>";
            }
            else
            {
                echo "";
            }
            
            $conn-> close();
            
            echo json_encode(42);
            ?>
            </table>
   
    </body>
</html>
            