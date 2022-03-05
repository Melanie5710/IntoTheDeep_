package org.firstinspires.ftc.teamcode.opMode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Acquirer;
import org.firstinspires.ftc.teamcode.hardware.CapVision;
import org.firstinspires.ftc.teamcode.hardware.Carousel;
import org.firstinspires.ftc.teamcode.hardware.RetractableOdoSys;
import org.firstinspires.ftc.teamcode.hardware.util.DelayCommand;
import org.firstinspires.ftc.teamcode.hardware.FreightSensor;
import org.firstinspires.ftc.teamcode.hardware.LiftScoringV2;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous
public class PetersCyclesRed extends LinearOpMode {
    private CapVision cv = new CapVision();
    private Carousel carousel = new Carousel();
    private DelayCommand delay = new DelayCommand();
    private FreightSensor sensor = new FreightSensor();
    private LiftScoringV2 scoringMech = new LiftScoringV2();
    private RetractableOdoSys odoSys = new RetractableOdoSys();
    private Acquirer acquirer = new Acquirer();
    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    public static double startx = 15.0;
    public static double starty = 70.0;
    public static double startAng = Math.toRadians(90);

    public static double scoreHubPosx = 7;
    public static double scoreHubPosy = 52;

    public static double scoreHubPosAngB = -50;
    public static double scoreHubPosAngR = -40;

    public static double repositionX = 15.0;
    public static double reposistionY = 71.5;

    public static double distanceForwards = 40;
    public static double strafeDistance = 24;

    public static double preSplineY = -53.5;
    public static double bEnterX = 20;
    public static double bEnterY = -70.5;
    public static double warehouseX = 55;


    public static String goal = "highgoal";

    Pose2d startPosB = new Pose2d(startx, starty, startAng);
    Vector2d scoreHubPosB = new Vector2d(scoreHubPosx, -scoreHubPosy);
    Pose2d repositionB = new Pose2d(repositionX, reposistionY, Math.toRadians(0));
    Vector2d preSpline = new Vector2d(scoreHubPosx, preSplineY);
    Vector2d bEnter = new Vector2d(bEnterX, bEnterY);
    Vector2d wareHouse = new Vector2d(warehouseX, bEnterY);
    //bruh
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        //initialize mechanisms
        carousel.init(hardwareMap);
        scoringMech.init(hardwareMap);
        sensor.init(hardwareMap);
        cv.init(hardwareMap);
        odoSys.init(hardwareMap, true);
        acquirer.init(hardwareMap);


        //drive train + async updates of mechanisms
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setSlides(scoringMech);
        drive.setAcquirer(acquirer,sensor);
        //important coordinates here
        Pose2d startPos = new Pose2d(startx, -starty, -startAng);        //set startPose
        drive.setPoseEstimate(startPos);

        //trajectory
        TrajectorySequence depoPath = drive.trajectorySequenceBuilder(startPos)
                .waitSeconds(1)
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(scoreHubPosB, Math.toRadians(scoreHubPosAngB)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    scoringMech.releaseHard();
                    drive.acquirerRuns = true;
                })
                .waitSeconds(.25)
                .lineTo(preSpline)
                .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                .lineToLinearHeading(new Pose2d(warehouseX, bEnterY))
                .waitSeconds(.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    scoringMech.toggle("highgoal");
                    drive.acquirerRuns = false;
                })
                .splineTo(bEnter, Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(scoreHubPosx, -scoreHubPosy, Math.toRadians(scoreHubPosAngB)), Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    scoringMech.releaseHard();
                    drive.acquirerRuns = true;
                })
                .waitSeconds(.5)
                .lineTo(preSpline)
                .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                .lineTo(wareHouse)
                .waitSeconds(.25)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    scoringMech.toggle("highgoal");
                    drive.acquirerRuns = false;
                })
                .splineTo(bEnter, Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(scoreHubPosx, -scoreHubPosy, Math.toRadians(scoreHubPosAngB)), Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    scoringMech.releaseHard();
                })
                .waitSeconds(.5)
                .lineTo(preSpline)
                .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                .lineTo(wareHouse)
                .build();


        //3ftx3ftmovement

//        TrajectorySequenceBuilder taahkbeer = drive.trajectorySequenceBuilder(alFatihah.build().end())
//                .splineTo(new Vector2d(bankcurveX,bankcurveY),Math.toRadians(90))
//                .addDisplacementMarker(()->{
//                    drive.acquirerRuns = true;
//                })
//                .forward(tuningNumber)
//                .waitSeconds(2);

//        TrajectorySequenceBuilder allahhuackbar = drive.trajectorySequenceBuilder(taahkbeer.build().end())
//                .back(tuningNumber)
//                .addDisplacementMarker(() -> {
//                    scoringMech.toggle("highgoal");
//                    drive.acquirerRuns = false;
//                })
//                .setReversed(true)
//                .splineTo(new Vector2d(18, starty), Math.toRadians(0))
//                .UNSTABLE_addTemporalMarkerOffset(0,()->{
//                    scoringMech.release();
//                });


        //mashallah

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        if (cv.whichRegion() == 1) {
            goal = "highgoal";
        }
        if (cv.whichRegion() == 2) {
            goal = "midgoal";
        }
        if (cv.whichRegion() == 3) {
            goal = "lowgoal";
        }
        telemetry.addData("goal: ", goal);
        telemetry.addData("region", cv.whichRegion());
        telemetry.update();

        scoringMech.toggle(goal);
        drive.followTrajectorySequence(depoPath);
    }
}
