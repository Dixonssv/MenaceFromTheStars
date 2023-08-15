package spaceinvaders;

public class SpawnManager {

    private Handler handler;
    private long spawnTimer;
    private int spawnDelay;
    private int level;
    private long levelUpTimer;
    private final int MAX_LEVEL;
    public static boolean BOSS;
    private Player player;

    public SpawnManager(Handler handler) {
        this.handler = handler;
        spawnDelay = 1500;
        level = 5;
        spawnTimer = System.currentTimeMillis();
        levelUpTimer = System.currentTimeMillis();
        MAX_LEVEL = 5;
        
        player = new Player(Game.WIDTH / 2 - Player.getSizeX(), Game.HEIGHT - 64 - Player.getSizeY(), this.handler);
        handler.addObject(player);
    }

    public void tick() {
        if (!BOSS) {
            if (System.currentTimeMillis() - levelUpTimer >= 20000) {

                level += 1;
                System.out.println("level " + level);
                
                levelUpTimer = System.currentTimeMillis();

                if (level < MAX_LEVEL) {
                    spawnDelay -= level * 100;
                }
                if (level % 7 == 0) {
                    handler.addObject(new SquidEnemy(player, handler));
                }
            }

            if (System.currentTimeMillis() - spawnTimer >= spawnDelay) {
                chooseEnemy();
                spawnTimer = System.currentTimeMillis();
            }
        }
    }

    public void chooseEnemy() {
        int x, y = 0;
        Enemy e = null;

        x = (int) (Math.random() * (Window.paneWidth - StandardEnemy.sizeX));

        if (level == 0) {
            e = new StandardEnemy(x, y, handler);
            e.setVelX((int) (Math.random() * 3) - (int) (Math.random() * 3));
        }else if (level % 5 == 0) {
            BOSS = true;
            e = new BossEnemy(handler);
        } else {
            int r = (int) (Math.random() * 100 + 1);
            if (r < 85) {
                //StandardEnemy 85% de probabilidad
                e = new StandardEnemy(x, y, handler);
                e.setVelX((int) (Math.random() * 3) - (int) (Math.random() * 3));
            } else if (r < 95) {
                //Charger 10% de probabilidad
                e = new ChargerEnemy(x, y, handler);
            } else if (r < 98) {
                //Sneaker 3% de probabilidad
                e = new SneakerEnemy(x, y, handler);
            } else {
                //Bonus 2% de probabilidad
                handler.addObject(new Bonus(x, y, handler));
                System.out.println("Bonus");
                return;
            }
        }
        handler.addObject(e);
    }

}
