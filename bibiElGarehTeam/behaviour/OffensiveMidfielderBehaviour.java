package bibiElGarehTeam.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  bibiElGarehTeam.*;
import bibiElGarehTeam.actions.*;

/**
 * Comportement de milieu offensif. Seul le mileu offensif tir.
 */
public class OffensiveMidfielderBehaviour extends Behaviour {

    GameContext gameContext;

    public OffensiveMidfielderBehaviour(GameContext gameContext) {

        this.gameContext = gameContext;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        this.gameContext.getAbstractRobot().setDisplayString("OffensiveMidfielder");
        return !this.gameContext.teammateCloserToTheBall();
    }

    @Override
    public Action action() {
        AttractionRepulsion attractionRepulsion = new AttractionRepulsion(this.gameContext);

        Vec2 attractionRepulsionBall = new Vec2(attractionRepulsion.getBallAttraction());

        if ( this.canShoot(attractionRepulsionBall) ){
            return new ShootAction(this.gameContext, this.gameContext.getPosition(), true);
        }
        return new ShootAction(this.gameContext, attractionRepulsionBall, false);
    }

    /**
     * Le milieu offensif peut-il tirer, selon l'axe?
     * Plusieurs tirs sont possibles : court, moyen et long
     * @param axis
     * @return
     */
    public boolean canShoot(Vec2 axis) {

        double distanceToTheOpponentGoal = GameContext.X_LIMIT - Utilities.getDistance(gameContext.getOpponentGoal(), axis).r;

        // Tir de près
        if (distanceToTheOpponentGoal < 0.15 && Utilities.inAxis(axis.t, this.gameContext.getOpponentGoal() , 0.04) && this.gameContext.emptyPlayground(axis,0.04)){
            return true;
        }
        // Tir à moyenne distance
        if (distanceToTheOpponentGoal < 0.8 && Utilities.inAxis(axis.t, this.gameContext.getOpponentGoal() , 0.08) && this.gameContext.emptyPlayground(axis,0.08)){
            return true;
        }
        // Tir de loin
        if (distanceToTheOpponentGoal < 0.8 && Utilities.inAxis(axis.t, this.gameContext.getOpponentGoal() , 0.12) && this.gameContext.emptyPlayground(axis,0.12)){
            return true;
        }
        return false;
    }

}