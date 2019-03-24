package Bibi_ElGareh_Team.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.*;
import Bibi_ElGareh_Team.actions.*;

public class OffsidePosition extends Behaviour {

    protected double teammatesFactor=1.0;
    protected double opponentsFactor=1.0;
    protected double centerFactor=-1.0;

    public GameContext gameContext;

    /**
     * Constructeur logique.
     *
     * @param soccerMap
     */
    public OffsidePosition(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        int countOffsidePosition = 0;
        for (Vec2 teammate : this.gameContext.getTeamMates()) {
            if (Utilities.getDistance(this.gameContext.getOpponentGoal(), teammate).r < this.gameContext.getOpponentGoal().r) {
                countOffsidePosition++;
            }
        }
        if (countOffsidePosition < GameContext.N_OFFSIDE) {
            this.gameContext.getAbstractRobot().setDisplayString("Offside");
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action action() {

        double ATTACK_RADIUS = 1.25;

        AttractionRepulsion attractionRepulsion = new AttractionRepulsion(this.gameContext);

        Vec2 temp=new Vec2(this.gameContext.getBall());
        temp.sub(this.gameContext.getOpponentGoal());
        temp.setr(ATTACK_RADIUS);
        temp.add(this.gameContext.getOpponentGoal());

        /*if (temp.x * this.gameContext.getSide() < -2 * ATTACK_RADIUS) {

        }
        else {
            temp.setr(Math.min(temp.r - ATTACK_RADIUS, ATTACK_RADIUS));
            temp.add(this.gameContext.getOpponentGoal());
        }*/

        temp.add(attractionRepulsion.getOpponentRepulsion(0.2));

        return new ShootAction(this.gameContext, temp, true);
    }
}