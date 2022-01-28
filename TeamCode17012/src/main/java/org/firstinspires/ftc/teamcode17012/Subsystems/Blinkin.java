package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Blinkin {
    private RevBlinkinLedDriver blinkin;
    private RevBlinkinLedDriver.BlinkinPattern pattern;

    public Blinkin(HardwareMap hardwareMap, Telemetry telemetry) {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "Blinkin");
    }

    public void getPattern(){

    }
    // Rainbow Patterns
    // Blinkin adjustments affect pattern density and speed
    public void setRainbowRainbow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE);
    }
    public void setPartyRainbow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_PARTY_PALETTE);
    }
    public void setOceanRainbow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_OCEAN_PALETTE);
    }
    public void setLavaRainbow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_LAVA_PALETTE);
    }
    public void setForestRainbow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_FOREST_PALETTE);
    }
    public void setRainbowGlitter(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
    }
    public void setConfetti(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);
    }

    // Shot Patterns
    public void setRedShot(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SHOT_RED);
    }
    public void setBlueShot(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SHOT_BLUE);
    }
    public void setWhiteShot(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SHOT_WHITE);
    }
    public void setColor1Shot(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_SHOT);
    }
    public void setColor2Shot(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_SHOT);
    }

    // Sinelon Patterns
    // Adjustments on the Blinkin adjust pattern density and speed
    public void setRainbowSinelon() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_RAINBOW_PALETTE);
    }
    public void setPartySinelon(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_PARTY_PALETTE);
    }
    public void setOceanSinelon(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_OCEAN_PALETTE);
    }
    public void setLavaSinelon(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_LAVA_PALETTE);
    }
    public void setForestSinelon(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SINELON_FOREST_PALETTE);
    }
    public void setColor12Sinelon(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SINELON);
    }

    // BPM Patterns
    // Adjustments on the Blink adjust pattern density and speed
    public void setLavaBPM() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE);
    }
    public void setOceanBPM() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_OCEAN_PALETTE);
    }
    public void setRainbowBPM() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_RAINBOW_PALETTE);
    }
    public void setPartyBPM() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);
    }
    public void setForestBPM() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_FOREST_PALETTE);
    }
    public void setColor12BPM(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_BEATS_PER_MINUTE);
    }

    // Fire Patterns
    public void setFireMedium(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.FIRE_MEDIUM);
    }
    public void setFireLarge(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.FIRE_LARGE);
    }

    // Twinkle Patterns
    public void setRainbowTwinkle(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_RAINBOW_PALETTE);
    }
    public void setPartyTwinkle(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_PARTY_PALETTE);
    }
    public void setOceanTwinkle(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_OCEAN_PALETTE);
    }
    public void setLavaTwinkle(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_LAVA_PALETTE);
    }
    public void setForestTwinkle(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.TWINKLES_FOREST_PALETTE);
    }
    public void setColor12Twinkle(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_TWINKLES);
    }

    // Color Waves
    public void setRainbowWaves(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_RAINBOW_PALETTE);
    }
    public void setPartyWaves(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_PARTY_PALETTE);
    }
    public void setOceanWaves(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_OCEAN_PALETTE);
    }
    public void setLavaWaves(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_LAVA_PALETTE);
    }
    public void setForestWaves(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_FOREST_PALETTE);
    }
    public void setColor12Waves(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_COLOR_WAVES);
    }

    // Larson Scanner Patterns
    // Blinkin adjusts pattern width and speed
    public void setRedScanner(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LARSON_SCANNER_RED);
    }
    public void setGreyScanner(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LARSON_SCANNER_GRAY);
    }
    public void setColor1Scanner(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_LARSON_SCANNER);
    }
    public void setColor2Scanner(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_LARSON_SCANNER);
    }

    // Light Chase
    // Blinkin adjusts dimming and speed
    public void setRedChase(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_RED);
    }
    public void setBlueChase(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_BLUE);
    }
    public void setGreyChase(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_GRAY);
    }
    public void setColor1Chase(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_LIGHT_CHASE);
    }
    public void setColor2Chase(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_LIGHT_CHASE);
    }

    // Heartbeat Patterns
    // These will always be the color specified
    public void setRedHeartbeat(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_RED);
    }
    public void setBlueHeartbeat(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_BLUE);
    }
    public void setWhiteHeartbeat(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_WHITE);
    }
    public void setGreyHeartbeat(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_GRAY);
    }
    // The color for these patterns are set on the blinkin. This is the Color1 on the blinkin
    public void setColor1HeartbeatSlow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_SLOW);
    }
    public void setColor1HeatbeatMedium(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_MEDIUM);
    }
    public void setColor1HeartbeatFast(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_FAST);
    }
    // The color for these patterns are set on the blinkin. This is the Color2 on the blinkin
    public void setColor2HeartbeatSlow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_HEARTBEAT_SLOW);
    }
    public void setColor2HeartbeatMedium(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_HEARTBEAT_MEDIUM);
    }
    public void setColor2HeartbeatFast(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_HEARTBEAT_FAST);
    }

    // Breath Patterns
    public void setRedBreath(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
    }
    public void setBlueBreath(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
    }
    public void setGreyBreath(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_GRAY);
    }
    // Color 1 Breath (Set on Blinkin)
    public void setColor1BreathSlow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_BREATH_SLOW);
    }
    public void setColor1BreathFast(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_BREATH_FAST);
    }
    // Color 2 Breath (Set on Blinkin)
    public void setColor2BreathSlow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_BREATH_SLOW);
    }
    public void setColor2BreathFast(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_BREATH_FAST);
    }

    // Strobe Patterns
    public void setRedStrobe(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_RED);
    }
    public void setGoldStrobe() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_GOLD);
    }
    public void setBlueStrobe() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_BLUE);
    }
    public void setWhiteStrobe(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_WHITE);
    }
    public void setColor1Strobe(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_STROBE);
    }
    public void setColor2Strobe() {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_STROBE);
    }

    // Blend to black
    public void setColor1BlendBlack(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_END_TO_END_BLEND_TO_BLACK);
    }
    public void setColor2BlendBlack(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP2_END_TO_END_BLEND_TO_BLACK);
    }
    public void setColor12Blend(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_END_TO_END_BLEND_1_TO_2);
    }
    public void setColor12BlendBlack(){ // Verify what this does/looks like???
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_END_TO_END_BLEND);
    }
    public void setColor12NoBlend(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_NO_BLENDING);
    }

    // Sparkles!
    public void setSparkle1on2(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SPARKLE_1_ON_2);
    }
    public void setSparkle2on1(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_SPARKLE_2_ON_1);
    }

    // Color Gradient
    public void setGradient(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_2_COLOR_GRADIENT);
    }

    // Solid Colors
    public void setRed(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
    }
    public void setBlue(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
    }
    public void setHotPink(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
    }
    public void setDarkRed(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
    }
    public void setRedOrange(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED_ORANGE);
    }
    public void setOrange(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
    }
    public void setGold(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GOLD);
    }
    public void setYellow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
    }
    public void setLawnGreen(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LAWN_GREEN);
    }
    public void setLime(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIME);
    }
    public void setDarkGreen(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
    }
    public void setGreen(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }
    public void setBlueGreen(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);
    }
    public void setAqua(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.AQUA);
    }
    public void setSkyBlue(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.SKY_BLUE);
    }
    public void setDarkBlue(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE);
    }
    public void setBlueViolet(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_VIOLET);
    }
    public void setViolet(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
    }
    public void setWhite(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
    }
    public void setGrey(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.GRAY);
    }
    public void setDarkGrey(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GRAY);
    }
    public void turnOff(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
    }
}
