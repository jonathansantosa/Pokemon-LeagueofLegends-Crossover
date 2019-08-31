public abstract class Champion
{
   private int spot;
   public String champName;
   private double hitPoints;
   private double damage;
   private int[] cooldown;
   private double maxHealth;
   private int range;
   private boolean isStunned;
   private int howLongStunned;
   private int howLong;
   public abstract void getSkill();
   public abstract void Q(Champion champ);
   public abstract void W(Champion champ);
   public abstract void E(Champion champ);
   public abstract void R(Champion champ);
   public void autoAttack(Champion champ)
   {
      if(enoughRange(champ, range))
      {champ.gotHit(getDamage());
       System.out.println(champName+" autoattacked");
       doAdditionalEffects(champ);
      }
      else{
         System.out.println("You missed, you don't have enough range");
         move("forwards", champ);
         }
   }
     public void move(String f, Champion champ)
   {
      if((f.equals("forwards") && getSpot()<champ.getSpot()) || (f.equals("backwards") && getSpot()>=champ.getSpot()))
       {  setSpot(getSpot()+1); 
          System.out.println(champName+" moved upwards");
          managePosition();
          }
      else
      {
         setSpot(getSpot()-1);
         System.out.println(champName+" moved downwards");
         managePosition();
         }
   }
   public void doAdditionalEffects(Champion champ)
   {
      int d = 0;
   }

   public void win()
   {
    System.out.println(champName + "WINS");
   }
   public void lose()
   {
      System.out.println(champName + "LOSES");
   }
   public void stunned(int howL) // Make sure to use for-loop on the stun method in duel
   {
      isStunned = true;
      howLong = howL;
   }
   public void resetStunned()
   {
      isStunned = false;
      howLongStunned = 0;
   }
   public void lowerStun()
   {
      howLongStunned++;
      if(howLongStunned >= howLong)
      {
         isStunned = false;
         howLongStunned = 0;
      }

   }
   public int getSpot()
   {
      return spot;
   }
   public String getChamp()
   {
      return champName;
   }
   public double getHitPoints()
   {
      return hitPoints;
   }
    public double getMaxHitPoints()
   {
      return maxHealth;
   }
   public double getDamage()
   {
      return damage;
   }
   public int getCooldown(int a)
   {
     return cooldown[a];
   }
    public boolean isStunned()
   {
      return isStunned;
   }
   public int getRange()
   {
      return range;
   }
    public boolean enoughRange(Champion champ, int g)
   {
      if(Math.abs(getSpot()-champ.getSpot()) <= g)
         return true;
      return false;

   }
   public int managePosition()
   {
      if(spot >10)
      spot = 10;
      if(spot <0)
      spot = 0;
      return spot;
   }

   public void setRange(int g)
   {
      range = g;
   }
     public void setSpot(int spots)
   {
      spot = spots;
   }
   public void gotHit(double dam)
   {
      hitPoints = hitPoints - dam;
   }

   public void setDamage(double dam)
   {
      damage = damage + dam;
   }

   public void changeCooldown(int g, int h)
   {
      cooldown[g] = cooldown[g] + h;
   }
   public void setHealth(double health)
   {
      hitPoints += health;
      maxHealth = hitPoints;
   }
   public void setCooldown(int g, int h)
   {
      cooldown[g] = h;
   }
  
   public void setNumCooldown()
   {
      cooldown = new int[4];
   }


}

public class Draven extends Champion
{

   private int numberOfTimes;
  
  
   public Draven()
   {
      champName = "Draven";
      setRange(5);
      setSpot(0);
      setHealth(900);
      resetStunned();
      setNumCooldown();
      setDamage(65);
      System.out.println("");
   }
   public Draven(int sp)
   {
      champName = "Draven";
      setRange(5);
      setSpot(sp);
      setHealth(800);
      setNumCooldown();
      resetStunned();
      setDamage(65);
      System.out.println("");
   }

   public void getSkill()
   {
      System.out.println("Draven's first ability is SPINNING AXE: \nDraven's next basic attack allows him to deal 115% bonus damage\nif he caught it by chance, he can autoattack again to deal extra bonus damage");
      System.out.println("");
      System.out.println("Draven's second ability is BLOOD RUSH: \nDraven moves 3 steps depending on where the opponent is currently");
      System.out.println("");
      System.out.println("Draven's third ability is STAND ASIDE: \nDraven throws his axes 3 steps ahead of him, dealing only a small amount of damage but allows Draven to attack again");
      System.out.println("");
      System.out.println("Draven's ultimate ability is WHIRLING DEATH: \nDraven throws his axes throughout the entire map, dealing an absurd amount of damage");
   }
  
    public void Q(Champion champ)
   {
      if(getCooldown(0)<= 0)
      {
         double c = Math.random();
         setDamage(getDamage()* 2.15);
         autoAttack(champ);
         System.out.println("Draven threw his empowered axe");
         if(c < 0.25 - 2*numberOfTimes)
         {
            setCooldown(1, 0);
            numberOfTimes+=2;
            System.out.println("Draven caught his axe");
            Q(champ);         
         }
         else
         {
            numberOfTimes=0;
            setCooldown(0,11);
            setDamage(-100);
            System.out.println("Draven missed his empowered axe and finished his turn");
         }
      }
        
   } 
   public void W(Champion champ)
   {
      if(getCooldown(1)<= 0)
      {
         if((Math.abs(getSpot()-champ.getSpot()) <= 3 && getSpot()<champ.getSpot()) || (Math.abs(getSpot()-champ.getSpot()) >= 3 && getSpot()>champ.getSpot()))
         {
            setSpot(getSpot() - 3);
            System.out.println("Draven moved downwards rapidly");
            managePosition();
         }
         else
         {
            setSpot(getSpot() + 3);
            System.out.println("Draven moved upwards rapidly");
            managePosition();
           
         }
      }
      setCooldown(1,12);
   }
   public void E(Champion champ)
   {
      if(getCooldown(2)<= 0){
         if(enoughRange(champ, 3))
         {
            champ.gotHit(90);
            champ.stunned(1);
            System.out.println("Draven threw his axe and stunned the enemy");
         }
         else
         {
            System.out.println("Draven missed");
         }
         setCooldown(2, 15);
      }
   }
   public void R(Champion champ)
   {
      if(getCooldown(3)<= 0){
         champ.gotHit(200);
         champ.gotHit(200);
         setCooldown(3, 90);
         System.out.println("Draven threw his axe and dealt a lot of damage");
      }
   } 
  
}
public class Varus extends Champion
{
   private int wStacks;
   private int chargeTime;
  
   public Varus()
   { 
      champName = "Varus";
      setRange(6);
      setSpot(0);
      setHealth(800);
      setNumCooldown();
      setDamage(60);
      System.out.println("");
      chargeTime = 0;
      wStacks = 0;
      resetStunned();
  
   }
   public Varus(int sp)
   {
      champName = "Varus";
      setRange(6);
      setSpot(sp);
      setHealth(800);
      setNumCooldown();
      setDamage(60);
      System.out.println("");
      wStacks = 0;
      chargeTime = 0;
      resetStunned();
   }
   public void getSkill()
   {
      System.out.println("Varus' first ability is Piercing Arrow: \nIn exchange for two turns, Varus charges a powerful arrow that deals absurd amount of damage depending on the enemy's missing health");
      System.out.println("");
      System.out.println("Varus' second ability is Blighted Quiver: \nIt doesn't have any active abilities, but each autoattack applies a stack of Blighted Quiver \nIf he uses an ability after some stacks, it will deal extra damage depending on the enemy's missing health");
      System.out.println("");
      System.out.println("Varus' third ability is Hail of Arrows: \nVarus launches a hail of arrows that desecrates the ground 3-6 spots ahead of him and doesn't allow the opponent to move for 1 second");
      System.out.println("");
      System.out.println("Varus' ultimate ability is Chain of Corrution: \nVarus flings out a tendril of corruption 4 spots in front of him, dealing some damage and applying all stacks of Blighted Quiver \nAdditionally, the opponent is stunned for three turns");
   }
   public void Q(Champion champ)
   {
      if(getCooldown(0)<= 0)
      {
         chargeTime++;
         if(chargeTime < 3)
         {
            System.out.println("Varus is charging his piercing Arrow. Wait" + (3-chargeTime)+ " more turn");
         }
         else
         {
            champ.gotHit(300+(champ.getMaxHitPoints()-champ.getHitPoints())*(0.35*wStacks+1));
            wStacks = 0;
            setCooldown(0, 15);
            System.out.println("Varus shot his piercing arrow");
         }
      }
   }
   public void W(Champion champ)
   {
      System.out.println("This ability doesn't have an active silly. You lost your turn");
   }
   public void E(Champion champ)
   {
      if(getCooldown(2)<= 0)
      {
      if(enoughRange(champ, 6) || Math.abs(getSpot()- champ.getSpot()) >=3)
      {
         champ.gotHit(150 +(champ.getMaxHitPoints()-champ.getHitPoints()) * (0.2 * wStacks+1));
         champ.stunned(2);
         wStacks = 0;
         System.out.println("Varus launched a hail of arrows");
      }
      else
         System.out.println("Varus missed");
      setCooldown(2,9);
      }
   }
   public void R(Champion champ)
   {
   if(getCooldown(3)<= 0)
      {

      if(enoughRange(champ, 4))
      {
         champ.stunned(4);
         wStacks += 3;
         System.out.println("Varus launched a tendril of corruption, stunning " +champ.champName+ " for 3 turns");
      }
      else
         System.out.println("Varus missed");
      setCooldown(3,60);


      }
     
   }
   public void doAdditionalEffects(Champion champ)
   {
      wStacks++;
   }




}
import java.util.*;

public class Maokai extends Champion
{
   private int passiveCooldown;
   private ArrayList<Sapling> sapling;
   public Maokai()
   {
      champName = "Maokai";
      sapling = new ArrayList<Sapling>();
      setRange(2);
      setSpot(0);
      setHealth(1700);
      setNumCooldown();
      setDamage(45);
      System.out.println("");
      resetStunned();
  
   }
   public Maokai(int sp)
   {
      champName = "Maokai";
      sapling = new ArrayList<Sapling>();
      setRange(2);
      setSpot(sp);
      setHealth(1700);
      setNumCooldown();
      setDamage(45);
      System.out.println("");
      resetStunned();
   }
  
   public void getSkill()
   {
      System.out.println("Maokai's first ability is Bramble Smash: \nMaokai knocks back nearby enemies with a shockwave, dealing damage and pushing them two spots backwards.");
      System.out.println("");
      System.out.println("Maokai's first ability is Twisted Advance: \nMaokai contorts into a mass of moving roots, becoming untargetable and dashing to the target. Upon arrival, he stuns and damages the target");
      System.out.println("");
      System.out.println("Maokai's third ability is Sapling Toss: \nMaokai flings a sapling to the target area to stand guard. Anyone who steps on it will be dealth some damage");
      System.out.println("");
      System.out.println("Maokai's ultimate ability is Nature's Grasp: \nMaokai summons a colossal wall of brambles and thorns that slowly advances forwards, damaging and strongly stunning any enemies in the path");
   }
  
   public void Q(Champion champ)
   {
      if(getCooldown(0)<= 0)
      {
         if(enoughRange(champ, 2))
         {
            champ.gotHit(160);
            if(getSpot()<=champ.getSpot())
            {
               champ.setSpot(champ.getSpot() + 2);
               champ.stunned(1);
               champ.managePosition();
            }
            else
            {
               champ.setSpot(champ.getSpot() - 2);
               champ.stunned(1);
               champ.managePosition();
            }
            System.out.println("Maokai knocked back enemies, dealing damage and stunning them");
         }
         else
            System.out.println("Not enough range");
         setCooldown(0, 3);
      }
      isChampTouchingSapling(champ);
   } 
   public void W(Champion champ)
   {
      if(getCooldown(1)<= 0)
      {
         if(enoughRange(champ,3))
         {
           champ.gotHit(50);
           setSpot(champ.getSpot());
           System.out.println("Maokai teleported to the enemy and dealt damage");
         }
         else
         {
           System.out.println("Not enough range");
         }
      }
      setCooldown(1,8);
      isChampTouchingSapling(champ);
   }
   public void E(Champion champ)
   {
      if(getCooldown(2)<= 0){
      Scanner sc = new Scanner(System.in);
      System.out.println("Sapling at which position? \n1-10");
      String b = sc.nextLine();
      sapling.add(new Sapling(Integer.parseInt(b)));
      setCooldown(2, 3);
     }
    
   }
   public void R(Champion champ)
   {
      if(getCooldown(3)<= 0){
         if(enoughRange(champ, 8))
         {  champ.gotHit(50);
            champ.stunned(3);
            setCooldown(3, 90);
            System.out.println("Maokai stunned the enemy for 3 turns");
         }
      }
      else
         System.out.println("You missed");
      isChampTouchingSapling(champ);
   }
   public void doAdditionalEffects(Champion champ)
   {
      if(passiveCooldown<=0)
      {
         gotHit(-180);
         passiveCooldown = 3;
      }
      else
      {
         passiveCooldown -= 1;
      }
      isChampTouchingSapling(champ);
   }
   public boolean isChampTouchingSapling(Champion champ)
   {
      for(int a = 0; a<sapling.size(); a++)
      if(champ.getSpot() == sapling.get(a).getSpot())
      {
         sapling.get(a).explode(champ);
         sapling.remove(a);
         System.out.println(champ.champName+" touched a sapling and he exploded");
         return true;
      }
      return false;
     
   }

 
  
}

public class Sapling
{
   private int spot;
   public Sapling(int s)
   {
      spot = s;
   }
   public void explode(Champion champ)
   {
    champ.gotHit(150); 
   }
   public int getSpot()
   {
      return spot;
   }
}

import java.util.*;

public class Duel
{
   public static void main(String[] args) //main program
   {
  
      Scanner sc = new Scanner(System.in); 
      Champion[] champ = new Champion[2];
      champ[0] = chooseYourChampion(sc, 0);
      if(aiMode(sc))
      {  champ[1] = randomChamp(champ[0], 10);
         System.out.println("LET THE DUEL BEGIN");
         Duel(champ, sc, true);
      }
      else{
         champ[1] = chooseYourChampion(sc, 10);
         System.out.println("LET THE DUEL BEGIN");
         Duel(champ, sc, false);
      }
  
  
   }
   public static void playerDuelItself(Champion[] champ, int turn, Scanner sc) //Duel players can control
   {
      if(champ[turn].isStunned() == true)
      {
         champ[turn].lowerStun();
         System.out.println("Player "+(turn+1)+" is stunned still stunned. Player "+(whichChamp(champ,turn)+1)+"'s turn");
      }
      else
      {
         System.out.println("Choose your move "+champ[turn].champName+ "\n Q(" + champ[turn].getCooldown(0) + ") | W("+champ[turn].getCooldown(1)+") | E("+champ[turn].getCooldown(2)+") | R(" + champ[turn].getCooldown(3)+") | Autoattack (AA) | move | Help");
         String c = sc.nextLine();
         if(c.equals("Q"))
            champ[turn].Q(champ[whichChamp(champ,turn)]);
         else if(c.equals("W"))
            champ[turn].W(champ[whichChamp(champ,turn)]);
         else if(c.equals("E"))
            champ[turn].E(champ[whichChamp(champ,turn)]);
         else if(c.equals("R"))
            champ[turn].R(champ[whichChamp(champ,turn)]);
         else if(c.equals("AA") || c.equals("Autoattack"))
            champ[turn].autoAttack(champ[whichChamp(champ,turn)]);
         else if(c.equals("Help"))
         {  champ[turn].getSkill();
            turn--;
         }
         else if(c.equals("move"))
         {  System.out.println("forwards or backwards");
            String d = sc.nextLine();
            if(d.equals("forwards"))
               champ[turn].move("forwards", champ[whichChamp(champ,turn)]);
            else
               champ[turn].move("backwards", champ[whichChamp(champ,turn)]);
         }
         else
            System.out.println("Move not found");
      }
      System.out.println("");
      System.out.println(champ[0].champName+": "+champ[0].getHitPoints() + " Health |" + champ[0].getSpot() + " Spot| ");
      System.out.println(champ[1].champName+": "+champ[1].getHitPoints() + " Health |" + champ[1].getSpot() + " Spot| ");
                 
   }
   public static void aiDuelItself(Champion[] champ,int turn) //Duel Ai is controlling
   {
      if(champ[turn].isStunned() == true)
      {
         champ[turn].lowerStun();
         System.out.println("Player "+(turn+1)+" is stunned still stunned. Player "+(whichChamp(champ,turn)+1)+"'s turn");
      }
      else
      {
         System.out.println("Player 2's turn: \n Q(" + champ[turn].getCooldown(0) + ") | W("+champ[turn].getCooldown(1)+") | E("+champ[turn].getCooldown(2)+") | R(" + champ[turn].getCooldown(3)+") | Autoattack (AA) | move | Help");
         int c = (int)(Math.random()*6);
         if(c == 0)
         { 
            if(champ[turn].getCooldown(0) > 0)
               c++;
            else
               champ[turn].Q(champ[whichChamp(champ,turn)]);
         }
         else if(c== 1)
         { 
            if(champ[turn].getCooldown(1) > 0)
               c++;
            else
               champ[turn].W(champ[whichChamp(champ,turn)]);
         }
         else if(c == 2 )
         { 
            if(champ[turn].getCooldown(2) > 0)
               c++;
            else
               champ[turn].E(champ[whichChamp(champ,turn)]);
         }
        
         else if(c == 3)
         { 
            if(champ[turn].getCooldown(3) > 0)
               c++;
            else
               champ[turn].R(champ[whichChamp(champ,turn)]);
         }
        
         else if(c == 4)
            champ[turn].autoAttack(champ[whichChamp(champ,turn)]);
         else
            champ[turn].move("backwards", champ[whichChamp(champ,turn)]);
      }
      System.out.println("");
      System.out.println(champ[0].champName+": "+champ[0].getHitPoints() + " Health |" + champ[0].getSpot() + " Spot| ");
      System.out.println(champ[1].champName+": "+champ[1].getHitPoints() + " Health |" + champ[1].getSpot() + " Spot| ");
   }
   public static void Duel(Champion[] champ, Scanner sc, boolean ai) // Where the duel takes place
   {
      while(champ[0].getHitPoints()>0 && champ[1].getHitPoints()>0)
      {
         if(ai == true)
         {
            for(int turn = 0; turn<2; turn++)
            {
               if(turn == 0)
                  playerDuelItself(champ, turn, sc);
               else
                  aiDuelItself(champ, turn);
               resetCooldown(champ, turn);
               winner(champ);
            }
         }
         else
            for(int turn = 0; turn<2; turn++)
            {
               playerDuelItself(champ, turn, sc);
               resetCooldown(champ, turn);
               winner(champ);
            }
      }
     
   }

   public static Champion chooseYourChampion(Scanner sc, int b) // player chooses champion
   {
      System.out.println("Choose Your Champion");
      String c = sc.nextLine();
      Champion champ = null;
      if(c.equals("Draven"))
      {  champ = new Draven(b);
         System.out.println("You chose Draven");
      }
        
      else if(c.equals("Varus"))
      {  champ = new Varus(b);
         System.out.println("You chose Varus");
      }
      else if(c.equals("Maokai"))
      {  champ = new Maokai(b);
         System.out.println("You chose Maokai");
      }
      return champ;
   }
   public static Champion randomChamp(Champion champ1, int p) // Ai chooses champion
   {
      Champion champ = null;
      double a = Math.random();
      if(a <0.5)
      {  champ = new Draven(p);
         System.out.println("Enemy chose Draven");
      }
      else
      {  champ = new Varus(p);
         System.out.println("Enemy chose Varus");
      }
      return champ;
   }
   public static boolean aiMode(Scanner sc) // Single Player or Coop
   {
      System.out.println("Single Player or Coop");
      String b = sc.nextLine();
      if(b.equals("Single Player"))
      {
         return true;
      }
      else
         return false;
   }
       
   public static int whichChamp(Champion[] champ, int a) // choosing which turn
   {
      if(a == champ.length-1)
         return a-1;
      else
         return a+1;
   }
   public static void resetCooldown(Champion[] champ, int b) // changing the cooldown by 1 everytime
   {
      for(int a = 0; a<4; a++)
      {
         if(champ[b].getCooldown(a) > 0)
            champ[b].changeCooldown(a, -1);
      }
   }
   public static void winner(Champion[] champ)
   {
      if(champ[1].getHitPoints()<0)
      {
         champ[0].win();
      }
      else if(champ[0].getHitPoints()<0)
         champ[1].win();
  
   }
 
}

