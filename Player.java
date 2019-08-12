public class Player {
  private int x;
  private int y;
  private int height;
  private int width;
  private int speedX;
  private int speedY;
  private int score;
  public Player(int x, int y, int height, int width) {
    this.x = x;
    this. y = y;
    this.height = height;
    this.width = width;
    this.speedX = 0;
    this.speedY = 0;
    this.score = 0;
  }
  public void newPos(int xSpeed, int ySpeed) {
    this.x += xSpeed;
    this.y += ySpeed;
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
  public void setScore(int score) {
    this.score += score;
  }
  public int getScore() {
    return this.score;
  }
}
