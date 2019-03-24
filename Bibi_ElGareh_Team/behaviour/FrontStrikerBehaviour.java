package Bibi_ElGareh_Team.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.*;
import Bibi_ElGareh_Team.actions.*;

public class FrontStrikerBehaviour extends AttackBehaviour {

    public FrontStrikerBehaviour(GameContext gameContext) {

        super(gameContext);

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        this.gameContext.getAbstractRobot().setDisplayString("Attack");
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


    public boolean canShoot(Vec2 axis) {

        double distanceToTheOpponentGoal = GameContext.X_LIMIT - Utilities.getDistance(gameContext.getOpponentGoal(), axis).r;

        if (distanceToTheOpponentGoal < 0.15 && Utilities.inAxis(axis.t, this.gameContext.getOpponentGoal() , 0.06) && this.gameContext.emptyPlayground(axis,0.03)){
            //System.out.println("short shoot");
            return true;
        }
        if (distanceToTheOpponentGoal < 0.8 && Utilities.inAxis(axis.t, this.gameContext.getOpponentGoal() , 0.06) && this.gameContext.emptyPlayground(axis,0.065)){
            //System.out.println("middle shoot");
            return true;
        }
        if (distanceToTheOpponentGoal < 0.8 && Utilities.inAxis(axis.t, this.gameContext.getOpponentGoal() , 0.06) && this.gameContext.emptyPlayground(axis,0.8)){
            //System.out.println("long shoot");
            return true;
        }
        return false;
    }

}