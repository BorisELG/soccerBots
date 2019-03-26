package bibiElGarehTeam.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  bibiElGarehTeam.*;
import bibiElGarehTeam.actions.*;

/**
 * Comportement par défaut
 */
public class DefaultBehaviour extends Behaviour {

    GameContext gameContext;

    /**
     * Constructeur logique.
     *
     * @param gameContext
     */
    public DefaultBehaviour(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action action() {

        AttractionRepulsion attractionRepulsion = new AttractionRepulsion(this.gameContext);

        Vec2 aR = new Vec2(attractionRepulsion.getBallAttraction());

        Vec2 teammatesAR = attractionRepulsion.getTeamMatesRepulsion(0.4);
        aR.add(teammatesAR);

        Vec2 opponentsAR = attractionRepulsion.getOpponentRepulsion(1.0);
        aR.add(opponentsAR);

        Vec2 centerAR = new Vec2(this.gameContext.getPosition());
        centerAR.setr(centerAR.r*-1.0);

        aR.add(centerAR);

        return new ShootAction(this.gameContext, aR, false);
    }
}