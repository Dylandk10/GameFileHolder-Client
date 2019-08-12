public class Bullet {
  private int x;
  private int y;
  private int height;
  private int width;

  public Bullet(int x, int y, int width, int height) {
    this.x =x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  public void newPos(int x, int y) {
    this.x += x;
    this.y += y;
  }
  public boolean crashWith(NPC npc) {
    int bulletLeft = this.x;
    int bulletRight = this.x + (this.width);
    int bulletTop = this.y;
    int bulletBottom = this.y + (this.height);
    int npcLeft = npc.getX();
    int npcRight = npc.getX() + npc.getWidth();
    int npcTop = npc.getY();
    int npcBottom = npc.getY() + npc.getHeight();

    if((bulletBottom < npcTop) || (bulletTop > npcBottom) || (bulletRight < npcLeft) || (bulletLeft > npcRight))
      return false;
    else
      return true;
  }
  public int getX() {
    return this.x;
  }
  public int getY() {
    return this.y;
  }
  public int getHeight(){
    return this.height;
  }
  public int getWidth() {
    return this.width;
  }
}
