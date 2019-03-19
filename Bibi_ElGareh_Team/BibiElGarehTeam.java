package Bibi_ElGareh_Team;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.behaviour.*;

public class BibiElGarehTeam extends ControlSystemSS {

    private GameContext gameContext;

    private Behaviour behaviour;
    private Behaviour frontStrikerBehaviour;
    private Behaviour defenderBehaviour;
    private Behaviour defaultBehaviour;

    public void Configure(){
        gameContext = new GameContext(abstract_robot);

        frontStrikerBehaviour = new FrontStrikerBehaviour(gameContext);
        defenderBehaviour = new DefenderBehaviour(gameContext);
        defaultBehaviour = new DefaultBehaviour(gameContext);


        frontStrikerBehaviour.setNextBehaviour(defenderBehaviour);
        defenderBehaviour.setNextBehaviour(defaultBehaviour);
    }
    
    public int takeStep(){

        gameContext.init();
        behaviour = frontStrikerBehaviour;

        while(!behaviour.isActivated()){
            behaviour = behaviour.getNextBehaviour();
            gameContext.init();
        }

        behaviour.action();

        return(CSSTAT_OK);
        
    }
  
}
