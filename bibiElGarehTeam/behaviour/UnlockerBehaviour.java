package bibiElGarehTeam.behaviour;

import	EDU.gatech.cc.is.util.Vec2;
import	EDU.gatech.cc.is.abstractrobot.*;
import  bibiElGarehTeam.*;
import bibiElGarehTeam.actions.*;

/**
 * Comportement de déblocage du jeu. Si le jeu est trop longtemps bloqué, les joueurs s'écartent
 * tous du ballon pendant une courte période, comme s'ils étaient "effrayés"
 */
public class UnlockerBehaviour extends Behaviour {

    GameContext gameContext;

    /**
     * Constructeur de la cloasse UnlockerBehaviour
     * @param gameContext
     */
    public UnlockerBehaviour(GameContext gameContext)
    {
        this.gameContext = gameContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActivated() {
        if (this.gameContext.getCountLocked() > GameContext.LOCK+25) {
            gameContext.setCountLocked(0);
        }

         return (this.gameContext.getCountLocked() > GameContext.LOCK);
    }

    /**
     * {@inheritDoc}
     */
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