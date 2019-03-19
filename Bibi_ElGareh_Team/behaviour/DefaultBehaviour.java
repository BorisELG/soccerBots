package Bibi_ElGareh_Team.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  Bibi_ElGareh_Team.*;
import Bibi_ElGareh_Team.actions.*;

public class DefaultBehaviour extends Behaviour {

    protected double teammatesFactor=0.4;
    protected double opponentsFactor=1.0;
    protected double centerFactor=-1.0;

    GameContext gameContext;

    /**
     * Constructeur logique.
     *
     * @param soccerMap
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

        Vec2 teammatesAR = attractionRepulsion.getTeamMatesRepulsion(teammatesFactor);
        aR.add(teammatesAR);

        Vec2 opponentsAR = attractionRepulsion.getOpponentRepulsion(opponentsFactor);
        aR.add(opponentsAR);

        Vec2 centerAR = new Vec2(this.gameContext.getPosition());
        centerAR.setr(centerAR.r*centerFactor);

        aR.add(centerAR);

        return new ShootAction(this.gameContext, aR, false);
    }
}