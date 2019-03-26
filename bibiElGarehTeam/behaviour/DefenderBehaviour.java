package bibiElGarehTeam.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  bibiElGarehTeam.*;
import bibiElGarehTeam.actions.*;

/**
 * Comportement de défenseur. Le défenseur est placé au niveau du but, et avance sur la blace quand celle-ci
 * pénètre la partie allié.
 */
public class DefenderBehaviour extends Behaviour {

    GameContext gameContext;

    /**
     * Constructeur de la classe DefenderBehaviour
     * @param gameContext
     */
    public DefenderBehaviour(GameContext gameContext)
    {
        this.gameContext = gameContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        int countDefender = 0;
        for (Vec2 teammate : this.gameContext.getTeamMates()) {
            if (Utilities.getDistance(this.gameContext.getOurGoal(), teammate).r < this.gameContext.getOurGoal().r) {
                countDefender++;
            }
        }
        if (countDefender < GameContext.N_DEFENDERS) {
            this.gameContext.getAbstractRobot().setDisplayString("Defender");
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action action() {

        AttractionRepulsion attractionRepulsion = new AttractionRepulsion(this.gameContext);

        Vec2 attractionRepulsionOurGoal = new Vec2(attractionRepulsion.getBallAttraction());
        attractionRepulsionOurGoal.add(attractionRepulsion.getTeamMatesRepulsion(0.1));
        attractionRepulsionOurGoal.add(attractionRepulsion.getOpponentRepulsion(0.1));
        Vec2 sideRepulsion = attractionRepulsion.getRepulsionY();
        sideRepulsion.setr(sideRepulsion.r * 0.6);
        attractionRepulsionOurGoal.add(sideRepulsion);
        Vec2 ourGoalAttraction = new Vec2(this.gameContext.getOurGoal());
        ourGoalAttraction.setr(ourGoalAttraction.r * 3.2);
        attractionRepulsionOurGoal.add(ourGoalAttraction);

        return new ShootAction(this.gameContext, attractionRepulsionOurGoal, false);
    }
}