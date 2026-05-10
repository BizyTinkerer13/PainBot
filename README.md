# HumbleRumble FRC Team 9521 (2024/2025 Season)

This is the robot code for (i think it was called) **SanRio**, Team 9521's competition robot for the 2024/2025 FRC season. It uses a **swerve drive** (MAXSwerve modules) and is written in **Java** using the WPILib framework.

> **Note:** This code is incomplete and was left as a starting point for future team members. Read this guide fully before touching anything.

---

## Table of Contents

1. [What You Need to Install](#1-what-you-need-to-install)
2. [Opening the Project in VS Code](#2-opening-the-project-in-vs-code)
3. [Understanding the Project Structure](#3-understanding-the-project-structure)
4. [Where the Important Code Lives](#4-where-the-important-code-lives)
5. [What Each File Does](#5-what-each-file-does)
6. [Robot Hardware Reference](#6-robot-hardware-reference)
7. [Controller Mapping](#7-controller-mapping)
8. [Building and Simulating](#8-building-and-simulating)
9. [Known Incomplete Parts](#9-known-incomplete-parts)
10. [Tips for New Members](#10-tips-for-new-members)

---

## 1. What You Need to Install

You **must** install the following before opening this project. Do them in order.

### Step 1 — Install WPILib (Most Important)

WPILib is an all-in-one installer that gives you:
- A special version of VS Code set up for FRC
- Java 17 (the correct version for this project)
- All the FRC build tools

Go to the [WPILib GitHub releases page](https://github.com/wpilibsuite/allwpilib/releases) and download the installer for the **2025 season** (look for `WPILib_Windows-2025.x.x.iso` or `.exe`).

Run the installer and follow the prompts. It installs everything to `C:\Users\Public\wpilib\2025\`.

> **Do not use a regular VS Code install or a different Java version.** The WPILib installer sets everything up correctly. Using the wrong tools will cause build errors.

### Step 2 — Install Git (if not already installed)

Download Git from [https://git-scm.com/download/win](https://git-scm.com/download/win) and install it. This lets you clone the repository and track changes.

### Step 3 — Clone This Repository

Open a terminal (PowerShell or Command Prompt) and run:

```
git clone <your-repo-url-here>
```

This downloads all the files to your computer.

---

## 2. Opening the Project in VS Code

> **Important:** You must open the correct folder. Open the `MAXSwerve-Java-Template` folder, **not** the root folder that contains it.

1. Open the **WPILib VS Code** (installed by the WPILib installer — it has a red WPI logo in the taskbar, not the regular VS Code logo).
2. Go to **File → Open Folder...**
3. Navigate to where you cloned the repo, then open the `MAXSwerve-Java-Template` folder.
4. VS Code will ask if you trust the workspace — click **Yes, I trust the authors**.
5. Wait 1–2 minutes. VS Code will automatically download Gradle dependencies in the background. Watch the bottom status bar for progress.

When it's done, the project is ready to edit.

---

## 3. Understanding the Project Structure

Inside the `MAXSwerve-Java-Template` folder:

```
MAXSwerve-Java-Template/
│
├── src/main/java/frc/robot/       ← ALL the robot code you will edit is here
│   ├── Main.java
│   ├── Robot.java
│   ├── RobotContainer.java
│   ├── Constants.java
│   ├── Configs.java
│   ├── commands/
│   │   └── runClimber.java
│   └── subsystems/
│       ├── DriveSubsystem.java
│       ├── MAXSwerveModule.java
│       ├── arm.java
│       ├── coral.java
│       └── climber.java
│
├── src/main/deploy/               ← Files that get copied to the robot's RoboRIO
│   └── pathplanner/               ← Autonomous path files (PathPlanner)
│
├── vendordeps/                    ← Third-party library config files (don't edit these)
├── build.gradle                   ← Build configuration (don't edit unless you know what you're doing)
└── .wpilib/                       ← WPILib settings (team number, etc.)
```

**The only folder you will regularly edit is `src/main/java/frc/robot/`.**

---

## 4. Where the Important Code Lives

| What you want to change | File to open |
|---|---|
| Motor speeds, CAN IDs, physical measurements | `Constants.java` |
| Button bindings (which button does what) | `RobotContainer.java` |
| Drive behavior | `subsystems/DriveSubsystem.java` |
| Arm behavior | `subsystems/arm.java` |
| Coral intake behavior | `subsystems/coral.java` |
| Climber behavior | `subsystems/climber.java` |
| Robot startup logic | `Robot.java` |
| SparkMax motor configs (PID, current limits) | `Configs.java` |

---

## 5. What Each File Does

### `Main.java`
The entry point. You will never need to edit this file. It just starts the robot program.

### `Robot.java`
The main robot class. It runs on a 20ms loop and calls the right code depending on what mode the robot is in (autonomous, teleoperated, disabled, etc.). You generally won't edit this much unless you're adding a new robot mode.

### `RobotContainer.java`
This is where everything gets wired together. It:
- Creates all the subsystem objects (drive, arm, coral, climber)
- Sets the default command for each subsystem
- Maps Xbox controller buttons to commands

**If you want to change what a button does, this is the file.**

### `Constants.java`
All the numbers that define the robot. Think of it as a settings file. Things like:
- Max drive speed
- Wheel size
- CAN bus IDs for every motor
- Kinematics (physical measurements of the robot frame)

**If you're tuning the robot or changing hardware, update numbers here.**

### `Configs.java`
SparkMax motor controller configuration — things like PID gains, current limits, and idle behavior. These settings get applied to the motors on startup.

### `subsystems/DriveSubsystem.java`
Controls all four swerve modules. Handles field-relative driving, gyro reset, and odometry (tracking the robot's position on the field). This is the most complex file in the project.

### `subsystems/MAXSwerveModule.java`
Represents one individual swerve wheel module. You typically don't edit this unless something about the swerve hardware changes. It handles driving and steering each wheel using SparkMax controllers.

### `subsystems/arm.java`
Controls the arm motor (CAN ID 10). Has methods to move up, move down, and hold position.

### `subsystems/coral.java`
Controls the coral intake/ejection motor (CAN ID 11). Has methods to run forward (intake) and reverse (eject).

### `subsystems/climber.java`
Controls the climber winch motor (CAN ID 9). Has methods to extend and retract.

### `commands/runClimber.java`
A simple command that runs the climber at half speed. This is an example of how commands are structured — useful to look at when writing new commands.

---

## 6. Robot Hardware Reference

### CAN Bus IDs

| Device | Type | CAN ID |
|---|---|---|
| Front Left — Drive motor | NEO brushless | 1 |
| Front Left — Turn motor | NEO 550 | 2 |
| Rear Left — Drive motor | NEO brushless | 3 |
| Rear Left — Turn motor | NEO 550 | 4 |
| Front Right — Drive motor | NEO brushless | 5 |
| Front Right — Turn motor | NEO 550 | 6 |
| Rear Right — Drive motor | NEO brushless | 7 |
| Rear Right — Turn motor | NEO 550 | 8 |
| Climber | NEO brushless | 9 |
| Arm | Brushed motor | 10 |
| Coral | Brushed motor | 11 |

> These IDs must match what's configured on the actual hardware using the REV Hardware Client. If a motor isn't responding, check that its CAN ID matches this table.

### Gyro

- **NavX AHRS** connected via SPI on the RoboRIO
- Used for field-relative drive and autonomous

### Robot Frame

- 30 inches wide, 30 inches long (wheelbase and track width)
- Drive pinion gear: 14 teeth

---

## 7. Controller Mapping

Uses one **Xbox controller** on USB port 0.

| Input | Action |
|---|---|
| Left stick Y (up/down) | Drive forward / backward |
| Left stick X (left/right) | Strafe left / right |
| Right stick X | Rotate robot |
| Right trigger (hold) | Coral intake forward |
| Left trigger (hold) | Coral intake reverse |
| Y button (hold) | Arm up |
| B button (hold) | Arm down |
| A button (hold) | Climber extend |
| Left bumper (hold) | Climber retract |
| Right bumper | X-lock wheels (defense mode) |

---

## 8. Building and Simulating

All WPILib actions are accessed through the **Command Palette** in VS Code:

Press `Ctrl+Shift+P` and type `WPILib` to see all available commands.

### Build the Project (check for errors)

`Ctrl+Shift+P` → `WPILib: Build Robot Code`

This compiles the code. If there are errors, they will appear in the **Problems** panel at the bottom. Fix all errors before trying to deploy.

### Simulate the Robot (no physical robot needed)

`Ctrl+Shift+P` → `WPILib: Simulate Robot Code`

This opens a simulator window where you can test drive code on your computer without a robot. Useful for testing logic changes.

### Deploy to the Robot

`Ctrl+Shift+P` → `WPILib: Deploy Robot Code`

Make sure your computer is connected to the robot's radio (WiFi or tether cable). The code will compile and upload to the RoboRIO. Team number is **9521** — the build system uses this to find the robot on the network.

---

## 9. Known Incomplete Parts

This code is a **work in progress**. Here is what is missing or unfinished:

- **`Alge Arm.java`** — This file is empty. It was started but never implemented.
- **Autonomous routines** — Only a basic drive-forward command exists. PathPlanner is set up but no full auto paths have been built.
- **Arm and climber commands** — Only one command (`runClimber.java`) was written using the commands folder. The arm and coral are controlled with inline lambdas in `RobotContainer.java`.
- **PID tuning** — The PID gains in `Configs.java` and `Constants.java` are template values from the MAXSwerve example. They will need tuning on the actual robot.
- **Subsystem class naming** — `arm.java`, `coral.java`, and `climber.java` use lowercase class names, which breaks Java naming conventions. They work fine, but future classes should use uppercase names (e.g., `Arm.java`).

---

## 10. Tips for New Members

- **Never edit `build.gradle` or `settings.gradle`** unless you know exactly what you're doing. Mistakes here break the entire build.
- **Never delete the `vendordeps/` folder.** These JSON files tell Gradle where to download the REV, NavX, and PathPlanner libraries from. Deleting them will cause build failures.
- **Use the WPILib VS Code**, not a regular VS Code. The WPILib version has the Java toolchain and extensions pre-configured for FRC.
- **`Constants.java` is your friend.** If a number is wrong (speed too fast, wrong CAN ID), it's probably in there.
- **Read the WPILib docs.** The official documentation at [docs.wpilib.org](https://docs.wpilib.org) explains command-based robot programming in detail and is the best learning resource for FRC Java.
- **Git commit often.** Before making any changes, make sure you're on your own branch so you don't break the main code. Use `git checkout -b your-name-changes` to create a new branch.

---

*Code maintained by Team 9521. Originally written for the 2024/2025 FRC season.*
