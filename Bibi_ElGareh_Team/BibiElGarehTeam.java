package Bibi_ElGareh_Team;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.behaviour.*;

public class BibiElGarehTeam extends ControlSystemSS {

    private GameContext gameContext;

    private Behaviour behaviour;
    private Behaviour unlockerBehaviour;
    private Behaviour frontStrikerBehaviour;
    private Behaviour defenderBehaviour;
    private OffsidePosition offsidePosition;
    private Behaviour defaultBehaviour;

    public void Configure(){
        gameContext = new GameContext(abstract_robot);

        unlockerBehaviour = new UnlockerBehaviour(gameContext);
        frontStrikerBehaviour = new FrontStrikerBehaviour(gameContext);
        defenderBehaviour = new DefenderBehaviour(gameContext);
        offsidePosition = new OffsidePosition(gameContext);
        defaultBehaviour = new DefaultBehaviour(gameContext);

        unlockerBehaviour.setNextBehaviour(frontStrikerBehaviour);
        frontStrikerBehaviour.setNextBehaviour(defenderBehaviour);
        defenderBehaviour.setNextBehaviour(offsidePosition);
        offsidePosition.setNextBehaviour(defaultBehaviour);
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
