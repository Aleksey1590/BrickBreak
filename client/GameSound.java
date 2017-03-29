package client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * A class to play sounds in the menu and in-game.
 */
public class GameSound {
	private static final String BRICK_HIT = "/client//resource/brickHit.wav";
	private static final String PLAYER_HIT = "/client//resource/space_playerHit.wav";
	private static final String BACKGROUND_MUSIC = "/client//resource/background_space_loop.wav";
	private static final String GOOD_BONUS = "/client//resource/goodBonus.wav";
	private static final String BAD_BONUS = "/client//resource/badBonus.wav";

	private static Clip bgmClip;

	private static Clip hitPlayerClip;
	private static Clip hitBrickClip;
	private static Clip goodBonusClip;
	private static Clip badBonusClip;

	public static float gameSoundVolume = 0;
	public static float gameMusicVolume = 0;

	/**
	 * Plays the sound at the given file path
	 * 
	 * @param soundPath
	 *            the file path of the sound to be played
	 */
	public static void playHitPlayerSound(String hitPlayerSoundPath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameSound.class.getResource(hitPlayerSoundPath));
			hitPlayerClip = AudioSystem.getClip();
			hitPlayerClip.open(audioInputStream);
			hitPlayerClip.start();
			FloatControl gainControl = (FloatControl) hitPlayerClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gameSoundVolume);
		} catch (Exception e) {
			e.printStackTrace();
			// FIX: Commented out because there are errors on certain Operating Systems.
		}
	}

	/**
	 * Plays the sound at the given file path
	 * 
	 * @param soundPath
	 *            the file path of the sound to be played
	 */
	public static void playHitBrickSound(String hitPlayerSoundPath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameSound.class.getResource(hitPlayerSoundPath));
			hitBrickClip = AudioSystem.getClip();
			hitBrickClip.open(audioInputStream);
			hitBrickClip.start();
			FloatControl gainControl = (FloatControl) hitBrickClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gameSoundVolume);
		} catch (Exception e) {
			e.printStackTrace();
			// FIX: Commented out because there are errors on certain Operating Systems.
		}
	}

	/**
	 * Plays the sound at the given file path
	 * 
	 * @param soundPath
	 *            the file path of the sound to be played
	 */
	public static void playGoodBonusSound(String goodBonusSoundPath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameSound.class.getResource(goodBonusSoundPath));
			goodBonusClip = AudioSystem.getClip();
			goodBonusClip.open(audioInputStream);
			goodBonusClip.start();
			FloatControl gainControl = (FloatControl) goodBonusClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gameSoundVolume);
		} catch (Exception e) {
			e.printStackTrace();
			// FIX: Commented out because there are errors on certain Operating Systems.
		}
	}

	/**
	 * Plays the sound at the given file path
	 * 
	 * @param soundPath
	 *            the file path of the sound to be played
	 */
	public static void playBadBonusSound(String badBonusSoundPath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameSound.class.getResource(badBonusSoundPath));
			badBonusClip = AudioSystem.getClip();
			badBonusClip.open(audioInputStream);
			badBonusClip.start();
			FloatControl gainControl = (FloatControl) badBonusClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gameSoundVolume);
		} catch (Exception e) {
			e.printStackTrace();
			// FIX: Commented out because there are errors on certain Operating Systems.
		}
	}

	/**
	 * Plays the background music at the given file path
	 * 
	 * @param soundPath
	 *            the file path of the background music to be played
	 */
	public static void playBackgroundMusic(String soundPath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameSound.class.getResource(soundPath));
			bgmClip = AudioSystem.getClip();
			bgmClip.open(audioInputStream);
			FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gameMusicVolume);
			bgmClip.start();
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A helper function for playing the brick hit sound
	 */
	public void brickHitSound() {
		playHitBrickSound(BRICK_HIT);
	}

	/**
	 * A helper function for playing the player hit sound
	 */
	public void playerHitSound() {
		playHitPlayerSound(PLAYER_HIT);
	}

	/**
	 * A helper function for playing the bonus hit sound
	 */
	public void goodBonusSound() {
		playGoodBonusSound(GOOD_BONUS);
	}

	public void badBonusSound() {
		playBadBonusSound(BAD_BONUS);
	}

	/**
	 * A helper function for playing the background music
	 */
	public void playBGM() {
		playBackgroundMusic(BACKGROUND_MUSIC);
	}

	/**
	 * @return the background music bgmClip
	 */
	public Clip getBGMClip() {
		return bgmClip;
	}

}
