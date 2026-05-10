package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase; 

public class arm extends SubsystemBase {
    
    private final SparkMax motor = new SparkMax(10, MotorType.kBrushed);

    public arm () {}
    
    public void setSpeed(double speed) {
        motor.set(speed/2); 
    }

    public void stop () {
        motor.set(0);
    }

    public void holdBack() {
        motor.set(-0.1); //lil tnesion to hold arm back
    }

}
