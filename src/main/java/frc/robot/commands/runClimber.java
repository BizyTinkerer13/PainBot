package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber;

// a to d

public class runClimber extends Command {

  private climber s_climber;

  public runClimber(climber s_climber) {
    this.s_climber = s_climber;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    s_climber.spin(.5);
  }

  @Override
  public void end(boolean interrupted) {
    s_climber.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
