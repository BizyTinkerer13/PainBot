package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.runClimber;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.arm;
import frc.robot.subsystems.coral;
import frc.robot.subsystems.climber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  // Subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final coral Coral = new coral();
  private final arm Arm = new arm();
  private final climber Climber = new climber();

  private final runClimber rc;

  // Controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

  public RobotContainer() {
    rc = new runClimber(Climber);

    configureButtonBindings();

    // run the climber back on a
    new JoystickButton(m_driverController, 1).whileTrue(rc);

    // Default drive command (field-relative)
    m_robotDrive.setDefaultCommand(
        new RunCommand(() ->
            m_robotDrive.drive(
                MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true),
            m_robotDrive)
    );

    // Default Coral trigger control (RT = forward, LT = reverse)
    Coral.setDefaultCommand(
        new RunCommand(() -> {
            double rt = m_driverController.getRightTriggerAxis();
            double lt = m_driverController.getLeftTriggerAxis();
            final double triggerThreshold = 0.2;

            if (rt > triggerThreshold) {
                Coral.spin(0.4);
            } else if (lt > triggerThreshold) {
                Coral.spin(-0.4);
            } else {
                Coral.stop();
            }
        }, Coral)
    );

    // Default Arm control (Y = up, B = down)
    Arm.setDefaultCommand(
        new RunCommand(() -> {
            double armSpeed = 0.2;

            if (m_driverController.getYButton()) {
                Arm.setSpeed(armSpeed);  // Raise
            } else if (m_driverController.getBButton()) {
                Arm.setSpeed(-armSpeed); // Lower
            } else {
                Arm.holdBack(); // constant gentle hold
            }
        }, Arm)
    );

    // Default Climber to stop when not in use
    Climber.setDefaultCommand(
        new RunCommand(() -> Climber.stop(), Climber)
    );
  }

  private void configureButtonBindings() {
    // R1 → X-lock
    new JoystickButton(m_driverController, Button.kR1.value)
        .whileTrue(new RunCommand(() -> m_robotDrive.setX(), m_robotDrive));

    // A → spin winch forward while held
    new JoystickButton(m_driverController, XboxController.Button.kA.value)
        .whileTrue(new RunCommand(() -> Climber.spin(1.0), Climber));

        // LB → spin winch backward while held
    new JoystickButton(m_driverController, XboxController.Button.kLeftBumper.value)
        .whileTrue(new RunCommand(() -> Climber.spin(-1.0), Climber));
  }

  public Command getAutonomousCommand() {
    return m_robotDrive.driveForward().andThen(() -> m_robotDrive.drive(0, 0, 0, false));
  }
}
