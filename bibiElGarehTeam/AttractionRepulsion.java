package bibiElGarehTeam;

import EDU.gatech.cc.is.abstractrobot.SocSmall;
import EDU.gatech.cc.is.util.Vec2;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe AttractionRepulsion qui calcule toutes les phénomènes d'attraction et de repulsion
 * propres aux robots
 */
public class AttractionRepulsion {

    GameContext gameContext;

    /**
     * Constructeur de la classe AttractionRepulsion
     * @param gameContext
     */
    public AttractionRepulsion(GameContext gameContext) {

        this.gameContext = gameContext;
    }

    /**
     * Méthode qui calcule la répulsion qu'un joueur émet
     * @param player
     * @return
     */
    public Vec2 getRepulsionPlayer(Vec2 player) {
        Vec2 repulsion = new Vec2(player);
        repulsion .setr(-0.1 / ( player.r * player.r));
        return repulsion ;
    }

    /**
     * Méthode qui calcule la répulsion des bords verticaux du terrain
     * @return Vec2
     */
    public Vec2 getRepulsionY() {

        Vec2 topRepulsion = new Vec2(0, GameContext.Y_LIMIT - this.gameContext.getPosition().y);
        topRepulsion.setr( -0.1 / (topRepulsion.r * topRepulsion.r) );
        Vec2 bottomAR=new Vec2( 0, - GameContext.Y_LIMIT - this.gameContext.getPosition().y );
        topRepulsion.setr( -0.1 / ( bottomAR.r * bottomAR.r ) );

        topRepulsion.add(bottomAR);

        return topRepulsion;
    }

    /**
     * Méthode qui calcule la répulsion des coéquipiers selon un facteur de répulsion
     * @param repulsionValue
     * @return Vec2
     */
    public Vec2 getTeamMatesRepulsion(double repulsionValue) {

        Vec2 repulsion = new Vec2(0,0);
        for (Vec2 teammate : this.gameContext.getTeamMates()) {
            repulsion.add(this.getRepulsionPlayer(teammate));
        }
        repulsion.setr(repulsion.r * repulsionValue);
        return repulsion;
    }

    /**
     * Méthode qui calcule la répulsion des ennemies selon un facteur de répulsion
     * @param repulsionValue
     * @return Vec2 repulsion
     */
    public Vec2 getOpponentRepulsion(double repulsionValue) {

        Vec2 repulsion = new Vec2(0,0);
        for (Vec2 opponentPlayer : this.gameContext.getOpponentsTeamPlayers()) {
            repulsion.add(this.getRepulsionPlayer(opponentPlayer));
        }
        repulsion.setr(repulsion.r * repulsionValue);
        return repulsion;
    }

    /**
     * Retourne l'attraction que la balle émet. Elle diffère si la balle est devant le joueur ou non.
     * @return Vec2
     */
    public Vec2 getBallAttraction(){

        // Si la balle est devant le joueur
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

        // Si la balle est derrière le joueur, une attraction vers le but et une repulsion de la balle
        else {

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