package bibiElGarehTeam.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  bibiElGarehTeam.*;
import bibiElGarehTeam.actions.*;

/**
 * Comportement de l'avant-centre de l'équipe. Il se place toujours dans la partie ennemie du terrain et
 * il se place systèmatiquement au centre du terrain ppur récupérer le ballon quand le ballon est remis
 * au centre
 */
public class CenterForwardBehaviour extends Behaviour {

    public GameContext gameContext;

    /**
     * Constructeur de la classe CenterForwardBehaviour
     *
     * @param gameContext
     */
    public CenterForwardBehaviour(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        int countCenterForwardBehaviour = 0;
        for (Vec2 teammate : this.gameContext.getTeamMates()) {
            if (Utilities.getDistance(this.gameContext.getOpponentGoal(), teammate).r < this.gameContext.getOpponentGoal().r) {
                countCenterForwardBehaviour++;
            }
        }
        if (countCenterForwardBehaviour < GameContext.N_OFFSIDE) {
            this.gameContext.getAbstractRobot().setDisplayString("CenterForward");
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

        Vec2 temp=new Vec2(this.gameContext.getBall());
        temp.sub(this.gameContext.getOpponentGoal());
        temp.setr(1.25);
        temp.add(this.gameContext.getOpponentGoal());

        temp.add(attractionRepulsion.getOpponentRepulsion(0.2));

        return new ShootAction(this.gameContext, temp, true);
    }
}