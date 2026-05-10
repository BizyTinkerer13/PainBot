package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class coral extends SubsystemBase {

    private final SparkMax motor = new SparkMax(11, MotorType.kBrushed);

    public coral () {}

    public void spin(double speed) {
        motor.set(speed);
    }
    
    public void stop () {
        motor.set(0);
    }
}
