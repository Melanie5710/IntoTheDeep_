package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import kotlin.math.MathKt;

public class beep {

    public static double startx = 15.0;
    public static double starty = 70.0;
    public static double startAng = Math.toRadians(90);

    public static double scoreHubPosx = 2;
    public static double scoreHubPosy = 48;

    public static double scoreHubPosAngB = 50;
    public static double scoreHubPosAngR = -40;

    public static double repositionX = 15.0;
    public static double reposistionY = 71.5;

    public static double preSplineY = 53.5;
    public static double bEnterX = 20;
    public static double bEnterY = 68;
    public static double warehouseX = 53;
    public static double bExitY = -70.5;
    public static double inc = 0;
    public static Pose2d startPos = new Pose2d(startx, starty, startAng);

    Pose2d startPosB = new Pose2d(startx, starty, startAng);
    Vector2d scoreHubPosB = new Vector2d(scoreHubPosx, scoreHubPosy);
    Pose2d repositionB = new Pose2d(repositionX, reposistionY, Math.toRadians(0));
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        Pose2d startPosB = new Pose2d(startx, starty, startAng);
        Pose2d startPosR = new Pose2d(startx, -starty, -startAng);

        Vector2d scoreHubPosB = new Vector2d(scoreHubPosx, scoreHubPosy);
        Vector2d scoreHubPosR = new Vector2d(scoreHubPosx, -scoreHubPosy);

        Pose2d repositionB = new Pose2d(repositionX, reposistionY, Math.toRadians(0));
        Vector2d preSpline = new Vector2d(scoreHubPosx, preSplineY);
        Vector2d bEnter = new Vector2d(bEnterX, bEnterY);
        Vector2d bEnter2 = new Vector2d(bEnterX, bEnterY-inc);
        Vector2d bEnter3 = new Vector2d(bEnterX, bEnterY-2*inc);
        Vector2d bEnter4 = new Vector2d(bEnterX, bEnterY-3*inc);
        Vector2d bEnter5 = new Vector2d(bEnterX, bEnterY-4*inc);
        Vector2d bExit = new Vector2d(bEnterX, bExitY);
        Vector2d bExit2 = new Vector2d(bEnterX, bEnterY-2*inc);
        Vector2d bExit3 = new Vector2d(bEnterX, bEnterY-4*inc);
        Vector2d bExit4 = new Vector2d(bEnterX, bEnterY-6*inc);
        Vector2d wareHouse = new Vector2d(warehouseX, bEnterY);
        Vector2d wareHouse2 = new Vector2d(warehouseX, bEnterY-inc);
        Vector2d wareHouse3 = new Vector2d(warehouseX, bEnterY-2*inc);
        Vector2d wareHouse4 = new Vector2d(warehouseX, bEnterY-3*inc);
        Vector2d wareHouse5 = new Vector2d(warehouseX, bEnterY-4*inc);
        RoadRunnerBotEntity myBotBlue = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 45, Math.toRadians(180), Math.toRadians(180), 9.85)
                .setDimensions(11.838583, 14.4882 )
                //.setStartPose(startPos)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPos)
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(scoreHubPosB, Math.toRadians(scoreHubPosAngB)))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //scoringMech.releaseHard();
                            // drive.acquirerRuns = true;
                        })
                        .waitSeconds(.1)
                        .lineTo(preSpline)
                        .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                        .lineToLinearHeading(new Pose2d(warehouseX-1, bEnterY))
                        .waitSeconds(0.1)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //scoringMech.toggle("highgoal");
                            // drive.acquirerRuns = false;
                        })
                        .lineTo(new Vector2d(bEnterX+5, bEnterY))
                        .splineTo(new Vector2d(scoreHubPosx, scoreHubPosy), Math.toRadians(scoreHubPosAngB+180))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // scoringMech.releaseHard();
                            // drive.acquirerRuns = true;
                        })
                        .waitSeconds(.1)
                        .lineTo(preSpline)
                        .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                        .lineToLinearHeading(new Pose2d(warehouseX-1, bEnterY))
                        .waitSeconds(0.1)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //scoringMech.toggle("highgoal");
                            // drive.acquirerRuns = false;
                        })
                        .lineTo(new Vector2d(bEnterX+5, bEnterY))
                        .splineTo(new Vector2d(scoreHubPosx, scoreHubPosy), Math.toRadians(scoreHubPosAngB+180))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // scoringMech.releaseHard();
                            // drive.acquirerRuns = true;
                        })
                        .lineTo(preSpline)
                        .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                        .lineToLinearHeading(new Pose2d(warehouseX-1, bEnterY))
                        .waitSeconds(0.1)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //scoringMech.toggle("highgoal");
                            // drive.acquirerRuns = false;
                        })
                        .lineTo(new Vector2d(bEnterX+5, bEnterY))
                        .splineTo(new Vector2d(scoreHubPosx, scoreHubPosy), Math.toRadians(scoreHubPosAngB+180))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // scoringMech.releaseHard();
                            // drive.acquirerRuns = true;
                        })
                        .lineTo(preSpline)
                        .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                        .lineToLinearHeading(new Pose2d(warehouseX-1, bEnterY))
                        .waitSeconds(0.1)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //scoringMech.toggle("highgoal");
                            // drive.acquirerRuns = false;
                        })
                        .lineTo(new Vector2d(bEnterX+5, bEnterY))
                        .splineTo(new Vector2d(scoreHubPosx, scoreHubPosy), Math.toRadians(scoreHubPosAngB+180))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // scoringMech.releaseHard();
                            // drive.acquirerRuns = true;
                        })
                        .lineTo(preSpline)
                        .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                        .lineToLinearHeading(new Pose2d(warehouseX-1, bEnterY))
                        .waitSeconds(0.1)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            //scoringMech.toggle("highgoal");
                            // drive.acquirerRuns = false;
                        })
                        .lineTo(new Vector2d(bEnterX+5, bEnterY))
                        .splineTo(new Vector2d(scoreHubPosx, scoreHubPosy), Math.toRadians(scoreHubPosAngB+180))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            // scoringMech.releaseHard();
                            // drive.acquirerRuns = true;
                        })
                        .lineTo(preSpline)
                        .splineToSplineHeading(new Pose2d(bEnter, Math.toRadians(0)), Math.toRadians(0))
                        .lineToLinearHeading(new Pose2d(warehouseX-10, bEnterY))
                        .build()

                );


        


        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBotBlue)
                //.addEntity(myBotRed)
                .start();
    }
}