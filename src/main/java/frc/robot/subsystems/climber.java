package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 


public class climber extends SubsystemBase {
     private final SparkMax winchMotor = new SparkMax(9, MotorType.kBrushless);

     public climber () {}

     public void spin(double speed) {
        winchMotor.set(speed);
     }

     public void stop() {
        winchMotor.set(0);
     }
    
}
