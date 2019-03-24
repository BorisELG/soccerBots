package Bibi_ElGareh_Team;

import EDU.gatech.cc.is.abstractrobot.SocSmall;
import EDU.gatech.cc.is.util.Vec2;
import java.util.HashMap;
import java.util.Map;

public class AttractionRepulsion {

    GameContext gameContext;

    public AttractionRepulsion(GameContext gameContext) {

        this.gameContext = gameContext;
    }

    public Vec2 getRepulsionPlayer(Vec2 player) {
        Vec2 aR=new Vec2(player);
        aR.setr(-0.1/(player.r*player.r));

        return aR;
    }

    public Vec2 getRepulsionY() {

        Vec2 topAR = new Vec2(0, GameContext.Y_LIMIT - this.gameContext.getPosition().y);
        topAR.setr( -0.1 / (topAR.r * topAR.r) );
        Vec2 bottomAR=new Vec2( 0, - GameContext.Y_LIMIT - this.gameContext.getPosition().y );
        topAR.setr( -0.1 / ( bottomAR.r * bottomAR.r ) );

        topAR.add(bottomAR);

        return topAR;
    }

    public Vec2 getTeamMatesRepulsion(double repulsionValue) {

        Vec2 repulsion = new Vec2(0,0);
        for (Vec2 teammate : this.gameContext.getTeamMates()) {
            repulsion.add(this.getRepulsionPlayer(teammate));
        }
        repulsion.setr(repulsion.r * repulsionValue);
        return repulsion;
    }

    public Vec2 getOpponentRepulsion(double repulsionValue) {

        Vec2 repulsion = new Vec2(0,0);
        for (Vec2 opponentPlayer : this.gameContext.getOpponentsTeamPlayers()) {
            repulsion.add(this.getRepulsionPlayer(opponentPlayer));
        }
        repulsion.setr(repulsion.r * repulsionValue);
        return repulsion;
    }

    public Vec2 getBallAttraction(){

        if (this.gameContext.ballIsInFront()){
            //attraction dans laxe du but ennemi
            Vec2 axe=new Vec2(this.gameContext.getBall());
            axe.sub(this.gameContext.getOpponentGoal());
            Vec2 pA=new Vec2(axe);

            pA.setr(SocSmall.RADIUS-0.02);
            pA.add(this.gameContext.getBall());
            pA.setr(this.getRepulsionY().r*2);
            pA.setr(this.getOpponentRepulsion(2).r);

            return pA;
        }

        else {
            //attraction vers la balle et le but et une repulsion de la balle quand on l'approche
            Vec2 ourGoalAttraction = new Vec2(this.gameContext.getOurGoal());
            Vec2 ballRepulsion = new Vec2(this.gameContext.getBall());

            ourGoalAttraction.setr(( 2.0 / ourGoalAttraction.r ));
            ballRepulsion.setr(( -0.2 / ballRepulsion.r));

            ourGoalAttraction.add(ballRepulsion);
            ourGoalAttraction.add(this.gameContext.getBall());

            return ourGoalAttraction;
        }
    }
}