package Bibi_ElGareh_Team.behaviour;
import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import Bibi_ElGareh_Team.actions.*;

public abstract class Behaviour {

   private Behaviour nextBehaviour;

   /**
    * @param nextBehaviour the nextBehaviour to set
    */
   public void setNextBehaviour(Behaviour nextBehaviour) {
      this.nextBehaviour = nextBehaviour;
   }

   /**
    * @return the nextBehaviour
    */
   public Behaviour getNextBehaviour() {
      return nextBehaviour;
   }

   /**
    * L'action est-elle réalisable?
    */
   public abstract boolean isActivated();

   //Si oui, on realise l'action
   public abstract Action action();

}
