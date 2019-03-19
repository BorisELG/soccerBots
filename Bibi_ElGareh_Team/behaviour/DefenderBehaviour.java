package Bibi_ElGareh_Team.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.*;
import Bibi_ElGareh_Team.actions.*;

public class DefenderBehaviour extends Behaviour {

    GameContext gameContext;

    public DefenderBehaviour(GameContext gameContext)
    {
        this.gameContext = gameContext;
    }

    @Override
    public boolean isActivated() {
        int countDefender = 0;
        for (Vec2 teammate : this.gameContext.getTeamMates()) {
            if (Utilities.getDistance(this.gameContext.getOurGoal(), teammate).r < this.gameContext.getOurGoal().r) {
                countDefender++;
            }
        }
        System.out.println(countDefender);
        if (countDefender < GameContext.N_DEFENDERS) {
            return true;
        }
        return false;
    }

    @Override
    public Action action() {

        AttractionRepulsion attractionRepulsion = new AttractionRepulsion(this.gameContext);

        Vec2 attractionRepulsionOurGoal = new Vec2(attractionRepulsion.getBallAttraction());

        attractionRepulsionOurGoal.add(attractionRepulsion.getTeamMatesRepulsion(0.1));
        attractionRepulsionOurGoal.add(attractionRepulsion.getOpponentRepulsion(0.1));

        Vec2 sideRepulsion = attractionRepulsion.getRepulsionY();
        sideRepulsion.setr(sideRepulsion.r * 0.8);
        attractionRepulsionOurGoal.add(sideRepulsion);

        Vec2 ourGoalAttraction = new Vec2(this.gameContext.getOurGoal());
        ourGoalAttraction.setr(ourGoalAttraction.r * 2.0);
        attractionRepulsionOurGoal.add(ourGoalAttraction);

        return new ShootAction(this.gameContext, attractionRepulsionOurGoal, false);
    }
}