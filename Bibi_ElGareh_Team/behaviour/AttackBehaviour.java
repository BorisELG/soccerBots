package Bibi_ElGareh_Team.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.*;
import  Bibi_ElGareh_Team.Utilities;
import Bibi_ElGareh_Team.actions.*;

public class AttackBehaviour extends Behaviour {

    public static double COHESION = 0.1;
    public static double SEPARATION = 0.1;
    public static double ALIGNEMENT = 0.1;

    protected GameContext gameContext;

    public AttackBehaviour(GameContext gameContext){

        this.gameContext = gameContext;
    }

    public boolean isActivated(){
        int countAttackers = 0;
        for (Vec2 mate : this.gameContext.getTeamMates()) {
            if (Utilities.getDistance(this.gameContext.getOpponentGoal(), mate).r < this.gameContext.getOpponentGoal().r) {
                countAttackers++;
            }
        }
        if (countAttackers < this.gameContext.N_ATTACKERS) {
            return true;
        }
        return false;
    }

    @Override
    public Action action(){
        return new ShootAction(this.gameContext, this.gameContext.getPosition(), false);
    }

    public Vec2 getFlockPosition()
    {
        Vec2 result = this.cohesion();
        result.add(this.separation(0.2));
        result.add(this.alignement());
        Vec2 position = this.gameContext.getPosition();
        position.add(result);
        position.add(this.gameContext.getBall());

        return position;
    }

    private Vec2 cohesion() {
        Vec2 resultCohesion = new Vec2(0,0);
        for(Vec2 mate : this.gameContext.getTeamMates()){
            if(Utilities.getDistance(mate,this.gameContext.getOpponentGoal()).r > 0.1){
                resultCohesion.add(mate);
            }
        }
        resultCohesion.sub(this.gameContext.getPosition());
        resultCohesion.setx(resultCohesion.x * COHESION);
        resultCohesion.sety(resultCohesion.y * COHESION);
        return resultCohesion;
    }

    private Vec2 separation(double rayon) {
        Vec2 resultSeperation = new Vec2(0,0);
        for(Vec2 mate : this.gameContext.getTeamMates()){
            if(Utilities.getDistance(mate,this.gameContext.getOpponentGoal()).r > 0.1 && mate.r < SocSmall.RADIUS + rayon){
                mate.sub(this.gameContext.getPosition());
                resultSeperation.sub(mate);
            }
        }
        resultSeperation.setx(resultSeperation.x * SEPARATION);
        resultSeperation.sety(resultSeperation.y * SEPARATION);
        return resultSeperation;
    }

    private Vec2 alignement() {
        Vec2 position = this.gameContext.getPosition();
        position.sett(this.gameContext.getAbstractRobot().getSteerHeading(this.gameContext.getCurrTime()));
        Vec2 resultAlignement = new Vec2(0,0);
        for(Vec2 mate : this.gameContext.getTeamMates()){
            Vec2 ball = this.gameContext.getBall();
            ball.sub(mate);
            if(Utilities.getDistance(mate, this.gameContext.getOpponentGoal()).r > 0.1){
                resultAlignement.add(this.gameContext.getBall());
            }
        }

        resultAlignement.sub(position);
        resultAlignement.setx(resultAlignement.x * ALIGNEMENT);
        resultAlignement.sety(resultAlignement.y * ALIGNEMENT);

        return resultAlignement;
    }
}