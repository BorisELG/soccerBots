package Bibi_ElGareh_Team.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.*;
import Bibi_ElGareh_Team.actions.*;

public class UnlockerBehaviour extends Behaviour {

    GameContext gameContext;

    public UnlockerBehaviour(GameContext gameContext)
    {
        this.gameContext = gameContext;
    }

    @Override
    public boolean isActivated() {
        if (this.gameContext.getCountLocked() > GameContext.LOCK+25) {
            gameContext.setCountLocked(0);
        }

         return (this.gameContext.getCountLocked() > GameContext.LOCK);
    }

    @Override
    public Action action() {

        AttractionRepulsion attractionRepulsion = new AttractionRepulsion(this.gameContext);

        Vec2 aR = new Vec2(0,0);

        Vec2 teammatesAR = attractionRepulsion.getTeamMatesRepulsion(0.1);
        aR.add(teammatesAR);

        Vec2 opponentsAR = attractionRepulsion.getOpponentRepulsion(1.0);
        aR.add(opponentsAR);

        this.gameContext.getAbstractRobot().setDisplayString("Unlocker");

        return new ShootAction(this.gameContext, aR, false);


    }
}