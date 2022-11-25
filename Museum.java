/*
 * Name Chihiro Asanoma
 * ID   010960290
 */
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
public class Museum {
  private final static Random generator = new Random();
  static int waiting_track = 0;
  static int dinosaur_track = 0;
  static int zoology_track = 0;
  static int gift_track = 0;
  private final Semaphore running = new Semaphore(1,true);
  private static final int WAITING_ROOMS = 40;
  private final Semaphore WAITING_SEMAPHORE = new Semaphore(WAITING_ROOMS, true); 
  private static final int DINOSAUR_ROOMS = 20;
  private final Semaphore DINOSAUR_SEMAPHORE = new Semaphore(DINOSAUR_ROOMS, true);
  private static final int ZOOLOGY_ROOMS = 25;
  private final Semaphore ZOOLOGY_SEMAPHORE = new Semaphore(ZOOLOGY_ROOMS, true);
  private static final int GIFT_SHOP = 30;
  private final Semaphore GIFT_SEMAPHORE = new Semaphore(GIFT_SHOP, true);
  public void StartTest(int k) {
    for (int i = 1; i < k+1; i++) {
        Person person = new Person(i);
        person.start();
    }
  } 
  public class Person extends Thread {
    
    int i;
    Person(int k){
      this.i = k;
    }
    
    @Override
    public void run() {
        try{
          Thread.sleep(generator.nextInt(400)); 
          WAITING_SEMAPHORE.acquire();
          running.acquire();
          waiting_track++;
          System.out.println("Visitor " + i + " enters the system.\n" + "Visitor " + i + " enters the waiting area and is waiting to enter the museum. There are " + waiting_track + " waiting" ); 
          if(dinosaur_track>=20){
           running.release();
          }
          DINOSAUR_SEMAPHORE.acquire();
          if(dinosaur_track>=20){
           running.acquire();
          }
          waiting_track--;
          dinosaur_track++;
          WAITING_SEMAPHORE.release();
          System.out.println("Visitor " + i + " enters dinosaur room. " +  "There are " + dinosaur_track + " watching dinosaurs!" );
          running.release();
          Thread.sleep(generator.nextInt(1000));
          ZOOLOGY_SEMAPHORE.acquire();
          running.acquire();
          zoology_track++;
          dinosaur_track--;
          DINOSAUR_SEMAPHORE.release();
          System.out.println("Visitor " + i + " enters Zoology room. " +  "There are " + zoology_track + " enjoying animals!");
          running.release();
          Thread.sleep(generator.nextInt(1500));
          GIFT_SEMAPHORE.acquire();
          running.acquire();
          gift_track++;
          zoology_track--;
          ZOOLOGY_SEMAPHORE.release();
          System.out.println("Visitor " + i + " enters Gift room. " +  "There are " + gift_track + " looking to buy!");
          running.release();
          Thread.sleep(generator.nextInt(3000));
          running.acquire();
          System.out.println("Visitor " + i + " exits the system.");
          gift_track--;
          GIFT_SEMAPHORE.release();
          running.release();
        }catch(InterruptedException e)
        {
             // this part is executed when an exception (in this example InterruptedException) occurs
             System.out.println("error");
        }
        
        
        
    }
  }
  public static void main(String[] args) {
    int sleep_time=Integer.parseInt(args[0]); 
    int visitors =Integer.parseInt(args[1]);
    System.out.println("Sleep time = " + sleep_time + " Number of users = " + visitors);
    Museum test = new Museum();
    test.StartTest(visitors);
  }
}
