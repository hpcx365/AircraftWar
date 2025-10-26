package pers.hpcx.app;

import pers.hpcx.audio.BufferedAudio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResourceManager {
    
    private static final Path RESOURCE_PATH = Paths.get("res");
    private static final Path IMAGE_PATH = RESOURCE_PATH.resolve("image");
    private static final Path AUDIO_PATH = RESOURCE_PATH.resolve("audio");
    
    public static final List<BufferedImage> BACKGROUND_IMAGES = List.of(
        loadImage("bg1.jpg"), loadImage("bg2.jpg"), loadImage("bg3.jpg"), loadImage("bg4.jpg"), loadImage("bg5.jpg")
    );
    public static final BufferedImage HERO_IMAGE = loadImage("hero.png");
    public static final BufferedImage HERO_BULLET_IMAGE = loadImage("bullet_hero.png");
    public static final BufferedImage ENEMY_BULLET_IMAGE = loadImage("bullet_enemy.png");
    public static final BufferedImage MOB_ENEMY_IMAGE = loadImage("mob.png");
    public static final BufferedImage ELITE_ENEMY_IMAGE = loadImage("elite.png");
    public static final BufferedImage SUPER_ELITE_ENEMY_IMAGE = loadImage("super_elite.png");
    public static final BufferedImage BOSS_ENEMY_IMAGE = loadImage("boss.png");
    public static final BufferedImage HEALTH_PROP_IMAGE = loadImage("prop_blood.png");
    public static final BufferedImage BULLET_PROP_IMAGE = loadImage("prop_bullet.png");
    public static final BufferedImage BOMB_PROP_IMAGE = loadImage("prop_bomb.png");
    public static final BufferedImage SUPER_BULLET_PROP_IMAGE = loadImage("prop_super_bullet.png");
    
    public static final BufferedAudio BACKGROUND_AUDIO = loadAudio("bgm.wav");
    public static final BufferedAudio BOSS_BACKGROUND_AUDIO = loadAudio("bgm_boss.wav");
    public static final BufferedAudio BOMB_EXPLOSION_AUDIO = loadAudio("bomb_explosion.wav");
    public static final BufferedAudio BULLET_AUDIO = loadAudio("bullet.wav");
    public static final BufferedAudio BULLET_HIT_AUDIO = loadAudio("bullet_hit.wav");
    public static final BufferedAudio GET_SUPPLY_AUDIO = loadAudio("get_supply.wav");
    public static final BufferedAudio GAME_OVER_AUDIO = loadAudio("game_over.wav");
    
    private static BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(IMAGE_PATH.resolve(name).toFile());
        } catch (Exception e) {
            Main.LOGGER.error(
                "Failed to load image: " + name,
                "Game will exit immediately",
                e
            );
            System.exit(0);
            return null;
        }
    }
    
    private static BufferedAudio loadAudio(String name) {
        try {
            return BufferedAudio.read(AUDIO_PATH.resolve(name).toFile());
        } catch (Exception e) {
            Main.LOGGER.error(
                "Failed to load image: " + name,
                "Game will exit immediately",
                e
            );
            System.exit(0);
            return null;
        }
    }
}
