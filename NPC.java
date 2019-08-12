public class NPC {
  private int x;
  private int y;
  private int height;
  private int width;
  public NPC(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 20;
    this.height = 20;
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
  public void setX(int x) {
    this.x += x;
  }
}
