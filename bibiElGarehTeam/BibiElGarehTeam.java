package bibiElGarehTeam;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  bibiElGarehTeam.behaviour.*;

public class BibiElGarehTeam extends ControlSystemSS {
    /**
     * Classe principale qui controle l'agent et
     * ordonancement des comportements par priorité
     */

    private GameContext gameContext;

    private Behaviour behaviour;
    private Behaviour unlockerBehaviour;
    private Behaviour offensiveMidfielderBehaviour;
    private Behaviour defenderBehaviour;
    private Behaviour centerForwardBehaviour ;
    private Behaviour defaultBehaviour;

    public void Configure(){
        gameContext = new GameContext(abstract_robot);

        unlockerBehaviour = new UnlockerBehaviour(gameContext);
        offensiveMidfielderBehaviour = new OffensiveMidfielderBehaviour(gameContext);
        defenderBehaviour = new DefenderBehaviour(gameContext);
        centerForwardBehaviour = new CenterForwardBehaviour(gameContext);
        defaultBehaviour = new DefaultBehaviour(gameContext);

        //ordonnancement des comportements selon le patron de conception Chain of Responsability
        unlockerBehaviour.setNextBehaviour(offensiveMidfielderBehaviour);
        offensiveMidfielderBehaviour.setNextBehaviour(defenderBehaviour);
        defenderBehaviour.setNextBehaviour(centerForwardBehaviour);
        centerForwardBehaviour.setNextBehaviour(defaultBehaviour);

    }
    
    public int takeStep(){

        gameContext.init();
        behaviour = unlockerBehaviour;

        while(!behaviour.isActivated()){
            behaviour = behaviour.getNextBehaviour();
            gameContext.init();
        }

        behaviour.action();
        gameContext.countLocked();

        return(CSSTAT_OK);
        
    }
  
}
