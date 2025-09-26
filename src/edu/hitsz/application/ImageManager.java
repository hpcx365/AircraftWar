package edu.hitsz.application;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;
import edu.hitsz.prop.HealthProp;
import pers.hpcx.util.Random;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 * @author fengyang
 */
public class ImageManager {
    
    private static final Path IMAGE_PATH = Paths.get("src/images");
    
    public static final BufferedImage BACKGROUND_IMAGE;
    public static final BufferedImage HERO_IMAGE;
    public static final BufferedImage HERO_BULLET_IMAGE;
    public static final BufferedImage ENEMY_BULLET_IMAGE;
    public static final BufferedImage MOB_ENEMY_IMAGE;
    public static final BufferedImage ELITE_ENEMY_IMAGE;
    public static final BufferedImage HEALTH_PROP_IMAGE;
    public static final BufferedImage BULLET_PROP_IMAGE;
    public static final BufferedImage BOMB_PROP_IMAGE;
    
    static {
        // 每次启动时随机加载背景
        BACKGROUND_IMAGE = loadImage(Random.getInstance().choice(new String[] {
                "bg.jpg",
                "bg2.jpg",
                "bg3.jpg",
                "bg4.jpg",
                "bg5.jpg",
        }));
        HERO_IMAGE = loadImage("hero.png");
        MOB_ENEMY_IMAGE = loadImage("mob.png");
        ELITE_ENEMY_IMAGE = loadImage("elite.png");
        HERO_BULLET_IMAGE = loadImage("bullet_hero.png");
        ENEMY_BULLET_IMAGE = loadImage("bullet_enemy.png");
        HEALTH_PROP_IMAGE = loadImage("prop_blood.png");
        BULLET_PROP_IMAGE = loadImage("prop_bullet.png");
        BOMB_PROP_IMAGE = loadImage("prop_bomb.png");
    }
    
    private static BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(IMAGE_PATH.resolve(name).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 类名-图片 映射，存储各基类的图片
     */
    private static final Map<Class<?>, BufferedImage> CLASSNAME_IMAGE_MAP = Map.ofEntries(
            Map.entry(HeroAircraft.class, HERO_IMAGE),
            Map.entry(MobEnemy.class, MOB_ENEMY_IMAGE),
            Map.entry(EliteEnemy.class, ELITE_ENEMY_IMAGE),
            Map.entry(HeroBullet.class, HERO_BULLET_IMAGE),
            Map.entry(EnemyBullet.class, ENEMY_BULLET_IMAGE),
            Map.entry(HealthProp.class, HEALTH_PROP_IMAGE),
            Map.entry(BulletProp.class, BULLET_PROP_IMAGE),
            Map.entry(BombProp.class, BOMB_PROP_IMAGE)
    );
    
    public static BufferedImage get(Object obj) {
        return obj == null ? null : CLASSNAME_IMAGE_MAP.get(obj.getClass());
    }
}
