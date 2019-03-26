package bibiElGarehTeam;

import EDU.gatech.cc.is.abstractrobot.SocSmall;
import EDU.gatech.cc.is.util.Vec2;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lounis Bibi et Boris El Gareh
 * @version 1.0
 * Classe GameContext qui décrit toutes les informations relatives au terrain de jeu
 */
public class GameContext {

    // Constantes qui définient nos limites du terrain
    public final static double X_LIMIT = 1.24;
    public final static double Y_LIMIT = 0.7625;

    // Constante qui définie la valeur maximale du temps de blocage
    public static final int LOCK = 300;

    // Constantes qui attribut le nombre de robots pour chaque rôle
    public final static int N_ATTACKERS=3;
    public final static int N_DEFENDERS=1;
    public final static int N_OFFSIDE=1;


    // Robot
    private SocSmall abstract_robot;

    // Attributs du robot
    private int numPlayer;
    private long curr_time;
    private Vec2 axe;
    private Vec2 position;
    private Vec2 ball;
    private Vec2 ourGoal;
    private Vec2 opponentGoal;
    private int countLocked;
    private int side;

    private Vec2 ballPosition;

    /**
     * Constructeur de la classe GameContext
     * @param abstract_robot L'agent robot joueur
     */
    public GameContext(SocSmall abstract_robot) {

        this.abstract_robot = abstract_robot;
        this.countLocked = 0;
    } 

    /**
     * Méthode qui initialise le jeu. Elle est appelée à chaque itération de TakeStep()
     */
    public void init() {
        this.curr_time = this.abstract_robot.getTime();
        this.position= this.abstract_robot.getPosition(curr_time);
        this.ball= this.abstract_robot.getBall(curr_time);
        this.ourGoal = this.abstract_robot.getOurGoal(curr_time);
        this.opponentGoal = this.abstract_robot.getOpponentsGoal(curr_time);

        if(this.ourGoal.x < 0)
            this.side = -1;
        else
            this.side = 1;
    }

    /**
     * Méthode qui permet de savoir si la balle est devant le robot
     * @return true si la balle est devant le robot
     */
    public boolean ballIsInFront() {
        double scalarProduct = this.ball.x * this.ourGoal.x + this.ball.y* this.ourGoal.y;
        if (scalarProduct < 0){
            // la balle est devant le joueur
            return true;
        }
        // la balle est derriere le joueur
        return false;
    }


    /**
     * Méthode qui permet de savoir si il y a des ennemies dans l'axe ou non, selon un intervalle
     * @param axis
     * @param interval
     * @return vrai si il n'y a pas d'ennemie dans l'axe
     */
    public boolean emptyPlayground(Vec2 axis, double interval){

        for (Vec2 ennemy : this.getOpponentsTeamPlayers()) {
            if (Utilities.inAxis(axis.t, ennemy , interval)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui permet de savoir si il y a un coéquipier plus proche du but que le robot
     * @return vrai si il y a un coéquipier plus proche du but que le robot
     */
    public boolean teammateCloserToTheBall(){
        for (Vec2 teammate : this.abstract_robot.getTeammates(this.curr_time)){
            if (Utilities.getDistance(this.ball, teammate).r < this.ball.r) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui permet de savoir si il y a un coéquipier plus proche de la balle que le robot
     * @return vrai si il y a un coéquipier plus proche de la balle que le robot
     */
    public boolean teammateCloserToOurGoal(){
        for (Vec2 teammate : this.abstract_robot.getTeammates(this.curr_time)){
            if (Utilities.getDistance(this.ourGoal, teammate).r < this.ourGoal.r) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui incrémente le compteur quand la balle est bloquée par les joueurs
     */
    public void countLocked() {
        if (!this.isLocked()) {
            countLocked = 0;
        }
        else {
            countLocked++;
        }
        this.ballPosition = Utilities.getAbsolutePosition(this.ball, this.position);
    }

    /**
     * Méthode qui permet de savoir de si la balle est bloquée par les joueurs
     * @return
     */
    public Boolean isLocked(){
        if(this.ballPosition == null){
            return false;
        }
        Vec2 anciennePos = this.ballPosition;
        Vec2 nouvellePos = Utilities.getAbsolutePosition(this.ball, this.position);

        return  Utilities.equalsPosition(anciennePos, nouvellePos, 0.04);

    }

    /******** Accesseurs  ********/

    /**
     * @return the abstract_robot
     */
    public SocSmall getAbstractRobot() {
        return abstract_robot;
    }

    /**
     * @return the ball
     */
    public Vec2 getBall() {
        return ball;
    }

    /**
     * @return the curr_time
     */
    public long getCurrTime() {
        return curr_time;
    }
    
    /**
     * @return the numPlayer
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    public Vec2[] getTeamMates() { return abstract_robot.getTeammates(curr_time); }

    public Vec2[] getOpponentsTeamPlayers() {
        return abstract_robot.getOpponents(curr_time);
    }

    /**
     * @return the opponentGoal
     */
    public Vec2 getOpponentGoal() {
        return opponentGoal;
    }

    /**
     * @return the ourGoal
     */
    public Vec2 getOurGoal() {
        return ourGoal;
    }

    /**
     * @return the position
     */
    public Vec2 getPosition() {
        return position;
    }

    /**
     * @return le coté de notre équipe
     */
    public int getSide() {return this.side;}

    /**
     * @return le compteur de blocage
     */
    public int getCountLocked() {return this.countLocked;}

    /********* Modificateurs ***********/

    /**
     * Méthode qui modifie la valeur du compteur de blocage
     * @param value
     */
    public void setCountLocked(int value) {this.countLocked = value;}
}