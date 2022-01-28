package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.hardware.rev.RevBlinkinLedDriver.BlinkinPattern.*;

public class Blinkin {
    private RevBlinkinLedDriver blinkin;
    private RevBlinkinLedDriver.BlinkinPattern pattern;

    public Blinkin(HardwareMap hardwareMap, Telemetry telemetry) {
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "Blinkin");
    }

    public String getBlinkinPatternAsString(){
        return pattern.toString();
    }
    public RevBlinkinLedDriver.BlinkinPattern getBlinkinPattern(){
        return pattern;
    }
    public void runBlinkinPattern(){
        blinkin.setPattern(pattern);
    }

    // Rainbow Patterns
    // Blinkin adjustments affect pattern density and speed
    public void setRainbowRainbow(){
        pattern = RAINBOW_RAINBOW_PALETTE;
    }
    public void setPartyRainbow(){
        pattern = RAINBOW_PARTY_PALETTE;
    }
    public void setOceanRainbow(){
        pattern = RAINBOW_OCEAN_PALETTE;
    }
    public void setLavaRainbow(){
        pattern = RAINBOW_LAVA_PALETTE;
    }
    public void setForestRainbow(){
        pattern = RAINBOW_FOREST_PALETTE;
    }
    public void setRainbowGlitter(){
        pattern = RAINBOW_WITH_GLITTER;
    }
    public void setConfetti(){
        pattern = CONFETTI;
    }

    // Shot Patterns
    public void setRedShot(){
        pattern = SHOT_RED;
    }
    public void setBlueShot(){
        pattern = SHOT_BLUE;
    }
    public void setWhiteShot(){
        pattern = SHOT_WHITE;
    }
    public void setColor1Shot(){
        pattern = CP1_SHOT;
    }
    public void setColor2Shot(){
        pattern = CP2_SHOT;
    }

    // Sinelon Patterns
    // Adjustments on the Blinkin adjust pattern density and speed
    public void setRainbowSinelon() {
        pattern = SINELON_RAINBOW_PALETTE;
    }
    public void setPartySinelon(){
        pattern = SINELON_PARTY_PALETTE;
    }
    public void setOceanSinelon(){
        pattern = SINELON_OCEAN_PALETTE;
    }
    public void setLavaSinelon(){
        pattern = SINELON_LAVA_PALETTE;
    }
    public void setForestSinelon(){
        pattern = SINELON_FOREST_PALETTE;
    }
    public void setColor12Sinelon(){
        pattern = CP1_2_SINELON;
    }

    // BPM Patterns
    // Adjustments on the Blink adjust pattern density and speed
    public void setLavaBPM() {
        pattern = BEATS_PER_MINUTE_LAVA_PALETTE;
    }
    public void setOceanBPM() {
        pattern = BEATS_PER_MINUTE_OCEAN_PALETTE;
    }
    public void setRainbowBPM() {
        pattern = BEATS_PER_MINUTE_RAINBOW_PALETTE;
    }
    public void setPartyBPM() {
        pattern = BEATS_PER_MINUTE_PARTY_PALETTE;
    }
    public void setForestBPM() {
        pattern = BEATS_PER_MINUTE_FOREST_PALETTE;
    }
    public void setColor12BPM(){
        pattern = CP1_2_BEATS_PER_MINUTE;
    }

    // Fire Patterns
    public void setFireMedium(){
        pattern = FIRE_MEDIUM;
    }
    public void setFireLarge(){
        pattern = FIRE_LARGE;
    }

    // Twinkle Patterns
    public void setRainbowTwinkle(){
        pattern = TWINKLES_RAINBOW_PALETTE;
    }
    public void setPartyTwinkle(){
        pattern = TWINKLES_PARTY_PALETTE;
    }
    public void setOceanTwinkle(){
        pattern = TWINKLES_OCEAN_PALETTE;
    }
    public void setLavaTwinkle(){
        pattern = TWINKLES_LAVA_PALETTE;
    }
    public void setForestTwinkle(){
        pattern = TWINKLES_FOREST_PALETTE;
    }
    public void setColor12Twinkle(){
        pattern = CP1_2_TWINKLES;
    }

    // Color Waves
    public void setRainbowWaves(){
        pattern = COLOR_WAVES_RAINBOW_PALETTE;
    }
    public void setPartyWaves(){
        pattern = COLOR_WAVES_PARTY_PALETTE;
    }
    public void setOceanWaves(){
        pattern = COLOR_WAVES_OCEAN_PALETTE;
    }
    public void setLavaWaves(){
        pattern = COLOR_WAVES_LAVA_PALETTE;
    }
    public void setForestWaves(){
        pattern = COLOR_WAVES_FOREST_PALETTE;
    }
    public void setColor12Waves(){
        pattern = CP1_2_COLOR_WAVES;
    }

    // Larson Scanner Patterns
    // Blinkin adjusts pattern width and speed
    public void setRedScanner(){
        pattern = LARSON_SCANNER_RED;
    }
    public void setGreyScanner(){
        pattern = LARSON_SCANNER_GRAY;
    }
    public void setColor1Scanner(){
        pattern = CP1_LARSON_SCANNER;
    }
    public void setColor2Scanner(){
        pattern = CP2_LARSON_SCANNER;
    }

    // Light Chase
    // Blinkin adjusts dimming and speed
    public void setRedChase(){
        pattern = LIGHT_CHASE_RED;
    }
    public void setBlueChase(){
        pattern = LIGHT_CHASE_BLUE;
    }
    public void setGreyChase(){
        pattern = LIGHT_CHASE_GRAY;
    }
    public void setColor1Chase(){
        pattern = CP1_LIGHT_CHASE;
    }
    public void setColor2Chase(){
        pattern = CP2_LIGHT_CHASE;
    }

    // Heartbeat Patterns
    // These will always be the color specified
    public void setRedHeartbeat(){
        pattern = HEARTBEAT_RED;
    }
    public void setBlueHeartbeat(){
        pattern = HEARTBEAT_BLUE;
    }
    public void setWhiteHeartbeat(){
        pattern = HEARTBEAT_WHITE;
    }
    public void setGreyHeartbeat(){
        pattern = HEARTBEAT_GRAY;
    }
    // The color for these patterns are set on the blinkin. This is the Color1 on the blinkin
    public void setColor1HeartbeatSlow(){
        pattern = CP1_HEARTBEAT_SLOW;
    }
    public void setColor1HeatbeatMedium(){
        pattern = CP1_HEARTBEAT_MEDIUM;
    }
    public void setColor1HeartbeatFast(){
        pattern = CP1_HEARTBEAT_FAST;
    }
    // The color for these patterns are set on the blinkin. This is the Color2 on the blinkin
    public void setColor2HeartbeatSlow(){
        pattern = CP2_HEARTBEAT_SLOW;
    }
    public void setColor2HeartbeatMedium(){
        pattern = CP2_HEARTBEAT_MEDIUM;
    }
    public void setColor2HeartbeatFast(){
        pattern = CP2_HEARTBEAT_FAST;
    }

    // Breath Patterns
    public void setRedBreath(){
        pattern = BREATH_RED;
    }
    public void setBlueBreath(){
        pattern = BREATH_BLUE;
    }
    public void setGreyBreath(){
        pattern = BREATH_GRAY;
    }
    // Color 1 Breath (Set on Blinkin)
    public void setColor1BreathSlow(){
        pattern = CP1_BREATH_SLOW;
    }
    public void setColor1BreathFast(){
        pattern = CP1_BREATH_FAST;
    }
    // Color 2 Breath (Set on Blinkin)
    public void setColor2BreathSlow(){
        pattern = CP2_BREATH_SLOW;
    }
    public void setColor2BreathFast(){
        pattern = CP2_BREATH_FAST;
    }

    // Strobe Patterns
    public void setRedStrobe(){
        pattern = STROBE_RED;
    }
    public void setGoldStrobe() {
        pattern = STROBE_GOLD;
    }
    public void setBlueStrobe() {
        pattern = STROBE_BLUE;
    }
    public void setWhiteStrobe(){
        pattern = STROBE_WHITE;
    }
    public void setColor1Strobe(){
        pattern = CP1_STROBE;
    }
    public void setColor2Strobe() {
        pattern = CP2_STROBE;
    }

    // Blend to black
    public void setColor1BlendBlack(){
        pattern = CP1_END_TO_END_BLEND_TO_BLACK;
    }
    public void setColor2BlendBlack(){
        pattern = CP2_END_TO_END_BLEND_TO_BLACK;
    }
    public void setColor12Blend(){
        pattern = CP1_2_END_TO_END_BLEND_1_TO_2;
    }
    public void setColor12BlendBlack(){ // Verify what this does/looks like???
        pattern = CP1_2_END_TO_END_BLEND;
    }
    public void setColor12NoBlend(){
        pattern = CP1_2_NO_BLENDING;
    }

    // Sparkles!
    public void setSparkle1on2(){
        pattern = CP1_2_SPARKLE_1_ON_2;
    }
    public void setSparkle2on1(){
        pattern = CP1_2_SPARKLE_2_ON_1;
    }

    // Color Gradient
    public void setGradient(){
        pattern = CP1_2_COLOR_GRADIENT;
    }

    // Solid Colors
    public void setRed(){
        pattern = RED;
    }
    public void setBlue(){
        pattern = BLUE;
    }
    public void setHotPink(){
        pattern = HOT_PINK;
    }
    public void setDarkRed(){
        pattern = DARK_RED;
    }
    public void setRedOrange(){
        pattern = RED_ORANGE;
    }
    public void setOrange(){
        pattern = ORANGE;
    }
    public void setGold(){
        pattern = GOLD;
    }
    public void setYellow(){
        pattern = YELLOW;
    }
    public void setLawnGreen(){
        pattern = LAWN_GREEN;
    }
    public void setLime(){
        pattern = LIME;
    }
    public void setDarkGreen(){
        pattern = DARK_GREEN;
    }
    public void setGreen(){
        pattern = GREEN;
    }
    public void setBlueGreen(){
        pattern = BLUE_GREEN;
    }
    public void setAqua(){
        pattern = AQUA;
    }
    public void setSkyBlue(){
        pattern = SKY_BLUE;
    }
    public void setDarkBlue(){
        pattern = DARK_BLUE;
    }
    public void setBlueViolet(){
        pattern = BLUE_VIOLET;
    }
    public void setViolet(){
        pattern = VIOLET;
    }
    public void setWhite(){
        pattern = WHITE;
    }
    public void setGrey(){
        pattern = GRAY;
    }
    public void setDarkGrey(){
        pattern = DARK_GRAY;
    }
    public void turnOff(){
        pattern = BLACK;
    }
}
