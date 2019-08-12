import java.awt.*;
import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
/**
  This class is for inititalizing canvas and game area and starting game threads
*/
public class GameFrame extends JFrame {
  public ArrayList<NPC> npcs = new ArrayList<NPC>();
  public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  private MyCanvas canvas = new MyCanvas();
  Random rand = new Random();

  public GameFrame() {
    setLayout(new BorderLayout());
    setSize(650, 400);
    setTitle("Square Wars");
    add("Center", canvas);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);
    //just for testing we are going to send a fixed rate of npcs
    for(int i = 0; i < 21; i++) {
      npcs.add(new NPC(getRandomX(), getRandomY()));
    }
    /**
      Threads are alittle laggy need to redesigns
    */
    new Timer().scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          canvas.gameEngine();
      }
    },0, 42);

    //sets the enemies updateing X-position for npcs and bullets
    new Timer().scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          canvas.npcAndBulletUpdate();
      }
    },0, 50);

//    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
//    ScheduledFuture<?> exe = executor.scheduleAtFixedRate(new Runnable() {
//      @Override
//      public void run() {
//        canvas.gameEngine();
//      }
//    }, 0, 1, TimeUnit.MILLISECONDS);

  //  executor.scheduleWithFixedDelay(new Runnable() {
  //    public void run() {
  //      canvas.gameEngine();
  //    }
//    }, 1, TimeUnit.MILLISECONDS);

//    ScheduledFuture<?> exe2 =  executor.scheduleAtFixedRate(new Runnable() {
//      @Override
//      public void run() {
//        canvas.npcAndBulletUpdate();
//      }
//    }, 0, 50, TimeUnit.MILLISECONDS);

  }
  //random coor generaters
  public int getRandomY() {
    int NPCy = rand.nextInt(350);
    return NPCy;
  }
  public int getRandomX() {
    int low = 651;
    int high = 2600;
    int result = rand.nextInt(high-low) + low;
    return result;
  }

  /*********************************************************************************
  private class that controlls the canvas and game framing layout
    ->GameEngine and NPC rending<-
  ***********************************************************************************/
  private class MyCanvas extends Canvas implements KeyListener {
    Player player = new Player(50, 200, 20, 20);

    public MyCanvas() {
      addKeyListener(this);
    }
    //method to paint the canvas with the player and NPCs
    @Override
    public void paint(Graphics g) {
      super.paint(g);
      Graphics2D playerGraphic = (Graphics2D) g;
      Graphics2D npcGraphic = (Graphics2D) g;
      Graphics2D bulletGraphic = (Graphics2D) g;
      Graphics2D score = (Graphics2D) g;
      playerGraphic.drawRect(player.getX(), player.getY(), player.getHeight(), player.getWidth());
      playerGraphic.setColor(Color.RED);
      playerGraphic.fillRect(player.getX(), player.getY(), player.getHeight(), player.getWidth());
      //causes rendering issues 
//      score.setColor(Color.BLACK);
//      score.setFont(new Font("Bold", 1, 20));
//      score.drawString("Score: " + player.getScore(), 400, 100);

      if(!(npcs.isEmpty())) {
      for(int i = 0; i < npcs.size(); i++) {
          npcGraphic.drawRect(npcs.get(i).getX(), npcs.get(i).getY(), npcs.get(i).getHeight(), npcs.get(i).getWidth());
          npcGraphic.setColor(Color.BLUE);
          npcGraphic.fillRect(npcs.get(i).getX(), npcs.get(i).getY(), npcs.get(i).getHeight(), npcs.get(i).getWidth());
        }
      }

      if(!(bullets.isEmpty())) {
        for(int i = 0; i < bullets.size(); i++) {
          bulletGraphic.drawRect(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getHeight(), bullets.get(i).getWidth());
          bulletGraphic.setColor(Color.BLACK);
          bulletGraphic.fillRect(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getHeight(), bullets.get(i).getWidth());
        }
      }
    }

    //method for game engine controlls
    //return afer each staement because it rends every millisecond so no need for runthrough method
    public void gameEngine() {
      if(!(bullets.isEmpty() && npcs.isEmpty())) {
        //have npcs first
        for(int i = 0; i < npcs.size(); i++) {
          for(int k = 0; k < bullets.size(); k++) {
            if(bullets.get(k).crashWith(npcs.get(i))) {
                npcs.remove(i);
                bullets.remove(k);
                player.setScore(1);
                repaint();
                return;
            }
            else if(bullets.get(k).getX() > 651) {
              bullets.remove(k);
              repaint();
              return;
            }
            else if(npcs.get(i).getX() <= 0) {
              npcs.remove(i);
              repaint();
              return;
            }
          }
        }
      }
      repaint();
    }
    public void npcAndBulletUpdate() {
      for(int i = 0; i < npcs.size(); i++) {
        npcs.get(i).setX(-3);
      }

      if(!(bullets.isEmpty())) {
        for(int i = 0; i < bullets.size(); i++) {
          bullets.get(i).newPos(12, 0);
        }
      }
    }


    /******************************************************************************
    This is for game controlls moving player shooting ect
    ********************************************************************************/

    //key codes for moving player on screen
    @Override
    public void keyPressed(KeyEvent event) {
      if(event.getKeyCode() == KeyEvent.VK_UP) {
        player.newPos(0, -9);
      } else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
        player.newPos(0, 9);
      } else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
          player.newPos(9, 0);
      } else if(event.getKeyCode() == KeyEvent.VK_LEFT) {
        player.newPos(-9,0);
      } else if(event.getKeyCode() == KeyEvent.VK_SPACE) {
        bullets.add(new Bullet(player.getX(), player.getY(), 5, 15));
      }
    }
    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyTyped(KeyEvent e){}
  }
}
